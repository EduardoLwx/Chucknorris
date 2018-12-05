package com.eduardo.chucknorris.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eduardo.chucknorris.ChuckNorrisApp
import com.eduardo.chucknorris.R
import com.eduardo.chucknorris.model.JokeCategory
import com.eduardo.chucknorris.ui.joke.JokeFragment
import com.eduardo.chucknorris.ui.jokecategory.JokeCategoryListFragment

class MainActivity : AppCompatActivity(), JokeCategoryListFragment.HostActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as ChuckNorrisApp).appComponent.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    JokeCategoryListFragment.newInstance()
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onSelectJokeCategory(jokeCategory: JokeCategory) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.container,
                JokeFragment.newInstance(jokeCategory)
            )
            .addToBackStack(null)
            .commit()
    }
}
