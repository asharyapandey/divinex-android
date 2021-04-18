package com.asharya.divinex.ui.splash


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.asharya.divinex.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class NotificationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
"android.permission.CAMERA",
"android.permission.WRITE_EXTERNAL_STORAGE")

    @Test
    fun notificationTest() {
        val appCompatEditText = onView(
allOf(withId(R.id.etUsername),
childAtPosition(
allOf(withId(R.id.clLogin),
childAtPosition(
withId(android.R.id.content),
0)),
1),
isDisplayed()))
        appCompatEditText.perform(replaceText("ash"), closeSoftKeyboard())
        
        val appCompatEditText2 = onView(
allOf(withId(R.id.etPassword),
childAtPosition(
allOf(withId(R.id.clLogin),
childAtPosition(
withId(android.R.id.content),
0)),
2),
isDisplayed()))
        appCompatEditText2.perform(replaceText("wakandaforever"), closeSoftKeyboard())
        
        val materialButton = onView(
allOf(withId(R.id.btnLogin), withText("Login"),
childAtPosition(
allOf(withId(R.id.clLogin),
childAtPosition(
withId(android.R.id.content),
0)),
3),
isDisplayed()))
        materialButton.perform(click())
        
        val bottomNavigationItemView = onView(
allOf(withId(R.id.notificationFragment), withContentDescription("Notification"),
childAtPosition(
childAtPosition(
withId(R.id.bnvDashboard),
0),
3),
isDisplayed()))
        bottomNavigationItemView.perform(click())
        
        val textView = onView(
allOf(withId(R.id.textView), withText("Notification"),
withParent(withParent(withId(R.id.flFragment))),
isDisplayed()))
        textView.check(matches(withText("Notification")))
        }
    
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    }
