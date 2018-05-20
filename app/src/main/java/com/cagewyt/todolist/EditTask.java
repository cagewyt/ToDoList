package com.cagewyt.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTask extends AppCompatActivity {
    private String taskKey;
    private DatabaseReference databaseReference;
    private EditText editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskKey = getIntent().getExtras().getString("TaskId");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");
        editTask = (EditText)findViewById(R.id.editTaskName);

        databaseReference.child(taskKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String taskTitle = (String) dataSnapshot.child("name").getValue();
                editTask.setText(taskTitle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void editButtonClicked(View view) {
        String taskName = editTask.getText().toString();
        databaseReference.child(taskKey).child("name").setValue(taskName);

        Intent mainActivity = new Intent(EditTask.this, MainActivity.class);
        startActivity(mainActivity);
    }
}
