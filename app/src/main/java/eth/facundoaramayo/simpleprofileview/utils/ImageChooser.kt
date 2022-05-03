package eth.facundoaramayo.simpleprofileview.utils

import android.content.Intent
import android.provider.MediaStore

class ImageChooser {

    companion object {
        @JvmStatic
        fun getImageChooser(): Intent {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            return chooserIntent
        }
    }
}
