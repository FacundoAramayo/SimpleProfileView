package eth.facundoaramayo.simpleprofileview.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import eth.facundoaramayo.simpleprofileview.data.database.dao.UserDao
import eth.facundoaramayo.simpleprofileview.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
}