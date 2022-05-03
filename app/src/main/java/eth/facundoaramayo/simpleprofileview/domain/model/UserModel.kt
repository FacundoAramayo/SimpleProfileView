package eth.facundoaramayo.simpleprofileview.domain.model

import android.os.Parcelable
import eth.facundoaramayo.simpleprofileview.data.database.entities.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: Int? = null,
    var name: String,
    var description: String,
    var avatar: String
): Parcelable

fun UserEntity.toDomain() = UserModel(id, name, description, avatarUri)
