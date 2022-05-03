package eth.facundoaramayo.simpleprofileview.ui

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import eth.facundoaramayo.simpleprofileview.screenrobot.userdetailrobot.UserDetailRobot
import eth.facundoaramayo.simpleprofileview.screenrobot.userslistrobot.UsersListScreenRobot
import eth.facundoaramayo.simpleprofileview.ui.userslist.UsersListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersListTest {

    @get:Rule
    val scenarioRule: ActivityScenarioRule<UsersListActivity> = ActivityScenarioRule(
        UsersListActivity::class.java
    )

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }


    @Test
    fun emptyList() {
        UsersListScreenRobot()
            .launch()
            .emptyList()
    }

    @Test
    fun clickToAdd() {
        UsersListScreenRobot()
            .launch()
            .clickOnAdd()

        UserDetailRobot()
            .launch()
            .completeName("Name lorem ipsum genesis lorem ipsum genesis")
            .completeDescription("Description large lorem ipsum genesis lorem ipsum genesis lorem ipsum genesis lorem ipsum genesis")
    }

    @Test
    fun clickToAddAndShowError() {
        UsersListScreenRobot()
            .launch()
            .clickOnAdd()

        UserDetailRobot()
            .launch()
            .completeName("Name lorem ipsum genesis lorem ipsum genesis")
            .completeDescription("Description large lorem ipsum genesis lorem ipsum genesis lorem ipsum genesis lorem ipsum genesis")
            .clickOnSave()
            .errorMessage()
    }
}