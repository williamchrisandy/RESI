package com.resi.remindassistant;

public class NoteContact
{
    private int noteId;
    private int contactId;

    public NoteContact(int noteId, int contactId)
    {
        this.noteId = noteId;
        this.contactId = contactId;
    }

    public int getNoteId()
    {
        return noteId;
    }

    public void setNoteId(int noteId)
    {
        this.noteId = noteId;
    }

    public int getContactId()
    {
        return contactId;
    }

    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }
}
