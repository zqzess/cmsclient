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

import cms.app.R;
import cms.app.cms.model.CourseInfo;

public class AllElectiveCourseAdapter extends ArrayAdapter<CourseInfo> {
    int resId;

    public AllElectiveCourseAdapter(Context context, int resource, List<CourseInfo> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseInfo courseInfo = getItem(position);
        View v = LayoutInflater.from(getContext()).inflate(resId, parent, false);
        TextView textViewId = v.findViewById(R.id.textview_select_course_id);
        TextView textViewName = v.findViewById(R.id.textview_select_course_name);
        if (courseInfo.id.isEmpty()) {
            textViewId.setText("");
            textViewName.setText("");
        } else {
            textViewId.setText(courseInfo.id);
            textViewName.setText(courseInfo.name);
        }

        return v;
    }
}
