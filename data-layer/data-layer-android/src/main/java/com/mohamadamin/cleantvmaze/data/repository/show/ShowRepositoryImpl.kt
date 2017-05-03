package com.mohamadamin.cleantvmaze.data.repository.show

import com.mohamadamin.cleantvmaze.domain.Constants
import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.ShowRepository
import com.mohamadamin.cleantvmaze.domain.repository.datasource.InsertShowDataSource
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 * Todo: somehow remove the ugly [needsPush] boolean that exists in some methods and solve the problem without it,
 * there are chances of causing memory leaks because of this reference
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

    override fun getShows(page: Int): Observable<List<Show>> {

        var needsPush = true
        val networkObservable = networkRetrieveDataSource.getShows(page)

        val shows = realmRetrieveDataSource.getShows(page)
                .filter { list ->
                    if (!list.isEmpty()) {
                        needsPush = false
                    }
                    !list.isEmpty()
                }
                .subscribeOn(Schedulers.computation())
                .switchIfEmpty(networkObservable)
                .doOnNext {
                    if (needsPush) {
                        realmInsertDataSource.insertShows(it).subscribeOn(Schedulers.computation())
                    }
                }.subscribeOn(Schedulers.io())


        return shows

    }

    override fun getShow(showId: String): Observable<Show> =
            realmRetrieveDataSource.getShow(showId)
                    .switchIfEmpty(networkRetrieveDataSource.getShow(showId))
                    .subscribeOn(Schedulers.io())

    override fun getShowEpisodes(showId: String): Observable<List<Episode>> {

        var needsPush = true
        val networkObservable = networkRetrieveDataSource.getShowEpisodes(showId)

        val shows = realmRetrieveDataSource.getShowEpisodes(showId)
                .filter { list ->
                    if (!list.isEmpty()) {
                        needsPush = false
                    }
                    !list.isEmpty()
                }
                .subscribeOn(Schedulers.computation())
                .switchIfEmpty(networkObservable)
                .doOnNext {
                    if (needsPush) {
                        realmInsertDataSource.insertEpisodes(showId, it).subscribeOn(Schedulers.computation())
                    }
                }.subscribeOn(Schedulers.io())

        return shows

    }

    override fun getShowSeasons(showId: String): Observable<List<Season>> {

        var needsPush = true
        val networkObservable = networkRetrieveDataSource.getShowSeasons(showId)

        val shows = realmRetrieveDataSource.getShowSeasons(showId)
                .filter { list ->
                    if (!list.isEmpty()) {
                        needsPush = false
                    }
                    !list.isEmpty()
                }
                .subscribeOn(Schedulers.computation())
                .switchIfEmpty(networkObservable)
                .doOnNext {
                    if (needsPush) {
                        realmInsertDataSource.insertSeasons(showId, it).subscribeOn(Schedulers.computation())
                    }
                }
                .subscribeOn(Schedulers.io())

        return shows

    }

    override fun singleSearchShow(query: String): Observable<Show> =
            realmRetrieveDataSource.singleSearchShow(query)
                    .switchIfEmpty(networkRetrieveDataSource.singleSearchShow(query))
                    .subscribeOn(Schedulers.io())

    override fun searchShows(query: String): Observable<List<Show>> =
            realmRetrieveDataSource.searchShows(query)
                    .filter { list -> !list.isEmpty() }
                    .switchIfEmpty(networkRetrieveDataSource.searchShows(query))
                    .subscribeOn(Schedulers.io())

}