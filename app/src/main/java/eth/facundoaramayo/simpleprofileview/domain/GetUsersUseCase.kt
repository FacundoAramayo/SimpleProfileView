package eth.facundoaramayo.simpleprofileview.domain

import eth.facundoaramayo.simpleprofileview.data.UserRepository
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import javax.inject.Inject
import kotlin.jvm.Throws

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {
    @Throws
    suspend operator fun invoke(): List<UserModel> {
        return repository.getAllUsers()
    }
}
