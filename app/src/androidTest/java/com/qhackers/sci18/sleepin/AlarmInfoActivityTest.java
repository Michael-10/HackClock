package com.qhackers.sci18.sleepin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TimePicker;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AlarmInfoActivityTest {

    @Rule
    public ActivityTestRule<AlarmInfoActivity> myActivityRule = new ActivityTestRule<>(AlarmInfoActivity.class, true, false);

    Context myContext = getInstrumentation().getTargetContext();
    SharedPreferences myPreferences = myContext.getSharedPreferences("Sleepin", Context.MODE_PRIVATE);
    SharedPreferences.Editor myEditor = myPreferences.edit();

    @Test
    public void saveAlarmChanges_writesNewAlarmToSharedPreferences() throws Exception {
        // start activity with intent to create alarm
        Intent intent = new Intent();
        intent.putExtra("action", "create");
        myActivityRule.launchActivity(intent);

        // new alarm values
        int hour = 7;
        int minute = 47;
        boolean isVibrate = true;
        String name = "CreateAlarmTestCase";
        onView(withId(R.id.timePicker)).perform(PickerActions.setTime(hour, minute));
        onView(withId(R.id.isVibrate)).perform(click());
        onView(withId(R.id.alarmName)).perform(typeText(name), closeSoftKeyboard());

        // check if alarm was saved to SharedPreferences
        onView(withId(R.id.ok)).perform(click());
        String expectedAlarmID = "alarm0";
        String expectedString = "{\"alarmName\":\"" + name + "\",\"hour\":" + hour + ",\"id\":\"" + expectedAlarmID + "\",\"isSet\":true,\"minute\":" + minute + ",\"vibrate\":" + isVibrate + "}";
        assertThat(myPreferences.getString(expectedAlarmID, null), is(equalTo(expectedString)));
    }

    @After
    public void tearDown() throws Exception {
        myEditor.clear();
        myEditor.commit();
    }

}
