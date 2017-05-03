package com.mohamadamin.cleantvmaze.data.network

import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
interface ShowsAPI {

    @GET("/shows")
    fun getShows(@Query("page") page: Int): Observable<List<Show>>

    @GET("/shows/{id}")
    fun getShow(@Path("id") showId: Int): Observable<Show>

    @GET("/shows/{id}/episodes")
    fun getEpisodes(@Path("id") showId: Int): Observable<List<Episode>>

    @GET("/shows/{id}/seasons")
    fun getSeasons(@Path("id") showId: Int): Observable<List<Season>>

    @GET("/search/shows")
    fun searchShows(@Query("q") query: String): Observable<List<Show>>

    @GET("/singlesearch/shows")
    fun searchSingleShow(@Query("q") query: String): Observable<Show>

}