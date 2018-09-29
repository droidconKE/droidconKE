package droiddevelopers254.droidconke.datastates;

import droiddevelopers254.droidconke.models.StarredSessionModel;

public class StarSessionState {
    private boolean starred;
    private String error;
    private StarredSessionModel starredSessionModel;
    private int starMessage;

    public StarSessionState(boolean starred) {
        this.starred = starred;
        this.error=null;
        this.starredSessionModel=null;
        this.starMessage=0;
    }

    public StarSessionState(String error) {
        this.error = error;
        this.starred=false;
        this.starredSessionModel=null;
        this.starMessage=0;
    }

    public StarSessionState(StarredSessionModel starredSessionModel) {
        this.starredSessionModel = starredSessionModel;
        this.starred=false;
        this.error=null;
        this.starMessage=0;
    }

    public StarSessionState(int starMessage) {
        this.starMessage = starMessage;
        this.starred=false;
        this.error=null;
        this.starredSessionModel=null;
    }

    public boolean isStarred() {
        return starred;
    }

    public String getError() {
        return error;
    }

    public StarredSessionModel getStarredSessionModel() {
        return starredSessionModel;
    }

    public int getStarMessage() {
        return starMessage;
    }
}
