package com.sample.newsfeed

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sample.news.R
import com.sample.newsfeed.layers.presentation.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RecyclerviewTest {

    /** Launches [MainActivity] for every test  */
    @Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    /**
     * Test a heading of the recycler view is clickable.
     */
    @Test
    fun testIsClickable() {
        Espresso.onView(withId(R.id.nav_host_container))
            .check(ViewAssertions.matches(isClickable()))
    }

    @Test
    fun performScrollToDescendantPosition() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(
                        withText(
                            "dubai"
                        )
                    )
                )
            )
    }

    @Test
    fun performClickOnFirstItem() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
    }

    @Test
    fun performClickOnBasedOnText() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("")), ViewActions.click()
                )
            )
    }

    @Test
    fun checkTheItemPositionContent() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, ViewActions.click())
            )
    }
}