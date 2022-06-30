package com.example.hangouts.models;

import java.util.List;

public class DropZone {

    private int dropValue;
    private List<String> content;

    public DropZone(int dropValue, List<String> content) {
        this.dropValue = dropValue;
        this.content = content;
    }

    public int getDropValue() {
        return dropValue;
    }

    public void setDropValue(int dropValue) {
        this.dropValue = dropValue;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;

    }

    public void storeValue(String value){
        content.add(value);
    }
}
