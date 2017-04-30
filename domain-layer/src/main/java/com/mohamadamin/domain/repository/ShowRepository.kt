package com.mohamadamin.domain.repository

import com.mohamadamin.domain.entity.Episode
import com.mohamadamin.domain.entity.Show
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
interface ShowRepository {

    fun getShows(page: Int): Observable<List<Show>>

    fun getShow(showId: String): Observable<Show>

    fun getShowEpisodes(showId: String): Observable<List<Episode>>

    fun getShowSeasons(showId: String): Observable<List<Episode>>

}
