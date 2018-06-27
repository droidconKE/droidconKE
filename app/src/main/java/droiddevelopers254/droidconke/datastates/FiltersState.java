package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.FiltersModel;

public class FiltersState {
    private List<FiltersModel> filtersModel;
    private String databaseError;

    public FiltersState(List<FiltersModel> filtersModel) {
        this.filtersModel = filtersModel;
        this.databaseError=null;
    }

    public FiltersState(String databaseError) {
        this.databaseError = databaseError;
        this.filtersModel=null;
    }

    public List<FiltersModel> getFiltersModel() {
        return filtersModel;
    }

    public String getDatabaseError() {
        return databaseError;
    }
}
