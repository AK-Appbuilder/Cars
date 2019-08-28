package com.sevenpeakssoftware.cars.repository

import com.sevenpeakssoftware.cars.api.ApiEmptyResponse
import com.sevenpeakssoftware.cars.api.ApiErrorResponse
import com.sevenpeakssoftware.cars.api.ApiResponse
import com.sevenpeakssoftware.cars.api.ApiSuccessResponse
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundSource<LocalType, RemoteType>(emitter: FlowableEmitter<Resource<LocalType>>) {




    init {


        val firstDataDisposable = loadFromDb()
            .map { it -> Resource.loading<LocalType>(it) }
            .subscribe { it -> emitter.onNext(it) }

        createCall()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({
                    response ->
                firstDataDisposable.dispose()

                        saveCallResult(response)
                        loadFromDb().map { it -> Resource.success<LocalType>(it) }
                            .subscribe(Consumer<Resource<LocalType>> {
                                emitter.onNext(it)
                            })



                },

                {
                    loadFromDb().map { it -> Resource.error<LocalType>(it.toString(), it) }
                        .subscribe(Consumer<Resource<LocalType>> {
                            emitter.onNext(it)
                        })

                })
    }


    abstract fun createCall(): Single<RemoteType>

    abstract fun loadFromDb(): Flowable<LocalType>

    abstract fun saveCallResult(data: RemoteType)

}
