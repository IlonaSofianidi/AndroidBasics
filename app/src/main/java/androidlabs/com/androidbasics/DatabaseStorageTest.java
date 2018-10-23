package androidlabs.com.androidbasics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class DatabaseStorageTest extends ListActivity {
    CourseAdapter courseAdapter;
    ListView listView;
    boolean deleteClicked = false;
    List<CourseEntity> course_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage_test);
        new UpdateListTask().execute();


    }

    @Override
    protected void onRestart() {
        new UpdateListTask().execute();
        super.onRestart();
    }

    private class UpdateListTask extends AsyncTask<Void, Void, List<CourseEntity>> {


        @Override
        protected List<CourseEntity> doInBackground(Void... voids) {
            DatabaseSchedule database = new DatabaseSchedule(DatabaseStorageTest.this);
            course_list = database.getAllCourses();
            return course_list;
        }

        @Override
        protected void onPreExecute() {
            if (listView == null) {
                listView = getListView();
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<CourseEntity> courseEntities) {
            if (courseAdapter != null) {
                courseAdapter.clear();
                courseAdapter.addAll(courseEntities);
                courseAdapter.notifyDataSetChanged();
            } else {
                courseAdapter = new CourseAdapter(DatabaseStorageTest.this, R.layout.list_item_view, courseEntities);

                listView.setAdapter(courseAdapter);
            }
            super.onPostExecute(courseEntities);
        }
    }

    private class DeleteTask extends AsyncTask<CourseEntity, Void, Void> {
        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {
            CourseEntity courseEntity = courseEntities[0];
            CourseDao courseDao = new DatabaseSchedule(DatabaseStorageTest.this);
            courseDao.deleteCourse(courseEntity);
            return null;
        }


    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        CourseEntity courseEntity = (CourseEntity) courseAdapter.getItem(position);
        if (deleteClicked) {
            new DeleteTask().execute(courseEntity);
            courseAdapter.remove(courseEntity);
            courseAdapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(this, CourseActivity.class);
            intent.putExtra(CourseActivity.EXTRA_COURSE, courseEntity);

            startActivity(intent);
        }
    }

    public void onClick_buttonDelete(View view) {
        Button button = (Button) findViewById(R.id.button_deleteCourse);
        if (deleteClicked == false) {
            deleteClicked = true;

            button.setTextColor(-65536);
        } else {
            deleteClicked = false;
            button.setTextColor(-16777216);
        }
    }

    public void onClick_buttonAdd(View view) {
        Intent intent = new Intent(this, AddCourseActivity.class);

        startActivity(intent);
    }
}
