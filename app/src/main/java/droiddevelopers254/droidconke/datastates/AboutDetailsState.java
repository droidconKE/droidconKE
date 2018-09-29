package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.AboutDetailsModel;

public class AboutDetailsState {
    private List<AboutDetailsModel> aboutDetailsModelList;
    private String databaseError;

    public AboutDetailsState(List<AboutDetailsModel> aboutDetailsModelList) {
        this.aboutDetailsModelList = aboutDetailsModelList;
        this.databaseError=null;
    }

    public AboutDetailsState(String databaseError) {
        this.databaseError = databaseError;
        this.aboutDetailsModelList=null;
    }

    public List<AboutDetailsModel> getAboutDetailsModelList() {
        return aboutDetailsModelList;
    }

    public String getDatabaseError() {
        return databaseError;
    }
}
