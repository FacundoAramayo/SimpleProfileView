package eth.facundoaramayo.simpleprofileview.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eth.facundoaramayo.simpleprofileview.domain.CreateUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.EditUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import eth.facundoaramayo.simpleprofileview.ui.userdetail.UserDetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    @RelaxedMockK
    private lateinit var getEditUserUseCase: EditUserUseCase

    @RelaxedMockK
    private lateinit var getCreateUserUseCase: CreateUserUseCase

    private lateinit var viewModel: UserDetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = UserDetailViewModel(getEditUserUseCase, getCreateUserUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if some field is empty then show error`() = runTest {
        // GIVEN
        viewModel.isEditing = true
        val user = UserModel(0, "Name", "Description", "AvatarUri")
        coEvery { getEditUserUseCase.invoke(user) } throws Exception()

        // WHEN
        viewModel.submitUser("", "Description")

        // THEN
        assert(viewModel.observeError().value == true)
        coVerify(exactly = 0) { getEditUserUseCase.invoke(user) }
    }

    @Test
    fun `if submitUser fails show error`() = runTest {
        // GIVEN
        viewModel.isEditing = true
        viewModel.avatarUri = "AvatarUri"
        val user = UserModel(0, "Name", "Description", "AvatarUri")
        viewModel.currentUser = user
        coEvery { getEditUserUseCase.invoke(user) } throws Exception()

        // WHEN
        viewModel.submitUser("Name", "Description")

        // THEN
        assert(viewModel.observeError().value == true)
        coVerify { getEditUserUseCase.invoke(user) }
    }

    @Test
    fun `if is editing and user is submitted then update user`() = runTest {
        // GIVEN
        viewModel.isEditing = true
        viewModel.avatarUri = "AvatarUri"
        val user = UserModel(0, "Name", "Description", "AvatarUri")
        viewModel.currentUser = user
        coEvery { getEditUserUseCase.invoke(user) } returns true

        // WHEN
        viewModel.submitUser("Name", "Description")

        // THEN
        assert(viewModel.observeError().value == false)
        assert(viewModel.observeIsCompleted().value == true)
        coVerify (exactly = 1) { getEditUserUseCase.invoke(user) }
        coVerify (exactly = 0) { getCreateUserUseCase.invoke(user) }
    }

    @Test
    fun `if is creating and user is submitted then create user`() = runTest {
        // GIVEN
        viewModel.isEditing = false
        viewModel.avatarUri = "AvatarUri"
        val user = UserModel(null, "Name", "Description", "AvatarUri")
        viewModel.currentUser = null
        coEvery { getCreateUserUseCase.invoke(user) } returns true

        // WHEN
        viewModel.submitUser("Name", "Description")

        // THEN
        assert(viewModel.observeError().value == false)
        assert(viewModel.observeIsCompleted().value == true)
        coVerify (exactly = 0) { getEditUserUseCase.invoke(user) }
        coVerify (exactly = 1) { getCreateUserUseCase.invoke(user) }
    }
}