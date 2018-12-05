package com.eduardo.chucknorris.data.store.remote

import com.eduardo.chucknorris.data.exception.NetworkConnectionException
import com.eduardo.chucknorris.data.exception.RequestFailException
import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.data.store.remote.api.JokeApi
import com.eduardo.chucknorris.data.utils.NetworkUtils
import io.reactivex.Single

class JokeRemoteStoreImpl(private val networkUtils: NetworkUtils, private val jokeApi: JokeApi):
    JokeRemoteStore {

    override fun getJokeCategories(): Single<List<JokeCategoryEntity>> {
        return if(networkUtils.isNetworkAvailable()){
            jokeApi.getJokeCategories().map { response ->
                if(response.isSuccessful){
                    response.body()?.map {
                        JokeCategoryEntity(it)
                    }
                } else {
                    throw RequestFailException(response.code())
                }
            }
        } else {
            Single.error(NetworkConnectionException())
        }
    }

    override fun getJoke(jokeCategory: JokeCategoryEntity): Single<JokeEntity> {
        return if(networkUtils.isNetworkAvailable()){
            jokeApi.getJoke(jokeCategory.name).map { response ->
                if (response.isSuccessful){
                    response.body()
                } else {
                    throw RequestFailException(response.code())
                }
            }
        } else {
            Single.error(NetworkConnectionException())
        }
    }

}