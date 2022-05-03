package eth.facundoaramayo.simpleprofileview.domain

import eth.facundoaramayo.simpleprofileview.data.UserRepository
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(id: Int) {
        repository.deleteUser(id)
    }
}
