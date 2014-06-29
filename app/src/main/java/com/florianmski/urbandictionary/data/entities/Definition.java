package com.florianmski.urbandictionary.data.entities;

import com.google.gson.annotations.SerializedName;

public class Definition
{
    @SerializedName("defid")
    private long id;
    private String word;
    private String author;
    private String permalink;
    private String definition;
    private String example;
    @SerializedName("thumbs_up")
    private int thumbsUp;
    @SerializedName("thumbs_down")
    private int thumbsDown;
    @SerializedName("current_vote")
    private String currentVote;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPermalink()
    {
        return permalink;
    }

    public void setPermalink(String permalink)
    {
        this.permalink = permalink;
    }

    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(String definition)
    {
        this.definition = definition;
    }

    public String getExample()
    {
        return example;
    }

    public void setExample(String example)
    {
        this.example = example;
    }

    public int getThumbsUp()
    {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp)
    {
        this.thumbsUp = thumbsUp;
    }

    public int getThumbsDown()
    {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown)
    {
        this.thumbsDown = thumbsDown;
    }

    public String getCurrentVote()
    {
        return currentVote;
    }

    public void setCurrentVote(String currentVote)
    {
        this.currentVote = currentVote;
    }
}
