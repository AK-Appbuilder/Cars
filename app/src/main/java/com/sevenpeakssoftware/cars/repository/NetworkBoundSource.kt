package com.sevenpeakssoftware.cars.repository

import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundSource<LocalType, RemoteType>(emitter: FlowableEmitter<Resource<LocalType>>) {

    init {
        val firstDataDisposable = loadFromDb()
            .map { Resource.loading<LocalType>(it) }
            .subscribe { emitter.onNext(it) }

        createCall()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    firstDataDisposable.dispose()
                    saveCallResult(response)
                    loadFromDb().map { Resource.success<LocalType>(it) }
                        .subscribe {
                            emitter.onNext(it)
                        }

                }
            ) {
                loadFromDb().map { data ->
                    Resource.error<LocalType>(it.toString(), data)

                }
                    .subscribe {
                        emitter.onNext(it)
                    }

            }
    }

    abstract fun createCall(): Single<RemoteType>

    abstract fun loadFromDb(): Flowable<LocalType>

    abstract fun saveCallResult(data: RemoteType)

}
