package droiddevelopers254.droidconke.repository;

import java.util.List;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.repository.remote.DayTwoService;
import droiddevelopers254.droidconke.utils.DroidCoin;
import droiddevelopers254.droidconke.utils.EntityMapper;
import droiddevelopers254.droidconke.utils.network.NetworkBoundSource;
import droiddevelopers254.droidconke.utils.network.Resource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class DayTwoRepo {
    private DayTwoService dayTwoService;
    private SessionsDao sessionsDao;

    public DayTwoRepo(){
        sessionsDao = AppDatabase.getDatabase(DroidCoin.context).sessionsDao();
        dayTwoService = new DayTwoService();
    }
    public Flowable<Resource<List<SessionsEntity>>> getDayTwoSessions(){
        return Flowable.create(emitter -> new NetworkBoundSource<List<SessionsEntity>,List<SessionsModel>>(emitter){

            @Override
            public Single<List<SessionsModel>> getRemote() {
                return dayTwoService.getDayOneSessions() ;
            }

            @Override
            public Flowable<List<SessionsEntity>> getLocal() {
                return sessionsDao.getDayTwoSessions("day_two");
            }

            @Override
            public void saveCallResult(List<SessionsEntity> data) {
                sessionsDao.saveDayOneSession(data);
            }

            @Override
            public Function<List<SessionsModel>, List<SessionsEntity>> mapper() {
                return EntityMapper.convert();
            }
        }, BackpressureStrategy.BUFFER);
    }

}
