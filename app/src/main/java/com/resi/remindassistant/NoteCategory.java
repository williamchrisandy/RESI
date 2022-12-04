package com.resi.remindassistant;

public class NoteCategory
{
    private int noteId;
    private int categoryId;

    public NoteCategory(int noteId, int categoryId)
    {
        this.noteId = noteId;
        this.categoryId = categoryId;
    }

    public int getNoteId()
    {
        return noteId;
    }

    public void setNoteId(int noteId)
    {
        this.noteId = noteId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }
}
