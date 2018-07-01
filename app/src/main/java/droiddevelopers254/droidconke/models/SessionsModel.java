package droiddevelopers254.droidconke.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionsModel {

    public static final String FIELD_ID="id";
    public static final String FIELD_TOPIC="topic";
    public static final String FIELD_TYPE="type";
    public static final String FIELD_STARRED="starred";

    private int id;
    private ArrayList<Integer> speaker_id;
    private int room_id;
    private String main_tag;
    private String room;
    private String speakers;
    private String starred;
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

    public SessionsModel(int id, ArrayList<Integer> speaker_id, int room_id, String main_tag, String room, String speakers, String starred, String time, String title, String topic, String url, String duration, String description, String session_color, int topic_id, int type_id, String type) {
        this.id = id;
        this.speaker_id = speaker_id;
        this.room_id = room_id;
        this.main_tag = main_tag;
        this.room = room;
        this.speakers = speakers;
        this.starred = starred;
        this.time = time;
        this.title = title;
        this.topic = topic;
        this.url = url;
        this.duration = duration;
        this.description = description;
        this.session_color = session_color;
        this.topic_id = topic_id;
        this.type_id = type_id;
        this.type = type;
    }

    public SessionsModel(int id, String starred) {
        this.id = id;
        this.starred = starred;
    }
}
