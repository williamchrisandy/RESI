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

public class ContactListActivity extends AppCompatActivity implements ContactAdapter.ClickListener
{
    protected static final String KEY_NOTE_ID = "noteId";

    private RecyclerView recyclerViewContact;
    private ContactAdapter contactAdapter;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Intent intent = getIntent();
        noteId = intent.getIntExtra(KEY_NOTE_ID, -1);

        recyclerViewContact = findViewById(R.id.recycler_view_contact);

        contactAdapter = new ContactAdapter(this, null, false, true);
        recyclerViewContact.setAdapter(contactAdapter);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadContactData();
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
        if (item.getItemId() == R.id.add_item) intentToContactActivity(-1);

        return super.onOptionsItemSelected(item);
    }

    private void loadContactData()
    {
        ContactDatabase contactDatabase = new ContactDatabase(this);
        Vector<Contact> contactVector = contactDatabase.getContact();

        NoteContactDatabase noteContactDatabase = new NoteContactDatabase(this);
        Iterator<Contact> contactIterator = contactVector.iterator();
        while(contactIterator.hasNext()) if(noteContactDatabase.isContactInTheNote(noteId, contactIterator.next().getContactId())) contactIterator.remove();

        contactAdapter.setContactVector(contactVector);
    }

    private void intentToContactActivity(int contactId)
    {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(ContactActivity.KEY_CONTACT_ID, contactId);
        startActivity(intent);
    }

    @Override
    public void onContactItemClick(int contactId)
    {
        NoteContactDatabase noteContactDatabase = new NoteContactDatabase(this);
        noteContactDatabase.insertNoteContact(new NoteContact(noteId, contactId));
        finish();
    }

    @Override
    public void onContactItemEditedClick(int contactId)
    {
        intentToContactActivity(contactId);
    }

    @Override
    public void onContactItemDeletedClick(int contactId)
    {

    }
}