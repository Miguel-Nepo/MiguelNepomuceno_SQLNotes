package com.example.nepomucenom6435.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editNameText);
        editPhone = findViewById(R.id.editPhoneText);
        editAddress = findViewById(R.id.editAddressText);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");
    }

    public void addData(View view) {
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString());
        if (isInserted == true) {
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor");

        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("name " + res.getString(1) + "\n");
            buffer.append("phone number " + res.getString(2) + "\n");
            buffer.append("address " + res.getString(3) + "\n");
            buffer.append("\n");
            //Delimit each of the "appends" with line feed "\n"
        }

        showMessage("Data", buffer.toString());

    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.nepomucenom6435.mycontactapp.MESSAGE";

    public void SearchRecord(View view) {
        Log.d("MyContactApp", "MainActivity: showMessage: launching SearchActivity");
        android.content.Intent intent = new android.content.Intent(this, SearchActivity.class);

        StringBuffer buffer = new StringBuffer();
        Cursor res = myDb.getAllData();
        while (res.moveToNext()) {
            if (editName.getText().toString().equals(res.getString(1))) {
                buffer.append("name " + res.getString(1) + "\n");
                buffer.append("phone number " + res.getString(2) + "\n");
                buffer.append("address " + res.getString(3) + "\n");
                buffer.append("\n");
                //Delimit each of the "appends" with line feed "\n"
            }
        }
        if (buffer.toString().equals("")){
            intent.putExtra(EXTRA_MESSAGE,"No Contacts Found");
        }
        else{
            intent.putExtra(EXTRA_MESSAGE, buffer.toString());
        }
        startActivity(intent);
    }
}
