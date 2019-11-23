package com.layyan.sqlexample;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class UserRecordAdapter extends ArrayAdapter<UserRecord>{
    private  UserRecord currentUserRecord;
    private List<UserRecord> list;
    public UserRecordAdapter(Activity context, int resource, List<UserRecord> list) {
        super(context, resource, list);
        this.list = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        currentUserRecord = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_record, parent,
                    false);
        }

        TextView textViewPhone, textViewName, textViewEmail;

        textViewPhone = (TextView) convertView.findViewById(R.id.textViewPhone);
        textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);

        textViewName.setText(currentUserRecord.getName());
        textViewPhone.setText(convertView.getResources().getString(R.string.phone_label)+ currentUserRecord.getPhone());
        textViewEmail.setText(convertView.getResources().getString(R.string.email_label)+ currentUserRecord.getEmail());

        ((Button)convertView.findViewById(R.id.btn_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(currentUserRecord.getId());
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        ((Button)convertView.findViewById(R.id.btn_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEditMode();
            }
        });
        return convertView;

    }

    private void deleteItem(String id){
        UserSQLHelper userDataSource = new UserSQLHelper(getContext());
        userDataSource.deleteUser(id);
}

    private void navigateToEditMode(){
        Intent toEditActivity = new Intent(getContext(),EditActivity.class);
        toEditActivity.putExtra("UserRecord",currentUserRecord);
        getContext().startActivity(toEditActivity);
    }
}
