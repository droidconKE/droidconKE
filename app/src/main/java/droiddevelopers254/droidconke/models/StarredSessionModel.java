package droiddevelopers254.droidconke.models;

public class StarredSessionModel {
    private String day;
    private String session_id;
    private String user_id;

    public StarredSessionModel() {
    }



    public StarredSessionModel(String day, String session_id, String user_id) {
        this.day = day;
        this.session_id = session_id;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
