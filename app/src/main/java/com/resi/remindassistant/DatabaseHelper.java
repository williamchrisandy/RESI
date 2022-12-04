package com.resi.remindassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "RemindAssistantDatabase";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NOTE = "Note";
    public static final String FIELD_NOTE_ID = "NoteId";
    public static final String FIELD_NOTE_TITLE = "NoteTitle";
    public static final String FIELD_NOTE_DATE = "NoteDate";
    public static final String FIELD_NOTE_DESCRIPTION = "NoteDescription";

    public static final String TABLE_CATEGORY = "Category";
    public static final String FIELD_CATEGORY_ID = "CategoryId";
    public static final String FIELD_CATEGORY_TITLE = "CategoryTitle";

    public static final String TABLE_CONTACT = "Contact";
    public static final String FIELD_CONTACT_ID = "ContactId";
    public static final String FIELD_CONTACT_NAME = "ContactName";
    public static final String FIELD_CONTACT_LINE = "ContactLine";
    public static final String FIELD_CONTACT_WHATSAPP = "ContactWhatsApp";

    public static final String TABLE_NOTE_CATEGORY = "NoteCategory";

    public static final String TABLE_NOTE_CONTACT = "NoteContact";

    private static final String CREATE_NOTE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE +
                    " (" +
                    FIELD_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_NOTE_TITLE + " TEXT, " +
                    FIELD_NOTE_DATE + " TEXT, " +
                    FIELD_NOTE_DESCRIPTION + " TEXT" +
                    ")";

    private static final String DROP_NOTE = "DROP TABLE IF EXISTS " + TABLE_NOTE;

    private static final String CREATE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY +
                    " (" +
                    FIELD_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_CATEGORY_TITLE + " TEXT" +
                    ")";

    private static final String DROP_CATEGORY = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;

    private static final String CREATE_CONTACT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACT +
                    " (" +
                    FIELD_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_CONTACT_NAME + " TEXT, " +
                    FIELD_CONTACT_LINE + " TEXT, " +
                    FIELD_CONTACT_WHATSAPP + " TEXT" +
                    ")";

    private static final String DROP_CONTACT = "DROP TABLE IF EXISTS " + TABLE_CONTACT;

    private static final String CREATE_NOTE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE_CATEGORY +
                    " (" +
                    FIELD_NOTE_ID + " INTEGER REFERENCES " + TABLE_NOTE + " (" + FIELD_NOTE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
                    FIELD_CATEGORY_ID + " INTEGER REFERENCES " + TABLE_CATEGORY + " (" + FIELD_CATEGORY_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
                    "PRIMARY KEY (" + FIELD_NOTE_ID + ", " + FIELD_CATEGORY_ID + ")" +
                    ")";

    private static final String DROP_NOTE_CATEGORY = "DROP TABLE IF EXISTS " + TABLE_NOTE_CATEGORY;

    private static final String CREATE_NOTE_CONTACT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE_CONTACT +
                    " (" +
                    FIELD_NOTE_ID + " INTEGER REFERENCES " + TABLE_NOTE + " (" + FIELD_NOTE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
                    FIELD_CONTACT_ID + " INTEGER REFERENCES " + TABLE_CONTACT + " (" + FIELD_CONTACT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
                    "PRIMARY KEY (" + FIELD_NOTE_ID + ", " + FIELD_CONTACT_ID + ")" +
                    ")";

    private static final String DROP_NOTE_CONTACT = "DROP TABLE IF EXISTS " + TABLE_NOTE_CONTACT;

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_NOTE);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_CONTACT);
        db.execSQL(CREATE_NOTE_CATEGORY);
        db.execSQL(CREATE_NOTE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_NOTE);
        db.execSQL(DROP_CATEGORY);
        db.execSQL(DROP_CONTACT);
        db.execSQL(DROP_NOTE_CATEGORY);
        db.execSQL(DROP_NOTE_CONTACT);
        onCreate(db);
    }
}