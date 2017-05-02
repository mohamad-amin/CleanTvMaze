package com.mohamadamin.cleantvmaze.data.repository.show

import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.ShowRepository
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class ShowRepositoryImpl: ShowRepository {

    override fun getShows(page: Int): Observable<List<Show>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShow(showId: String): Observable<Show> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShowEpisodes(showId: String): Observable<List<Episode>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShowSeasons(showId: String): Observable<List<Episode>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun singleSearchShow(query: String): Observable<Show> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchShows(query: String): Observable<List<Show>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}