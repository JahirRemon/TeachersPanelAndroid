package reverb.smartstudy.teacher.responseModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mdjahirulislam on 06/01/18.
 */

public class CourseStudentAttendanceModel {


    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Datum {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("students")
        @Expose
        private List<Student> students = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }

        public static class Student {

            @SerializedName("userid")
            @Expose
            private Integer userid;
            @SerializedName("classroll")
            @Expose
            private String classroll;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("imagePath")
            @Expose
            private String imagePath;
            @SerializedName("lastPresentDate")
            @Expose
            private String lastPresentDate;
            @SerializedName("totalPresent")
            @Expose
            private Integer totalPresent;

            public Integer getUserid() {
                return userid;
            }

            public void setUserid(Integer userid) {
                this.userid = userid;
            }

            public String getClassroll() {
                return classroll;
            }

            public void setClassroll(String classroll) {
                this.classroll = classroll;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getLastPresentDate() {
                return lastPresentDate;
            }

            public void setLastPresentDate(String lastPresentDate) {
                this.lastPresentDate = lastPresentDate;
            }

            public Integer getTotalPresent() {
                return totalPresent;
            }

            public void setTotalPresent(Integer totalPresent) {
                this.totalPresent = totalPresent;
            }
        }

    }
}
