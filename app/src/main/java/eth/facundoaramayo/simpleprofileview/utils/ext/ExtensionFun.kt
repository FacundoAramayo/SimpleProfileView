package eth.facundoaramayo.simpleprofileview.utils.ext

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

fun ImageView.setImage(data: Uri?) {
    Glide.with(this.context)
        .load(data)
        .transform(CircleCrop())
        .into(this)
}

fun ImageView.setImage(data: String?) {
    Glide.with(this.context)
        .load(data)
        .transform(CircleCrop())
        .into(this)
}
