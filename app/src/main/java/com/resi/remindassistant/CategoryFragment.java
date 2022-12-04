package com.resi.remindassistant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements  CategoryAdapter.ClickListener
{
    private RecyclerView recyclerViewCategory;
    private CategoryAdapter categoryAdapter;

    public CategoryFragment()
    {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance()
    {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerViewCategory = rootView.findViewById(R.id.recycler_view_category);

        categoryAdapter = new CategoryAdapter(getActivity(), this, true, true);
        recyclerViewCategory.setAdapter(categoryAdapter);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadCategoryData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.add_item) intentToCategoryActivity(-1);

        return super.onOptionsItemSelected(item);
    }

    private void loadCategoryData()
    {
        CategoryDatabase categoryDatabase = new CategoryDatabase(getActivity());
        Vector<Category> categoryVector = categoryDatabase.getCategory();
        categoryAdapter.setCategoryVector(categoryVector);
    }

    private void intentToCategoryActivity(int categoryId)
    {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(CategoryActivity.KEY_CATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClick(int categoryId)
    {
        Intent intent = new Intent(getActivity(), NoteListActivity.class);
        intent.putExtra(NoteListActivity.KEY_CATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void onCategoryItemEditedClick(int categoryId)
    {
        intentToCategoryActivity(categoryId);
    }

    @Override
    public void onCategoryItemDeletedClick(int categoryId)
    {
        CategoryDatabase categoryDatabase = new CategoryDatabase(getActivity());
        categoryDatabase.deleteCategory(categoryId);
    }
}