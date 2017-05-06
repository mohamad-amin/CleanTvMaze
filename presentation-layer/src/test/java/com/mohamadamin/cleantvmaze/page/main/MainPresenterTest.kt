package com.mohamadamin.cleantvmaze.page.main

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.interactor.GetShowsUseCase
import com.mohamadamin.cleantvmaze.utils.ResourceReader
import com.mohamadamin.cleantvmaze.utils.TrampolineSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.stubbing.Answer
import rx.Observable


/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */

class MainPresenterTest {

    @Rule @JvmField
    var mockitoRule = MockitoJUnit.rule()
    @Rule @JvmField
    var trampolineSchedulerRule = TrampolineSchedulerRule()
    @Rule @JvmField
    var thrown = ExpectedException.none()

    @Mock
    internal var getShowsUseCase: GetShowsUseCase? = null
    @Mock
    internal var mainView: MainView? = null

    internal var mainPresenter: MainPresenter? = null

    private var shows: Observable<List<Show>>? = null
    private var showsList: List<Show>? = null

    @Before
    fun setUp() {

        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        showsList = gson.fromJson<List<Show>>(ResourceReader.getJsonFromResource(
                "json_shows_ok.json", javaClass.classLoader), object : TypeToken<List<Show>>() {

        }.type)

        shows = Observable.just<List<Show>>(showsList)

        mainPresenter = MainPresenter(getShowsUseCase!!)

    }

    @Test
    fun onViewAttachedTrue() {

        `when`(getShowsUseCase!!.execute(PAGE)).thenAnswer(createAnswer(shows!!))

        mainPresenter!!.onViewAttached(mainView)

        verify(mainView)!!.showLoadingShows()
        verify(getShowsUseCase)!!.execute(PAGE)
        verify(mainView)!!.showShows(showsList!!)

        verifyNoMoreInteractions(mainView)
        verifyNoMoreInteractions(getShowsUseCase)

    }

    @Test(expected = Exception::class)
    fun onViewAttachedFalse() {

        val exception: Exception = Exception("Fuck you")

        `when`(getShowsUseCase!!.execute(PAGE)).thenThrow(exception)

        mainPresenter!!.onViewAttached(mainView)

        verify(mainView)!!.showLoadingShows()
        verify(getShowsUseCase)!!.execute(PAGE)
        verify(mainView)!!.showNetworkError()

        verifyNoMoreInteractions(mainView)
        verifyNoMoreInteractions(getShowsUseCase)

    }

    companion object {

        val PAGE = 1

        fun <T> createAnswer(value: T): Answer<T> {
            return Answer { value }
        }
    }

}
