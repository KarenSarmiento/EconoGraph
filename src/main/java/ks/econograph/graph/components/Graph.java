package ks.econograph.graph.components;

/**
 * Created by KSarm on 17/11/2016.
 */
public class Graph {
    private String title;
    private long date; //yyyyMMddhhmm -> greater values of date mean later in timeline
    private String topic;
    private boolean favourite;
    private String imageLocation; //this will all be in the same location with name same as title.

    public Graph() {
        this.title = "Title";
        this.topic = "Topic";
        this.favourite = false;
        this.imageLocation = "C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\image.png";
    }

    public Graph(String title, int date, String topic, boolean favourite, String imageLocation) {
        this.title = title;
        this.date = date;
        this.topic = topic;
        this.favourite = favourite;
        this.imageLocation = imageLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }
}
