package droiddevelopers254.droidconke.models;

import com.google.gson.annotations.Expose;

public class SessionsUserFeedback {

    @Expose
    private String user_id;
    private String session_id;
    private String day_number;
    private String session_title;
    private String session_feedback;

    public String getDay_number() {
        return day_number;
    }

    public void setDay_number(String day_number) {
        this.day_number = day_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_title() {
        return session_title;
    }

    public void setSession_title(String session_title) {
        this.session_title = session_title;
    }

    public String getSession_feedback() {
        return session_feedback;
    }

    public void setSession_feedback(String session_feedback) {
        this.session_feedback = session_feedback;
    }
}
