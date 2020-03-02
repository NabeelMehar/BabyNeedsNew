package com.example.babyneedsnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babyneedsnew.adapter.RecyclerItemAdapter;
import com.example.babyneedsnew.data.DatabaseHandler;
import com.example.babyneedsnew.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText item_edt,qty_edt,color_edt,size_edt;
    Button save;

    DatabaseHandler db;
    ArrayList<Item> arrayList;
    RecyclerView recyclerView;
    RecyclerItemAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView=findViewById(R.id.rv);
        fab=findViewById(R.id.new_btn);
        db=new DatabaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList=db.getAllItems();
        for (Item item : arrayList) {
            Log.d("Tokes", "onCreate: "+item.toString());

        }

        adapter=new RecyclerItemAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpopUp();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!String.valueOf(item_edt.getText()).isEmpty() &&
                                !String.valueOf(color_edt.getText()).isEmpty() &&
                                !String.valueOf(qty_edt.getText()).isEmpty() &&
                                !String.valueOf(size_edt.getText()).isEmpty()) {
                            saveitems(v);
                        }
                        else {
                            Snackbar.make(v,"Empty Fields Not Allowed",Snackbar.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
            }
        });





    }

    private void saveitems(View v) {
        Item item=new Item();
        item.setItemName(String.valueOf(item_edt.getText()).trim());
        item.setColor(String.valueOf(color_edt.getText()).trim());
        item.setQuantity(Integer.parseInt(String.valueOf(qty_edt.getText()).trim()));
        item.setItem_size(Integer.parseInt(String.valueOf(size_edt.getText()).trim()));
        Snackbar.make(v,"Item Added",Snackbar.LENGTH_SHORT
        ).show();
        db.addItem(item);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();
            }
        },1200);
    }

    private void createpopUp() {

        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.pop_up,null);

        item_edt=view.findViewById(R.id.item_edt);
        qty_edt=view.findViewById(R.id.qty_edt);
        color_edt=view.findViewById(R.id.clr_edt);
        size_edt=view.findViewById(R.id.size_edt);
        save=view.findViewById(R.id.save_btn);

        builder.setView(view);
        dialog=builder.create();
        dialog.show();

    }
}
