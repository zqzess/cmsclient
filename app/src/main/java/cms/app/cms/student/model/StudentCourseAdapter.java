package cms.app.cms.student.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cms.app.cms.model.CourseInfo;
import cms.app.R;

public class StudentCourseAdapter extends ArrayAdapter<CourseInfo> {
    int resId;

    public StudentCourseAdapter(Context context, int resource, List<CourseInfo> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseInfo courseInfo = getItem(position);
        View v = LayoutInflater.from(getContext()).inflate(resId, parent, false);
        TextView textViewId = v.findViewById(R.id.textview_student_course_id);
        TextView textViewName = v.findViewById(R.id.textview_student_course_name);
        TextView textViewType = v.findViewById(R.id.textview_student_course_type);
        textViewId.setText(courseInfo.id);
        textViewName.setText(courseInfo.name);
        if (courseInfo.type == 0) {
            textViewType.setText("必修");
        } else if (courseInfo.type == 1) {
            textViewType.setText("选修");
        }
        return v;
    }
}
