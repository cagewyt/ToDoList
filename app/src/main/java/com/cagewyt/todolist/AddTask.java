package com.cagewyt.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTask extends AppCompatActivity {

    private EditText editTask;

    private FirebaseDatabase database;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        database = FirebaseDatabase.getInstance();
    }

    public void addButtonClicked(View view) {
        editTask = (EditText)findViewById(R.id.editTask);

        String taskName = editTask.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy hh:mm a");

        String dateString = sdf.format(new Date());

        databaseReference = database.getInstance().getReference().child("Tasks");
        DatabaseReference newTask = databaseReference.push();
        newTask.child("name").setValue(taskName);
        newTask.child("time").setValue(dateString);


        Intent mainActivity = new Intent(AddTask.this, MainActivity.class);
        startActivity(mainActivity);
    }
}
