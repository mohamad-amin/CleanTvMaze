package com.mohamadamin.cleantvmaze.database.entity

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
import io.realm.RealmObject

public open class RealmIntArray(open var joinedInts: String? = null) : RealmObject()

public fun RealmIntArray.toList(): List<Int> {
    if (joinedInts != null) {
        return joinedInts!!.split(",").map { it.toInt() }
    } else {
        return listOf()
    }
}

public open class RealmLongArray(open var joinedLongs: String? = null) : RealmObject()

public fun RealmLongArray.toList(): List<Long> {
    if (joinedLongs != null) {
        return joinedLongs!!.split(",").map { it.toLong() }
    } else {
        return listOf()
    }
}

public open class RealmStringArray(open var joinedStrings: String? = null) : RealmObject()

public fun RealmStringArray.toList(): List<String> {
    // TODO think of a not horrible solution for this
    if (joinedStrings != null) {
        return joinedStrings!!.split(",,,,")
    } else {
        return listOf()
    }
}