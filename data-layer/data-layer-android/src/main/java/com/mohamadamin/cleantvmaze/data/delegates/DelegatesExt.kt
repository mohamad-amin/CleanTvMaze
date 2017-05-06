package com.mohamadamin.cleantvmaze.data.delegates

import kotlin.properties.ReadWriteProperty

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
object DelegatesExt {

    fun <T> notNullSingleValue() : ReadWriteProperty<Any?, T> =
            NotNullSingleValueVar()

}