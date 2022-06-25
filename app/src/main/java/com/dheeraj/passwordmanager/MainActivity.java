package com.dheeraj.passwordmanager;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private boolean authenticationSucess = true;
    private RecyclerView mainRecyclerView;
    public ArrayList<Model> list;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            addButton=findViewById(R.id.fab);
            Executor newExecutor = Executors.newSingleThreadExecutor();
            FragmentActivity activity = this;


            final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {

                @Override
                //onAuthenticationError is called when a fatal error occurrs//
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    // finish();

                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    //Print a message to Logcat//
                    Log.d(TAG, "Fingerprint recognised successfully");
                    authenticationSucess = true;

                }

                //onAuthenticationFailed is called when the fingerprint doesn’t match//
                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    //Print a message to Logcat//
                    Toast toast = Toast.makeText(getApplicationContext(), "Hahahaha, Dont try to login, you are not the right owner of this device", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    Log.d(TAG, "Fingerprint not recognised");
                }
            });
            //Create the BiometricPrompt instance//
            final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    //Add some text to the dialog//
                    .setTitle("Please authenticate using biometric")
                    .setSubtitle("Finger print sensor active")
                    .setDescription("Unless you are the sole owner of this mobile you'll not be able to access the contents in this app! Hopefully :D")
                    .setNegativeButtonText("Hastala Vista")

                    //Build the dialog//
                    .build();
            myBiometricPrompt.authenticate(promptInfo);
            if (authenticationSucess) {
                final DBHelper dbHelper = new DBHelper(getApplicationContext());
                list = dbHelper.getAllData();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

               MyListAdapter adapter = new MyListAdapter( getApplicationContext(),list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);

                RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);


            }
            addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(MainActivity.this, AddPasswordDetails.class);
                       // startActivityForResult(intent, 0);
                        Intent chooserIntent=Intent.createChooser(intent,"Add Details Intent sender");
                        startActivity(chooserIntent);
                    } catch (Exception ex) {
                        Log.d("Error", "onClick: " + ex);
                    }
                }
            });

        } catch (Exception ex) {
            Log.d(TAG, "onCreate: Exception " + ex);
        }
    }

}