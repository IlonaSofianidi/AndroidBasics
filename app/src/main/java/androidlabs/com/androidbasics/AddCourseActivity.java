package androidlabs.com.androidbasics;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddCourseActivity extends AppCompatActivity {
    private TextView subject;
    private  TextView instructor;
    private TextView credits;
    private  TextView schedule;
    private TextView grade;
    private  TextView id_course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initializeViews();

        subject.setHint("Subject name");
        instructor.setHint("Instructor name");
        credits.setHint("Credits");
        schedule.setHint("Day of the week");
        grade.setHint("Grade");
        id_course.setText("Auto");


    }
    private void initializeViews(){
        subject = (TextView) findViewById(R.id.textViewCourse2_subject);
        instructor = (TextView) findViewById(R.id.textViewCourse2_instructor);
        credits = (TextView) findViewById(R.id.textViewCourse2_credits);
        schedule = (TextView) findViewById(R.id.textViewCourse2_schedule);
        grade = (TextView) findViewById(R.id.textViewCourse2_grade);
        id_course = (TextView)findViewById(R.id.textViewCourse2_id);
    }

    public void addButtonOnClick(View view) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourse_subject(String.valueOf(subject.getText()));
        courseEntity.setCourse_instructor(String.valueOf(instructor.getText()));
        courseEntity.setCredits(String.valueOf(credits.getText()));
        courseEntity.setSchedule(String.valueOf(schedule.getText()));
        courseEntity.setStudent_grade(String.valueOf(grade.getText()));

        new AddTask().execute(courseEntity);
    }
    private class AddTask extends AsyncTask<CourseEntity,Void,Integer>{

        @Override
        protected Integer doInBackground(CourseEntity... courseEntities) {
            CourseEntity courseEntity = courseEntities[0];
            CourseDao courseDao = new DatabaseSchedule(AddCourseActivity.this);
            int id = (int)courseDao.addCourse(courseEntity);
            return id;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            id_course.setText(String.valueOf(integer));
            super.onPostExecute(integer);
        }
    }
}
