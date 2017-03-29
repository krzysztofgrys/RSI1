package com.company.model;

/**
 * Created by krzysztofgrys on 3/28/17.
 */
public class Book {
    private String author;
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }
}
