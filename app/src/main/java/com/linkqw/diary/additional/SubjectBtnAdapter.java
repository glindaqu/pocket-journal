package com.linkqw.diary.additional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.R;

import java.util.ArrayList;

public class SubjectBtnAdapter extends RecyclerView.Adapter<SubjectBtnAdapter.SubViewHolder> {

    Context context;
    ArrayList<String> name;

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.btn_row, parent, false);
        return new SubViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        holder.name.setText(name.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public static class SubViewHolder extends RecyclerView.ViewHolder {

        Button name;
        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subjectStartFill);
        }
    }

    public SubjectBtnAdapter(Context context, ArrayList<String> name) {
        this.context = context;
        this.name = name;
    }


}