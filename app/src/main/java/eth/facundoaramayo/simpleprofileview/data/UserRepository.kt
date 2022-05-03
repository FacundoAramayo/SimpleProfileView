package eth.facundoaramayo.simpleprofileview.data

import eth.facundoaramayo.simpleprofileview.data.database.dao.UserDao
import eth.facundoaramayo.simpleprofileview.data.database.entities.UserEntity
import eth.facundoaramayo.simpleprofileview.data.database.entities.toDatabase
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import eth.facundoaramayo.simpleprofileview.domain.model.toDomain
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getAllUsers(): List<UserModel> {
        val response: List<UserEntity> = userDao.getAllUsers()
        return response.map { it.toDomain() }
    }

    suspend fun insertUser(userModel: UserModel) {
        userDao.insertUser(userModel.toDatabase())
    }

    suspend fun editUser(userModel: UserModel) {
        userDao.insertUser(userModel.toDatabase())
    }

    suspend fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }
}