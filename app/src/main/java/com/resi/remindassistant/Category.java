package com.resi.remindassistant;

public class Category
{
    private int categoryId;
    private String categoryTitle;

    public Category(int categoryId, String categoryTitle)
    {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle()
    {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle)
    {
        this.categoryTitle = categoryTitle;
    }
}
