package com.eduardo.chucknorris.data.store.remote.api

import com.eduardo.chucknorris.data.model.JokeEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApi {

    @GET("categories")
    fun getJokeCategories(): Single<Response<List<String>>>

    @GET("random")
    fun getJoke(@Query("category") categoryName: String): Single<Response<JokeEntity>>

}