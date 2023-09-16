package com.linkqw.diary.additional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.R;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> firstname, lastname, status;
    Boolean isLastFirst = false;
    String pairNum;
    String date;

    public SectionAdapter(Context context, ArrayList<String> f, ArrayList<String> l,
                          ArrayList<String> status, Boolean isLastFirst,
                          int pairNum, String date) {
        this.context = context;
        this.lastname = l;
        this.firstname = f;
        this.status = status;
        this.isLastFirst = isLastFirst;
        this.pairNum = String.valueOf(pairNum + 1);
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.section_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int name_len = (lastname.get(position) + firstname.get(position)).length();
        if (isLastFirst) {
            holder.title.setText(lastname.get(position) + ((name_len > 15) ? "\n" : " ") + firstname.get(position));
        } else {
            holder.title.setText(firstname.get(position) + ((name_len > 15) ? "\n" : " ") + lastname.get(position));
        }

        holder.sectionAdapterItemStatus.setText(status.get(position));

        UsersHelper usersHelper = new UsersHelper(context);

        holder.sectionAdapterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView b = holder.sectionAdapterItemStatus;
                if (b.getText() == "Уважительная") {
                    b.setText("Не уважительная");
                    usersHelper.updateHeap(usersHelper.getIdByUser(firstname.get(position), lastname.get(position)),
                            pairNum, "Не уважительная", date);
                } else if (b.getText() == "Не уважительная") {
                    b.setText("Был");
                    usersHelper.updateHeap(usersHelper.getIdByUser(firstname.get(position), lastname.get(position)),
                            pairNum, "Был", date);
                } else {
                    usersHelper.updateHeap(usersHelper.getIdByUser(firstname.get(position), lastname.get(position)),
                            pairNum, "Уважительная", date);
                    b.setText("Уважительная");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CardView sectionAdapterItem;
        TextView sectionAdapterItemStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.personName);
            sectionAdapterItem = itemView.findViewById(R.id.SectionAdapterItem);
            sectionAdapterItemStatus = itemView.findViewById(R.id.SectionAdapterItemStatus);
        }
    }
}
