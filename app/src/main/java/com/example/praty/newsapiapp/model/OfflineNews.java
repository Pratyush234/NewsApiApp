package com.example.praty.newsapiapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "news")
public class OfflineNews implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "publishedAt")
    private String timestamp;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    public OfflineNews(String title, String description, String author, String timestamp, String url, String imageUrl) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.timestamp = timestamp;
        this.url=url;
        this.imageUrl=imageUrl;
    }

    @Ignore
    public OfflineNews() {
    }

    protected OfflineNews(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        author = in.readString();
        timestamp = in.readString();
        url = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<OfflineNews> CREATOR = new Creator<OfflineNews>() {
        @Override
        public OfflineNews createFromParcel(Parcel in) {
            return new OfflineNews(in);
        }

        @Override
        public OfflineNews[] newArray(int size) {
            return new OfflineNews[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeString(timestamp);
        dest.writeString(url);
        dest.writeString(imageUrl);
    }
}
