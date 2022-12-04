package com.resi.remindassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Vector;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.ClickListener
{
    protected static final String KEY_CATEGORY_ID = "categoryId";

    private RecyclerView recyclerViewNote;
    private NoteAdapter noteAdapter;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra(KEY_CATEGORY_ID, -1);

        recyclerViewNote = findViewById(R.id.recycler_view_note);

        noteAdapter = new NoteAdapter(this, null);
        recyclerViewNote.setAdapter(noteAdapter);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadNoteData();
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
        if (item.getItemId() == R.id.add_item)
        {
            NoteDatabase noteDatabase = new NoteDatabase(this);
            noteDatabase.insertNote(new Note(-1, "Untitled", "No Date", ""));

            NoteCategoryDatabase noteCategoryDatabase = new NoteCategoryDatabase(this);
            noteCategoryDatabase.insertNoteCategory(new NoteCategory(noteDatabase.getLastNote().getNoteId(), categoryId));

            intentToNoteActivity(-1);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadNoteData()
    {
        NoteCategoryDatabase noteCategoryDatabase = new NoteCategoryDatabase(this);
        Vector<NoteCategory> noteCategoryVector = noteCategoryDatabase.getNoteCategoryByCategory(categoryId);

        NoteDatabase noteDatabase = new NoteDatabase(this);
        Vector<Note> noteVector = new Vector<>();
        for(NoteCategory noteCategory: noteCategoryVector)
        {
            noteVector.add(noteDatabase.getNote(noteCategory.getNoteId()));
        }
        noteAdapter.setNoteVector(noteVector);
    }

    private void intentToNoteActivity(int noteId)
    {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(NoteActivity.KEY_NOTE_ID, noteId);
        startActivity(intent);
    }

    @Override
    public void onNoteItemClick(int noteId)
    {
        intentToNoteActivity(noteId);
    }

    @Override
    public void onNoteItemDeletedClick(int noteId)
    {
        NoteDatabase noteDatabase = new NoteDatabase(this);
        noteDatabase.deleteNote(noteId);
    }
}