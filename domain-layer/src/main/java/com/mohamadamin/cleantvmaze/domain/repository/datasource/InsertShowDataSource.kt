package com.mohamadamin.cleantvmaze.domain.repository.datasource

import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import rx.Completable
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
interface InsertShowDataSource {

    fun insertShows(shows: Observable<List<Show>>): Completable

    fun insertEpisodes(showId: String, episodes: Observable<List<Episode>>): Completable

    fun insertSeasons(showId: String, seasons: Observable<List<Season>>): Completable

}