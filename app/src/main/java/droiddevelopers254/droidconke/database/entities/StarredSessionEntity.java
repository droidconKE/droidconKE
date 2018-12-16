package droiddevelopers254.droidconke.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "starredSessions")
public class StarredSessionEntity {
    @PrimaryKey
    private int id;
    private ArrayList<Integer> speaker_id;
    private int room_id;
    private String main_tag;
    private String room;
    private String speakers;
    private boolean isStarred;
    private String time;
    private String title;
    private String topic;
    private String url;
    private String duration;
    private String description;
    private String session_color;
    private int topic_id;
    private int type_id;
    private String type;
    private String documentId;
    private String timestamp;
    private String day_number;

    public StarredSessionEntity() {
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

    public boolean getStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        this.isStarred = starred;
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

    public ArrayList<Integer> getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(ArrayList<Integer> speaker_id) {
        this.speaker_id = speaker_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSession_color() {
        return session_color;
    }

    public void setSession_color(String session_color) {
        this.session_color = session_color;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDay_number() {
        return day_number;
    }

    public void setDay_number(String day_number) {
        this.day_number = day_number;
    }
}
