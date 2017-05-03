package com.mohamadamin.cleantvmaze.data.repository.show;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mohamadamin.cleantvmaze.data.network.NetworkModule;
import com.mohamadamin.cleantvmaze.domain.ResourceReader;
import com.mohamadamin.cleantvmaze.domain.entity.Episode;
import com.mohamadamin.cleantvmaze.domain.entity.Season;
import com.mohamadamin.cleantvmaze.domain.entity.Show;
import com.mohamadamin.cleantvmaze.domain.repository.datasource.InsertShowDataSource;
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import rx.Observable;

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
public class ShowRepositoryImplTest {

    @Mock
    InsertShowDataSource realmInsert;
    @Mock
    RetrieveShowDataSource realmRetrieve;
    @Mock
    RetrieveShowDataSource networkRetrieve;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private ShowRepositoryImpl showRepository =
            new ShowRepositoryImpl(networkRetrieve, realmRetrieve, realmInsert);

    private Observable<List<Episode>> episodes1, episodes2;
    private Observable<List<Season>> seasons1, seasons2;
    private Observable<List<Show>> shows1, shows2;
    private Observable<Show> show1, show2;

    @BeforeClass
    public void setupObservables() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Show show = gson.fromJson(ResourceReader.INSTANCE.
                getJsonFromResource("json_show_ok.json", getClass().getClassLoader()), Show.class);

        Show searched = gson.fromJson(ResourceReader.INSTANCE.
                getJsonFromResource("json_singlesearch_ok.json", getClass().getClassLoader()), Show.class);

        List<Show> listOfShows = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_shows_ok", getClass().getClassLoader()), new TypeToken<List<Show>>(){}.getType());

        List<Episode> listOfEpisodes1 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_episodes_ok", getClass().getClassLoader()), new TypeToken<List<Episode>>(){}.getType());

        List<Episode> listOfEpisodes2 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_episodes2_ok", getClass().getClassLoader()), new TypeToken<List<Episode>>(){}.getType());

        List<Season> listOfSeasons1 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_seasons_ok", getClass().getClassLoader()), new TypeToken<List<Season>>(){}.getType());

        List<Season> listOfSeasons2 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_seasons2_ok", getClass().getClassLoader()), new TypeToken<List<Season>>(){}.getType());

        episodes1 = Observable.just(listOfEpisodes1);
        episodes2 = Observable.just(listOfEpisodes2);
        seasons1 = Observable.just(listOfSeasons1);
        seasons2 = Observable.just(listOfSeasons2);
        shows1 = Observable.just(listOfShows);
        shows2 = Observable.just(show, searched).toList();
        show1 = Observable.just(show);
        show2 = Observable.just(searched);

    }


    @Test
    public void getShows() {

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
