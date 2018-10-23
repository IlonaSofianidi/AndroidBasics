package androidlabs.com.androidbasics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    List<CourseEntity> courseEntities_list = null;

    public CourseAdapter(Activity context, int layoutResourceId, List<CourseEntity> courseEntities_list) {
        super(context, layoutResourceId, courseEntities_list);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.courseEntities_list = courseEntities_list;
    }

    public void updateCourseList(List<CourseEntity> newlist) {
        courseEntities_list = newlist;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        CourseEntityyHolder holder = null;
        if (listItemView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            listItemView = layoutInflater.inflate(layoutResourceId, parent, false);

            holder = new CourseEntityyHolder();
            holder.course_subject = (TextView) listItemView.findViewById(R.id.subject_textView);
            holder.course_instructor = (TextView) listItemView.findViewById(R.id.instructor_textView);
            holder.credits = (TextView) listItemView.findViewById(R.id.credits_textView);
            holder.schedule = (TextView) listItemView.findViewById(R.id.schedule_day_textView);
            holder.students_grade = (TextView) listItemView.findViewById(R.id.grade_textView);

            listItemView.setTag(holder);


        } else {
            holder = (CourseEntityyHolder) listItemView.getTag();
        }
        CourseEntity courseEntity = courseEntities_list.get(position);
        holder.course_subject.setText(courseEntity.getCourse_subject());
        holder.course_instructor.setText(courseEntity.getCourse_instructor());
        holder.credits.setText(courseEntity.getCredits());
        holder.schedule.setText(courseEntity.getSchedule());
        holder.students_grade.setText(courseEntity.getStudent_grade());


        return listItemView;
    }

    static class CourseEntityyHolder {
        TextView course_subject;
        TextView course_instructor;
        TextView credits;
        TextView schedule;
        TextView students_grade;

    }
}


