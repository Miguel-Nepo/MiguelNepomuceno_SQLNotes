package com.example.nepomucenom6435.mycontactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydB = new DatabaseHelper(this);
        Log.d("MyContactApp","MainActivity: instantiated myDb");
    }


}
