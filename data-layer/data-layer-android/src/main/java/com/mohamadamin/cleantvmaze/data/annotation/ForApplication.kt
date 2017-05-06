package com.mohamadamin.cleantvmaze.data.annotation

import javax.inject.Qualifier

/**
 * Explicitly differentiate the object from subviews.
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ForApplication