package com.mohamadamin.domain.repository

import com.mohamadamin.domain.entity.People
import com.mohamadamin.domain.entity.Show
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
interface SearchRepository {

    fun singleSearchShow(query: String): Observable<Show>

    fun searchShows(query: String): Observable<List<Show>>

    fun searchPeople(query: String): Observable<List<People>>

}
