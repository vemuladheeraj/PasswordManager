package com.dheeraj.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PasswordEditActivity extends AppCompatActivity {
    TextView username;
    TextView password;
    TextView domain;
    TextView url;
    TextView application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit);
        username = (TextView) findViewById(R.id.usernameEditData);
        password = (TextView) findViewById(R.id.passwordEditData);
        application = (TextView) findViewById(R.id.appNameEditData);
        domain = (TextView) findViewById(R.id.domainEditDta);
        url = (TextView) findViewById(R.id.urlAddEditData);
        ArrayList<String> updateToBeDoneData = getIntent().getStringArrayListExtra("CurrentViewValuesForUpdate");

        username.setText(updateToBeDoneData.get(4));
        url.setText(updateToBeDoneData.get(3));
        domain.setText(updateToBeDoneData.get(2));
        application.setText(updateToBeDoneData.get(1));
        password.setText(updateToBeDoneData.get(0));


        ImageView update = (ImageView) findViewById(R.id.updateData);
        ImageView close = (ImageView) findViewById(R.id.CloseBtn);
        final DBHelper dbHelper = new DBHelper(this);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sucess = dbHelper.updateData(application.getText().toString(), username.getText().toString(), password.getText().toString(), domain.getText().toString() != "" ? domain.getText().toString() : "", url.getText().toString() != "" ? url.getText().toString() : "");
                Toast toast = null;
                if (sucess) {
                    toast = Toast.makeText(getApplicationContext(), "Data updated successfully...", Toast.LENGTH_SHORT);

                }else{
                    toast = Toast.makeText(getApplicationContext(), "There was some error while updating.", Toast.LENGTH_SHORT);

                }
                toast.show();
                finish();
//                Intent intent = new Intent(PasswordEditActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(PasswordEditActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }
}