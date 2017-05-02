package com.mohamadamin.cleantvmaze.database.datasource

import com.mohamadamin.cleantvmaze.database.entity.RealmEpisode
import com.mohamadamin.cleantvmaze.database.entity.RealmSeason
import com.mohamadamin.cleantvmaze.database.entity.RealmShow
import com.mohamadamin.cleantvmaze.database.entity.RealmShowImageUrl
import com.mohamadamin.cleantvmaze.database.mapper.episode.EpisodeDatabaseToDomainMapper
import com.mohamadamin.cleantvmaze.database.mapper.season.SeasonDatabaseToDomainMapper
import com.mohamadamin.cleantvmaze.database.mapper.show.ShowDatabaseToDomainMapper
import com.mohamadamin.cleantvmaze.database.mapper.show.ShowDomainToDatabaseMapper
import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.datasource.InsertShowDataSource
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import io.realm.Realm
import rx.Completable
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
@Singleton
class RealmShowDataSource: RetrieveShowDataSource, InsertShowDataSource {

    private lateinit var showDomainToDatabaseMapper: ShowDomainToDatabaseMapper
    private lateinit var showDatabaseToDomainMapper: ShowDatabaseToDomainMapper
    private lateinit var seasonDatabaseToDomainMapper: SeasonDatabaseToDomainMapper
    private lateinit var episodeDatabaseToDomainMapper: EpisodeDatabaseToDomainMapper

    @Inject
    constructor(showDomainToDB: ShowDomainToDatabaseMapper, showDbToDomain: ShowDatabaseToDomainMapper,
                episodeDbToDomain: EpisodeDatabaseToDomainMapper, seasonDbToDomain: SeasonDatabaseToDomainMapper) {
        showDatabaseToDomainMapper = showDbToDomain
        showDomainToDatabaseMapper = showDomainToDB
        seasonDatabaseToDomainMapper = seasonDbToDomain
        episodeDatabaseToDomainMapper = episodeDbToDomain
    }

    override fun insertShows(shows: Observable<List<Show>>): Completable {
        return Completable.fromAction {

            val realm = Realm.getDefaultInstance()
            val realmShows = shows.flatMapIterable {
                list -> list
            }.map(showDomainToDatabaseMapper)

            realm.executeTransaction {
                realmShows.forEach { realmShow ->
                    it.copyToRealm(realmShow)
                }
            }

            realm.close()

        }
    }

    override fun insertEpisodes(showId: String, episodes: Observable<List<Episode>>): Completable {
        return Completable.fromAction {

            val realm = Realm.getDefaultInstance()
            val realmEpisodes = episodes.flatMapIterable {
                list -> list
            }.map { episode ->
                RealmEpisode(episode.id, episode.number, episode.runtime, episode.season,
                        episode.url, episode.name, showId, episode.airdate, episode.airtime, episode.airstamp,
                        episode.summary, RealmShowImageUrl(episode.image.medium, episode.image.original))
            }

            realm.executeTransaction {
                realmEpisodes.forEach { realmEpisode ->
                    it.copyToRealm(realmEpisode)
                }
            }

            realm.close()

        }
    }

    override fun insertSeasons(showId: String, seasons: Observable<List<Season>>): Completable {
        return Completable.fromAction {

            val realm = Realm.getDefaultInstance()
            val realmSeasons = seasons.flatMapIterable {
                list -> list
            }.map { season ->
                RealmSeason(season.id, season.season, season.number, season.runtime,
                        season.url, season.name, showId, season.airdate, season.airtime, season.airstamp,
                        season.summary, RealmShowImageUrl(season.image.medium, season.image.original))
            }

            realm.executeTransaction {
                realmSeasons.forEach { realmSeason ->
                    it.copyToRealm(realmSeason)
                }
            }

            realm.close()

        }
    }

    override fun getShows(page: Int): Observable<List<Show>> {
        val realm = Realm.getDefaultInstance()
        val shows = realm.where(RealmShow::class.java)
                .findAll()
                .asObservable()
                .flatMap { realmResults ->
                    Observable.from(realmResults)
                            .map(showDatabaseToDomainMapper)
                            .toList()
                }
        realm.close()
        return shows
    }

    override fun getShow(showId: String): Observable<Show> {
        val realm = Realm.getDefaultInstance()
        val show = realm.where(RealmShow::class.java)
                .equalTo("showId", showId)
                .findFirst()
                .asObservable<RealmShow>()
                .map(showDatabaseToDomainMapper)
        realm.close()
        return show
    }

    override fun getShowEpisodes(showId: String): Observable<List<Episode>> {
        val realm = Realm.getDefaultInstance()
        val episodes = realm.where(RealmEpisode::class.java)
                .equalTo("showId", showId)
                .findAll()
                .asObservable()
                .flatMap { realmResults ->
                    Observable.from(realmResults)
                            .map(episodeDatabaseToDomainMapper)
                            .toList()
                }
        realm.close()
        return episodes
    }

    override fun getShowSeasons(showId: String): Observable<List<Season>> {
        val realm = Realm.getDefaultInstance()
        val seasons = realm.where(RealmSeason::class.java)
                .equalTo("showId", showId)
                .findAll()
                .asObservable()
                .flatMap { realmResults ->
                    Observable.from(realmResults)
                            .map(seasonDatabaseToDomainMapper)
                            .toList()
                }
        realm.close()
        return seasons
    }

    override fun singleSearchShow(query: String): Observable<Show> {
        val realm = Realm.getDefaultInstance()
        val show = realm.where(RealmShow::class.java)
                .like("name", query)
                .or()
                .like("summary", query)
                .findFirst()
                .asObservable<RealmShow>()
                .map(showDatabaseToDomainMapper)
        realm.close()
        return show
    }

    override fun searchShows(query: String): Observable<List<Show>> {
        val realm = Realm.getDefaultInstance()
        val shows = realm.where(RealmShow::class.java)
                .like("name", query)
                .or()
                .like("summary", query)
                .findAll()
                .asObservable()
                .flatMap { realmResults ->
                    Observable.from(realmResults)
                            .map(showDatabaseToDomainMapper)
                            .toList()
                }
        realm.close()
        return shows

    }

}