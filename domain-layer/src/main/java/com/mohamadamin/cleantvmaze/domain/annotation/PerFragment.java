package com.mohamadamin.cleantvmaze.domain.annotation;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom dagger {@link Scope} for fragments.
 *
 * @author Saeed Masoumi (saeed@6thsolution.com)
 */
@Scope
@Retention(RUNTIME)
public @interface PerFragment {
}
