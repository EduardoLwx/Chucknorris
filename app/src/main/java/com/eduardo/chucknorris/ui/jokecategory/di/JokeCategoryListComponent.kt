package com.eduardo.chucknorris.ui.jokecategory.di

import com.eduardo.chucknorris.ui.jokecategory.JokeCategoryListFragment
import dagger.Subcomponent

@JokeCategoryListScope
@Subcomponent(modules = [JokeCategoryListModule::class])
interface JokeCategoryListComponent{

    fun inject(jokeCategoryListFragment: JokeCategoryListFragment)

}