package com.eduardo.chucknorris

import android.app.Application
import com.eduardo.chucknorris.di.AndroidModule
import com.eduardo.chucknorris.di.AppComponent
import com.eduardo.chucknorris.di.DaggerAppComponent
import com.eduardo.chucknorris.di.JokeRepositoryModule
import com.eduardo.chucknorris.ui.joke.JokeContract
import com.eduardo.chucknorris.ui.joke.di.JokeComponent
import com.eduardo.chucknorris.ui.joke.di.JokeModule
import com.eduardo.chucknorris.ui.jokecategory.JokeCategoryListContract
import com.eduardo.chucknorris.ui.jokecategory.di.JokeCategoryListComponent
import com.eduardo.chucknorris.ui.jokecategory.di.JokeCategoryListModule


class ChuckNorrisApp : Application() {


    lateinit var appComponent: AppComponent
        private set

    private var jokeCategoryListComponent: JokeCategoryListComponent? = null
    private var jokeComponent: JokeComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .jokeRepositoryModule(JokeRepositoryModule())
            .androidModule(AndroidModule(this))
            .build()
    }

    fun createJokeCategoryListComponent(view: JokeCategoryListContract.View): JokeCategoryListComponent {
        if(jokeCategoryListComponent == null){
            jokeCategoryListComponent = appComponent.plus(JokeCategoryListModule(view))
        }
        return jokeCategoryListComponent!!
    }

    fun destroyJokeCategoryListComponent(){
        jokeCategoryListComponent = null
    }

    fun createJokeComponent(jokeView: JokeContract.View): JokeComponent {
        if(jokeComponent == null){
            jokeComponent = appComponent.plus(JokeModule(jokeView))
        }
        return jokeComponent!!
    }

    fun destroyJokeComponent(){
        jokeComponent = null
    }
}