package com.resi.remindassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class NoteDatabase
{
    private DatabaseHelper databaseHelper;

    public NoteDatabase(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertNote(Note note)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_NOTE_TITLE, note.getNoteTitle());
        values.put(DatabaseHelper.FIELD_NOTE_DATE, note.getNoteDate());
        values.put(DatabaseHelper.FIELD_NOTE_DESCRIPTION, note.getNoteDescription());

        sqLiteDatabase.insert(DatabaseHelper.TABLE_NOTE, null, values);
        sqLiteDatabase.close();
    }

    public Vector<Note> getNote()
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<Note> noteVector = new Vector<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE, null, null, null, null, null, DatabaseHelper.FIELD_NOTE_DATE + " ASC");

        Note note = null;

        while (cursor.moveToNext())
        {
            int noteId;
            String noteTitle, noteDate, noteDescription;

            noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_ID));
            noteTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_TITLE));
            noteDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_DATE));
            noteDescription = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_DESCRIPTION));

            note = new Note(noteId, noteTitle, noteDate, noteDescription);
            noteVector.add(note);
        }

        cursor.close();
        sqLiteDatabase.close();
        return noteVector;
    }

    public Note getNote(int noteId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String selection = DatabaseHelper.FIELD_NOTE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE, null, selection, selectionArgs, null, null, null);

        Note note = null;

        if (cursor.moveToFirst())
        {
            String noteTitle, noteDate, noteDescription;

            noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_ID));
            noteTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_TITLE));
            noteDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_DATE));
            noteDescription = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_DESCRIPTION));

            note = new Note(noteId, noteTitle, noteDate, noteDescription);
        }

        cursor.close();
        sqLiteDatabase.close();
        return note;
    }

    public Note getLastNote()
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE, null, null, null, null, null, DatabaseHelper.FIELD_NOTE_ID + " DESC", "1");
        Note note = null;

        if (cursor.moveToNext())
        {
            int noteId;
            String noteTitle, noteDate, noteDescription;

            noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_ID));
            noteTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_TITLE));
            noteDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_DATE));
            noteDescription = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_DESCRIPTION));

            note = new Note(noteId, noteTitle, noteDate, noteDescription);
        }

        cursor.close();
        sqLiteDatabase.close();
        return note;
    }

    public void updateNote(Note note)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int diaryId = note.getNoteId();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_NOTE_TITLE, note.getNoteTitle());
        values.put(DatabaseHelper.FIELD_NOTE_DATE, note.getNoteDate());
        values.put(DatabaseHelper.FIELD_NOTE_DESCRIPTION, note.getNoteDescription());

        String whereClause = DatabaseHelper.FIELD_NOTE_ID + " = ?";
        String[] whereArgs = {String.valueOf(diaryId)};
        sqLiteDatabase.update(DatabaseHelper.TABLE_NOTE, values, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void deleteNote(int noteId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        String tableName = DatabaseHelper.TABLE_NOTE;
        String whereClause = DatabaseHelper.FIELD_NOTE_ID + " = ?";
        String[] whereArgs = {String.valueOf(noteId)};

        sqLiteDatabase.delete(tableName, whereClause, whereArgs);
        sqLiteDatabase.close();
    }
}