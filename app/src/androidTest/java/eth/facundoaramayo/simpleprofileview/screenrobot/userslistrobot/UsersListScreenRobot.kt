package eth.facundoaramayo.simpleprofileview.screenrobot.userslistrobot

import androidx.test.platform.app.InstrumentationRegistry
import eth.facundoaramayo.simpleprofileview.R
import eth.facundoaramayo.simpleprofileview.screenrobot.ScreenRobot

class UsersListScreenRobot : ScreenRobot<UsersListScreenRobot>() {

    init {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    fun launch(): UsersListScreenRobot {
        checkIsVisible(R.id.main_screen)
        return this
    }

    fun emptyList(): UsersListScreenRobot {
        checkViewHasText(R.id.empty_list, R.string.empty_list)
        return this
    }

    fun clickOnAdd(): UsersListScreenRobot {
        clickOnItem(R.id.btn_add_user)
        return this
    }
}