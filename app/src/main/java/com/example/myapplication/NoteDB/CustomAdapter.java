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

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    Context context;
    List<Note> noteList;
    private onItemClickListener clickListener;



    public CustomAdapter(Context context, onItemClickListener clickListener){
        this.context = context;
        this.clickListener = clickListener;
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

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.layout_title.setText(this.noteList.get(position).title);
        holder.layout_description.setText(this.noteList.get(position).description);


        holder.itemView.setOnClickListener(view -> {
            clickListener.onClick(noteList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public interface onItemClickListener {
        void onClick(Note note);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView layout_title;
        TextView layout_description;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_title = itemView.findViewById(R.id.layout_title);
            layout_description = itemView.findViewById(R.id.layout_description);
        }
    }
}
