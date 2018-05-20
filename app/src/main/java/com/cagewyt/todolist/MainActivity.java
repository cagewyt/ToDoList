package com.cagewyt.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskListView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskListView = (RecyclerView)findViewById(R.id.task_list);
        taskListView.setHasFixedSize(true);
        taskListView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        TextView bannerDay = (TextView)findViewById(R.id.bannerDay);
        Date d = new Date();
        String dayOfWeek = sdf.format(d);
        bannerDay.setText(dayOfWeek);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM MM dd, yyy h:mm a");
        TextView bannerDate = (TextView)findViewById(R.id.bannerDate);
        String dateString = sdf2.format(d);
        bannerDate.setText(dateString);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Task, TaskViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.task_row,
                TaskViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {
                final String taskKey = getRef(position).getKey().toString();

                viewHolder.setName(model.getName());
                viewHolder.setTime(model.getTime());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleTaskActivity = new Intent(MainActivity.this, SingleTask.class);
                        singleTaskActivity.putExtra("TaskId", taskKey);
                        startActivity(singleTaskActivity);
                    }
                });
            }
        };

        taskListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TaskViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name)
        {
            TextView taskName = (TextView)mView.findViewById(R.id.taskName);
            taskName.setText(name);
        }

        public void setTime(String time)
        {
            TextView taskName = (TextView)mView.findViewById(R.id.taskTime);
            taskName.setText(time);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.addTask) {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
