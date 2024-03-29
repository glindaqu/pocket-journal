package com.linkqw.diary.additional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.R;

import java.util.ArrayList;

public class JournalSectionAdapter
        extends RecyclerView.Adapter<JournalSectionAdapter.MyViewHolder> {

    Context context;
    ArrayList<ArrayList<String>> firstname, lastname, status;
    ArrayList<String> title;
    Boolean isLastFirst = false;
    String date;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.journal_section, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(title.get(position));

        SectionAdapter adapter = new SectionAdapter(this.context, firstname.get(position),
                lastname.get(position), status.get(position), isLastFirst, position, date);
        holder.list.setAdapter(adapter);
        holder.list.setLayoutManager(new LinearLayoutManager(this.context));
    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        RecyclerView list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            list = itemView.findViewById(R.id.totals);
        }
    }

    public JournalSectionAdapter(Context context, ArrayList<ArrayList<String>> f,
                                 ArrayList<ArrayList<String>> l,
                                 ArrayList<ArrayList<String>> status, ArrayList<String> title,
                                 Boolean isLastFirst, String date) {
        this.context = context;
        this.lastname = l;
        this.firstname = f;
        this.status = status;
        this.title = title;
        this.isLastFirst = isLastFirst;
        this.date = date;
    }
}
