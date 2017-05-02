package com.mohamadamin.cleantvmaze.domain.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Explicitly differentiate the object from subviews.
 *
 * @author Saeed Masoumi (saeed@6thsolution.com)
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {
}
