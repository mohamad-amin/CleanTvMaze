package com.mohamadamin.cleantvmaze.page.main

import com.mohamadamin.cleantvmaze.domain.entity.Show

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/6/17.
 */
interface MainView {

    fun showShows(shows: List<Show>)

    fun showNetworkError()

    fun showLoadingShows()

}