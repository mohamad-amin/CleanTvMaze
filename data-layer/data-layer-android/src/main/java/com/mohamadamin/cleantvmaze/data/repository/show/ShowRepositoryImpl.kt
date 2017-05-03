package com.mohamadamin.cleantvmaze.data.repository.show

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
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
*/
@Singleton
class ShowRepositoryImpl: ShowRepository {

    private lateinit var networkRetrieveDataSource: RetrieveShowDataSource
    private lateinit var realmRetrieveDataSource: RetrieveShowDataSource
    private lateinit var realmInsertDataSource: InsertShowDataSource

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
                    realmRetrieveDataSource.getShows(page)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShows(page).doOnNext {
                        realmInsertDataSource.insertShows(it).subscribeOn(Schedulers.computation())
                    }.subscribeOn(Schedulers.io())
            ).filter { list -> !list.isEmpty() }.first()


    override fun getShow(showId: String): Observable<Show> =
            realmRetrieveDataSource.getShow(showId)
                    .switchIfEmpty(networkRetrieveDataSource.getShow(showId))
                    .subscribeOn(Schedulers.io())

    override fun getShowEpisodes(showId: String): Observable<List<Episode>> =
            Observable.concat(
                    realmRetrieveDataSource.getShowEpisodes(showId)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShowEpisodes(showId).doOnNext {
                        realmInsertDataSource.insertEpisodes(showId, it).subscribeOn(Schedulers.computation())
                    }.subscribeOn(Schedulers.io())
            ).first()


    override fun getShowSeasons(showId: String): Observable<List<Season>> =
            Observable.concat(
                    realmRetrieveDataSource.getShowSeasons(showId)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.getShowSeasons(showId).doOnNext {
                        realmInsertDataSource.insertSeasons(showId, it).subscribeOn(Schedulers.computation())
                    }.subscribeOn(Schedulers.io())
            ).first()

    override fun singleSearchShow(query: String): Observable<Show> =
            realmRetrieveDataSource.singleSearchShow(query)
                    .switchIfEmpty(networkRetrieveDataSource.singleSearchShow(query))
                    .subscribeOn(Schedulers.io())

    override fun searchShows(query: String): Observable<List<Show>> =
            Observable.concat(
                    realmRetrieveDataSource.searchShows(query)
                            .subscribeOn(Schedulers.computation()),
                    networkRetrieveDataSource.searchShows(query).doOnNext {
                        realmInsertDataSource.insertShows(it).subscribeOn(Schedulers.computation())
                    }.subscribeOn(Schedulers.io())
            ).first()

}