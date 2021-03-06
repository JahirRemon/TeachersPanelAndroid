package reverb.smartstudy.teacher.Fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mdjahirulislam.youtubestyletabs.R;

import reverb.smartstudy.teacher.Activity.attendance.adapter.CustomAttendanceClassScheduleListRecyclerViewAdapter;
import reverb.smartstudy.teacher.contentprovider.RequestProvider;
import reverb.smartstudy.teacher.database.AttendanceClassScheduleTableItems;
import reverb.smartstudy.teacher.preference.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceClassScheduleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = AttendanceClassScheduleFragment.class.getSimpleName();

    public final int offset = 30;
    private int page = 0;
    private Handler handlerToWait = new Handler();
    private boolean loadingMore = false;


    private Context context;
    private TextView fragmentCourseNameTV;
    private TextView fragmentClassScheduleListNotFoundTV;
    private CustomAttendanceClassScheduleListRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String courseID;


    public AttendanceClassScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate( R.layout.fragment_attedance_class_schedule, container, false );
        mRecyclerView = (RecyclerView) view.findViewById( R.id.attendanceClassScheduleListRV );

        context = getActivity();
        mAdapter = new CustomAttendanceClassScheduleListRecyclerViewAdapter( context, null );
        mLayoutManager = new LinearLayoutManager( context );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mRecyclerView.setAdapter( mAdapter );

        fragmentCourseNameTV = (TextView) view.findViewById( R.id.fragmentClassScheduleCourseNameTV );
        fragmentClassScheduleListNotFoundTV = (TextView) view.findViewById( R.id.fragmentAttendanceNoClassScheduleTV );
        courseID =  SharedPref.getInstance( context ).getCourseId();

        fragmentCourseNameTV.setText( SharedPref.getInstance( context ).getCourseName() );

        getActivity().getSupportLoaderManager().restartLoader( 1, null, AttendanceClassScheduleFragment.this );


        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 1:
                return new CursorLoader( context, RequestProvider.urlForAttendanceClassScheduleListItems( offset * page ),
                        null, AttendanceClassScheduleTableItems.COURSE_ID + "=? ", new String[]{courseID}, null );

            default:
                throw new IllegalArgumentException( "no id handled!" );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 1:
                if (data.getCount() > 0) {
                    fragmentClassScheduleListNotFoundTV.setVisibility( View.GONE );
                    //shortToast.setText("loading MORE " + page);
                    if (page != 0) {
                        // shortToast.setText("loading more data");
                        //shortToast.show();
                    }

                    Cursor cursor = ((CustomAttendanceClassScheduleListRecyclerViewAdapter) mRecyclerView.getAdapter()).getCursor();

                    //fill all existing in adapter
                    MatrixCursor mx = new MatrixCursor( AttendanceClassScheduleTableItems.Columns );
                    fillMx( cursor, mx );

                    //fill with additional result
                    fillMx( data, mx );

                    ((CustomAttendanceClassScheduleListRecyclerViewAdapter) mRecyclerView.getAdapter()).swapCursor( mx );

                    handlerToWait.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            loadingMore = false;
                        }
                    }, 1000 );
                } else {
                    fragmentClassScheduleListNotFoundTV.setVisibility( View.VISIBLE );
                }

                break;
            default:
                throw new IllegalArgumentException( "no loader id handled!" );
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void fillMx(Cursor data, MatrixCursor mx) {
        if (data == null)
            return;

        data.moveToPosition( -1 );
        while (data.moveToNext()) {
            mx.addRow( new Object[]{
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems._ID ) ),
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems.CLASS_SCHEDULE_ID ) ),
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems.COURSE_ID ) ),
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems.CLASS_SCHEDULE_DATE ) ),
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems.TOTAL_PRESENT_STUDENT_NUMBER ) ),
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems.CLASS_DAY ) ),
                    data.getString( data.getColumnIndex( AttendanceClassScheduleTableItems.CLASS_TIME ) ),
            } );
        }
    }

}
