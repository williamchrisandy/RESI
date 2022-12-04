package com.resi.remindassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CategoryActivity extends AppCompatActivity
{
    protected static final String KEY_CATEGORY_ID = "categoryId";

    private EditText editTextTitle;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        editTextTitle = findViewById(R.id.edit_text_title);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra(KEY_CATEGORY_ID, -1);

        if(categoryId != -1)
        {
            CategoryDatabase categoryDatabase = new CategoryDatabase(this);
            category = categoryDatabase.getCategory(categoryId);
            editTextTitle.setText(category.getCategoryTitle());
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Your change in this category will be discarded. Continue?");
        builder.setTitle("Discard Change");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                CategoryActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveCategory(View view)
    {
        String categoryTitle = editTextTitle.getText().toString();
        if(categoryTitle.isEmpty())
        {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        CategoryDatabase categoryDatabase = new CategoryDatabase(this);

        if(category == null) categoryDatabase.insertCategory(new Category(-1, categoryTitle));
        else
        {
            category.setCategoryTitle(categoryTitle);
            categoryDatabase.updateCategory(category);
        }

        finish();
    }
}