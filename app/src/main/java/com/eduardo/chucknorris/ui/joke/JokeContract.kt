package com.eduardo.chucknorris.ui.joke

import com.eduardo.chucknorris.model.Joke
import com.eduardo.chucknorris.model.JokeCategory

class JokeContract {

    interface Presenter {

        fun getJoke(jokeCategory: JokeCategory)

        fun onDestroy()
    }

    interface View {
        fun onGetJoke(joke: Joke)

        fun onGetJokeFails()

        fun onGetJokeNetworkFails()
    }

}