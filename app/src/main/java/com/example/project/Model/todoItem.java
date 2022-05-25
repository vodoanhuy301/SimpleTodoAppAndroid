package com.example.project.Model;

public class todoItem {
    private String item;
    private int id,status;

    public String getItem() {
        return item;
    }

    public void setItemName(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
