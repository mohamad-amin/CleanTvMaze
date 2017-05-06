package com.mohamadamin.cleantvmaze.domain.repository.datasource

import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import rx.Completable
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
public interface InsertShowDataSource {

    fun insertShows(shows: List<Show>): Completable

    fun insertSeasons(showId: String, seasons: List<Season>): Completable

    fun insertEpisodes(showId: String, episodes: List<Episode>): Completable

}