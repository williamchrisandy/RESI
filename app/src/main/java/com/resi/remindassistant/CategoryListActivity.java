package com.resi.remindassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Iterator;
import java.util.Vector;

public class CategoryListActivity extends AppCompatActivity implements CategoryAdapter.ClickListener
{
    protected static final String KEY_NOTE_ID = "noteId";

    private RecyclerView recyclerViewCategory;
    private CategoryAdapter categoryAdapter;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Intent intent = getIntent();
        noteId = intent.getIntExtra(KEY_NOTE_ID, -1);

        recyclerViewCategory = findViewById(R.id.recycler_view_category);

        categoryAdapter = new CategoryAdapter(this, null, false, true);
        recyclerViewCategory.setAdapter(categoryAdapter);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadCategoryData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.add_item) intentToCategoryActivity(-1);

        return super.onOptionsItemSelected(item);
    }

    private void loadCategoryData()
    {
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        Vector<Category> categoryVector = categoryDatabase.getCategory();

        NoteCategoryDatabase noteCategoryDatabase = new NoteCategoryDatabase(this);
        Iterator<Category> categoryIterator = categoryVector.iterator();
        while(categoryIterator.hasNext()) if(noteCategoryDatabase.isCategoryInTheNote(noteId, categoryIterator.next().getCategoryId())) categoryIterator.remove();

        categoryAdapter.setCategoryVector(categoryVector);
    }

    private void intentToCategoryActivity(int categoryId)
    {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.KEY_CATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClick(int categoryId)
    {
        NoteCategoryDatabase noteCategoryDatabase = new NoteCategoryDatabase(this);
        noteCategoryDatabase.insertNoteCategory(new NoteCategory(noteId, categoryId));
        finish();
    }

    @Override
    public void onCategoryItemEditedClick(int categoryId)
    {
        intentToCategoryActivity(categoryId);
    }

    @Override
    public void onCategoryItemDeletedClick(int categoryId)
    {

    }
}