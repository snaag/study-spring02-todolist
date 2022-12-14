package snaag.company.todolist.controller;

public class TodoItemForm {
    private String id;
    private String text;
    private String done;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDone() {
        if(done == null) return "false";
        return "true";
    }

    public void setDone(String done) {
        this.done = done;
    }




}
