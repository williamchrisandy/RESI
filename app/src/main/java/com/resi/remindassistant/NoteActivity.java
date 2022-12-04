package com.resi.remindassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class NoteActivity extends AppCompatActivity implements CategoryAdapter.ClickListener, ContactAdapter.ClickListener
{
    protected static final String KEY_NOTE_ID = "noteId";

    private TextView textViewNoteDate;
    private EditText editTextTitle, editTextDescription;
    private RecyclerView recyclerViewCategory, recyclerViewContact;
    private CategoryAdapter categoryAdapter;
    private ContactAdapter contactAdapter;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        textViewNoteDate = findViewById(R.id.text_view_note_date);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);

        recyclerViewCategory = findViewById(R.id.recycler_view_category);

        categoryAdapter = new CategoryAdapter(this, null, true, false);
        recyclerViewCategory.setAdapter(categoryAdapter);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewContact = findViewById(R.id.recycler_view_contact);

        contactAdapter = new ContactAdapter(this, null, true, false);
        recyclerViewContact.setAdapter(contactAdapter);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        int noteId = intent.getIntExtra(KEY_NOTE_ID, -1);

        NoteDatabase noteDatabase= new NoteDatabase(this);
        note = noteId == -1? noteDatabase.getLastNote() : noteDatabase.getNote(noteId);
        editTextTitle.setText(note.getNoteTitle());
        textViewNoteDate.setText(note.getNoteDate().equals("No Date") ? "yyyy-mm-dd" : note.getNoteDate());
        editTextDescription.setText(note.getNoteDescription());

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadData();
    }

    public void openCalendar(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_date, null);
        builder.setView(dialogView);

        builder.setTitle("Pick Date");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DatePicker picker = dialogView.findViewById(R.id.date_picker);

                String year = Integer.toString(picker.getYear());
                String month = Integer.toString(picker.getMonth() + 1);
                String day = Integer.toString(picker.getDayOfMonth());
                String createdDate = year + "-" + (month.length() < 2 ? "0" + month : month) + "-" + (day.length() < 2 ? "0" + day : day);
                textViewNoteDate.setText(createdDate);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadData()
    {
        NoteCategoryDatabase noteCategoryDatabase = new NoteCategoryDatabase(this);
        Vector<NoteCategory> noteCategoryVector = noteCategoryDatabase.getNoteCategoryByNote(note.getNoteId());

        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        Vector<Category> categoryVector = new Vector<>();
        for(NoteCategory noteCategory : noteCategoryVector)
        {
            categoryVector.add(categoryDatabase.getCategory(noteCategory.getCategoryId()));
        }
        categoryAdapter.setCategoryVector(categoryVector);

        NoteContactDatabase noteContactDatabase = new NoteContactDatabase(this);
        Vector<NoteContact> noteContactVector = noteContactDatabase.getNoteContact(note.getNoteId());

        ContactDatabase contactDatabase = new ContactDatabase(this);
        Vector<Contact> contactVector = new Vector<>();
        for(NoteContact noteContact : noteContactVector)
        {
            contactVector.add(contactDatabase.getContact(noteContact.getContactId()));
        }
        contactAdapter.setContactVector(contactVector);
    }

    public void updateNote(View view)
    {
        String noteTitle = editTextTitle.getText().toString();
        String createdDate = textViewNoteDate.getText().toString();
        String noteDescription = editTextDescription.getText().toString();

        if(createdDate.equals("yyyy-mm-dd")) createdDate = "No Date";
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
        }

        NoteDatabase noteDatabase = new NoteDatabase(this);
        note.setNoteTitle(noteTitle);
        note.setNoteDate(createdDate);
        note.setNoteDescription(noteDescription);
        noteDatabase.updateNote(note);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Your change in note title, date, and description will be discarded. Continue?");
        builder.setTitle("Discard Change");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                NoteActivity.super.onBackPressed();
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

    public void addCategory(View view)
    {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra(CategoryListActivity.KEY_NOTE_ID, note.getNoteId());
        startActivity(intent);
    }

    public void addContact(View view)
    {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra(ContactListActivity.KEY_NOTE_ID, note.getNoteId());
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClick(int categoryId)
    {

    }

    @Override
    public void onCategoryItemEditedClick(int categoryId)
    {

    }

    @Override
    public void onCategoryItemDeletedClick(int categoryId)
    {
        NoteCategoryDatabase noteCategoryDatabase = new NoteCategoryDatabase(this);
        noteCategoryDatabase.deleteNoteCategory(note.getNoteId(), categoryId);
    }

    @Override
    public void onContactItemClick(int contactId)
    {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(ContactActivity.KEY_CONTACT_ID, contactId);
        startActivity(intent);
    }

    @Override
    public void onContactItemEditedClick(int contactId)
    {

    }

    @Override
    public void onContactItemDeletedClick(int contactId)
    {
        NoteContactDatabase noteContactDatabase = new NoteContactDatabase(this);
        noteContactDatabase.deleteNoteContact(note.getNoteId(), contactId);
    }
}