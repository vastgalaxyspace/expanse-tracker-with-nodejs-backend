package com.example.jforce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpanseAdapter extends RecyclerView.Adapter<ExpanseAdapter.ViewHolder> {
    private List<Expanse> expanses;

    public ExpanseAdapter(List<Expanse> expanses) {
        this.expanses = expanses;
    }
    @NonNull
    @Override
    public ExpanseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
        return new ViewHolder(view1);

    }

    @Override
    public void onBindViewHolder(@NonNull ExpanseAdapter.ViewHolder holder, int position) {
        Expanse expanse=expanses.get(position);
        holder.tittleTextView.setText(expanse.getTitle());
        holder.amountTextView.setText(String.valueOf(expanse.getAmount()));
        holder.dateTextView.setText(expanse.getDate());

    }

    @Override
    public int getItemCount() {
return expanses.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tittleTextView,amountTextView, dateTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittleTextView = itemView.findViewById(R.id.title1);
            amountTextView = itemView.findViewById(R.id.idamount);
            dateTextView = itemView.findViewById(R.id.date);

        }



    }
}
