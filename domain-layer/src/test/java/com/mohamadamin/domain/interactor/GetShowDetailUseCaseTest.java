package com.mohamadamin.domain.interactor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.mohamadamin.domain.entity.Show;
import com.mohamadamin.domain.repository.ShowRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import easymvp.executer.PostExecutionThread;
import easymvp.executer.UseCaseExecutor;
import rx.Observable;
import rx.observers.AssertableSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */

public class GetShowDetailUseCaseTest {

    private static final String SHOW_ID = "showid";
    public static final String JSON = "{\n" +
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

    private final Observable<Show> obserableOneShow = Observable.just(show);

    @Mock
    ShowRepository showRepository;
    @Mock
    UseCaseExecutor useCaseExecutor;
    @Mock
    PostExecutionThread postExecutionThread;

    private GetShowDetailUseCase getShowDetailUseCase;

    @Before
    public void setupGetShowDetailUseCaseTest() {
        MockitoAnnotations.initMocks(this);
        getShowDetailUseCase = new GetShowDetailUseCase(useCaseExecutor, postExecutionThread, showRepository);
    }

    @Test
    public void testInteract() {

        when(showRepository.getShow(SHOW_ID)).thenReturn(obserableOneShow);
        AssertableSubscriber<Show> returned = getShowDetailUseCase.interact(SHOW_ID).test();

        returned.awaitTerminalEvent();

        verify(showRepository).getShow(SHOW_ID);
        returned.assertCompleted()
                .assertNoErrors()
                .assertValue(show)
                .assertValueCount(1);

    }

    @Test
    public void testInvalidShowId() {

        NullPointerException fucker = new NullPointerException("Fucker");

        when(showRepository.getShow(anyString())).thenReturn(Observable.create(showEmitter -> showEmitter.onError(fucker)));
        AssertableSubscriber<Show> returned = getShowDetailUseCase.interact(SHOW_ID).test();

        returned.awaitTerminalEvent();

        verify(showRepository).getShow(SHOW_ID);
        returned.assertError(fucker)
                .assertValueCount(0);

    }

}
