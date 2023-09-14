package com.linkqw.diary.additional;

import android.content.Intent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.R;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class ListFillAdapter extends RecyclerView.Adapter<ListFillAdapter.MyHolder> {

    Context context;
    ArrayList<String> firstname, lastname;
    ArrayList<Integer> id;
    Bundle bundle;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.list_state_adapter_item, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(lastname.get(position) + " " + firstname.get(position));
        holder.hID.setText(id.get(position).toString());
        holder.hID.setOnClickListener(new View.OnClickListener() {
            final UsersHelper us = new UsersHelper(context);
            String userId = (String) ((TextView)holder.hID).getText();
            String pair = String.valueOf(us.getCurrentPairNum(bundle.getString("date")) + 1);

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Выберите статус");
                builder.setPositiveButton("ув", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (us.isExistInHeap(userId, pair, bundle.getString("date"))) {
                            us.updateHeap(userId, pair, "Уважительная", bundle.getString("date"));
                        } else {
                            us.addToHeap(
                                    Integer.parseInt(userId), "Уважительная", bundle.getString("subject"),
                                    us.getCurrentPairNum(bundle.getString("date")) + 1,
                                    bundle.getString("date"));
                        }
                    }
                });
                builder.setNegativeButton("нб", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        us.addToHeap(
                                Integer.parseInt((String) ((TextView)view).getText()), "Не уважительная", bundle.getString("subject"),
                                us.getCurrentPairNum(bundle.getString("date")) + 1,
                                bundle.getString("date"));
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView name;
        CardView card;
        TextView hID;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ListStateAdapterItem);
            card = itemView.findViewById(R.id.ListStateItem);
            hID = itemView.findViewById(R.id.hiddenID);
        }
    }

    public ListFillAdapter(Context context, ArrayList<String> f,
                           ArrayList<String> l, ArrayList<Integer> id, Bundle b) {
        this.context = context;
        this.lastname = l;
        this.firstname = f;
        this.id = id;
        this.bundle = b;
    }
}
