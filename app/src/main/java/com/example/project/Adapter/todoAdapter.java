package com.example.project.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.MainActivity;
import com.example.project.Model.todoItem;
import com.example.project.R;
import com.example.project.Util.DB;
import com.example.project.addNewItem;

import java.util.List;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.MyViewHolder> {

    private List<todoItem> list;
    private MainActivity activity;
    private DB db;

    public todoAdapter(DB db, MainActivity activity){
        this.activity=activity;
        this.db=db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    final todoItem i = list.get(position);
    holder.cbx.setText(i.getItem());
    holder.cbx.setChecked(toBoolean(i.getStatus()));
    holder.cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                db.updateItemStatus(i.getId(), 1);
            }
            else db.updateItemStatus(i.getId(), 0);
        }
    });
    }
    public Context getContext(){
        return activity;
    }
    public void setList(List<todoItem> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public void deleteItem(int pos){
        todoItem i = list.get(pos);
        db.deleteItem(i.getId());
        list.remove(pos);
        notifyItemRemoved(pos);
    }
    public void editItem(int pos){
        todoItem i = list.get(pos);

        Bundle bundle = new Bundle();
        bundle.putInt("id", i.getId());
        bundle.putString("item", i.getItem());

        addNewItem itemNew = new addNewItem();
        itemNew.setArguments(bundle);
        itemNew.show(activity.getSupportFragmentManager(),itemNew.getTag());
    }
    private boolean toBoolean(int intValue){
        if (intValue >= 1) {
           return true;
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox cbx;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbx = itemView.findViewById(R.id.checkbox);
        }
    }
}
