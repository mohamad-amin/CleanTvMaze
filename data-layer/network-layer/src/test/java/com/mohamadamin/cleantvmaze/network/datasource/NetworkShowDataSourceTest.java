package com.mohamadamin.cleantvmaze.network.datasource;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mohamadamin.cleantvmaze.data.network.ShowsAPI;
import com.mohamadamin.cleantvmaze.domain.entity.Episode;
import com.mohamadamin.cleantvmaze.domain.entity.Season;
import com.mohamadamin.cleantvmaze.domain.entity.Show;
import com.mohamadamin.cleantvmaze.domain.ResourceReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
public class NetworkShowDataSourceTest {

    private static final int PAGE = 1;
    private static final String SHOW_ID = "1";
    private static final String QUERY = "girls";

    private NetworkShowDataSource dataSource;
    private MockWebServer server;
    private Gson gson;

    private Type showListType = new TypeToken<List<Show>>(){}.getType(),
            episodeListType = new TypeToken<List<Episode>>(){}.getType(),
            seasonListType = new TypeToken<List<Season>>(){}.getType();

    @Before
    public void setUp() throws IOException {

        server = new MockWebServer();
        server.start();

        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(server.url("/").toString())
                .build();

        dataSource = new NetworkShowDataSource(retrofit.create(ShowsAPI.class));

    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void getShows() {

        String showsJson = ResourceReader.INSTANCE
                .getJsonFromResource("json_shows_ok.json", getClass().getClassLoader());

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(showsJson)
        );

        Observable<List<Show>> shows = dataSource.getShows(PAGE);
        shows.test()
                .assertCompleted()
                .assertNoErrors()
                .assertValue((List<Show>) gson.fromJson(showsJson, showListType))
                .assertValueCount(1);

    }

    @Test
    public void getShow() {

        String showJson = ResourceReader.INSTANCE
                .getJsonFromResource("json_show_ok.json", getClass().getClassLoader());

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(showJson)
        );

        Observable<Show> shows = dataSource.getShow(SHOW_ID);
        shows.test()
                .assertCompleted()
                .assertNoErrors()
                .assertValue(gson.fromJson(showJson, Show.class))
                .assertValueCount(1);

    }

    @Test
    public void getShowEpisodes() {

        String episodesJson = ResourceReader.INSTANCE
                .getJsonFromResource("json_episodes_ok.json", getClass().getClassLoader());

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(episodesJson)
        );

        Observable<List<Episode>> shows = dataSource.getShowEpisodes(SHOW_ID);
        shows.test()
                .assertCompleted()
                .assertNoErrors()
                .assertValue((List<Episode>) gson.fromJson(episodesJson, episodeListType))
                .assertValueCount(1);

    }

    @Test
    public void getShowSeasons() {

        String seasonsJson = ResourceReader.INSTANCE
                .getJsonFromResource("json_seasons_ok.json", getClass().getClassLoader());

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(seasonsJson)
        );

        Observable<List<Season>> shows = dataSource.getShowSeasons(SHOW_ID);
        shows.test()
                .assertCompleted()
                .assertNoErrors()
                .assertValue((List<Season>) gson.fromJson(seasonsJson, seasonListType))
                .assertValueCount(1);

    }

    @Test
    public void singleSearchShow() {

        String singlesearchJson = ResourceReader.INSTANCE
                .getJsonFromResource("json_singlesearch_ok.json", getClass().getClassLoader());

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(singlesearchJson)
        );

        Observable<Show> shows = dataSource.singleSearchShow(QUERY);
        shows.test()
                .assertCompleted()
                .assertNoErrors()
                .assertValue(gson.fromJson(singlesearchJson, Show.class))
                .assertValueCount(1);

    }

    @Test
    public void searchShows() {

        String searchJson = ResourceReader.INSTANCE
                .getJsonFromResource("json_search_ok.json", getClass().getClassLoader());

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(searchJson)
        );

        Observable<List<Show>> shows = dataSource.searchShows(QUERY);
        shows.test()
                .assertCompleted()
                .assertNoErrors()
                .assertValue((List<Show>) gson.fromJson(searchJson, showListType))
                .assertValueCount(1);


    }

}
