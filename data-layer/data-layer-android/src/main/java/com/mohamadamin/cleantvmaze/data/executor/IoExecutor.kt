package com.mohamadamin.cleantvmaze.data.executor

import easymvp.executer.UseCaseExecutor
import rx.Scheduler
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
class IoExecutor @Inject constructor(): UseCaseExecutor {

    override fun getScheduler(): Scheduler {
        return Schedulers.io()
    }

}