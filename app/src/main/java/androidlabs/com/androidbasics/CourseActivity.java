package androidlabs.com.androidbasics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class CourseActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE = "courseEntity";
    CourseDao courseDB;
    private TextView subject;
    private TextView instructor;
    private TextView credits;
    private TextView schedule;
    private TextView grade;
    private TextView id_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initializeViews();

        CourseEntity courseEntity = (CourseEntity) getIntent().getParcelableExtra(EXTRA_COURSE);
        Log.e("Eror", String.valueOf(courseEntity.getCourse_id()));
        subject.setText(courseEntity.getCourse_subject());
        instructor.setText(courseEntity.getCourse_instructor());
        credits.setText(courseEntity.getCredits());
        schedule.setText(courseEntity.getSchedule());
        grade.setText(courseEntity.getStudent_grade());
        id_course.setText(String.valueOf(courseEntity.getCourse_id()));
    }
    private void initializeViews(){
        subject = (TextView) findViewById(R.id.textViewCourse_subject);
        instructor = (TextView) findViewById(R.id.textViewCourse_instructor);
        credits = (TextView) findViewById(R.id.textViewCourse_credits);
        schedule = (TextView) findViewById(R.id.textViewCourse_schedule);
        grade = (TextView) findViewById(R.id.textViewCourse_grade);
        id_course = (TextView) findViewById(R.id.textViewCourse_id);
    }


    public void updateButtonOnClick(View view) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourse_subject(String.valueOf(subject.getText()));
        courseEntity.setCourse_instructor(String.valueOf(instructor.getText()));
        courseEntity.setCredits(String.valueOf(credits.getText()));
        courseEntity.setSchedule(String.valueOf(schedule.getText()));
        courseEntity.setStudent_grade(String.valueOf(grade.getText()));

        courseEntity.setCourse_id(Integer.parseInt(String.valueOf(id_course.getText())));
        new UpdateCourseClass().execute(courseEntity);


    }

    private class UpdateCourseClass extends AsyncTask<CourseEntity, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CourseEntity... courseEntities) {
            courseDB = new DatabaseSchedule(CourseActivity.this);
            CourseEntity courseEntity = courseEntities[0];
            courseDB.updateCourse(courseEntity, courseEntity.getCourse_id());
            return true;
        }
    }
}




