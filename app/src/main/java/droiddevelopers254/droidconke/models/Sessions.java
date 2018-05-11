package droiddevelopers254.droidconke.models;

public class Sessions {
    String sessionTitle;
    String sessionDuration;
    String sessionRoom;
    String sessionCategory;

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(String sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getSessionRoom() {
        return sessionRoom;
    }

    public void setSessionRoom(String sessionRoom) {
        this.sessionRoom = sessionRoom;
    }

    public String getSessionCategory() {
        return sessionCategory;
    }

    public void setSessionCategory(String sessionCategory) {
        this.sessionCategory = sessionCategory;
    }

    public Sessions(String sessionTitle, String sessionDuration, String sessionRoom, String sessionCategory) {
        this.sessionTitle = sessionTitle;
        this.sessionDuration = sessionDuration;
        this.sessionRoom = sessionRoom;
        this.sessionCategory = sessionCategory;
    }
}
