package com.sevenpeakssoftware.cars.ui

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sevenpeakssoftware.cars.DaggerTestApplicationRule
import com.sevenpeakssoftware.cars.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode


@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class CarsFeedFragmentTest {

    @get:Rule
    val rule = DaggerTestApplicationRule()


    @Test
    fun display_loadingIndicator() {
        launchActivity()
        Espresso.onView(ViewMatchers.withId(R.id.refresh_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun display_loaded_items() {
        launchActivity()
        Espresso.onView(ViewMatchers.withId(R.id.car_image))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Audi-Greatest Start"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.articles_list) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

}