package eth.facundoaramayo.simpleprofileview.domain.model

import eth.facundoaramayo.simpleprofileview.data.database.entities.UserEntity

data class UserModel(
    val id: Int = 0,
    val name: String,
    val description: String,
    val avatar: String
)

fun UserEntity.toDomain() = UserModel(id, name, description, avatarUri)
