package cms.app.cms.admin.model;

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
import cms.app.cms.admin.model.UserApprovalSummary;

public class ApprovalAdapter extends ArrayAdapter<UserApprovalSummary> {
    int resId;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserApprovalSummary summary = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, parent, false);
        TextView textViewType = view.findViewById(R.id.list_item_approval_type);
        TextView textViewUserId = view.findViewById(R.id.list_item_approval_userid);
        TextView textViewUserName = view.findViewById(R.id.list_item_approval_username);

        if (summary.type == 0) {
            textViewType.setText("学生");
        } else if (summary.type == 1) {
            textViewType.setText("教师");
        }

        if (summary.userid.isEmpty()) {
            textViewUserId.setText("");
            textViewUserName.setText("");
        } else {
            textViewUserId.setText(summary.userid);
            textViewUserName.setText(summary.username);
        }

        return view;

    }

    public ApprovalAdapter(@NonNull Context context, int resource, @NonNull List<UserApprovalSummary> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }
}
