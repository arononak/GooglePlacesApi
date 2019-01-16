package com.example.googleplacesautocomplete

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.googleplacesautocomplete.ui.HistoryAdapter
import com.example.googleplacesautocomplete.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before() {

    }

    @Test
    fun deleteItemOpenPlaceDeleteItem() {

        // java.lang.SecurityException: Injecting to another application requires INJECT_EVENTS permission
        // onView(withId(R.id.place_autocomplete_search_input)).perform(clearText(), typeText("a"))

        // activityTestRule.activity.findViewById<>(R.id.place_autocomplete_search_input)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(2))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.Companion.HistoryViewHolder>(
                0,
                longClick()
            )
        )
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(1))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.Companion.HistoryViewHolder>(
                0,
                click()
            )
        )
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.Companion.HistoryViewHolder>(
                0,
                longClick()
            )
        )
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(0))
    }
}