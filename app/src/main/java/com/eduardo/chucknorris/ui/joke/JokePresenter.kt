package com.eduardo.chucknorris.ui.joke

import android.util.Log
import com.eduardo.chucknorris.data.exception.NetworkConnectionException
import com.eduardo.chucknorris.domain.core.SingleUseCase
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.domain.usecase.GetJoke
import com.eduardo.chucknorris.mapper.ModelMapper
import com.eduardo.chucknorris.model.Joke
import com.eduardo.chucknorris.model.JokeCategory
import io.reactivex.observers.DisposableSingleObserver

class JokePresenter(private val view: JokeContract.View,
                    private val jokeMapper:  ModelMapper<JokeModel, Joke>,
                    private val categoryMapper: ModelMapper<JokeCategoryModel, JokeCategory>,
                    private val getJoke: SingleUseCase<JokeModel, GetJoke.Params>) : JokeContract.Presenter {

    override fun getJoke(jokeCategory: JokeCategory) {
        getJoke.execute( params = GetJoke.Params(categoryMapper.toModel(jokeCategory)),
            singleObserver = object : DisposableSingleObserver<JokeModel>(){
                override fun onSuccess(jokeModel: JokeModel) {
                    view.onGetJoke(jokeMapper.fromModel(jokeModel))
                }

                override fun onError(e: Throwable) {
                    if(e is NetworkConnectionException){
                        view.onGetJokeNetworkFails()
                    } else {
                        Log.e("GetJoke", "fails", e)
                        view.onGetJokeFails()
                    }
                }
        })
    }

    override fun onDestroy() {
        getJoke.dispose()
    }
}