package com.eduardo.chucknorris.ui.jokecategory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduardo.chucknorris.ChuckNorrisApp
import com.eduardo.chucknorris.R
import com.eduardo.chucknorris.model.JokeCategory
import kotlinx.android.synthetic.main.fragment_joke_category_list.*
import kotlinx.android.synthetic.main.fragment_joke_category_list.view.*
import javax.inject.Inject

private const val BUNDLE_JOKE_CATEGORY_LIST = "BUNDLE_JOKE_CATEGORY_LIST"

class JokeCategoryListFragment : Fragment(), JokeCategoryListContract.View {

    @Inject lateinit var presenter: JokeCategoryListContract.Presenter
    private lateinit var jokeCategoryListAdapter: JokeCategoryListAdapter
    private lateinit var hostActivity: HostActivity
    private var jokeCategories: List<JokeCategory> = emptyList()

    companion object {
        fun newInstance() = JokeCategoryListFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        hostActivity = context as HostActivity
        activity?.setTitle(R.string.choose_a_category)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as ChuckNorrisApp).createJokeCategoryListComponent(this).inject(this)
        jokeCategoryListAdapter = JokeCategoryListAdapter(activity!!){
            hostActivity.onSelectJokeCategory(it)
        }

        if(savedInstanceState != null){
            jokeCategories = savedInstanceState
                .getParcelableArray(BUNDLE_JOKE_CATEGORY_LIST)?.toList()?.map {
                    it as JokeCategory
                } ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_joke_category_list, container, false)
        view.btTryAgain.setOnClickListener {
            getCategories()
        }
        configRecyclerView(view)
        return view
    }

    private fun configRecyclerView(view:  View){

        val linearLayoutManager = LinearLayoutManager(activity)

        val dividerItemDecoration = DividerItemDecoration(
            view.rvJokeCategories.context,
            linearLayoutManager.orientation
        )

        view.rvJokeCategories.apply {
            layoutManager = linearLayoutManager
            adapter = jokeCategoryListAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onResume() {
        super.onResume()
        if(jokeCategories.isEmpty()) {
            getCategories()
        } else {
            bind()
        }
    }

    private fun getCategories() {
        loading?.visibility = View.VISIBLE
        failsGroup?.visibility = View.GONE
        successGroup?.visibility = View.GONE
        presenter.getCategories()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        (activity?.application as ChuckNorrisApp).destroyJokeCategoryListComponent()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        if(jokeCategoryListAdapter.categoryList.isNotEmpty()){
            outState.putParcelableArray(BUNDLE_JOKE_CATEGORY_LIST,
                jokeCategoryListAdapter.categoryList.toTypedArray())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onGetCategories(jokeCategories: List<JokeCategory>) {
        val resultList = jokeCategories.sortedBy {
            it.name
        }

        this.jokeCategories = resultList

        activity!!.runOnUiThread{
            bind()
        }
    }

    private fun bind(){
        loading?.visibility = View.GONE
        failsGroup?.visibility = View.GONE
        successGroup?.visibility = View.VISIBLE
        jokeCategoryListAdapter.categoryList = jokeCategories
        jokeCategoryListAdapter.notifyDataSetChanged()
    }

    override fun onGetCategoriesFails() {
        activity?.runOnUiThread {
            loading?.visibility = View.GONE
            successGroup?.visibility = View.GONE
            ivErrorIcon?.setImageResource(R.drawable.ic_error)
            tvErrorMsg?.setText(R.string.unexpected_error)
            failsGroup?.visibility = View.VISIBLE
        }
    }

    override fun onGetCategoriesNetworkFails() {
        activity?.runOnUiThread {
            loading?.visibility = View.GONE
            successGroup?.visibility = View.GONE
            ivErrorIcon?.setImageResource(R.drawable.ic_signal_wifi_off)
            tvErrorMsg?.setText(R.string.connection_error)
            failsGroup?.visibility = View.VISIBLE
        }
    }

    interface HostActivity {
        fun onSelectJokeCategory(jokeCategory: JokeCategory)
    }
}
