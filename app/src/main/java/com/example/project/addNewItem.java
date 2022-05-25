package com.example.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.Model.todoItem;
import com.example.project.Util.DB;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class addNewItem extends BottomSheetDialogFragment {

    public   static  final String TAG= "AddNewItem";

    private EditText myText;
    private Button btnSave;

    private DB db;

    public  static addNewItem newInstance(){
        return new addNewItem();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_item,container, false);
        return  v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myText = view.findViewById(R.id.editText);
        btnSave = view.findViewById(R.id.btnSave);

        db = new DB(getActivity());
        boolean isUpdate = false;

        Bundle bundle = getArguments();

        if ( bundle!=null){
            isUpdate = true;
            String itemName = bundle.getString("item");
            myText.setText(itemName);

            if(itemName.length()>0){
                btnSave.setEnabled(false);
            }
        }
        myText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                     btnSave.setEnabled(false);
                     btnSave.setBackgroundColor(Color.GRAY);
                }
                else{
                    btnSave.setEnabled(true);
                    btnSave.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = myText.getText().toString();
                if(finalIsUpdate){
                    db.updateItemName(bundle.getInt("id"), text);
                }
                else{
                    todoItem item = new todoItem();
                    item.setItemName(text);
                    item.setStatus(0);
                    db.insertItem(item);
                }
                dismiss();

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if( activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
