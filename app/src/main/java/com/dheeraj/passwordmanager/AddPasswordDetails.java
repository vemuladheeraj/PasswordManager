package com.dheeraj.passwordmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AddPasswordDetails extends AppCompatActivity {
    TextView username;
    TextView password;
    TextView domain;
    TextView url;
    TextView application;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_add_password_details);

//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
            username = (TextView) findViewById(R.id.usernameNewData);//this is a comment
            password = (TextView) findViewById(R.id.passwordNewData);
            data = (TextView) findViewById(R.id.appNameNewData);
            domain = (TextView) findViewById(R.id.domainNewDta);
            url = (TextView) findViewById(R.id.urlAddNewData);
            ImageView save = (ImageView) findViewById(R.id.saveData);
            ImageView close = (ImageView) findViewById(R.id.CancelData);
            ImageView upload = (ImageView) findViewById(R.id.uploadPasswords);
            final DBHelper dbHelper = new DBHelper(this);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!application.getText().toString().matches("") && !username.getText().toString().matches("") && !password.getText().toString().matches("")) {
                        boolean sucess = dbHelper.addData(application.getText().toString(), username.getText().toString(), password.getText().toString(), domain.getText().toString()!=""?domain.getText().toString():"", url.getText().toString()!=""?url.getText().toString():"");
                        Toast toast = null;
                        if (sucess) {
                            toast = Toast.makeText(getApplicationContext(), "Data saved successfully...", Toast.LENGTH_SHORT);
                            username.setText(null);
                            application.setText(null);
                            password.setText(null);
                            domain.setText(null);
                            url.setText(null);
                        } else {
                            toast = Toast.makeText(getApplicationContext(), "Same App name Exists, please add some text after app name to make it unique..", Toast.LENGTH_SHORT);
                        }

                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Please Enter all details", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
//                    Intent intent = new Intent(AddPasswordDetails.this, MainActivity.class);
//                    startActivity(intent);
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "This functionality implementation is in progress..", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        } catch (Exception ex) {
            Log.d("Error", "onCreate: " + ex);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
