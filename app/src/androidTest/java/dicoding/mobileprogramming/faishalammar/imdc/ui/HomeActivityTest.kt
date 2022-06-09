package dicoding.mobileprogramming.faishalammar.imdc.ui

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.google.android.material.tabs.TabLayout
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.ui.activity.HomeActivity
import dicoding.mobileprogramming.faishalammar.imdc.utils.EspressoIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<HomeActivity> = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }


    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.film_detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.film_description_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.film_detail_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.film_detail_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.film_detail_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSeries() {
        onView(withId(R.id.page_2)).perform(click())
        onView(withId(R.id.rv_series)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailSeries() {
        onView(withId(R.id.page_2)).perform(click())
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.film_detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.film_description_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.film_detail_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.film_detail_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.film_detail_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavouriteMovies() {
        onView(withId(R.id.page_3)).perform(click())
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavouriteSeries() {
        onView(withId(R.id.page_3)).perform(click())
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        onView(withId(R.id.rv_fav_series)).check(matches(isDisplayed()))
    }

    @Test
    fun addAndDeleteFavouriteMovies() {

        // ADD Favourite Movies

        backToListFavouriteMovie()

        val currentFavMovie = countRecyclerViewItem(R.id.rv_fav_movie)
        Log.d("current fav movie : ", currentFavMovie.toString())

        backToListMovieAndClickLoveButton()

        backToListFavouriteMovie()

        val newFavMovieNumber = countRecyclerViewItem(R.id.rv_fav_movie)
        Log.d("current fav movie : ", newFavMovieNumber.toString())


        if(newFavMovieNumber > currentFavMovie) {
            // Previous Button Clicked Was Add to Fav Button
            // Success to Add Favourite Movies
            // able to click item in rv to show rv not empty
            onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        } else if (newFavMovieNumber < currentFavMovie) {
            // Previous Button Clicked Was Delete from Fav Button
            // First Item on RV Previously Already Favourite so What we do before is remove it
            // So, we need to add it again
            backToListMovieAndClickLoveButton()

            // able to click item in rv to show rv not empty
            backToListFavouriteMovie()
            onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
        }

        // At the end, first item in rv will be add to favourite
        // Its time to remove it from favourite

        backToListFavouriteMovie()

        // Click "delete icon" on rv_fav_movie
        onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.delete_button)));
    }

    @Test
    fun addAndDeleteFavouriteSeries() {

        // ADD Favourite Series

        backToListFavouriteSeries()

        val currentFavSeries = countRecyclerViewItem(R.id.rv_fav_series)
        Log.d("current fav series : ", currentFavSeries.toString())

        backToListSeriesAndClickLoveButton()

        backToListFavouriteSeries()

        val newFavMovieSeries = countRecyclerViewItem(R.id.rv_fav_series)
        Log.d("current fav series : ", newFavMovieSeries.toString())


        if(newFavMovieSeries > currentFavSeries) {
            // Previous Button Clicked Was Add to Fav Button
            // Success to Add Favourite Series
            // able to click item in rv to show rv not empty
            onView(withId(R.id.rv_fav_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        } else if (newFavMovieSeries < currentFavSeries) {
            // Previous Button Clicked Was Delete from Fav Button
            // First Item on RV Previously Already Favourite so What we do before is remove it
            // So, we need to add it again
            backToListSeriesAndClickLoveButton()

            // able to click item in rv to show rv not empty
            backToListFavouriteSeries()
            onView(withId(R.id.rv_fav_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
        }

        // At the end, first item in rv will be add to favourite
        // Its time to remove it from favourite

        backToListFavouriteSeries()

        // Click "delete icon" on rv_fav_movie
        onView(withId(R.id.rv_fav_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.delete_button)));
    }


    private fun backToListMovieAndClickLoveButton(){
        onView(withId(R.id.page_1)).perform(click())
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.fav_button)));
    }

    private fun backToListSeriesAndClickLoveButton(){
        onView(withId(R.id.page_2)).perform(click())
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.fav_button)));
    }

    private fun backToListFavouriteMovie(){
        onView(withId(R.id.page_3)).perform(click())
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
    }

    private fun backToListFavouriteSeries(){
        onView(withId(R.id.page_3)).perform(click())
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        onView(withId(R.id.rv_fav_series)).check(matches(isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction  {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    private fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }

    private fun countRecyclerViewItem(rvId : Int): Int {
        val recyclerView = mActivityRule.activity.findViewById(rvId) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }

}