package com.eduardo.chucknorris.ui.jokecategory

import com.eduardo.chucknorris.model.JokeCategory

class JokeCategoryListContract {

    interface Presenter {

        fun getCategories()

        fun onDestroy()

    }

    interface View {

        fun onGetCategories(jokeCategories: List<JokeCategory>)

        fun onGetCategoriesFails()

        fun onGetCategoriesNetworkFails()

    }


}