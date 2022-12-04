package com.resi.remindassistant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
{
    private Context context;
    private Vector<Note> noteVector;
    private ClickListener clickListener;

    public void setNoteVector(Vector<Note> noteVector)
    {
        this.noteVector = noteVector;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle;
        TextView textViewDate;
        Button buttonDelete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDate = itemView.findViewById(R.id.text_view_subtitle);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (clickListener != null)
                        clickListener.onNoteItemClick(noteVector.get(getBindingAdapterPosition()).getNoteId());
                }
            });
        }
    }

    public NoteAdapter(Context context, Fragment fragment)
    {
        this.context = context;
        if (fragment instanceof ClickListener) clickListener = (ClickListener) fragment;
        else if (context instanceof ClickListener) clickListener = (ClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_title_subtitle_delete, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Note item = noteVector.get(position);
        holder.textViewTitle.setText(item.getNoteTitle());
        holder.textViewDate.setText(item.getNoteDate());

        holder.buttonDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickListener.onNoteItemDeletedClick(item.getNoteId());
                Toast.makeText(context, "Note removed.", Toast.LENGTH_LONG).show();
                noteVector.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, noteVector.size());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return noteVector.size();
    }

    public interface ClickListener
    {
        void onNoteItemClick(int noteId);

        void onNoteItemDeletedClick(int noteId);
    }
}
