package com.eduardo.chucknorris.data.store.remote

import com.eduardo.chucknorris.data.exception.NetworkConnectionException
import com.eduardo.chucknorris.data.exception.RequestFailException
import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.data.store.remote.api.JokeApi
import com.eduardo.chucknorris.data.test.MockData
import com.eduardo.chucknorris.data.utils.NetworkUtils
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class JokeRemoteStoreImplTest {

    private lateinit var jokeRemoteStoreImpl: JokeRemoteStoreImpl
    @Mock private lateinit var networkUtils: NetworkUtils
    @Mock private lateinit var jokeApi: JokeApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        jokeRemoteStoreImpl = JokeRemoteStoreImpl(networkUtils, jokeApi)
    }

    @Test
    fun getJokeCategories_completes() {
        stubNetworkUtils_isNetworkAvailable(true)
        val response = Response.success(200, List(2){
            MockData.randomString()
        })
        stubJokeApi_getJokeCategories(Single.just(response))
        jokeRemoteStoreImpl.getJokeCategories().test()
            .assertComplete()
    }

    @Test
    fun getJokeCategories_returns() {
        stubNetworkUtils_isNetworkAvailable(true)
        val strList = List(2){
            MockData.randomString()
        }
        val response = Response.success(200, strList)
        stubJokeApi_getJokeCategories(Single.just(response))
        jokeRemoteStoreImpl.getJokeCategories().test()
            .assertValue(strList.map {
                JokeCategoryEntity(it)
            })
    }

    @Test
    fun getJokeCategories_throwsNetworkConnectionException() {
        stubNetworkUtils_isNetworkAvailable(false)
        val response = Response.success(200, List(2){
            MockData.randomString()
        })
        stubJokeApi_getJokeCategories(Single.just(response))
        jokeRemoteStoreImpl.getJokeCategories().test()
            .assertError(NetworkConnectionException::class.java)
    }

    @Test
    fun getJokeCategories_throwsRequestFailException() {
        stubNetworkUtils_isNetworkAvailable(true)
        val response = Response.error<List<String>>(500,
            ResponseBody.create(MediaType.parse("text/plain"), "unexpected error"))
        stubJokeApi_getJokeCategories(Single.just(response))
        jokeRemoteStoreImpl.getJokeCategories().test()
            .assertError(RequestFailException::class.java)
    }


    @Test
    fun getJoke_completes() {
        stubNetworkUtils_isNetworkAvailable(true)
        val response = Response.success(200, MockData.randomJokeEntity())
        stubJokeApi_getJoke(Single.just(response))
        jokeRemoteStoreImpl.getJoke(MockData.randomJokeCategoryEntity()).test()
            .assertComplete()
    }


    @Test
    fun getJoke_returns() {
        stubNetworkUtils_isNetworkAvailable(true)
        val jokeEntity = MockData.randomJokeEntity()
        val response = Response.success(200, jokeEntity)
        stubJokeApi_getJoke(Single.just(response))
        jokeRemoteStoreImpl.getJoke(MockData.randomJokeCategoryEntity()).test()
            .assertValue(jokeEntity)
    }

    @Test
    fun getJoke_throwsNetworkConnectionException() {
        stubNetworkUtils_isNetworkAvailable(false)
        val response = Response.success(200, MockData.randomJokeEntity())
        stubJokeApi_getJoke(Single.just(response))
        jokeRemoteStoreImpl.getJoke(MockData.randomJokeCategoryEntity()).test()
            .assertError(NetworkConnectionException::class.java)
    }

    @Test
    fun getJoke_throwsRequestFailException() {
        stubNetworkUtils_isNetworkAvailable(true)
        val response = Response.error<JokeEntity>(500,
            ResponseBody.create(MediaType.parse("text/plain"), "unexpected error"))
        stubJokeApi_getJoke(Single.just(response))
        jokeRemoteStoreImpl.getJoke(MockData.randomJokeCategoryEntity()).test()
            .assertError(RequestFailException::class.java)
    }



    private fun stubNetworkUtils_isNetworkAvailable(returnValue: Boolean){
        whenever(networkUtils.isNetworkAvailable()).thenReturn(returnValue)
    }

    private fun stubJokeApi_getJoke(returnValue: Single<Response<JokeEntity>>){
        whenever(jokeApi.getJoke(any())).thenReturn(returnValue)
    }

    private fun stubJokeApi_getJokeCategories(returnValue: Single<Response<List<String>>>){
        whenever(jokeApi.getJokeCategories()).thenReturn(returnValue)
    }
}