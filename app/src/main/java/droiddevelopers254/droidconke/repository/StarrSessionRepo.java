package droiddevelopers254.droidconke.repository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.database.dao.StarredSessionDao;
import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import droiddevelopers254.droidconke.utils.DroidCoin;
import io.reactivex.Single;

public class StarrSessionRepo {
    private StarredSessionDao starredSessionDao;
    private Executor executor;
    private SessionsDao sessionsDao;

    public StarrSessionRepo(){
        executor = Executors.newSingleThreadExecutor();
        starredSessionDao= AppDatabase.getDatabase(DroidCoin.context).starredSessionDao();
        sessionsDao=AppDatabase.getDatabase(DroidCoin.context).sessionsDao();
    }

    public Single<List<StarredSessionEntity>> getStarredSessions(){
        return starredSessionDao.getStarredSessions();
    }
    public void starrSession(StarredSessionEntity starredSessionEntity){
        executor.execute(() -> starredSessionDao.starSession(starredSessionEntity));
    }

    public void unStarrSession(int sessionId, String dayNumber){
        executor.execute(() -> starredSessionDao.unStarSession(sessionId, dayNumber));
    }

    public void updateSession(int sessionId, boolean isStarred){
        executor.execute(()-> sessionsDao.updateSession(sessionId,isStarred));
    }

    public void isSessionStarred(int sessionId){
        executor.execute(() -> sessionsDao.isSessionStarred(sessionId));
    }
}
