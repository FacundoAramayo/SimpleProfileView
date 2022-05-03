package eth.facundoaramayo.simpleprofileview.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eth.facundoaramayo.simpleprofileview.domain.EditUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getEditUserUseCase: EditUserUseCase
): ViewModel() {

    private var userSubmitted: MutableLiveData<UserModel> = MutableLiveData()
    private var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isCompleted: MutableLiveData<Boolean> = MutableLiveData(false)

    fun submitUser(name: String, description: String, avatarUri: String) {
        isError.postValue(false)
        when {
            name.isEmpty() -> isError.postValue(true)
            description.isEmpty() -> isError.postValue(true)
            avatarUri.isEmpty() -> isError.postValue(true)
            else -> {
                val user = UserModel(name = name, description = description, avatar = avatarUri)
                saveUser(user)
                //userSubmitted.postValue()
            }
        }
    }

    private fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getEditUserUseCase.invoke(userModel)

            if (result) {
                isLoading.postValue(false)
                isCompleted.postValue(true)
            }
        }
    }

    fun observeError(): LiveData<Boolean> = isError
    fun observeLoading(): LiveData<Boolean> = isLoading
    fun observeIsCompleted(): LiveData<Boolean> = isCompleted
    fun observeUser(): LiveData<UserModel> = userSubmitted
}