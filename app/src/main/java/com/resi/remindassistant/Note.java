package com.resi.remindassistant;

public class Note
{
    private int noteId;
    private String noteTitle;
    private String noteDate;
    private String noteDescription;

    public Note(int noteId, String noteTitle, String noteDate, String noteDescription)
    {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDate = noteDate;
        this.noteDescription = noteDescription;
    }

    public int getNoteId()
    {
        return noteId;
    }

    public void setNoteId(int noteId)
    {
        this.noteId = noteId;
    }

    public String getNoteTitle()
    {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle)
    {
        this.noteTitle = noteTitle;
    }

    public String getNoteDate()
    {
        return noteDate;
    }

    public void setNoteDate(String noteDate)
    {
        this.noteDate = noteDate;
    }

    public String getNoteDescription()
    {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription)
    {
        this.noteDescription = noteDescription;
    }
}
