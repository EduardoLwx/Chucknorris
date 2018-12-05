package com.eduardo.chucknorris.ui.joke.di

import com.eduardo.chucknorris.ui.joke.JokeFragment
import dagger.Subcomponent

@JokeScope
@Subcomponent(modules = [JokeModule::class])
interface JokeComponent {

    fun inject(jokeFragment: JokeFragment)

}