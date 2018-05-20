package droiddevelopers254.droidconke.models;

public class SessionsModel {

    private int id;
    private String main_tag;
    private String room;
    private String speakers;
    private String starred;
    private String startTimeStamp;
    private String title;
    private String topic;

    private String url;
    private String endTimeStamp;
    private String duration;
    private String description;

    public SessionsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain_tag() {
        return main_tag;
    }

    public void setMain_tag(String main_tag) {
        this.main_tag = main_tag;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public String getStarred() {
        return starred;
    }

    public void setStarred(String starred) {
        this.starred = starred;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SessionsModel(int id, String main_tag, String room, String speakers, String starred,
                         String startTimeStamp, String title, String topic, String url,
                         String endTimeStamp, String duration, String description) {
        this.id = id;
        this.main_tag = main_tag;
        this.room = room;
        this.speakers = speakers;
        this.starred = starred;
        this.startTimeStamp = startTimeStamp;
        this.title = title;
        this.topic = topic;
        this.url = url;
        this.endTimeStamp = endTimeStamp;
        this.duration = duration;
        this.description = description;
    }
}
