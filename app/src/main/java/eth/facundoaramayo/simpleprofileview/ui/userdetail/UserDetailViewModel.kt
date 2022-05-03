package eth.facundoaramayo.simpleprofileview.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eth.facundoaramayo.simpleprofileview.domain.CreateUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.EditUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getEditUserUseCase: EditUserUseCase,
    private val getCreateUserUseCase: CreateUserUseCase
): ViewModel() {

    var isEditing = false
    var avatarUri = ""
    var currentUser: UserModel? = null

    private var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isCompleted: MutableLiveData<Boolean> = MutableLiveData(false)

    fun submitUser(name: String, description: String) {
        isError.postValue(false)
        when {
            name.isEmpty() ||
            description.isEmpty() ||
            avatarUri.isEmpty() -> isError.postValue(true)
            else -> {
                var user: UserModel
                currentUser?.let {
                    it.name = name
                    it.description = description
                    it.avatar = avatarUri
                    user = it
                    saveUser(user)
                } ?: run {
                    user = UserModel(name = name, description = description, avatar = avatarUri)
                    saveUser(user)
                }
            }
        }
    }

    private fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val result =
                    if (isEditing)
                        getEditUserUseCase.invoke(userModel)
                    else
                        getCreateUserUseCase.invoke(userModel)

                if (result) {
                    isLoading.postValue(false)
                    isCompleted.postValue(true)
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                isError.postValue(true)
            }
        }
    }

    fun observeError(): LiveData<Boolean> = isError
    fun observeLoading(): LiveData<Boolean> = isLoading
    fun observeIsCompleted(): LiveData<Boolean> = isCompleted
}
