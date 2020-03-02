package com.example.babyneedsnew;

import android.content.Intent;
import android.os.Bundle;

import com.example.babyneedsnew.data.DatabaseHandler;
import com.example.babyneedsnew.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText item_edt,qty_edt,color_edt,size_edt;
    Button save;
    DatabaseHandler db=new DatabaseHandler(this);


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bypassAc();





        FloatingActionButton fab = findViewById(R.id.add_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();
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

    private void bypassAc() {

        if (db.getCount()>0)
        {
            startActivity(new Intent(this,ListActivity.class));
            finish();
        }

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
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200);
    }

    private void createPopUpDialog() {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
