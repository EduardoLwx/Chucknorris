package com.eduardo.chucknorris.di

import com.eduardo.chucknorris.ui.MainActivity
import com.eduardo.chucknorris.ui.joke.di.JokeComponent
import com.eduardo.chucknorris.ui.joke.di.JokeModule
import com.eduardo.chucknorris.ui.jokecategory.di.JokeCategoryListComponent
import com.eduardo.chucknorris.ui.jokecategory.di.JokeCategoryListModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [JokeRepositoryModule::class, AndroidModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun plus(jokeCategoryListModule: JokeCategoryListModule): JokeCategoryListComponent

    fun plus(jokeModule: JokeModule): JokeComponent

}