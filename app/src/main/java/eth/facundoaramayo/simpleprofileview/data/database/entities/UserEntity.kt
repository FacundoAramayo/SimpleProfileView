package eth.facundoaramayo.simpleprofileview.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "avatar_uri")
    val avatarUri: String = ""
)

fun UserModel.toDatabase() = UserEntity(name = name, description = description, avatarUri = avatar)
