package com.mohamadamin.cleantvmaze.network.api;

import com.mohamadamin.cleantvmaze.domain.entity.Episode;
import com.mohamadamin.cleantvmaze.domain.entity.Season;
import com.mohamadamin.cleantvmaze.domain.entity.Show;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */

public interface ShowsAPI {

    @GET("/shows")
    Observable<List<Show>> getShows(@Query("page") int page);

    @GET("/shows/{id}")
    Observable<Show> getShow(@Path("id") int showId);

    @GET("/shows/{id}/episodes")
    Observable<List<Episode>> getEpisodes(@Path("id") int showId);

    @GET("/shows/{id}/seasons")
    Observable<List<Season>> getSeasons(@Path("id") int showId);

    @GET("/search/shows")
    Observable<List<Show>> searchShows(@Query("q") String query);

    @GET("/singlesearch/shows")
    Observable<Show> searchSingleShow(@Query("q") String query);

}
