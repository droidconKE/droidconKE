package droiddevelopers254.droidconke.models;

public class SessionTimeModel {
    String sessionHour;
    String amPm;

    public String getSessionHour() {
        return sessionHour;
    }

    public void setSessionHour(String sessionHour) {
        this.sessionHour = sessionHour;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public SessionTimeModel(String sessionHour, String amPm) {
        this.sessionHour = sessionHour;
        this.amPm = amPm;
    }
}
