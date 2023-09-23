package com.hilmisatrio.githubuserapp.ui.home

import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.hilmisatrio.githubuserapp.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest {
    @Before
    fun setup() {
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_GithubUserApp)
    }

    @Test
    fun testSearchUser() {
        onView(withId(R.id.search_user)).check(matches(isDisplayed()))
        onView(withId(R.id.search_user)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
            typeText("hilmisatrio16"),
            pressKey(KeyEvent.KEYCODE_ENTER),
            closeSoftKeyboard()
        )
        onView(withId(androidx.appcompat.R.id.search_src_text)).check(matches(withText("hilmisatrio16")))
    }
}