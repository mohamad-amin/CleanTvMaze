package com.mohamadamin.cleantvmaze.data.utils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import rx.plugins.RxJavaHooks
import rx.schedulers.Schedulers

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/4/17.
 */
class TrampolineSchedulerRule: TestRule {

    override fun apply(base: Statement?, description: Description?) = object: Statement() {
        override fun evaluate() {
            RxJavaHooks.setOnNewThreadScheduler { Schedulers.trampoline() }
            base?.evaluate()
            RxJavaHooks.reset()
        }
    }

}