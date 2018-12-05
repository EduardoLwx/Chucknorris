package com.eduardo.chucknorris.ui.jokecategory

import android.util.Log
import com.eduardo.chucknorris.data.exception.NetworkConnectionException
import com.eduardo.chucknorris.domain.core.SingleUseCase
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.mapper.ModelMapper
import com.eduardo.chucknorris.model.JokeCategory
import io.reactivex.observers.DisposableSingleObserver

class JokeCategoryListPresenter(private val view: JokeCategoryListContract.View,
                                private val jokeCategoryModelMapper: ModelMapper<JokeCategoryModel, JokeCategory>,
                                private val getJokeCategories: SingleUseCase<List<JokeCategoryModel>, Nothing?>)
    : JokeCategoryListContract.Presenter {

    override fun getCategories() {
        getJokeCategories.execute(object : DisposableSingleObserver<List<JokeCategoryModel>>(){
            override fun onSuccess(list: List<JokeCategoryModel>) {
                    view.onGetCategories(list.map {
                        jokeCategoryModelMapper.fromModel(it)
                    })
            }

            override fun onError(e: Throwable) {
                if(e is NetworkConnectionException){
                    view.onGetCategoriesNetworkFails()
                } else {
                    Log.e("GetJokeCategories", "fails", e)
                    view.onGetCategoriesFails()
                }
            }
        })
    }

    override fun onDestroy() {
        getJokeCategories.dispose()
    }
}