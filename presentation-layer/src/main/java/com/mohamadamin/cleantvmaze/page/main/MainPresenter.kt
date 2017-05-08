package com.mohamadamin.cleantvmaze.page.main

import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.interactor.GetShowsUseCase
import easymvp.RxPresenter
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
open class MainPresenter
    @Inject constructor(private val getShowsUseCase: GetShowsUseCase) : RxPresenter<MainView>() {
    
    private var page = 0
    private var shows: List<Show>? = null
    
    fun loadShows(forceUpdate: Boolean = false) {

        if (forceUpdate) shows = null
        if (shows != null && !shows!!.isEmpty()) return

        view?.showLoadingShows(forceUpdate)

        val subscription = getShowsUseCase.execute(++page)
                .observeOn(getShowsUseCase.postExecutionThread)
                .subscribe(
                        { list: List<Show> ->
                            shows = list
                            if (isViewAttached) {
                                if (list.isEmpty()) view!!.showNetworkError()
                                else view!!.showShows(list)
                            }
                        },
                        { throwable: Throwable ->
                            Timber.e(throwable)
                            if (isViewAttached) {
                                view!!.showNetworkError()
                            }
                        }
                )

        addSubscription(subscription)

    }

    override fun onViewAttached(view: MainView?) {
        super.onViewAttached(view)
        if (shows == null || shows!!.isEmpty()) loadShows()
    }

}