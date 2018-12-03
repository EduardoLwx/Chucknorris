package com.eduardo.chucknorris.domain.usecase

import com.eduardo.chucknorris.domain.core.ObserveOnScheduler
import com.eduardo.chucknorris.domain.core.SingleUseCase
import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import io.reactivex.Single


class GetJoke(observeOnScheduler: ObserveOnScheduler, private val jokeRepository: JokeRepository)
    : SingleUseCase<JokeModel, GetJoke.Params> (observeOnScheduler){



    override fun buildUseCaseSingle(params: Params?): Single<JokeModel> {
        return if (params != null){
            jokeRepository.getJoke(params.jokeCategory)
        } else {
            Single.error(IllegalArgumentException("Params can't be null!"))
        }

    }

    data class Params(val jokeCategory: JokeCategoryModel)
}