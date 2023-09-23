package com.hilmisatrio.githubuserapp.ui.setting

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import com.hilmisatrio.githubuserapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingFragmentTest {
    @Before
    fun setup() {
        launchFragmentInContainer<SettingFragment>(themeResId = R.style.Theme_GithubUserApp)
    }

    @Test
    fun testThemeApplication() {
        onView(withId(R.id.swicth_theme)).check(matches(isDisplayed()))
        onView(withId(R.id.swicth_theme)).perform(click())

    }
}