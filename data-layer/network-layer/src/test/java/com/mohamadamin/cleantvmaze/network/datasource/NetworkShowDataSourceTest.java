package com.mohamadamin.cleantvmaze.network.datasource;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mohamadamin.cleantvmaze.data.network.ShowsAPI;
import com.mohamadamin.cleantvmaze.domain.entity.Show;
import com.mohamadamin.cleantvmaze.network.ResourceReader;

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

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
public class NetworkShowDataSourceTest {

    public static final int PAGE = 1;

    private NetworkShowDataSource dataSource;
    private MockWebServer server;
    private ShowsAPI showsApi;
    private Gson gson;

    private Type listType = new TypeToken<List<Show>>(){}.getType();
    private Type type = new TypeToken<Show>(){}.getType();


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

        showsApi = retrofit.create(ShowsAPI.class);
        dataSource = new NetworkShowDataSource(showsApi);

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
                .assertValue((List<Show>) gson.fromJson(showsJson, listType))
                .assertValueCount(1);

    }

    @Test
    public void getShow() {

    }

    @Test
    public void getShowEpisodes() {

    }

    @Test
    public void getShowSeasons() {

    }

    @Test
    public void singleSearchShow() {

    }

    @Test
    public void searchShows() {

    }

}
