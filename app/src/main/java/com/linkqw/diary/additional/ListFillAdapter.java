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
    ArrayList<String> firstname, lastname, status;
    ArrayList<Integer> id;
    Bundle bundle;
    boolean isLast;
    String pair;
    int intPair;
    UsersHelper us;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.list_state_adapter_item, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        int length = (lastname.get(position) + firstname.get(position)).length();

        if (length > 10) {
            if (isLast) {
                holder.name.setText(lastname.get(position) + "\n" + firstname.get(position));
            } else {
                holder.name.setText(firstname.get(position) + "\n" + lastname.get(position));
            }
        } else {
            if (isLast) {
                holder.name.setText(lastname.get(position) + " " + firstname.get(position));
            } else {
                holder.name.setText(firstname.get(position) + " " + lastname.get(position));
            }
        }

        final String userId = id.get(position).toString();

        holder.hID.setText(id.get(position).toString());
        holder.status.setText(status.get(position));
        holder.hID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Выберите статус");
                builder.setPositiveButton("Уважительная", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (us.isExistInHeap(userId, pair, bundle.getString("date"))) {
                            us.updateHeap(userId, pair, "Уважительная", bundle.getString("date"));
                        } else {
                            us.addToHeap(
                                    Integer.parseInt(userId), "Уважительная", bundle.getString("subject"),
                                    intPair,
                                    bundle.getString("date"));
                        }
                        status.set(position, "Уважительная");
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Не уважительная", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (us.isExistInHeap(userId, pair, bundle.getString("date"))) {
                            us.updateHeap(userId, pair, "Не уважительная", bundle.getString("date"));

                        } else {
                            us.addToHeap(
                                    Integer.parseInt(userId), "Не уважительная", bundle.getString("subject"),
                                    intPair,
                                    bundle.getString("date"));
                        }
                        status.set(position, "Не уважительная");
                        notifyDataSetChanged();
                    }
                });

                builder.setNeutralButton("Был", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        us.removeFromHeap(userId, pair, bundle.getString("date"));
                        status.set(position, "Был");
                        notifyDataSetChanged();
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
        TextView status;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ListStateAdapterItem);
            card = itemView.findViewById(R.id.ListStateItem);
            hID = itemView.findViewById(R.id.hiddenID);
            status = itemView.findViewById(R.id.curStatus);
        }
    }

    public ListFillAdapter(Context context, ArrayList<String> f,
                           ArrayList<String> l, ArrayList<Integer> id, Bundle b, boolean isLast) {
        this.context = context;
        this.lastname = l;
        this.firstname = f;
        this.id = id;
        this.bundle = b;
        this.isLast = isLast;
        this.us = new UsersHelper(context);
        this.intPair = us.getCurrentPairNum(bundle.getString("date")) + 1;
        this.pair = String.valueOf(intPair);

        this.status = new ArrayList<>(lastname.size());

        for (int i = 0; i < lastname.size(); i++) {
            status.add("Был");
        }
    }
}
