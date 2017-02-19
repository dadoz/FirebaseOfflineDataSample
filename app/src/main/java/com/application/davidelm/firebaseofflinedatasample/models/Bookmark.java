package com.application.davidelm.firebaseofflinedatasample.models;


public class Bookmark {
    private long id;
    private long timestamp;
    private long lastUpdate;
    private String iconPath;
    private String url;
    private int userId;
    private String name;
    private byte[] blobIcon;

    public int getUserId() {
        return this.userId;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getName() { return this.name; }

    public void setName(String value) {
        this.name = value;
    }

    public long getId() {
        return this.id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getLastUpdate() {
        return this.lastUpdate;
    }

    public void setBlobIcon(byte[] blobIcon) {
        this.blobIcon = blobIcon;
    }

    public byte[] getBlobIcon() {
        return blobIcon;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
