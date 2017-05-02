package com.mohamadamin.cleantvmaze.data.executor

import easymvp.executer.UseCaseExecutor
import rx.schedulers.Schedulers

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class ComputationThread: UseCaseExecutor {

    override fun getScheduler() =
            Schedulers.computation()

}