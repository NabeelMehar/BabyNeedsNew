package com.example.babyneedsnew.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyneedsnew.R;
import com.example.babyneedsnew.data.DatabaseHandler;
import com.example.babyneedsnew.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Item> arrayList=new ArrayList<>();

    public RecyclerItemAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_recycler,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item=arrayList.get(position);

        holder.item_edt.setText(item.getItemName());
        holder.qty_edt.setText(String.valueOf(item.getQuantity()));
        holder.clr_edt.setText(String.valueOf(item.getColor()));
        holder.date_edt.setText(item.getDate_added());
        holder.size_edt.setText(String.valueOf(item.getItem_size()));




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView item_edt,clr_edt,qty_edt,size_edt,date_edt;
        EditText item1_edt,qty1_edt,color_edt,size1_edt;
        TextView title_tv;
        Button save;



        Button edt_btn,dlt_btn;
        public int id;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;

            item_edt=itemView.findViewById(R.id.acitemedt);
            clr_edt=itemView.findViewById(R.id.acclredt);
            qty_edt=itemView.findViewById(R.id.acqtyedt);
            size_edt=itemView.findViewById(R.id.acsizeedt);
            date_edt=itemView.findViewById(R.id.acdateedt);


            edt_btn=itemView.findViewById(R.id.edt_btn);
            dlt_btn=itemView.findViewById(R.id.dlt_btn);

            edt_btn.setOnClickListener(this);
            dlt_btn.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.dlt_btn:

                deleteItem(arrayList.get(getAdapterPosition()));

                    break;
                case R.id.edt_btn:
                    Item item=arrayList.get(getAdapterPosition());
                    editItem(item);

                     break;

            }

        }

        private void editItem(final Item newItem) {



            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            final View view=LayoutInflater.from(context).inflate(R.layout.pop_up,null);
            builder.setView(view);
            final AlertDialog dialog=builder.create();
            dialog.show();



            item1_edt = view.findViewById(R.id.item_edt);
            qty1_edt=view.findViewById(R.id.qty_edt);
            color_edt=view.findViewById(R.id.clr_edt);
            size1_edt=view.findViewById(R.id.size_edt);
            save=view.findViewById(R.id.save_btn);
            title_tv=view.findViewById(R.id.itm_tv);

            title_tv.setText("Update Item");
            save.setText("Update");


            item1_edt.setText(newItem.getItemName());
            Log.d("Tokes", "editItem: "+newItem.toString());
            qty1_edt.setText(String.valueOf(newItem.getQuantity()));
            color_edt.setText(newItem.getColor());
            size1_edt.setText(String.valueOf(newItem.getItem_size()));

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                        newItem.setItemName(String.valueOf(item1_edt.getText()).trim());
                        newItem.setColor(String.valueOf(color_edt.getText()).trim());
                        newItem.setQuantity(Integer.parseInt(String.valueOf(qty1_edt.getText()).trim()));
                        newItem.setItem_size(Integer.parseInt(String.valueOf(size1_edt.getText()).trim()));

                        DatabaseHandler db=new DatabaseHandler(context);

                    if(!item1_edt.getText().toString().isEmpty() &&
                            !color_edt.getText().toString().isEmpty() &&
                            !qty1_edt.getText().toString().isEmpty() &&
                            !size1_edt.getText().toString().isEmpty()) {
                        db.updateItem(newItem);
                        notifyItemChanged(getAdapterPosition(),newItem);
                    }
                    else {
                        Snackbar.make(view,"Empty Fields Not Allowed",Snackbar.LENGTH_SHORT
                        ).show();
                    }
                        dialog.dismiss();
                    }

            });

        }



        private void deleteItem(Item item) {


            createDialog();


        }

        private void createDialog() {

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            View view=LayoutInflater.from(context).inflate(R.layout.confrm_dialog,null);

            Button btnYes=view.findViewById(R.id.cnfm_yes_btn);
            Button btnNo=view.findViewById(R.id.cnfm_no_btn);

            builder.setView(view);

            final AlertDialog dialog=builder.create();
            dialog.show();

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteItem(arrayList.get(getAdapterPosition()));
                    arrayList.remove(arrayList.get(getAdapterPosition()));
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }
    }
}
