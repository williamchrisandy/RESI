package com.resi.remindassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener
{
    private BottomNavigationView bottomNavigationView;
    private NoteFragment noteFragment;
    private CategoryFragment categoryFragment;
    private ContactFragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteFragment = NoteFragment.newInstance();
        categoryFragment = CategoryFragment.newInstance();
        contactFragment = ContactFragment.newInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(item.getItemId() == R.id.home)
        {
            transaction.replace(R.id.fragment_container, noteFragment).commit();
            return true;
        }
        else if(item.getItemId() == R.id.category)
        {
            transaction.replace(R.id.fragment_container, categoryFragment).commit();
            return true;
        }
        else if(item.getItemId() == R.id.contact)
        {
            transaction.replace(R.id.fragment_container, contactFragment).commit();
            return true;
        }
        return false;
    }
}