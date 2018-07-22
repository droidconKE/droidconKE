package droiddevelopers254.droidconke.utils.network;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundSource<LocalType,RemoteType> {

    public NetworkBoundSource(FlowableEmitter<Resource<LocalType>> emitter) {
        Disposable firstDataDisposable = getLocal()
                .map(Resource::loading)
                .subscribe(emitter::onNext);

        getRemote().map(mapper())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(localTypeData -> {
                    firstDataDisposable.dispose();
                    saveCallResult(localTypeData);
                    getLocal().map(Resource::success).subscribe(emitter::onNext);
                });
    }

    public abstract Single<RemoteType> getRemote();

    public abstract Flowable<LocalType> getLocal();

    public abstract void saveCallResult(LocalType data);

    public abstract Function<RemoteType, LocalType> mapper();


}
