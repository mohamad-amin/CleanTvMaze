package com.mohamadamin.cleantvmaze.base.di

import com.mohamadamin.cleantvmaze.app.AppController
import com.mohamadamin.cleantvmaze.domain.annotation.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
@Module
class ApplicationModule(val application: AppController) {

    @ForApplication
    @Provides
    fun provideContext() = application.applicationContext

    @Provides
    fun provideSimpleContext() = application.applicationContext

}