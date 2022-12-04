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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
{
    private Context context;
    private Vector<Contact> contactVector;
    private ClickListener clickListener;
    private boolean showDelete, showEdit;

    public void setContactVector(Vector<Contact> contactVector)
    {
        this.contactVector = contactVector;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle;
        Button buttonEdit, buttonDelete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            if(showEdit == true) buttonEdit = itemView.findViewById(R.id.button_edit);
            if(showDelete == true) buttonDelete = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(clickListener != null) clickListener.onContactItemClick(contactVector.get(getBindingAdapterPosition()).getContactId());
                }
            });
        }
    }

    public ContactAdapter(Context context, Fragment fragment, boolean showDelete, boolean showEdit)
    {
        this.context = context;
        this.showDelete = showDelete;
        this.showEdit = showEdit;
        if(fragment instanceof ClickListener) clickListener = (ClickListener) fragment;
        else if(context instanceof ClickListener) clickListener = (ClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        int layout;
        if(showEdit == true && showDelete == true) layout = R.layout.item_title_edit_delete;
        else if(showEdit == true) layout = R.layout.item_title_edit;
        else if(showDelete == true) layout = R.layout.item_title_delete;
        else layout = R.layout.item_title;

        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ContactAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Contact item = contactVector.get(position);
        holder.textViewTitle.setText(item.getContactName());

        if (showDelete == true)
        {
            holder.buttonDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(clickListener != null) clickListener.onContactItemDeletedClick(item.getContactId());
                    Toast.makeText(context, "Contact removed.", Toast.LENGTH_LONG).show();
                    contactVector.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, contactVector.size());
                }
            });
        }

        if (showEdit == true)
        {
            holder.buttonEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(clickListener != null) clickListener.onContactItemEditedClick(item.getContactId());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return contactVector.size();
    }

    public interface ClickListener
    {
        void onContactItemClick(int contactId);

        void onContactItemEditedClick(int contactId);

        void onContactItemDeletedClick(int contactId);
    }
}