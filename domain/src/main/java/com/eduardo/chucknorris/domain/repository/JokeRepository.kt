package com.eduardo.chucknorris.domain.repository

import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import io.reactivex.Single

interface JokeRepository {

    fun getJokeCategories(): Single<List<JokeCategoryModel>>

    fun getJoke(jokeCategory: JokeCategoryModel): Single<JokeModel>

}