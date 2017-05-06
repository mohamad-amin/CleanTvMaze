package com.mohamadamin.cleantvmaze.app

import android.app.Application
import com.mohamadamin.cleantvmaze.base.di.ApplicationComponent
import com.mohamadamin.cleantvmaze.base.di.ApplicationModule
import com.mohamadamin.cleantvmaze.base.di.DaggerApplicationComponent
import com.mohamadamin.cleantvmaze.data.DaggerDataLayerComponent
import com.mohamadamin.cleantvmaze.data.delegates.DelegatesExt
import timber.log.Timber

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
class AppController: Application() {

    companion object {
        var applicationComponent: ApplicationComponent by DelegatesExt.notNullSingleValue<ApplicationComponent>()
    }

    override fun onCreate() {
        super.onCreate()
        prepareApplicationComponent()
        Timber.plant(Timber.DebugTree())
    }

    fun prepareApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataLayerComponent(DaggerDataLayerComponent.builder().build())
                .build()
    }

}