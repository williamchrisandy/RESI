package com.resi.remindassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class ContactActivity extends AppCompatActivity
{
    protected static final String KEY_CONTACT_ID = "contactId";

    private EditText editTextName, editTextLine, editTextWhatsApp;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        editTextName = findViewById(R.id.edit_text_name);
        editTextLine = findViewById(R.id.edit_text_line);
        editTextWhatsApp = findViewById(R.id.edit_text_whatsapp);

        Intent intent = getIntent();
        int contactId = intent.getIntExtra(KEY_CONTACT_ID, -1);

        if(contactId != -1)
        {
            ContactDatabase contactDatabase = new ContactDatabase(this);
            contact = contactDatabase.getContact(contactId);
            editTextName.setText(contact.getContactName());
            editTextLine.setText(contact.getContactLine());
            editTextWhatsApp.setText(contact.getContactWhatsApp());
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Your change in this contact will be discarded. Continue?");
        builder.setTitle("Discard Change");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ContactActivity.super.onBackPressed();
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

    public void saveContact(View view)
    {
        String contactName = editTextName.getText().toString();
        if(contactName.isEmpty())
        {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        String contactLine = editTextLine.getText().toString();
        String contactWhatsApp = editTextWhatsApp.getText().toString();

        ContactDatabase contactDatabase = new ContactDatabase(this);

        if(contact == null) contactDatabase.insertContact(new Contact(-1, contactName, contactLine, contactWhatsApp));
        else
        {
            contact.setContactName(contactName);
            contact.setContactLine(contactLine);
            contact.setContactWhatsApp(contactWhatsApp);
            contactDatabase.updateContact(contact);
        }
        finish();
    }

    public void openLine(View view)
    {
        String contactLine = editTextLine.getText().toString().toLowerCase();
        if(contactLine.isEmpty()) Toast.makeText(this, "Line ID cannot be empty", Toast.LENGTH_LONG).show();
        else startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://line.me/ti/p/~" + contactLine)));
    }

    public void openWhatsApp(View view)
    {
        String contactWhatsApp = editTextWhatsApp.getText().toString().toLowerCase();
        if(contactWhatsApp.isEmpty()) Toast.makeText(this, "WhatsApp number cannot be empty", Toast.LENGTH_LONG).show();
        else startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + contactWhatsApp)));
    }
}