package com.mohamadamin.cleantvmaze.data.executor

import dagger.Module
import dagger.Provides
import easymvp.executer.PostExecutionThread
import easymvp.executer.UseCaseExecutor
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
@Singleton
@Module
class ExecutorModule {

    @Provides
    fun provideUseCaseExecutor(ioExecutor: IoExecutor): UseCaseExecutor {
        return ioExecutor
    }

    @Provides
    fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

}