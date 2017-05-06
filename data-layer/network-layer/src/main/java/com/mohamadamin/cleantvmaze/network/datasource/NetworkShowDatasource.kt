package com.mohamadamin.cleantvmaze.network.datasource

import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import com.mohamadamin.cleantvmaze.network.api.ShowsAPI
import rx.Observable
import javax.inject.Inject

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
public open class NetworkShowDatasource : RetrieveShowDataSource {

    private var showsApi: ShowsAPI

    @Inject
    constructor(showsApi: ShowsAPI) {
        this.showsApi = showsApi
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