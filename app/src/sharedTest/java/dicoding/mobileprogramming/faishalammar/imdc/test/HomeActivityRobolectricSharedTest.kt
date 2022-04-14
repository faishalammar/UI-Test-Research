package dicoding.mobileprogramming.faishalammar.imdc.test

import android.graphics.Movie
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import dicoding.mobileprogramming.faishalammar.imdc.*
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.ui.activity.HomeActivity
import dicoding.mobileprogramming.faishalammar.imdc.utils.EspressoIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition

import androidx.test.espresso.*

import androidx.test.espresso.IdlingRegistry
import dicoding.mobileprogramming.faishalammar.imdc.data.FakeMoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.LocalDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.RemoteDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.MovieResponse
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.TvSeriesResponse
import dicoding.mobileprogramming.faishalammar.imdc.ui.adapter.FilmListAdapter
import dicoding.mobileprogramming.faishalammar.imdc.utils.AppExecutors
import dicoding.mobileprogramming.faishalammar.imdc.utils.DataDummy
import org.mockito.ArgumentMatchers
import org.mockito.Mockito














@Config(
    sdk = [Build.VERSION_CODES.M],
    application = MyTestApplication::class,
    instrumentedPackages = ["androidx.loader.content"]
)
@RunWith(AndroidJUnit4::class)
class HomeActivityRobolectricSharedTest{


    private val robot = Robot()

    @Before
    fun setUp() {
        robot.setup()
    }

    @After
    fun tearDown() {
        robot.tearsDown()
    }


    @Test
    fun loadMovie() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            THEN { onView(withId(R.id.rv_movie)).check(matches(isDisplayed())) }
        }
    }

    @Test
    fun loadDetailMovie() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            THEN { onView(withId(R.id.rv_movie)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())) }
            THEN { onView(withId(R.id.film_detail_title)).check(matches(isDisplayed())) }
            THEN { onView(withId(R.id.film_description_detail)).check(matches(isDisplayed())) }
            THEN { onView(withId(R.id.film_detail_genre)).check(matches(isDisplayed())) }
            THEN {
                onView(withId(R.id.film_detail_rating)).check(matches(isDisplayed()))
                onView(withId(R.id.film_detail_poster)).check(matches(isDisplayed()))
            }
        }
    }

    @Test
    fun loadSeries() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            THEN { onView(withId(R.id.page_2)).perform(click()) }
            THEN { onView(withId(R.id.rv_series)).check(matches(isDisplayed())) }
        }
    }

    @Test
    fun loadDetailSeries() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            THEN { onView(withId(R.id.page_2)).perform(click()) }
            AND { onView(withId(R.id.rv_series)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())) }
            THEN {
                onView(withId(R.id.film_detail_title)).check(matches(isDisplayed()))
                onView(withId(R.id.film_description_detail)).check(matches(isDisplayed()))
                onView(withId(R.id.film_detail_genre)).check(matches(isDisplayed()))
                onView(withId(R.id.film_detail_rating)).check(matches(isDisplayed()))
                onView(withId(R.id.film_detail_poster)).check(matches(isDisplayed()))
            }
        }
    }

    @Test
    fun loadFavouriteMovies() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            WHEN { onView(withId(R.id.page_3)).perform(click()) }
            THEN { onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed())) }
        }

    }

    @Test
    fun loadFavouriteSeries() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            WHEN { onView(withId(R.id.page_3)).perform(click()) }
            AND { onView(withId(R.id.tabs)).perform(selectTabAtPosition(1)) }
            THEN { onView(withId(R.id.rv_fav_series)).check(matches(isDisplayed())) }
        }
    }

    @Test
    fun addAndDeleteFavouriteMovies() {
        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            WHEN { backToListFavouriteMovie() }
            THEN { val currentFavMovie = countRecyclerViewItem(R.id.rv_fav_movie)
                Log.d("current fav movie : ", currentFavMovie.toString())

                backToListMovieAndClickLoveButton()

                backToListFavouriteMovie()

                val newFavMovieNumber = countRecyclerViewItem(R.id.rv_fav_movie)
                Log.d("current fav movie : ", newFavMovieNumber.toString())


                if(newFavMovieNumber > currentFavMovie) {
                    // Previous Button Clicked Was Add to Fav Button
                    // Success to Add Favourite Movies
                    // able to click item in rv to show rv not empty
                    onView(withId(R.id.rv_fav_movie)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

                } else if (newFavMovieNumber < currentFavMovie) {
                    // Previous Button Clicked Was Delete from Fav Button
                    // First Item on RV Previously Already Favourite so What we do before is remove it
                    // So, we need to add it again
                    backToListMovieAndClickLoveButton()

                    // able to click item in rv to show rv not empty
                    backToListFavouriteMovie()
                    onView(withId(R.id.rv_fav_movie)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
                }
            }
            AND { backToListFavouriteMovie() }
            THEN { onView(withId(R.id.rv_fav_movie)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.delete_button))); }
        }
    }

    @Test
    fun addAndDeleteFavouriteSeries() {

        RUN_UI_TEST(robot){
            GIVEN { createHomeActivity() }
            WHEN { backToListFavouriteSeries() }
            AND {
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
                    onView(withId(R.id.rv_fav_series)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
                } else if (newFavMovieSeries < currentFavSeries) {
                    // Previous Button Clicked Was Delete from Fav Button
                    // First Item on RV Previously Already Favourite so What we do before is remove it
                    // So, we need to add it again
                    backToListSeriesAndClickLoveButton()

                    // able to click item in rv to show rv not empty
                    backToListFavouriteSeries()
                    onView(withId(R.id.rv_fav_series)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
                }
            }
            AND { backToListFavouriteSeries() }
            THEN { onView(withId(R.id.rv_fav_series)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.delete_button))); }
        }
    }



    private class Robot: BaseRobot() {

        var activityScenario: ActivityScenario<HomeActivity>? = null

//        private val remote = Mockito.mock(RemoteDataSource::class.java)
//        private val local = Mockito.mock(LocalDataSource::class.java)
//        private val appExecutors = Mockito.mock(AppExecutors::class.java)
//
//        private val repository = FakeMoviesSeriesRepository(remote, local, appExecutors)

        private val movieForTest : MovieResponse = DataDummy.generateRemoteDummyMovies()[0]
        private val seriesForTest : TvSeriesResponse = DataDummy.generateRemoteDummySeries()[0]
        private val listMovieResponse : ArrayList<MovieResponse> = DataDummy.generateRemoteDummyMovies()
        private val listSeriesResponse : ArrayList<TvSeriesResponse> = DataDummy.generateRemoteDummySeries()

        override fun setup() {
            IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        }

        override fun tearsDown() {
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
            activityScenario?.close()
        }


        fun createHomeActivity(args: Bundle? = null) {
            activityScenario = ActivityScenario.launch(HomeActivity::class.java, args)
        }

        fun backToListMovieAndClickLoveButton(){
            onView(withId(R.id.page_1)).perform(click())
            onView(withId(R.id.rv_movie)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.fav_button)));
        }

        fun backToListSeriesAndClickLoveButton(){
            onView(withId(R.id.page_2)).perform(click())
            onView(withId(R.id.rv_series)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.fav_button)));
        }

        fun backToListFavouriteMovie(){
            onView(withId(R.id.page_3)).perform(click())
            onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        }

        fun backToListFavouriteSeries(){
            onView(withId(R.id.page_3)).perform(click())
            onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
            onView(withId(R.id.rv_fav_series)).check(matches(isDisplayed()))
        }

        fun selectTabAtPosition(tabIndex: Int): ViewAction {
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

        fun clickChildViewWithId(id: Int): ViewAction {
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

        fun countRecyclerViewItem(rvId : Int): Int {
            var item = 0
            activityScenario?.onActivity { activity ->
                val recyclerView = activity.findViewById(rvId) as RecyclerView
                recyclerView.adapter?.let {
                    item = it.itemCount
                }
            }
            return item
        }

        fun waitFor(delay: Long): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> = isRoot()
                override fun getDescription(): String = "wait for $delay milliseconds"
                override fun perform(uiController: UiController, v: View?) {
                    uiController.loopMainThreadForAtLeast(delay)
                }
            }
        }

        object PagedListUtil {

            fun <T> mockPagedList(list: List<T>): PagedList<*> {
                val pagedList = Mockito.mock(PagedList::class.java) as PagedList<*>
                Mockito.`when`(pagedList[ArgumentMatchers.anyInt()]).then { invocation ->
                    val index = invocation.arguments.first() as Int
                    list[index]
                }
                Mockito.`when`(pagedList.size).thenReturn(list.size)

                return pagedList
            }
        }

    }

}