package com.eduardo.chucknorris.data

import com.eduardo.chucknorris.data.mapper.EntityMapper
import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.data.store.remote.JokeRemoteStore
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import io.reactivex.Single

class JokeRepositoryImpl(private val remoteStore: JokeRemoteStore,
                         private val jokeMapper: EntityMapper<JokeEntity, JokeModel>,
                         private val jokeCategoryMapper: EntityMapper<JokeCategoryEntity, JokeCategoryModel>)
    : JokeRepository {

    override fun getJokeCategories(): Single<List<JokeCategoryModel>> {
        return remoteStore.getJokeCategories().map { list ->
            list.map {
                jokeCategoryMapper.fromEntity(it)
            }
        }
    }

    override fun getJoke(jokeCategory: JokeCategoryModel): Single<JokeModel> {
        return remoteStore.getJoke(jokeCategoryMapper.toEntity(jokeCategory)).map {
            jokeMapper.fromEntity(it)
        }
    }

}