package com.cagewyt.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

public class SingleTask extends AppCompatActivity {
    private String taskKey;
    private TextView singleTask;
    private TextView singleTaskTime;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        taskKey = getIntent().getExtras().getString("TaskId");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");
        singleTask = (TextView)findViewById(R.id.singleTask);
        singleTaskTime = (TextView)findViewById(R.id.singleTaskTime);

        databaseReference.child(taskKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String taskTitle = (String) dataSnapshot.child("name").getValue();
                String taskTime = (String) dataSnapshot.child("time").getValue();
                singleTask.setText(taskTitle);
                singleTaskTime.setText(taskTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteTask(View view) {
        databaseReference.child(taskKey).removeValue();

        Intent mainActivity = new Intent(SingleTask.this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void updateTask(View view) {
        Intent editTask = new Intent(SingleTask.this, EditTask.class);
        editTask.putExtra("TaskId", taskKey);
        startActivity(editTask);
    }
}
