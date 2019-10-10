package com.sevenpeakssoftware.cars.api

import com.sevenpeakssoftware.cars.repository.NetworkBoundSource
import com.sevenpeakssoftware.cars.repository.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.atomic.AtomicReference

class NetworkBoundResourceTest {

    private lateinit var handleSaveCallResult: (Data) -> Unit

    private lateinit var handleCreateCall: () -> Single<Data>

    private lateinit var handleLoadFromDb: () -> Flowable<Data>

    private lateinit var source: Flowable<Resource<Data>>


    @Before
    fun init() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        RxAndroidPlugins.setMainThreadSchedulerHandler { sheduler -> Schedulers.trampoline() }

        RxJavaPlugins.setIoSchedulerHandler { schedular -> Schedulers.trampoline() }

        source = Flowable.create({ emitter ->
            object : NetworkBoundSource<Data, Data>(emitter) {

                override fun createCall(): Single<Data> {
                    return handleCreateCall()
                }

                override fun loadFromDb(): Flowable<Data> {
                    return handleLoadFromDb()
                }

                override fun saveCallResult(data: Data) {
                    handleSaveCallResult(data)
                }
            }

        }, BackpressureStrategy.BUFFER)

    }


    @Test
    fun dataFromNetwork() {
        val saved = AtomicReference<Data>()

        handleSaveCallResult = { data ->
            saved.set(data)
        }
        val networkResult = Data(1)

        val dbData = Data(1)
        handleCreateCall = {

            Single.create {
                it.onSuccess(networkResult)
            }

        }
        handleLoadFromDb = {
            Flowable.just(dbData)
        }


        val subscriber = object : DisposableSubscriber<Resource<Data>>() {
            override fun onComplete() {
            }

            override fun onNext(t: Resource<Data>?) {
            }

            override fun onError(t: Throwable?) {
            }

        }

        val spySubscriber = Mockito.spy(subscriber)
        source.subscribe(spySubscriber)

        Mockito.verify(spySubscriber).onNext(Resource.loading(dbData))
        MatcherAssert.assertThat(saved.get(), CoreMatchers.`is`(networkResult))
        Mockito.verify(spySubscriber).onNext(Resource.success(dbData))
    }


    private data class Data(var value: Int)

}