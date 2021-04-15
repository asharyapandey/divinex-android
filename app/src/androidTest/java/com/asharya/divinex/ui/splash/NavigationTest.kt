package com.asharya.divinex.ui.splash


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.asharya.divinex.R
import com.asharya.divinex.ui.login.LoginActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA",
        )

    @Test
    fun navigationTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.etUsername),
                childAtPosition(
                    allOf(
                        withId(R.id.clLogin),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("ash"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.etPassword),
                childAtPosition(
                    allOf(
                        withId(R.id.clLogin),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("wakandaforever"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.btnLogin), withText("Login"),
                childAtPosition(
                    allOf(
                        withId(R.id.clLogin),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        Thread.sleep(5000)

        val bottomNavigationItemView = onView(
            withId(R.id.addPostFragment)
        )
        bottomNavigationItemView.perform(click())
        Thread.sleep(5000)

        val textView = onView(
            allOf(
                withId(R.id.textView8), withText("Add Photo"),
                withParent(withParent(withId(R.id.flFragment))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Add Photo")))

        val textView2 = onView(
            allOf(
                withId(R.id.textView8), withText("Add Photo"),
                withParent(withParent(withId(R.id.flFragment))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Add Photo")))

        val bottomNavigationItemView2 = onView(
                withId(R.id.notificationFragment)
        )
        bottomNavigationItemView2.perform(click())
        Thread.sleep(5000)

        val textView3 = onView(
            allOf(
                withId(R.id.textView), withText("Notification"),
                withParent(withParent(withId(R.id.flFragment))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Notification")))

        val bottomNavigationItemView3 = onView(
                withId(R.id.profileFragment)
        )
        bottomNavigationItemView3.perform(click())
        Thread.sleep(5000)

        val textView4 = onView(
            allOf(
                withId(R.id.tvUsernameHeading), withText("ash"),
                withParent(withParent(withId(R.id.flFragment))),
                isDisplayed()
            )
        )
        textView4.check(matches(isDisplayed()))

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnEditProfile), withText("Edit Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.flFragment),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        Thread.sleep(5000)

        val imageView = onView(
            allOf(
                withId(R.id.civEditProfileImage),
                withParent(withParent(withId(R.id.flFragment))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
