package eth.facundoaramayo.simpleprofileview.screenrobot

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.assertion.ViewAssertions
import org.hamcrest.CoreMatchers
import androidx.annotation.StringRes
import androidx.test.espresso.action.ViewActions.*

abstract class ScreenRobot<T> {

    fun checkIsVisible(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        return this as T
    }

    fun checkIsHidden(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        }
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes stringId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(stringId)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this as T
    }

    fun checkIsSelected(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.isSelected()))
        }
        return this as T
    }

    fun clickOnItem(@IdRes viewId:Int): T {
        Espresso.onView(ViewMatchers.withId(viewId)).perform(click())
        return this as T
    }

    fun addTextToView(@IdRes viewId:Int, text: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(click())
            .perform(typeText(text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(closeSoftKeyboard())
        return this as T
    }
}
