package com.asharya.divinex

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.asharya.divinex.ui.LoginActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class PhotoSharingInstrumentedTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLogin() {
        onView(withId(R.id.etUsername))
            .perform(typeText("ash"))

        onView(withId(R.id.etPassword))
            .perform(typeText("wakandaforever"))

        closeSoftKeyboard()

        onView(withId(R.id.btnLogin))
            .perform(click())


        onView(withId(R.id.tvHeader))
            .check(matches(isDisplayed()))

    }

}