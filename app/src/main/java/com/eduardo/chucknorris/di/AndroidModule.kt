package com.eduardo.chucknorris.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AndroidModule(val app: Application) {

    @Provides
    fun providesContext(): Context {
        return app
    }

}