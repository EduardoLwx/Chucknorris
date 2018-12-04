package com.eduardo.chucknorris.data

import com.eduardo.chucknorris.data.mapper.EntityMapper
import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.data.store.remote.JokeRemoteStore
import com.eduardo.chucknorris.data.test.MockData
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class JokeRepositoryImplTest {

    private lateinit var jokeRepositoryImpl: JokeRepositoryImpl
    @Mock private lateinit var remoteStore: JokeRemoteStore
    @Mock private lateinit var jokeMapper: EntityMapper<JokeEntity, JokeModel>
    @Mock private lateinit var jokeCategoryMapper: EntityMapper<JokeCategoryEntity, JokeCategoryModel>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        jokeRepositoryImpl = JokeRepositoryImpl(remoteStore, jokeMapper, jokeCategoryMapper)
    }

    @Test
    fun getJokeCategories_completes() {
        stubRemoteStore_getJokeCategories(Single.just(MockData.randomJokeCategoryEntityList(2)))
        stubJokeCategoryMapper_fromEntity(MockData.randomJokeCategoryModel())
        jokeRepositoryImpl.getJokeCategories().test()
            .assertComplete()
    }

    @Test
    fun getJokeCategories_callsJokeCategoryMapperFromEntity(){
        val jokeCategoryEntity = MockData.randomJokeCategoryEntity()
        stubRemoteStore_getJokeCategories(Single.just(listOf(jokeCategoryEntity)))
        stubJokeCategoryMapper_fromEntity(MockData.randomJokeCategoryModel())
        jokeRepositoryImpl.getJokeCategories().test()
        verify(jokeCategoryMapper).fromEntity(jokeCategoryEntity)
    }

    @Test
    fun getJokeCategories_callsRemoteStoreGetJokeCategories() {
        stubRemoteStore_getJokeCategories(Single.just(MockData.randomJokeCategoryEntityList(1)))
        val jokeCategoryModel = MockData.randomJokeCategoryModel()
        stubJokeCategoryMapper_fromEntity(jokeCategoryModel)
        jokeRepositoryImpl.getJokeCategories().test()
        verify(remoteStore).getJokeCategories()
    }


    @Test
    fun getJokeCategories_returns() {
        stubRemoteStore_getJokeCategories(Single.just(MockData.randomJokeCategoryEntityList(1)))
        val jokeCategoryModel = MockData.randomJokeCategoryModel()
        stubJokeCategoryMapper_fromEntity(jokeCategoryModel)
        jokeRepositoryImpl.getJokeCategories().test()
            .assertValue(listOf(jokeCategoryModel))
    }

    @Test
    fun getJoke_completes() {
        stubRemoteStore_getJoke(Single.just(MockData.randomJokeEntity()))
        stubJokeCategoryMapper_toEntity(MockData.randomJokeCategoryEntity())
        stubJokeMapper_fromEntity(MockData.randomJokeModel())
        jokeRepositoryImpl.getJoke(MockData.randomJokeCategoryModel()).test()
            .assertComplete()
    }

    @Test
    fun getJoke_callsJokeMapperFromEntity() {
        val jokeEntity = MockData.randomJokeEntity()
        stubRemoteStore_getJoke(Single.just(jokeEntity))
        stubJokeCategoryMapper_toEntity(MockData.randomJokeCategoryEntity())
        stubJokeMapper_fromEntity(MockData.randomJokeModel())
        jokeRepositoryImpl.getJoke(MockData.randomJokeCategoryModel()).test()
        verify(jokeMapper).fromEntity(jokeEntity)
    }

    @Test
    fun getJoke_callsJokeCategoryMapperToEntity(){
        stubRemoteStore_getJoke(Single.just(MockData.randomJokeEntity()))
        stubJokeCategoryMapper_toEntity(MockData.randomJokeCategoryEntity())
        stubJokeMapper_fromEntity(MockData.randomJokeModel())
        val jokeCategoryModel = MockData.randomJokeCategoryModel()
        jokeRepositoryImpl.getJoke(jokeCategoryModel).test()
        verify(jokeCategoryMapper).toEntity(jokeCategoryModel)
    }

    @Test
    fun getJoke_callsRemoteStoreGetJoke() {
        stubRemoteStore_getJoke(Single.just(MockData.randomJokeEntity()))
        val jokeCategoryEntity = MockData.randomJokeCategoryEntity()
        stubJokeCategoryMapper_toEntity(jokeCategoryEntity)
        stubJokeMapper_fromEntity(MockData.randomJokeModel())
        jokeRepositoryImpl.getJoke(MockData.randomJokeCategoryModel()).test()
        verify(remoteStore).getJoke(jokeCategoryEntity)
    }

    @Test
    fun getJoke_returns() {
        stubRemoteStore_getJoke(Single.just(MockData.randomJokeEntity()))
        stubJokeCategoryMapper_toEntity(MockData.randomJokeCategoryEntity())
        val jokeModel = MockData.randomJokeModel()
        stubJokeMapper_fromEntity(jokeModel)
        jokeRepositoryImpl.getJoke(MockData.randomJokeCategoryModel()).test()
            .assertValue(jokeModel)
    }

    private fun stubRemoteStore_getJokeCategories(returnValue: Single<List<JokeCategoryEntity>>){
        whenever(remoteStore.getJokeCategories()).thenReturn(returnValue)
    }

    private fun stubRemoteStore_getJoke(returnValue: Single<JokeEntity>){
        whenever(remoteStore.getJoke(any())).thenReturn(returnValue)
    }

    private fun stubJokeCategoryMapper_toEntity(returnValue: JokeCategoryEntity){
        whenever(jokeCategoryMapper.toEntity(any())).thenReturn(returnValue)
    }

    private fun stubJokeCategoryMapper_fromEntity(returnValue: JokeCategoryModel){
        whenever(jokeCategoryMapper.fromEntity(any())).thenReturn(returnValue)
    }

    private fun stubJokeMapper_fromEntity(returnValue: JokeModel){
        whenever(jokeMapper.fromEntity(any())).thenReturn(returnValue)
    }
}