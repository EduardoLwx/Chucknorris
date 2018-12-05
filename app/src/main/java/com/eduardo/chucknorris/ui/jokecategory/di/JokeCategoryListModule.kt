package com.eduardo.chucknorris.ui.jokecategory.di

import com.eduardo.chucknorris.domain.core.ObserveOnScheduler
import com.eduardo.chucknorris.domain.core.SingleUseCase
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.repository.JokeRepository
import com.eduardo.chucknorris.domain.usecase.GetJokeCategories
import com.eduardo.chucknorris.mapper.JokeCategoryModelMapper
import com.eduardo.chucknorris.mapper.ModelMapper
import com.eduardo.chucknorris.model.JokeCategory
import com.eduardo.chucknorris.ui.jokecategory.JokeCategoryListContract
import com.eduardo.chucknorris.ui.jokecategory.JokeCategoryListPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

@Module
class JokeCategoryListModule(private val jokeCategoryListContractView: JokeCategoryListContract.View) {

    @Provides
    fun providesJokeCategoryListView(): JokeCategoryListContract.View {
        return jokeCategoryListContractView
    }

    @Provides
    fun providesJokeCategoryListPresenter(view: JokeCategoryListContract.View,
                                          mapper: ModelMapper<JokeCategoryModel, JokeCategory>,
                                          getJokeCategories: SingleUseCase<List<JokeCategoryModel>, Nothing?> )
            : JokeCategoryListContract.Presenter {

        return JokeCategoryListPresenter(view, mapper, getJokeCategories)
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
    fun providesGetJokeCategories(observeOnScheduler: ObserveOnScheduler, jokeRepository: JokeRepository)
            : SingleUseCase<List<JokeCategoryModel>, Nothing?> {

        return GetJokeCategories(observeOnScheduler, jokeRepository)
    }

    @Provides
    fun providesJokeCategoryModelMapper(): ModelMapper<JokeCategoryModel, JokeCategory> {
        return JokeCategoryModelMapper()
    }

}