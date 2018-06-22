package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.FiltersModel;

public class FiltersState {
    private List<FiltersModel> filtersModel;
    private DatabaseError databaseError;

    public FiltersState(List<FiltersModel> filtersModel) {
        this.filtersModel = filtersModel;
        this.databaseError=null;
    }

    public FiltersState(DatabaseError databaseError) {
        this.databaseError = databaseError;
        this.filtersModel=null;
    }

    public List<FiltersModel> getFiltersModel() {
        return filtersModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
