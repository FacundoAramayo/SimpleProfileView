package eth.facundoaramayo.simpleprofileview.domain

import eth.facundoaramayo.simpleprofileview.data.UserRepository
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import javax.inject.Inject

class EditUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(userModel: UserModel): Boolean {
        repository.insertUser(userModel)
        return true
    }
}
