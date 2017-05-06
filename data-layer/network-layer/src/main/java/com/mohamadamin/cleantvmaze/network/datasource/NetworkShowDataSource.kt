package com.mohamadamin.cleantvmaze.network.datasource

import com.mohamadamin.cleantvmaze.data.network.ShowsAPI
import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
public class NetworkShowDataSource: RetrieveShowDataSource {

    private var showsApi: ShowsAPI

    @Inject
    constructor(showsAPI: ShowsAPI) {
        showsApi = showsAPI
    }

    override fun getShows(page: Int): Observable<List<Show>> =
            showsApi.getShows(page)

    override fun getShow(showId: String): Observable<Show> =
            showsApi.getShow(showId.toInt())

    override fun getShowEpisodes(showId: String): Observable<List<Episode>> =
            showsApi.getEpisodes(showId.toInt())

    override fun getShowSeasons(showId: String): Observable<List<Season>> =
            showsApi.getSeasons(showId.toInt())

    override fun singleSearchShow(query: String): Observable<Show> =
            showsApi.searchSingleShow(query)

    override fun searchShows(query: String): Observable<List<Show>> =
            showsApi.searchShows(query)

}