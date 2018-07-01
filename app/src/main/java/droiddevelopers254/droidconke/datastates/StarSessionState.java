package droiddevelopers254.droidconke.datastates;

import droiddevelopers254.droidconke.models.StarredSessionModel;

public class StarSessionState {
    private boolean starred;
    private String error;
    private StarredSessionModel starredSessionModel;

    public StarSessionState(boolean starred) {
        this.starred = starred;
        this.error=null;
        this.starredSessionModel=null;
    }

    public StarSessionState(String error) {
        this.error = error;
        this.starred=false;
        this.starredSessionModel=null;
    }

    public StarSessionState(StarredSessionModel starredSessionModel) {
        this.starredSessionModel = starredSessionModel;
        this.starred=false;
        this.error=null;
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
}
