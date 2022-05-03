package eth.facundoaramayo.simpleprofileview.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eth.facundoaramayo.simpleprofileview.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY id ASC")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: Int)
}
