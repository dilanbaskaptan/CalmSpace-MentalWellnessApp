package com.example.calmspace;

public class HealthTip implements Trackable {
    private String title;
    private String description;
    private String category;

    public HealthTip(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public void saveEntry() {
        System.out.println("HealthTip saved: " + title);
    }

    @Override
    public void deleteEntry() {
        System.out.println("HealthTip deleted: " + title);
    }
}