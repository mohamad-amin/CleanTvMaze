package com.mohamadamin.cleantvmaze.app

import android.app.Application
import com.mohamadamin.cleantvmaze.base.di.ApplicationComponent

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
class AppController: Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        prepareApplicationComponent()
    }

    fun prepareApplicationComponent() {

    }


}