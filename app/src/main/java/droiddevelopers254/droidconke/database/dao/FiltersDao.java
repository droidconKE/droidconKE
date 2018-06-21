package droiddevelopers254.droidconke.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import droiddevelopers254.droidconke.database.converters.DateConverter;
import droiddevelopers254.droidconke.models.FiltersModel;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface FiltersDao {
    @Insert(onConflict = REPLACE)
    void saveFilter(FiltersModel filtersModel);

    @Query("SELECT * FROM FiltersModel")
    LiveData<List<FiltersModel>> getFiltersList();

    @Query("SELECT * FROM FiltersModel WHERE filterId = :filterId ")
    LiveData<FiltersModel> checkFilterStatus(int filterId);

}
