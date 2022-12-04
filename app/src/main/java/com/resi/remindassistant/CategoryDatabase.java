package com.resi.remindassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class CategoryDatabase
{
    private DatabaseHelper databaseHelper;

    public CategoryDatabase(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertCategory(Category category)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CATEGORY_TITLE, category.getCategoryTitle());

        sqLiteDatabase.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
        sqLiteDatabase.close();
    }

    public Vector<Category> getCategory()
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<Category> categoryVector = new Vector<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_CATEGORY, null, null, null, null, null, null);

        Category category = null;

        while (cursor.moveToNext())
        {
            int categoryId;
            String categoryTitle;

            categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_CATEGORY_ID));
            categoryTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CATEGORY_TITLE));

            category = new Category(categoryId, categoryTitle);
            categoryVector.add(category);
        }

        cursor.close();
        sqLiteDatabase.close();
        return categoryVector;
    }

    public Category getCategory(int categoryId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String selection = DatabaseHelper.FIELD_CATEGORY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_CATEGORY, null, selection, selectionArgs, null, null, null);

        Category category = null;

        if (cursor.moveToFirst())
        {
            String categoryTitle;

            categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_CATEGORY_ID));
            categoryTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_CATEGORY_TITLE));

            category = new Category(categoryId, categoryTitle);
        }

        cursor.close();
        sqLiteDatabase.close();
        return category;
    }

    public void updateCategory(Category category)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int categoryId = category.getCategoryId();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CATEGORY_TITLE, category.getCategoryTitle());

        String whereClause = DatabaseHelper.FIELD_CATEGORY_ID + " = ?";
        String[] whereArgs = {String.valueOf(categoryId)};
        sqLiteDatabase.update(DatabaseHelper.TABLE_CATEGORY, values, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void deleteCategory(int categoryId)
    {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        String tableName = DatabaseHelper.TABLE_CATEGORY;
        String whereClause = DatabaseHelper.FIELD_CATEGORY_ID + " = ?";
        String[] whereArgs = {String.valueOf(categoryId)};

        sqLiteDatabase.delete(tableName, whereClause, whereArgs);
        sqLiteDatabase.close();
    }
}
