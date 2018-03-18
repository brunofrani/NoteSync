package com.example.bruno.notesync.javaClasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bruno.notesync.R;

import java.util.ArrayList;

/**
 * Created by Bruno on 3/4/2018.
 */
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    private static OnClickListener onClickListener;
    ArrayList<NoteElements> noteElement;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener,View.OnTouchListener{
        // each data item is just a string in this case
        RelativeLayout relativeLayout;
        TextView textView;
        TextView textDate;

        public ViewHolder(View item) {
            super(item);
          //  item.setOnClickListener(this);
          //  item.setOnLongClickListener(this);
            textView = item.findViewById(R.id.textView1);
            textDate = item.findViewById(R.id.textViewDate);
            relativeLayout = item.findViewById(R.id.item_list);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            onClickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            return false;
        }
    }

  //  public void setOnItemClickListener(OnClickListener clickListener) {
    //    AdapterRecycler.onClickListener = clickListener;
    //}
    public AdapterRecycler(ArrayList<NoteElements> noteElements) {
        noteElement = noteElements;
    }

    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dual, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String noteToDisplay=  noteElement.get(position).getNote().toString();
        if(noteToDisplay.length()>61){
        String noteFInal = noteToDisplay.substring(0,60);
        holder.textView.setText(noteFInal);
        }else
            {
                holder.textView.setText(noteToDisplay);
            }

        holder.textDate.setText((CharSequence) noteElement.get(position).getDate());

    }
    @Override
    public int getItemCount() {
        return noteElement.size();
    }

}