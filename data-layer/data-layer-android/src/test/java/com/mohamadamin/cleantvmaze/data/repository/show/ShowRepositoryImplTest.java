package com.mohamadamin.cleantvmaze.data.repository.show;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mohamadamin.cleantvmaze.data.utils.TrampolineSchedulerRule;
import com.mohamadamin.cleantvmaze.domain.entity.Episode;
import com.mohamadamin.cleantvmaze.domain.entity.Season;
import com.mohamadamin.cleantvmaze.domain.entity.Show;
import com.mohamadamin.cleantvmaze.domain.repository.datasource.InsertShowDataSource;
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource;
import com.mohamadamin.cleantvmaze.network.utils.ResourceReader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.AssertableSubscriber;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
public class ShowRepositoryImplTest {

    public static final int PAGE = 1;
    public static final String SHOW_ID = "1";
    public static final String QUERY = "Girls";

    @Mock
    InsertShowDataSource realmInsert;
    @Mock
    RetrieveShowDataSource realmRetrieve;
    @Mock
    RetrieveShowDataSource networkRetrieve;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

    private ShowRepositoryImpl showRepository;

    private Observable<List<Episode>> episodes1, episodes2;
    private Observable<List<Season>> seasons1, seasons2;
    private Observable<List<Show>> shows1, shows2;
    private Observable<Show> show1, show2;

    private Show show, searched;
    private List<Show> listOfShows, mixedShows;
    private List<Episode> listOfEpisodes1, listOfEpisodes2;
    private List<Season> listOfSeasons1, listOfSeasons2;

    @Before
    public void setupObservables() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        show = gson.fromJson(ResourceReader.INSTANCE.
                getJsonFromResource("json_show_ok.json", getClass().getClassLoader()), Show.class);

        searched = gson.fromJson(ResourceReader.INSTANCE.
                getJsonFromResource("json_singlesearch_ok.json", getClass().getClassLoader()), Show.class);

        listOfShows = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_shows_ok.json", getClass().getClassLoader()), new TypeToken<List<Show>>(){}.getType());

        mixedShows = new ArrayList<>();
        mixedShows.add(show);
        mixedShows.add(searched);

        listOfEpisodes1 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_episodes_ok.json", getClass().getClassLoader()), new TypeToken<List<Episode>>(){}.getType());

        listOfEpisodes2 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_episodes2_ok.json", getClass().getClassLoader()), new TypeToken<List<Episode>>(){}.getType());

        listOfSeasons1 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_seasons_ok.json", getClass().getClassLoader()), new TypeToken<List<Season>>(){}.getType());

        listOfSeasons2 = gson.fromJson(ResourceReader.INSTANCE.getJsonFromResource(
                "json_seasons2_ok.json", getClass().getClassLoader()), new TypeToken<List<Season>>(){}.getType());

        episodes1 = Observable.just(listOfEpisodes1);
        episodes2 = Observable.just(listOfEpisodes2);
        seasons1 = Observable.just(listOfSeasons1);
        seasons2 = Observable.just(listOfSeasons2);
        shows1 = Observable.just(listOfShows);
        shows2 = Observable.just(mixedShows);
        show1 = Observable.just(show);
        show2 = Observable.just(searched);

        showRepository = new ShowRepositoryImpl(networkRetrieve, realmRetrieve, realmInsert);

    }
    
    @Test
    public void getShows() {

        when(realmRetrieve.getShows(PAGE))
                .thenReturn(Observable.<List<Show>>empty());
        when(networkRetrieve.getShows(PAGE))
                .thenReturn(shows1);

        AssertableSubscriber<List<Show>> getShows = showRepository.getShows(PAGE).test();

        getShows.assertNoErrors()
                .assertCompleted()
                .assertValue(listOfShows)
                .assertValueCount(1);

        assertEquals(listOfShows, new ArrayList<>());

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
