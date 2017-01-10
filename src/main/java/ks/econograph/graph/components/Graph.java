package ks.econograph.graph.components;

/**
 * Created by KSarm on 17/11/2016.
 */
public class Graph {
    private String title;
    private String topic;
    private long time;
    private boolean favourite;
    private String fileName;

    public Graph() {
        this.title = "Title";
        this.topic = "";
        this.favourite = false;
        this.fileName = generateFileName();
    }

    public Graph(String title) {
        this.title = title;
        this.topic = "";
        this.favourite = false;
        this.fileName = generateFileName();
    }

    public Graph(String title, String topic, long time, boolean favourite) {
        this.title = title;
        this.time = time;
        this.topic = topic;
        this.favourite = favourite;
        this.fileName = generateFileName();

    }

    private String generateFileName() {
        String fileName = "";
        for (int i = 0; i < 35; i++) {
            char letter = (char) (97+(int)(Math.random()*(26)));
            fileName += letter;
        }
        fileName += ".png";
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", time=" + time +
                ", favourite=" + favourite +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
