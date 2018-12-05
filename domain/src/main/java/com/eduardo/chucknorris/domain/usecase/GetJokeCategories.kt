package com.eduardo.chucknorris.domain.usecase

import com.eduardo.chucknorris.domain.core.ObserveOnScheduler
import com.eduardo.chucknorris.domain.core.SingleUseCase
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import io.reactivex.Single

class GetJokeCategories(observeOnScheduler: ObserveOnScheduler, private val jokeRepository: JokeRepository)
    : SingleUseCase<List<JokeCategoryModel>, Nothing?>(observeOnScheduler) {

    override fun buildUseCaseSingle(params: Nothing?): Single<List<JokeCategoryModel>> {
        return jokeRepository.getJokeCategories()
    }
}