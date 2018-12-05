package com.eduardo.chucknorris.domain.usecase

import com.eduardo.chucknorris.domain.core.ObserveOnScheduler
import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import com.eduardo.chucknorris.domain.test.MockData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetJokeTest {

    private lateinit var getJoke: GetJoke
    @Mock private lateinit var observeOnScheduler: ObserveOnScheduler
    @Mock private lateinit var jokeRepository: JokeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getJoke = GetJoke(observeOnScheduler, jokeRepository)
    }

    @Test
    fun getJoke_completes(){
        stubJokeRepositoryGetJoke(Single.just(MockData.randomJokeModel()))
        getJoke.buildUseCaseSingle(GetJoke.Params(MockData.randomJokeCategoryModel())).test()
            .assertComplete()
    }

    @Test
    fun getJoke_returns(){
        val expectedResult = MockData.randomJokeModel()
        stubJokeRepositoryGetJoke(Single.just(expectedResult))
        getJoke.buildUseCaseSingle(GetJoke.Params(MockData.randomJokeCategoryModel())).test()
            .assertValue(expectedResult)
    }

    @Test
    fun getJoke_noArgumentThrowsIllegalArgumentException(){
        val expectedResult = MockData.randomJokeModel()
        stubJokeRepositoryGetJoke(Single.just(expectedResult))
        getJoke.buildUseCaseSingle().test()
            .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun getJoke_nullArgumentThrowsIllegalArgumentException(){
        val expectedResult = MockData.randomJokeModel()
        stubJokeRepositoryGetJoke(Single.just(expectedResult))
        getJoke.buildUseCaseSingle().test()
            .assertError(IllegalArgumentException::class.java)
    }

    private fun stubJokeRepositoryGetJoke(returnValue: Single<JokeModel>) {
        whenever(jokeRepository.getJoke(any())).thenReturn(returnValue)
    }

}