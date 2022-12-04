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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
{
    private Context context;
    private Vector<Category> categoryVector;
    private ClickListener clickListener;
    private boolean showDelete, showEdit;

    public void setCategoryVector(Vector<Category> categoryVector)
    {
        this.categoryVector = categoryVector;
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
                    if(clickListener != null) clickListener.onCategoryItemClick(categoryVector.get(getBindingAdapterPosition()).getCategoryId());
                }
            });

        }
    }

    public CategoryAdapter(Context context, Fragment fragment, boolean showDelete, boolean showEdit)
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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Category item = categoryVector.get(position);
        holder.textViewTitle.setText(item.getCategoryTitle());

        if (showDelete == true)
        {
            holder.buttonDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(clickListener != null) clickListener.onCategoryItemDeletedClick(item.getCategoryId());
                    Toast.makeText(context, "Category removed.", Toast.LENGTH_LONG).show();
                    categoryVector.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, categoryVector.size());
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
                    if(clickListener != null) clickListener.onCategoryItemEditedClick(item.getCategoryId());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return categoryVector.size();
    }

    public interface ClickListener
    {
        void onCategoryItemClick(int categoryId);

        void onCategoryItemEditedClick(int categoryId);

        void onCategoryItemDeletedClick(int categoryId);
    }
}