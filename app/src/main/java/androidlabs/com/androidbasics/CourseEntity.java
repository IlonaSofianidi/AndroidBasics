package androidlabs.com.androidbasics;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CourseEntity implements Parcelable {
    private int course_id;
    private String course_subject;
    private String course_instructor;
    private String credits;
    private String schedule;
    private String student_grade;


    public CourseEntity() {
    }

    public CourseEntity(String course_subject, String course_instructor, String credits, String schedule, String student_grade) {
        this.course_subject = course_subject;
        this.course_instructor = course_instructor;
        this.credits = credits;

        this.schedule = schedule;
        this.student_grade = student_grade;
    }

    public int getCourse_id() {

        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_subject() {
        return course_subject;
    }

    public void setCourse_subject(String course_subject) {
        this.course_subject = course_subject;
    }

    public String getCourse_instructor() {
        return course_instructor;
    }

    public void setCourse_instructor(String course_instructor) {
        this.course_instructor = course_instructor;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getStudent_grade() {
        return student_grade;
    }

    public void setStudent_grade(String student_grade) {
        this.student_grade = student_grade;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Log.d("myLog", "writeToParcel");

        parcel.writeInt(course_id);
        parcel.writeString(course_subject);
        parcel.writeString(course_instructor);
        parcel.writeString(credits);
        parcel.writeString(schedule);
        parcel.writeString(student_grade);
    }



    public static final Parcelable.Creator<CourseEntity> CREATOR = new Creator<CourseEntity>() {
        @Override
        public CourseEntity createFromParcel(Parcel parcel) {
            Log.d("myLog", "createFromParcel");
            return new CourseEntity(parcel);
        }

        @Override
        public CourseEntity[] newArray(int i) {
            return new CourseEntity[i];
        }
    };
    public CourseEntity(Parcel in) {
        Log.d("myLog","Read From Parcel");
        this.course_id = in.readInt();
        this.course_subject = in.readString();
        this.course_instructor = in.readString();
        this.credits = in.readString();
        this.schedule = in.readString();
        this.student_grade = in.readString();
    }
}
