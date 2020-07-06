package cms.app.cms.teacher.model;

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


public class UserAdapter extends ArrayAdapter<UserSummary> {
    int resId;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<UserSummary> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserSummary summary = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, parent, false);
        TextView textViewUserId = view.findViewById(R.id.textview_whochoosecourse_id);
        TextView textViewUserName = view.findViewById(R.id.textview_whochoosecourse_name);


        textViewUserId.setText(summary.userid);
        textViewUserName.setText(summary.username);
        return view;
    }
}
