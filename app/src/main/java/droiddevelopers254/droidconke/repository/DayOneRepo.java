package droiddevelopers254.droidconke.repository;

import android.util.Log;

import java.util.List;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.repository.remote.DayOneService;
import droiddevelopers254.droidconke.utils.DroidCoin;
import droiddevelopers254.droidconke.utils.EntityMapper;
import droiddevelopers254.droidconke.utils.network.NetworkBoundSource;
import droiddevelopers254.droidconke.utils.network.Resource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class DayOneRepo {
    private SessionsDao sessionsDao;
    public DayOneService dayOneService;

    public DayOneRepo(){
        sessionsDao = AppDatabase.getDatabase(DroidCoin.context).sessionsDao();
        dayOneService= new DayOneService();

    }
    public Flowable<Resource<List<SessionsEntity>>> getDayOneSessions(){
        return Flowable.create(emitter -> new NetworkBoundSource<List<SessionsEntity>,List<SessionsModel>>(emitter){

            @Override
            public Single<List<SessionsModel>> getRemote() {
                return dayOneService.getDayOneSessions() ;
            }

            @Override
            public Flowable<List<SessionsEntity>> getLocal() {
                return sessionsDao.getDayOneSessions("day_one");
            }

            @Override
            public void saveCallResult(List<SessionsEntity> data) {
                sessionsDao.saveDayOneSession(data);
                Log.d("data",String.valueOf(data));
            }

            @Override
            public Function<List<SessionsModel>, List<SessionsEntity>> mapper() {
                return EntityMapper.convert();
            }
        }, BackpressureStrategy.BUFFER);
    }
}
