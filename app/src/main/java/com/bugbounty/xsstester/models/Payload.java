package com.bugbounty.xsstester.models;

public class Payload {
    private String name;
    private String payload;
    private String category;
    private boolean isFavorite;
    private int usageCount;

    public Payload(String name, String payload, String category) {
        this.name = name;
        this.payload = payload;
        this.category = category;
        this.isFavorite = false;
        this.usageCount = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPayload() {
        return payload;
    }

    public String getCategory() {
        return category;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getUsageCount() {
        return usageCount;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void incrementUsageCount() {
        this.usageCount++;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", favorite=" + isFavorite +
                '}';
    }
}
