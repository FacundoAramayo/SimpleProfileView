package eth.facundoaramayo.simpleprofileview.screenrobot.userdetailrobot

import eth.facundoaramayo.simpleprofileview.R
import eth.facundoaramayo.simpleprofileview.screenrobot.ScreenRobot

class UserDetailRobot : ScreenRobot<UserDetailRobot>()  {
    fun launch(): UserDetailRobot {
        checkIsVisible(R.id.pick_image_button)
        return this
    }

    fun clickOnSave(): UserDetailRobot {
        clickOnItem(R.id.save)
        return this
    }

    fun completeName(name: String): UserDetailRobot {
        addTextToView(R.id.et_name, name)
        return this
    }

    fun completeDescription(description: String): UserDetailRobot {
        addTextToView(R.id.et_description, description)
        return this
    }

    fun errorMessage(): UserDetailRobot {
        checkViewHasText(R.id.error_message, R.string.error_message_field_is_empty)
        return this
    }
}