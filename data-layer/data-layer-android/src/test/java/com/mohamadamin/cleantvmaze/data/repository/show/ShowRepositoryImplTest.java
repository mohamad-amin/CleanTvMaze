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
import java.util.NoSuchElementException;

import rx.Completable;
import rx.Observable;
import rx.observers.AssertableSubscriber;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
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
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

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

        // Testing error when no data is available
        when(realmRetrieve.getShows(PAGE))
                .thenReturn(Observable.<List<Show>>empty());
        when(networkRetrieve.getShows(PAGE))
                .thenReturn(Observable.<List<Show>>empty());
        when(realmInsert.insertShows(anyList())).thenReturn(Completable.complete());

        AssertableSubscriber<List<Show>> getShows = showRepository.getShows(PAGE)
                .test()
                .awaitTerminalEvent();

        getShows.assertNotCompleted()
                .assertNoValues();
        assertTrue(getShows.getOnErrorEvents().get(0) instanceof NoSuchElementException);

        // Testing to get data from internet first
        when(networkRetrieve.getShows(PAGE))
                .thenReturn(shows1);

        getShows = showRepository.getShows(PAGE)
                .test()
                .awaitTerminalEvent();

        getShows.assertNoErrors()
                .assertCompleted()
                .assertValue(listOfShows)
                .assertValueCount(1);

        verify(realmInsert).insertShows(listOfShows);

        // Testing to get data from realm
        when(realmRetrieve.getShows(PAGE))
                .thenReturn(Observable.just(mixedShows));

        getShows = showRepository.getShows(PAGE)
                .test()
                .awaitTerminalEvent();

        getShows.assertNoErrors()
                .assertCompleted()
                .assertValue(mixedShows)
                .assertValueCount(1);

    }

    @Test
    public void getShow() {

        // Testing error when no data is available
        when(realmRetrieve.getShow(SHOW_ID))
                .thenReturn(Observable.<Show>empty());
        when(networkRetrieve.getShow(SHOW_ID))
                .thenReturn(Observable.<Show>empty());

        AssertableSubscriber<Show> getShow = showRepository.getShow(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShow.assertNotCompleted()
                .assertNoValues();
        assertTrue(getShow.getOnErrorEvents().get(0) instanceof NoSuchElementException);

        // Testing to get data from internet first
        when(networkRetrieve.getShow(SHOW_ID))
                .thenReturn(show1);

        getShow = showRepository.getShow(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShow.assertNoErrors()
                .assertCompleted()
                .assertValue(show)
                .assertValueCount(1);

        // Testing to get data from realm
        when(realmRetrieve.getShow(SHOW_ID))
                .thenReturn(Observable.just(searched));

        getShow = showRepository.getShow(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShow.assertNoErrors()
                .assertCompleted()
                .assertValue(searched)
                .assertValueCount(1);

    }

    @Test
    public void getShowEpisodes() {

        // Testing error when no data is available
        when(realmRetrieve.getShowEpisodes(SHOW_ID))
                .thenReturn(Observable.<List<Episode>>empty());
        when(networkRetrieve.getShowEpisodes(SHOW_ID))
                .thenReturn(Observable.<List<Episode>>empty());
        when(realmInsert.insertEpisodes(anyString(), anyList())).thenReturn(Completable.complete());

        AssertableSubscriber<List<Episode>> getShowEpisodes = showRepository.getShowEpisodes(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShowEpisodes.assertNotCompleted()
                .assertNoValues();
        assertTrue(getShowEpisodes.getOnErrorEvents().get(0) instanceof NoSuchElementException);

        // Testing to get data from internet first
        when(networkRetrieve.getShowEpisodes(SHOW_ID))
                .thenReturn(episodes1);

        getShowEpisodes = showRepository.getShowEpisodes(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShowEpisodes.assertNoErrors()
                .assertCompleted()
                .assertValue(listOfEpisodes1)
                .assertValueCount(1);

        verify(realmInsert).insertEpisodes(SHOW_ID, listOfEpisodes1);

        // Testing to get data from realm
        when(realmRetrieve.getShowEpisodes(SHOW_ID))
                .thenReturn(Observable.just(listOfEpisodes2));

        getShowEpisodes = showRepository.getShowEpisodes(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShowEpisodes.assertNoErrors()
                .assertCompleted()
                .assertValue(listOfEpisodes2)
                .assertValueCount(1);

    }

    @Test
    public void getShowSeasons() {

        // Testing error when no data is available
        when(realmRetrieve.getShowSeasons(SHOW_ID))
                .thenReturn(Observable.<List<Season>>empty());
        when(networkRetrieve.getShowSeasons(SHOW_ID))
                .thenReturn(Observable.<List<Season>>empty());
        when(realmInsert.insertSeasons(anyString(), anyList())).thenReturn(Completable.complete());

        AssertableSubscriber<List<Season>> getShowSeasons = showRepository.getShowSeasons(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShowSeasons.assertNotCompleted()
                .assertNoValues();
        assertTrue(getShowSeasons.getOnErrorEvents().get(0) instanceof NoSuchElementException);

        // Testing to get data from internet first
        when(networkRetrieve.getShowSeasons(SHOW_ID))
                .thenReturn(seasons1);

        getShowSeasons = showRepository.getShowSeasons(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShowSeasons.assertNoErrors()
                .assertCompleted()
                .assertValue(listOfSeasons1)
                .assertValueCount(1);

        verify(realmInsert).insertSeasons(SHOW_ID, listOfSeasons1);

        // Testing to get data from realm
        when(realmRetrieve.getShowSeasons(SHOW_ID))
                .thenReturn(Observable.just(listOfSeasons2));

        getShowSeasons = showRepository.getShowSeasons(SHOW_ID)
                .test()
                .awaitTerminalEvent();

        getShowSeasons.assertNoErrors()
                .assertCompleted()
                .assertValue(listOfSeasons2)
                .assertValueCount(1);

    }

    @Test
    public void singleSearchShow() {

        // Testing error when no data is available
        when(realmRetrieve.singleSearchShow(QUERY))
                .thenReturn(Observable.<Show>empty());
        when(networkRetrieve.singleSearchShow(QUERY))
                .thenReturn(Observable.<Show>empty());

        AssertableSubscriber<Show> singleSearchShow = showRepository.singleSearchShow(QUERY)
                .test()
                .awaitTerminalEvent();

        singleSearchShow.assertNotCompleted()
                .assertNoValues();
        assertTrue(singleSearchShow.getOnErrorEvents().get(0) instanceof NoSuchElementException);

        // Testing to get data from internet first
        when(networkRetrieve.singleSearchShow(QUERY))
                .thenReturn(show1);

        singleSearchShow = showRepository.singleSearchShow(QUERY)
                .test()
                .awaitTerminalEvent();

        singleSearchShow.assertNoErrors()
                .assertCompleted()
                .assertValue(show)
                .assertValueCount(1);

        // Testing to get data from realm
        when(realmRetrieve.singleSearchShow(QUERY))
                .thenReturn(Observable.just(searched));

        singleSearchShow = showRepository.singleSearchShow(QUERY)
                .test()
                .awaitTerminalEvent();

        singleSearchShow.assertNoErrors()
                .assertCompleted()
                .assertValue(searched)
                .assertValueCount(1);
        
    }

    @Test
    public void searchShows() {

    }

}
