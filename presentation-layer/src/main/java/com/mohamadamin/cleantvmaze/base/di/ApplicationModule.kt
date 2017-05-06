package com.mohamadamin.cleantvmaze.base.di

import com.mohamadamin.cleantvmaze.app.AppController
import com.mohamadamin.cleantvmaze.domain.annotation.ForApplication
import dagger.Provides
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
class ApplicationModule(val application: AppController) {

    @ForApplication
    @Singleton
    @Provides
    fun provideContext() = application.applicationContext

    @Provides
    @Singleton
    fun provideSimpleContext() = application.applicationContext

}