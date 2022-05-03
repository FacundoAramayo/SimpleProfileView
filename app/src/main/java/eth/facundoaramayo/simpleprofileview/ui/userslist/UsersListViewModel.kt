package eth.facundoaramayo.simpleprofileview.ui.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eth.facundoaramayo.simpleprofileview.domain.GetUsersUseCase
import eth.facundoaramayo.simpleprofileview.domain.RemoveUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val getGetUsersUseCase: GetUsersUseCase,
    private val getRemoveUserUseCase: RemoveUserUseCase
): ViewModel() {

    private var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isEmpty: MutableLiveData<Boolean> = MutableLiveData(false)
    private val users: MutableLiveData<List<UserModel>> = MutableLiveData()

    fun observeError(): LiveData<Boolean> = isError
    fun observeLoading(): LiveData<Boolean> = isLoading
    fun observeEmpty(): LiveData<Boolean> = isEmpty
    fun observeUsers(): LiveData<List<UserModel>> = users

    fun requestUsers() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val result = getGetUsersUseCase.invoke()

                if (result.isNotEmpty()) {
                    users.postValue(result)
                    isEmpty.postValue(false)
                    isLoading.postValue(false)
                } else {
                    isEmpty.postValue(true)
                    isLoading.postValue(false)
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                isError.postValue(true)
            }
        }
    }

    fun removeUser(user: UserModel) {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                user.id?.let {
                    getRemoveUserUseCase.invoke(user.id)
                    requestUsers()
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                isError.postValue(true)
            }
        }
    }
}
