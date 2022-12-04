package com.resi.remindassistant;

import android.content.Context;
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
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment implements NoteAdapter.ClickListener
{
    private RecyclerView recyclerViewNote;
    private NoteAdapter noteAdapter;

    public NoteFragment()
    {
        // Required empty public constructor
    }

    public static NoteFragment newInstance()
    {
        return new NoteFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        recyclerViewNote = rootView.findViewById(R.id.recycler_view_note);

        noteAdapter = new NoteAdapter(getActivity(), this);
        recyclerViewNote.setAdapter(noteAdapter);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadNoteData();
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
        if (item.getItemId() == R.id.add_item)
        {
            NoteDatabase noteDatabase = new NoteDatabase(getActivity());
            noteDatabase.insertNote(new Note(-1, "Untitled", "No Date", ""));

            intentToNoteActivity(-1);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadNoteData()
    {
        NoteDatabase noteDatabase = new NoteDatabase(getActivity());
        Vector<Note> noteVector = noteDatabase.getNote();
        noteAdapter.setNoteVector(noteVector);
    }

    private void intentToNoteActivity(int noteId)
    {
        Intent intent = new Intent(getActivity(), NoteActivity.class);
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
        NoteDatabase noteDatabase = new NoteDatabase(getActivity());
        noteDatabase.deleteNote(noteId);
    }
}