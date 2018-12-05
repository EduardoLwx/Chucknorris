package com.eduardo.chucknorris.ui.joke.di

import com.eduardo.chucknorris.domain.core.ObserveOnScheduler
import com.eduardo.chucknorris.domain.core.SingleUseCase
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import com.eduardo.chucknorris.domain.usecase.GetJoke
import com.eduardo.chucknorris.mapper.JokeCategoryModelMapper
import com.eduardo.chucknorris.mapper.JokeModelMapper
import com.eduardo.chucknorris.mapper.ModelMapper
import com.eduardo.chucknorris.model.Joke
import com.eduardo.chucknorris.model.JokeCategory
import com.eduardo.chucknorris.ui.joke.JokeContract
import com.eduardo.chucknorris.ui.joke.JokePresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

@Module
class JokeModule(private val jokeView: JokeContract.View) {

    @Provides
    fun providesJokeContractView(): JokeContract.View {
        return jokeView
    }

    @Provides
    fun providesJokeContractPresenter(view: JokeContract.View,
                                      jokeMapper: ModelMapper<JokeModel, Joke>,
                                      categoryMapper: ModelMapper<JokeCategoryModel, JokeCategory>,
                                      getJoke: SingleUseCase<JokeModel, GetJoke.Params>) : JokeContract.Presenter {

        return JokePresenter(view, jokeMapper, categoryMapper, getJoke)
    }

    @Provides
    fun providesObserveOnScheduler() : ObserveOnScheduler {
        return object : ObserveOnScheduler {
            override fun getScheduler(): Scheduler {
                return Schedulers.io()
            }
        }
    }

    @Provides
    fun providesGetJokes(observeOnScheduler: ObserveOnScheduler, jokeRepository: JokeRepository)
            : SingleUseCase<JokeModel, GetJoke.Params> {
        return GetJoke(observeOnScheduler, jokeRepository)
    }

    @Provides
    fun providesJokeModelMapper(): ModelMapper<JokeModel, Joke>{
        return JokeModelMapper()
    }

    @Provides
    fun providesJokeCategoryModelMapper(): ModelMapper<JokeCategoryModel, JokeCategory> {
        return JokeCategoryModelMapper()
    }
}