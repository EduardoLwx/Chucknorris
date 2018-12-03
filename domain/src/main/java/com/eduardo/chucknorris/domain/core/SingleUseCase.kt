package com.eduardo.chucknorris.domain.core

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Params> constructor(private val observeOnScheduler: ObserveOnScheduler) {

    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseSingle(params: Params? = null): Single<T>

    fun execute(singleObserver: DisposableSingleObserver<T>, params: Params?) {
        val single = this.buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(observeOnScheduler.scheduler)
        disposables.add(single.subscribeWith(singleObserver))
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

}