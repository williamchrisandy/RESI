package com.resi.remindassistant;

public class Contact
{
    private int contactId;
    private String contactName;
    private String contactLine;
    private String contactWhatsApp;

    public Contact(int contactId, String contactName, String contactLine, String contactWhatsApp)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactLine = contactLine;
        this.contactWhatsApp = contactWhatsApp;
    }

    public int getContactId()
    {
        return contactId;
    }

    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getContactLine()
    {
        return contactLine;
    }

    public void setContactLine(String contactLine)
    {
        this.contactLine = contactLine;
    }

    public String getContactWhatsApp()
    {
        return contactWhatsApp;
    }

    public void setContactWhatsApp(String contactWhatsApp)
    {
        this.contactWhatsApp = contactWhatsApp;
    }
}
