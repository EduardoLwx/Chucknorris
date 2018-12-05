package com.eduardo.chucknorris.domain.usecase

import com.eduardo.chucknorris.domain.core.ObserveOnScheduler
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import com.eduardo.chucknorris.domain.test.MockData
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.random.Random

class GetJokeCategoriesTest {

    private lateinit var getJokeCategories: GetJokeCategories
    @Mock private lateinit var observeOnScheduler: ObserveOnScheduler
    @Mock private lateinit var jokeRepository: JokeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getJokeCategories = GetJokeCategories(observeOnScheduler, jokeRepository)
    }

    @Test
    fun getJokeCategories_completes() {
        stubJokeRepository_getJokeCategories(Single.just(mockRandomJokeCategoryModelList()))
        getJokeCategories.buildUseCaseSingle().test()
            .assertComplete()
    }

    @Test
    fun getJokeCategories_returns() {
        val listResult = mockRandomJokeCategoryModelList()
        stubJokeRepository_getJokeCategories(Single.just(listResult))
        getJokeCategories.buildUseCaseSingle().test()
            .assertValue(listResult)
    }

    private fun stubJokeRepository_getJokeCategories(resultValue: Single<List<JokeCategoryModel>>){
        whenever(jokeRepository.getJokeCategories()).thenReturn(resultValue)
    }

    private fun mockRandomJokeCategoryModelList(): List<JokeCategoryModel> {
        val numOfCategories = Random.nextInt(5) + 1
        return List(numOfCategories) {
            MockData.randomJokeCategoryModel()
        }
    }
}