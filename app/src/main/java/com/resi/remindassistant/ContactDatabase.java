package com.resi.remindassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class ContactDatabase
{
    private DatabaseHelper databaseHelper;

    public ContactDatabase(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertContact(Contact contact)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CONTACT_NAME, contact.getContactName());
        values.put(DatabaseHelper.FIELD_CONTACT_LINE, contact.getContactLine());
        values.put(DatabaseHelper.FIELD_CONTACT_WHATSAPP, contact.getContactWhatsApp());

        sqLiteDatabase.insert(DatabaseHelper.TABLE_CONTACT, null, values);
        sqLiteDatabase.close();
    }

    public Vector<Contact> getContact()
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<Contact> contactVector = new Vector<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_CONTACT, null, null, null, null, null, null);

        Contact contact = null;

        while (cursor.moveToNext())
        {
            int contactId;
            String contactName, contactLine, contactWhatsApp;

            contactId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_ID));
            contactName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_NAME));
            contactLine = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_LINE));
            contactWhatsApp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_WHATSAPP));

            contact = new Contact(contactId, contactName, contactLine, contactWhatsApp);
            contactVector.add(contact);
        }

        cursor.close();
        sqLiteDatabase.close();
        return contactVector;
    }

    public Contact getContact(int contactId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String selection = DatabaseHelper.FIELD_CONTACT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_CONTACT, null, selection, selectionArgs, null, null, null);

        Contact contact = null;

        if (cursor.moveToFirst())
        {
            String contactName, contactLine, contactWhatsApp;

            contactId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_ID));
            contactName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_NAME));
            contactLine = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_LINE));
            contactWhatsApp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_WHATSAPP));

            contact = new Contact(contactId, contactName, contactLine, contactWhatsApp);
        }

        cursor.close();
        sqLiteDatabase.close();
        return contact;
    }

    public void updateContact(Contact contact)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int contactId = contact.getContactId();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CONTACT_NAME, contact.getContactName());
        values.put(DatabaseHelper.FIELD_CONTACT_LINE, contact.getContactLine());
        values.put(DatabaseHelper.FIELD_CONTACT_WHATSAPP, contact.getContactWhatsApp());

        String whereClause = DatabaseHelper.FIELD_CONTACT_ID + " = ?";
        String[] whereArgs = {String.valueOf(contactId)};
        sqLiteDatabase.update(DatabaseHelper.TABLE_CONTACT, values, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void deleteContact(int contactId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        String tableName = DatabaseHelper.TABLE_CONTACT;
        String whereClause = DatabaseHelper.FIELD_CONTACT_ID + " = ?";
        String[] whereArgs = {String.valueOf(contactId)};

        sqLiteDatabase.delete(tableName, whereClause, whereArgs);
        sqLiteDatabase.close();
    }
}
