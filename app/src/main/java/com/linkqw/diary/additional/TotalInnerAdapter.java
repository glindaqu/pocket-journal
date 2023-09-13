package com.linkqw.diary.additional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class TotalInnerAdapter extends RecyclerView.Adapter<TotalInnerAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> dataset;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.total_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = dataset.get(position).split(",")[0];

        if (name.length() > 15) {
            holder.personUnit.setText(name.split(" ")[0] +
                    "\n" + name.split(" ")[1]);
        } else {
            holder.personUnit.setText(name);
        }

        int resp = Integer.parseInt(dataset.get(position).split(",")[1]);
        int disResp = Integer.parseInt(dataset.get(position).split(",")[2]);

        holder.disrespectCount.setText(String.valueOf(disResp));
        holder.respectCount.setText(String.valueOf(resp));
        holder.totalCount.setText(String.valueOf(disResp+resp));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView totalCount;
        TextView respectCount;
        TextView disrespectCount;
        TextView personUnit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            totalCount = itemView.findViewById(R.id.totalCount);
            respectCount = itemView.findViewById(R.id.respectCount);
            disrespectCount = itemView.findViewById(R.id.disrespectCount);
            personUnit = itemView.findViewById(R.id.personUnit);
        }
    }

    public TotalInnerAdapter(Context context, ArrayList<String> f, ArrayList<String> l,
                          ArrayList<String> status, Boolean isLastFirst) {
        this.context = context;
        this.dataset = new ArrayList<>();

        ArrayList<String> personsData = new ArrayList<>();
        Set<String> names = new LinkedHashSet<>();

        for (int i = 0; i<l.size(); i++) {
            if (isLastFirst) {
                personsData.add(l.get(i) + " " + f.get(i) + "," + status.get(i));
                names.add(l.get(i) + " " + f.get(i));
            } else {
                personsData.add(f.get(i) + " " + l.get(i) + "," + status.get(i));
                names.add(f.get(i) + " " + l.get(i));
            }
        }

        for (String name : names) {
            String line = name;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                int resp = Arrays.stream(personsData.toArray())
                        .filter(x -> x.toString().contains(name)
                                && x.toString().contains("Уважительная")).toArray().length;
                int disResp = Arrays.stream(personsData.toArray())
                        .filter(x -> x.toString().contains(name)
                                && x.toString().contains("Не уважительная")).toArray().length;

                line += "," + resp + "," + disResp;
            }
            dataset.add(line);
        }
    }
}
