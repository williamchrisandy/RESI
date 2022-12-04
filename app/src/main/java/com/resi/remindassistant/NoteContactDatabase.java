package com.resi.remindassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class NoteContactDatabase
{
    private DatabaseHelper databaseHelper;

    public NoteContactDatabase(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertNoteContact(NoteContact noteContact)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_NOTE_ID, noteContact.getNoteId());
        values.put(DatabaseHelper.FIELD_CONTACT_ID, noteContact.getContactId());

        sqLiteDatabase.insert(DatabaseHelper.TABLE_NOTE_CONTACT, null, values);
        sqLiteDatabase.close();
    }

    public Vector<NoteContact> getNoteContact(int noteId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<NoteContact> noteContactVector = new Vector<>();

        String selection = DatabaseHelper.FIELD_NOTE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE_CONTACT, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext())
        {
            int contactId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_CONTACT_ID));

            NoteContact noteContact = new NoteContact(noteId, contactId);
            noteContactVector.add(noteContact);
        }

        cursor.close();
        sqLiteDatabase.close();
        return noteContactVector;
    }

    public void deleteNoteContact(int noteId, int contactId)
    {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String tableName = DatabaseHelper.TABLE_NOTE_CONTACT;
        String whereClause = DatabaseHelper.FIELD_NOTE_ID + " =? AND " + DatabaseHelper.FIELD_CONTACT_ID + " = ?";
        String[] whereArgs = {String.valueOf(noteId), String.valueOf(contactId)};

        db.delete(tableName, whereClause, whereArgs);
        db.close();
    }

    public boolean isContactInTheNote(int noteId, int contactId)
    {
        boolean valid = false;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String selection =  DatabaseHelper.FIELD_NOTE_ID + " =? AND " + DatabaseHelper.FIELD_CONTACT_ID + " =?";
        String[] selectionArgs = {String.valueOf(noteId), String.valueOf(contactId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE_CONTACT, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext()) valid = true;

        cursor.close();
        sqLiteDatabase.close();

        return valid;
    }
}
