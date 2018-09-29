package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.database.dao.StarredSessionDao;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.utils.DroidCoin;
import io.reactivex.Single;

public class RoomStarrSessionRepo {
    private StarredSessionDao starredSessionDao;
    private Executor executor;
    private SessionsDao sessionsDao;

    public RoomStarrSessionRepo(){
        executor = Executors.newSingleThreadExecutor();
        starredSessionDao= AppDatabase.getDatabase(DroidCoin.context).starredSessionDao();
        sessionsDao=AppDatabase.getDatabase(DroidCoin.context).sessionsDao();
    }

    public Single<List<StarredSessionModel>> getStarredSessions(){
        return starredSessionDao.getStarredSessions();
    }
    public void starrSession(int sessionId,String isStarred ,String dayNumber){
        executor.execute(() -> sessionsDao.starSession(sessionId, isStarred, dayNumber));
    }

    public void unStarrSession(int sessionId,String isStarred ,String dayNumber){
        executor.execute(() -> sessionsDao.unStarSession(sessionId,isStarred,dayNumber));
    }

    public LiveData<Integer> isSessionStarred(int sessionId, String dayNumber){

        return sessionsDao.isSessionStarred(sessionId, dayNumber);
    }
}
