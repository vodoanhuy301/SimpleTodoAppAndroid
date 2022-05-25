package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.project.Adapter.todoAdapter;
import com.example.project.Model.todoItem;
import com.example.project.Util.DB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private RecyclerView myRecyclerView;
    private FloatingActionButton btnAdd;
    private DB db;
    private List<todoItem> myList;
    private todoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView= findViewById(R.id.recylerView);
        btnAdd=findViewById(R.id.btnAdd);
        db = new DB(MainActivity.this);
        myList = new ArrayList<>();
        adapter = new todoAdapter(db,MainActivity.this);

        myList = db.getAllItem();
        Collections.reverse(myList);
        adapter.setList(myList);

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addNewItem.newInstance().showNow(getSupportFragmentManager(), addNewItem.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(myRecyclerView);

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        myList = db.getAllItem();
        Collections.reverse(myList);
        adapter.setList(myList);
        adapter.notifyDataSetChanged();

    }
}