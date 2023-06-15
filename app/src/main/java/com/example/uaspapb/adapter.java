package com.example.uaspapb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    List<model> listdata;
    LayoutInflater inflater;

    public adapter(Context context, List<model> listdata) {
        this.listdata = listdata;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model todo = listdata.get(position);
        holder.todotime.setText(listdata.get(position).getTime());
        holder.whattodo.setText(listdata.get(position).getWhat());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView todotime, whattodo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            todotime = (TextView) itemView.findViewById(R.id.timetodo);
            whattodo = (TextView) itemView.findViewById(R.id.whattodo);
        }
    }
}
