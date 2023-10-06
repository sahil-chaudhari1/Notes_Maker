package com.example.notesapp;

public class firebasemodel {

    private String title;     // this names must match with  note.put("title", title); (bracket title ) which is in createnotes.java file
    private String content;   // same for this

    public firebasemodel()
    {

    }

    public firebasemodel(String title, String content)
    {
        this.title = title;
        this.content = content;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }







}
