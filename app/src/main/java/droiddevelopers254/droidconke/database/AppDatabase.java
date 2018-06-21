package droiddevelopers254.droidconke.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import droiddevelopers254.droidconke.database.dao.FiltersDao;

public abstract class AppDatabase extends RoomDatabase {
    //Singleton
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "eldo_taxi_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
    //dao
    public abstract FiltersDao filtersDao();

}
