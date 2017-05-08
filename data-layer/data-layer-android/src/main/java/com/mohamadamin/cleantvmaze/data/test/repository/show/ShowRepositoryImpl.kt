package com.mohamadamin.cleantvmaze.data.test.repository.show

import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.ShowRepository
import com.mohamadamin.cleantvmaze.domain.repository.datasource.InsertShowDataSource
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import com.mohamadamin.cleantvmaze.domain.utils.Constants
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
*/
class ShowRepositoryImpl: ShowRepository {

    private var networkRetrieveDataSource: RetrieveShowDataSource
    private var realmRetrieveDataSource: RetrieveShowDataSource
    private var realmInsertDataSource: InsertShowDataSource

    @Inject
    constructor(@Named(Constants.DATASOURCE_NETWORK) networkRetrieveShowDataSource: RetrieveShowDataSource,
                @Named(Constants.DATASOURCE_REALM) realmRetrieveShowDataSource: RetrieveShowDataSource,
                @Named(Constants.DATASOURCE_REALM) realmInsertShowDataSource: InsertShowDataSource) {
        this.networkRetrieveDataSource = networkRetrieveShowDataSource
        this.realmRetrieveDataSource = realmRetrieveShowDataSource
        this.realmInsertDataSource = realmInsertShowDataSource
    }

    override fun getShows(page: Int): Observable<List<Show>> =
            Observable.concat(
                    realmRetrieveDataSource.getShows(page).subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShows(page)
                            .subscribeOn(Schedulers.io())
                            .doOnNext {
                                realmInsertDataSource.insertShows(it).subscribeOn(Schedulers.computation())
                            }
            ).filter { t: List<Show> -> !t.isEmpty() }.first()


    override fun getShow(showId: String): Observable<Show> =
            Observable.concat(
                    realmRetrieveDataSource.getShow(showId).subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShow(showId).subscribeOn(Schedulers.computation())
            ).first()

    override fun getShowEpisodes(showId: String): Observable<List<Episode>> =
            Observable.concat(
                    realmRetrieveDataSource.getShowEpisodes(showId)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShowEpisodes(showId).observeOn(Schedulers.computation()).doOnNext {
                        realmInsertDataSource.insertEpisodes(showId, it)
                    }.subscribeOn(Schedulers.io())
            ).first()


    override fun getShowSeasons(showId: String): Observable<List<Season>> =
            Observable.concat(
                    realmRetrieveDataSource.getShowSeasons(showId)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShowSeasons(showId).observeOn(Schedulers.computation()).doOnNext {
                        realmInsertDataSource.insertSeasons(showId, it).subscribeOn(Schedulers.computation())
                    }.subscribeOn(Schedulers.io())
            ).first()

    override fun singleSearchShow(query: String): Observable<Show> =
            Observable.concat(
                    realmRetrieveDataSource.singleSearchShow(query).subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.singleSearchShow(query).subscribeOn(Schedulers.computation())
            ).first()

    override fun searchShows(query: String): Observable<List<Show>> =
            Observable.concat(
                    realmRetrieveDataSource.searchShows(query)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.searchShows(query).observeOn(Schedulers.computation()).doOnNext {
                        realmInsertDataSource.insertShows(it).subscribeOn(Schedulers.computation())
                    }.subscribeOn(Schedulers.io())
            ).first()

}