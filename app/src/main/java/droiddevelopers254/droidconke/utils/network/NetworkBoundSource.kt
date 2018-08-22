package droiddevelopers254.droidconke.utils.network

import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundSource<LocalType, RemoteType>(emitter: FlowableEmitter<Resource<LocalType>>) {

    abstract val remote: Single<RemoteType>

    abstract val local: Flowable<LocalType>

    init {
        val firstDataDisposable = local
                .map { Resource.loading(it) }
                .subscribe(Consumer<Resource<LocalType>> { emitter.onNext(it) })

        remote.map(mapper())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe { localTypeData ->
                    firstDataDisposable.dispose()
                    saveCallResult(localTypeData)
                    local.map { Resource.success(it) }.subscribe(Consumer<Resource<LocalType>> { emitter.onNext(it) })
                }
    }

    abstract fun saveCallResult(data: LocalType)

    abstract fun mapper(): Function<RemoteType, LocalType>


}
