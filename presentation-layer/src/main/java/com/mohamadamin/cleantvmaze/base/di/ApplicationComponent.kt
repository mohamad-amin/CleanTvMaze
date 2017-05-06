package com.mohamadamin.cleantvmaze.base.di

import android.content.Context
import com.mohamadamin.cleantvmaze.data.DataLayerComponent
import com.mohamadamin.cleantvmaze.domain.annotation.ForApplication
import com.mohamadamin.cleantvmaze.page.main.MainActivity
import dagger.Component

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
@Component(
        modules = arrayOf(ApplicationModule::class),
        dependencies = arrayOf(DataLayerComponent::class)
)
interface ApplicationComponent {

    fun injectTo(activity: MainActivity)

    @ForApplication
    fun provideApplicationContext(): Context

    fun provideSimpleContext(): Context

}