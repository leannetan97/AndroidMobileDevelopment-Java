package com.layyan.sqlexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private UserRecord userRecord;
    private EditText editTextPhone, editTextName, editTextEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        userRecord = getIntent().getParcelableExtra("UserRecord");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        editTextName.setText(userRecord.getName());
        editTextPhone.setText(userRecord.getPhone());
        editTextEmail.setText(userRecord.getEmail());
    }

    public void updateRecord(View view) {
        String phone, name, email;

        name = editTextName.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.error_name));
            return;
        }

        phone = editTextPhone.getText().toString();
        if (phone.isEmpty()) {
            editTextPhone.setError(getString(R.string.error_phone));
            return;
        }

        email = editTextEmail.getText().toString();
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.error_email));
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.error_invalid_email));
            return;
        }

        userRecord.setName(name);
        userRecord.setEmail(email);
        userRecord.setPhone(phone);

        UserSQLHelper userDataSource = new UserSQLHelper(this);
        userDataSource.updateUser(userRecord);
        finish();
    }

    public void cancelEdit(View view) {
        finish();
    }
}
