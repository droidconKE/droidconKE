package droiddevelopers254.droidconke.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import droiddevelopers254.droidconke.database.converters.Converter
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.database.dao.StarredSessionDao
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.models.StarredSessionModel

@Database(entities = [StarredSessionModel::class, SessionsModel::class], version = 4, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    //dao
    abstract fun sessionsDao(): SessionsDao

    abstract fun starredSessionDao(): StarredSessionDao

    companion object {
        //Singleton
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            when (INSTANCE) {
                null -> INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "droidconKE_db")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
