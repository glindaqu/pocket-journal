package com.linkqw.diary.additional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.JournalEdit;
import com.linkqw.diary.R;

import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> firstname, lastname, status;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.section_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(firstname.get(position) + " " + lastname.get(position));
        String st = status.get(position);

        switch (st) {
            case "Был":
                holder.edit.setText("б");
                break;

            case "Не уважительная":
                holder.edit.setText("н/б");
                break;

            default:
                holder.edit.setText("ув");
        }

    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.personName);
            edit = itemView.findViewById(R.id.statusEdit);
        }
    }

    public SectionAdapter(Context context, ArrayList<String> f, ArrayList<String> l,
                                 ArrayList<String> status) {
        this.context = context;
        this.lastname = l;
        this.firstname = f;
        this.status = status;
    }
}
