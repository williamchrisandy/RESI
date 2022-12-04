package com.resi.remindassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class NoteCategoryDatabase
{
    private DatabaseHelper databaseHelper;

    public NoteCategoryDatabase(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertNoteCategory(NoteCategory noteCategory)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_NOTE_ID, noteCategory.getNoteId());
        values.put(DatabaseHelper.FIELD_CATEGORY_ID, noteCategory.getCategoryId());

        sqLiteDatabase.insert(DatabaseHelper.TABLE_NOTE_CATEGORY, null, values);
        sqLiteDatabase.close();
    }

    public Vector<NoteCategory> getNoteCategoryByNote(int noteId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<NoteCategory> noteCategoryVector = new Vector<>();

        String selection = DatabaseHelper.FIELD_NOTE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE_CATEGORY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext())
        {
            int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_CATEGORY_ID));

            NoteCategory noteCategory = new NoteCategory(noteId, categoryId);
            noteCategoryVector.add(noteCategory);
        }

        cursor.close();
        sqLiteDatabase.close();
        return noteCategoryVector;
    }

    public Vector<NoteCategory> getNoteCategoryByCategory(int categoryId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<NoteCategory> noteCategoryVector = new Vector<>();

        String selection = DatabaseHelper.FIELD_CATEGORY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE_CATEGORY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext())
        {
            int noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_NOTE_ID));

            NoteCategory noteCategory = new NoteCategory(noteId, categoryId);
            noteCategoryVector.add(noteCategory);
        }

        cursor.close();
        sqLiteDatabase.close();
        return noteCategoryVector;
    }

    public void deleteNoteCategory(int noteId, int categoryId)
    {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String tableName = DatabaseHelper.TABLE_NOTE_CATEGORY;
        String whereClause = DatabaseHelper.FIELD_NOTE_ID + " =? AND " + DatabaseHelper.FIELD_CATEGORY_ID + " = ?";
        String[] whereArgs = {String.valueOf(noteId), String.valueOf(categoryId)};

        db.delete(tableName, whereClause, whereArgs);
        db.close();
    }

    public boolean isCategoryInTheNote(int noteId, int categoryId)
    {
        boolean valid = false;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String selection =  DatabaseHelper.FIELD_NOTE_ID + " =? AND " + DatabaseHelper.FIELD_CATEGORY_ID + " =?";
        String[] selectionArgs = {String.valueOf(noteId), String.valueOf(categoryId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NOTE_CATEGORY, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext()) valid = true;

        cursor.close();
        sqLiteDatabase.close();

        return valid;
    }
}
