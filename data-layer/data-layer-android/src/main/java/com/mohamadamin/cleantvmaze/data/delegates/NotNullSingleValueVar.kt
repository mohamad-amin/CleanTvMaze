package com.mohamadamin.cleantvmaze.data.delegates

import java.lang.IllegalStateException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
class NotNullSingleValueVar<T>: ReadWriteProperty<Any?, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} not initialized yet")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == null) {
            this.value = value
        } else {
            throw IllegalStateException("${property.name} is already initialized")
        }
    }

}