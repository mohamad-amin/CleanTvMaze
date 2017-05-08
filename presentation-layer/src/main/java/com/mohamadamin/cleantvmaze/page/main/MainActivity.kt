package com.mohamadamin.cleantvmaze.page.main

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mohamadamin.cleantvmaze.R
import com.mohamadamin.cleantvmaze.base.BaseActivity
import com.mohamadamin.cleantvmaze.base.di.ApplicationComponent
import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.view.SpaceItemDecoration
import easymvp.annotation.ActivityView
import easymvp.annotation.Presenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.no_connection.*
import timber.log.Timber
import javax.inject.Inject

@ActivityView(presenter = MainPresenter::class, layout = R.layout.activity_main)
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    @Inject
    @Presenter
    lateinit var presenter: MainPresenter

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mainAdapter = MainAdapter(this)
        setupToolbar()
        setupActions()
        setAdapter()
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        applicationComponent.injectTo(this)
    }

    override fun showShows(shows: List<Show>) {
        main_internet.visibility = View.GONE
        main_recycler.visibility = View.VISIBLE
        main_refresh_layout.isRefreshing = false
        mainAdapter.shows = shows
        Timber.i("Show shows")
    }

    override fun showNetworkError() {
        main_recycler.visibility = View.GONE
        main_internet.visibility = View.VISIBLE
        main_refresh_layout.isRefreshing = false
        Timber.i("Show network error")
    }

    override fun showLoadingShows(refresh: Boolean) {
        if (refresh) {
            main_internet.visibility = View.GONE
        } else {
            setVisibility(View.GONE, main_recycler, main_internet)
        }
        main_refresh_layout.isRefreshing = true
        Timber.i("Show loading shows")
    }

    /**
     * Function for:
     * Setting [toolbar] instead of [android.support.v7.app.ActionBar],
     * Creating a [ActionBarDrawerToggle] in [toolbar] as drawer layout's listener,
     * Setting [nav_view]'s item click listener.
     */
    fun setupToolbar() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * Responding to item click in this activity
     */
    fun setupActions() {
        internet_retry.setOnClickListener { presenter.loadShows(true) }
        main_refresh_layout.setOnRefreshListener { presenter.loadShows(true) }
    }

    /**
     * Setting an adapter and some fancy item decorations to the [main_recycler] view
     */
    fun setAdapter() {
        val space = resources.getDimensionPixelSize(R.dimen.space)
        main_recycler.adapter = mainAdapter
        if (main_recycler.tag == null || main_recycler.tag as Int != space) {
            main_recycler.addItemDecoration(SpaceItemDecoration(space))
            main_recycler.tag = space
        }
    }


    /**
     * This function closes the [drawer_layout] if it is open
     * before leaving the activity
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /** @inheritdoc */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /** @inheritdoc */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /** @inheritdoc */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }

    fun Context.setVisibility(visibility: Int, vararg view: View) = view.iterator().forEach {
        it.visibility = visibility
    }

}
