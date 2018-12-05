package com.eduardo.chucknorris.data.store.remote

import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import io.reactivex.Single

interface JokeRemoteStore {

    fun getJokeCategories(): Single<List<JokeCategoryEntity>>

    fun getJoke(jokeCategory: JokeCategoryEntity): Single<JokeEntity>

}