package droiddevelopers254.droidconke.firebase;

import android.text.TextUtils;

import com.google.firebase.firestore.Query;

import droiddevelopers254.droidconke.models.SessionsModel;

public class Filters {
    private String sessionCategory = null;
    private String sessionType = null;
    private String starred = null;
    private String sortBy = null;
    private Query.Direction sortDirection = null;

    public Filters(){

    }

    public static Filters getDefault(){
        Filters filters = new Filters();
        filters.setSortBy(SessionsModel.FIELD_ID);
        filters.setSortDirection(Query.Direction.ASCENDING);
        return filters;
    }

    public boolean hasSessionCategory(){
        return !(TextUtils.isEmpty(sessionCategory));
    }
    public boolean hasSessionType(){
        return !(TextUtils.isEmpty(sessionType));
    }

    public boolean hasStarred(){
        return !(TextUtils.isEmpty(starred));
    }

    private void setSortDirection(Query.Direction ascending) {
        this.sortDirection=ascending;
    }

    private void setSortBy(String fieldId) {
        this.sortBy=fieldId;
    }

    public String getSessionCategory() {
        return sessionCategory;
    }

    public void setSessionCategory(String sessionCategory) {
        this.sessionCategory = sessionCategory;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getStarred() {
        return starred;
    }

    public void setStarred(String starred) {
        this.starred = starred;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Query.Direction getSortDirection() {
        return sortDirection;
    }
}
