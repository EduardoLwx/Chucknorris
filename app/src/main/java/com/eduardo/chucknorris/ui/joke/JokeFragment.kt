package com.eduardo.chucknorris.ui.joke

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eduardo.chucknorris.ChuckNorrisApp
import com.eduardo.chucknorris.R
import com.eduardo.chucknorris.model.Joke
import com.eduardo.chucknorris.model.JokeCategory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_joke.*
import kotlinx.android.synthetic.main.fragment_joke.view.*
import javax.inject.Inject

private const val BUNDLE_JOKE_CATEGORY = "BUNDLE_JOKE_CATEGORY"
private const val BUNDLE_JOKE = "BUNDLE_JOKE"

class JokeFragment : Fragment(), JokeContract.View {

    @Inject lateinit var presenter: JokeContract.Presenter
    private lateinit var jokeCategory: JokeCategory
    private var joke : Joke? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as ChuckNorrisApp).createJokeComponent(this).inject(this)

        jokeCategory = arguments?.getParcelable(BUNDLE_JOKE_CATEGORY) as JokeCategory
        activity?.title = jokeCategory.name

        if(savedInstanceState != null){
            joke = savedInstanceState.getParcelable(BUNDLE_JOKE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_joke, container, false)
        view.btTryAgain.setOnClickListener {
            getJoke()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if(joke == null) {
            getJoke()
        } else {
            bind(joke!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(BUNDLE_JOKE, joke)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        (activity?.application as ChuckNorrisApp).destroyJokeComponent()
    }

    private fun getJoke(){
        loading?.visibility = View.VISIBLE
        failsGroup?.visibility = View.GONE
        successGroup?.visibility = View.GONE
        presenter.getJoke(jokeCategory)
    }

    override fun onGetJoke(joke: Joke) {
        this.joke = joke
        activity?.runOnUiThread {
            bind(joke)
        }
    }

    private fun bind(joke: Joke){
        if(ivIcon != null) {
            Picasso.get().load(joke.iconUrl).apply {
                error(R.drawable.ic_broken_image)
                into(ivIcon, object : Callback {
                    private fun showJoke() {
                        tvJoke?.text = joke.value
                        tvLink?.text = joke.url

                        loading?.visibility = View.GONE
                        failsGroup?.visibility = View.GONE
                        successGroup?.visibility = View.VISIBLE
                    }

                    override fun onSuccess() {
                        showJoke()
                    }

                    override fun onError(e: Exception?) {
                        showJoke()
                        Log.e("Picasso", "Load image fails", e)
                    }
                })
            }
        }
    }

    override fun onGetJokeFails() {
        activity?.runOnUiThread {
            loading?.visibility = View.GONE
            successGroup?.visibility = View.GONE
            ivErrorIcon?.setImageResource(R.drawable.ic_error)
            tvErrorMsg?.setText(R.string.unexpected_error)
            failsGroup?.visibility = View.VISIBLE
        }
    }

    override fun onGetJokeNetworkFails() {
        activity?.runOnUiThread {
            loading?.visibility = View.GONE
            successGroup?.visibility = View.GONE
            ivErrorIcon?.setImageResource(R.drawable.ic_signal_wifi_off)
            tvErrorMsg?.setText(R.string.connection_error)
            failsGroup?.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance(jokeCategory: JokeCategory) =
            JokeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_JOKE_CATEGORY, jokeCategory)
                }
            }
    }
}
