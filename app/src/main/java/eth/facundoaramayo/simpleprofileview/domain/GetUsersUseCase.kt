package eth.facundoaramayo.simpleprofileview.domain

import eth.facundoaramayo.simpleprofileview.data.UserRepository
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): List<UserModel> {
        val users = repository.getAllUsers()
        return users
    }
}
