package com.mohamadamin.domain.interactor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mohamadamin.domain.entity.Show;
import com.mohamadamin.domain.repository.ShowRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import easymvp.executer.PostExecutionThread;
import easymvp.executer.UseCaseExecutor;
import rx.Observable;
import rx.observers.AssertableSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */

public class GetShowsUseCaseTest {

    private static final int PAGE = 1;
    private static final String JSON = "{\n" +
            "id: 1,\n" +
            "url: \"http://www.tvmaze.com/shows/1/under-the-dome\",\n" +
            "name: \"Under the Dome\",\n" +
            "type: \"Scripted\",\n" +
            "language: \"English\",\n" +
            "genres: [\n" +
            "\"Drama\",\n" +
            "\"Science-Fiction\",\n" +
            "\"Thriller\"\n" +
            "],\n" +
            "status: \"Ended\",\n" +
            "runtime: 60,\n" +
            "premiered: \"2013-06-24\",\n" +
            "schedule: {\n" +
            "time: \"22:00\",\n" +
            "days: [\n" +
            "\"Thursday\"\n" +
            "]\n" +
            "},\n" +
            "rating: {\n" +
            "average: 6.6\n" +
            "},\n" +
            "weight: 0,\n" +
            "network: {\n" +
            "id: 2,\n" +
            "name: \"CBS\",\n" +
            "country: {\n" +
            "name: \"United States\",\n" +
            "code: \"US\",\n" +
            "timezone: \"America/New_York\"\n" +
            "}\n" +
            "},\n" +
            "webChannel: null,\n" +
            "externals: {\n" +
            "tvrage: 25988,\n" +
            "thetvdb: 264492,\n" +
            "imdb: \"tt1553656\"\n" +
            "},\n" +
            "image: {\n" +
            "medium: \"http://static.tvmaze.com/uploads/images/medium_portrait/0/1.jpg\",\n" +
            "original: \"http://static.tvmaze.com/uploads/images/original_untouched/0/1.jpg\"\n" +
            "},\n" +
            "summary: \"<p><strong>Under the Dome</strong> is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.</p>\",\n" +
            "updated: 1490211618,\n" +
            "_links: {\n" +
            "self: {\n" +
            "href: \"http://api.tvmaze.com/shows/1\"\n" +
            "},\n" +
            "previousepisode: {\n" +
            "href: \"http://api.tvmaze.com/episodes/185054\"\n" +
            "}\n" +
            "}\n" +
            "}";

    private final Show show = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
            .fromJson(JSON, Show.class);

    private final List<Show> shows = new ArrayList<>();

    private Observable<List<Show>> observableShows;

    @Mock
    ShowRepository showRepository;
    @Mock
    UseCaseExecutor useCaseExecutor;
    @Mock
    PostExecutionThread postExecutionThread;

    private GetShowsUseCase getShowsUseCase;

    @Before
    public void setupGetShowDetailUseCaseTest() {
        MockitoAnnotations.initMocks(this);
        shows.add(show);
        shows.add(show);
        observableShows = Observable.just(shows);
        getShowsUseCase = new GetShowsUseCase(useCaseExecutor, postExecutionThread, showRepository);
    }

    @Test
    public void testInteract() {

        when(showRepository.getShows(PAGE)).thenReturn(observableShows);
        AssertableSubscriber<List<Show>> returned = getShowsUseCase.interact(1).test();

        returned.awaitTerminalEvent();

        verify(showRepository).getShows(PAGE);
        returned.assertCompleted()
                .assertNoErrors()
                .assertValue(shows)
                .assertValueCount(1);

    }

    @Test
    public void testInvalidShowId() {

        NullPointerException fucker = new NullPointerException("Fucker");

        when(showRepository.getShows(anyInt())).thenReturn(
                Observable.create(showEmitter -> showEmitter.onError(fucker))
        );
        AssertableSubscriber<List<Show>> returned = getShowsUseCase.interact(PAGE).test();

        returned.awaitTerminalEvent();

        verify(showRepository).getShows(PAGE);
        returned.assertError(fucker)
                .assertValueCount(0)
                .assertNotCompleted();

    }

}
