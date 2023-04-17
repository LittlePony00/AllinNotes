package com.example.myapplication.NoteDB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    RecyclerViewIntefrace recyclerViewIntefrace;

    Context context;
    List<Note> noteList = new ArrayList<>();

    public CustomAdapter(Context context, RecyclerViewIntefrace recyclerViewIntefrace){
        this.context = context;
        this.recyclerViewIntefrace = recyclerViewIntefrace;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNoteList(List<Note> notesList) {
        this.noteList = notesList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);

        return new CustomViewHolder(view, recyclerViewIntefrace);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.layout_title.setText(this.noteList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView layout_title;
        TextView layout_description;
        public CustomViewHolder(@NonNull View itemView, RecyclerViewIntefrace recyclerViewIntefrace) {
            super(itemView);
            layout_title = itemView.findViewById(R.id.layout_title);
            layout_description = itemView.findViewById(R.id.layout_description);

            itemView.setOnClickListener(view -> {
                if (recyclerViewIntefrace != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        recyclerViewIntefrace.onItemClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (recyclerViewIntefrace != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        recyclerViewIntefrace.onLongItemClick(position);
                    }
                    return true;
                }

                return false;
            });
        }
    }
}
