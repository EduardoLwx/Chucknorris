package com.eduardo.chucknorris.di

import android.content.Context
import com.eduardo.chucknorris.data.JokeRepositoryImpl
import com.eduardo.chucknorris.data.mapper.EntityMapper
import com.eduardo.chucknorris.data.mapper.JokeCategoryEntityMapper
import com.eduardo.chucknorris.data.mapper.JokeEntityMapper
import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.data.store.remote.JokeRemoteStore
import com.eduardo.chucknorris.data.store.remote.JokeRemoteStoreImpl
import com.eduardo.chucknorris.data.store.remote.api.JokeApi
import com.eduardo.chucknorris.data.store.remote.api.Remote
import com.eduardo.chucknorris.data.utils.NetworkUtils
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import dagger.Module
import dagger.Provides

@Module
class JokeRepositoryModule{

    @Provides
    fun providesNetworkUtils(ctx: Context): NetworkUtils{
        return NetworkUtils(ctx)
    }

    @Provides
    fun providesJokeApi(): JokeApi{
        return Remote.getJokeApi()
    }

    @Provides
    fun providesJokeRemoteStore(networkUtils: NetworkUtils, jokeApi: JokeApi): JokeRemoteStore {
        return JokeRemoteStoreImpl(networkUtils, jokeApi)
    }

    @Provides
    fun providesJokeEntityMapper(): EntityMapper<JokeEntity, JokeModel> {
        return JokeEntityMapper()
    }

    @Provides
    fun providesJokeCategoryEntityMapper(): EntityMapper<JokeCategoryEntity, JokeCategoryModel> {
        return JokeCategoryEntityMapper()
    }

    @Provides
    fun providesJokeRepository(jokeRemoteStore: JokeRemoteStore, jokeMapper: EntityMapper<JokeEntity, JokeModel>,
                               jokeCategoryMapper: EntityMapper<JokeCategoryEntity, JokeCategoryModel>): JokeRepository {
        return JokeRepositoryImpl(jokeRemoteStore, jokeMapper, jokeCategoryMapper)
    }
}