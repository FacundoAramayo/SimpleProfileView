package eth.facundoaramayo.simpleprofileview.data.database.dao

import androidx.room.*
import eth.facundoaramayo.simpleprofileview.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY id ASC")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: Int)
}
