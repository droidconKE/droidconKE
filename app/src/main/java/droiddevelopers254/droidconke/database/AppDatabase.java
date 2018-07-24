package droiddevelopers254.droidconke.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import droiddevelopers254.droidconke.database.converters.Converter;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.database.dao.StarredSessionDao;
import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import droiddevelopers254.droidconke.models.SessionsModel;

@Database(entities = {StarredSessionEntity.class, SessionsModel.class},version = 3,exportSchema = false)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    //Singleton
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "droidconKE_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    //dao
    public abstract SessionsDao sessionsDao();

    public abstract StarredSessionDao starredSessionDao();

}
