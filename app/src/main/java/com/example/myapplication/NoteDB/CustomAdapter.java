package com.example.myapplication.NoteDB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    RecyclerViewIntefrace recyclerViewIntefrace;

    Context context;
    List<Note> noteList = new ArrayList<>();
    private int x = -1;


    public void setX(int x) {
        this.x = x;
    }

    public CustomAdapter(Context context, RecyclerViewIntefrace recyclerViewIntefrace){
        this.context = context;
        this.recyclerViewIntefrace = recyclerViewIntefrace;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNoteList(List<Note> notesList) {
        this.noteList = notesList;
        notifyDataSetChanged();
    }

    public void deleteItemFromNoteList(int id) {
        noteList.remove(id);
        notifyItemRemoved(id);
    }

    public void insertItemToNoteList(List<Note> noteList) {
        this.noteList.add(noteList.get(noteList.size()-1));
        notifyItemInserted(noteList.size()-1);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);

        return new CustomViewHolder(view, recyclerViewIntefrace);
    }

    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.layout_title.setText(this.noteList.get(position).title);


        Random random = new Random();
        int colorID = random.nextInt(3);
        if (colorID == 0)
            holder.layout_image.setImageResource(R.drawable.ic_note_foreground);
        else if (colorID == 1 )
            holder.layout_image.setImageResource(R.drawable.ic_note_red_foreground);
        else
            holder.layout_image.setImageResource(R.drawable.ic_note_blue_foreground);

//        holder.relativeLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.for_recycle_view));


        holder.itemView.setOnLongClickListener(view -> {
            recyclerViewIntefrace.onLongItemClick(holder.getAdapterPosition());
//            if (noteList.size() >= position)
//                recyclerViewIntefrace.onLongItemClick(position);
//            else
//                recyclerViewIntefrace.onLongItemClick(position-1);
            if (x == holder.getAdapterPosition()) {
                x = -1;
                notifyDataSetChanged();
            }else {
                x = holder.getAdapterPosition();
                notifyDataSetChanged();
            }


            return true;

        });

        if (x == holder.getAdapterPosition()) {
            holder.relativeLayout.setBackgroundColor(context.getColor(R.color.grey));

        } else if (x == -1) {
            holder.relativeLayout.setBackgroundColor(context.getColor(R.color.black));
        }
        else {
            holder.relativeLayout.setBackgroundColor(context.getColor(R.color.black));
        }

    }



    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView layout_title;
        TextView layout_description;
        ImageView layout_image;
        RelativeLayout relativeLayout;
        public CustomViewHolder(@NonNull View itemView, RecyclerViewIntefrace recyclerViewIntefrace) {
            super(itemView);
            layout_title = itemView.findViewById(R.id.layout_title);
            layout_description = itemView.findViewById(R.id.layout_description);
            layout_image = itemView.findViewById(R.id.layout_image);
            relativeLayout = itemView.findViewById(R.id.custom_layout);

            itemView.setOnClickListener(view -> {
                if (recyclerViewIntefrace != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        recyclerViewIntefrace.onItemClick(position);
                    }
                }
            });

//            itemView.setOnLongClickListener(view -> {
//                if (recyclerViewIntefrace != null) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION){
//                        recyclerViewIntefrace.onLongItemClick(position);
//                    }
//                    return true;
//                }
//
//                return false;
//            });
        }
    }
}
