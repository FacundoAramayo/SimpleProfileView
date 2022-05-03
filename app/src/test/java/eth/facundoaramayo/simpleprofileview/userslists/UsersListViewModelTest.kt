package eth.facundoaramayo.simpleprofileview.userslists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eth.facundoaramayo.simpleprofileview.domain.GetUsersUseCase
import eth.facundoaramayo.simpleprofileview.domain.RemoveUserUseCase
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import eth.facundoaramayo.simpleprofileview.ui.userslist.UsersListViewModel
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
class UsersListViewModelTest {

    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @RelaxedMockK
    private lateinit var getRemoveUserUseCase: RemoveUserUseCase

    private lateinit var viewModel: UsersListViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = UsersListViewModel(getUsersUseCase, getRemoveUserUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if requestUsers return empty show empty list message`() = runTest {
        // GIVEN
        val emptyResponse = listOf<UserModel>()
        coEvery { getUsersUseCase.invoke() } returns emptyResponse

        // WHEN
        viewModel.requestUsers()

        // THEN
        assert(viewModel.observeUsers().value == null)
        assert(viewModel.observeEmpty().value == true)
    }

    @Test
    fun `if requestUsers return a list of users show the list`() = runTest {
        // GIVEN
        val response = listOf(
            UserModel(1, "Mock", "Description", "Uri"),
            UserModel(1, "Mock", "Description", "Uri"),
            UserModel(1, "Mock", "Description", "Uri")
        )
        coEvery { getUsersUseCase.invoke() } returns response

        // WHEN
        viewModel.requestUsers()

        // THEN
        assert(viewModel.observeUsers().value == response)
        assert(viewModel.observeEmpty().value == false)
    }

    @Test
    fun `if requestUsers throw exception show error`() = runTest {
        // GIVEN
        coEvery { getUsersUseCase.invoke() } throws Exception()

        // WHEN
        viewModel.requestUsers()

        // THEN
        assert(viewModel.observeError().value == true)
    }

    @Test
    fun `if removeUser throw exception show error`() = runTest {
        // GIVEN
        val user = UserModel(0, "Name", "Description", "AvatarUri")
        coEvery { user.id?.let { getRemoveUserUseCase.invoke(it) } } throws Exception()

        // WHEN
        viewModel.removeUser(user)

        // THEN
        assert(viewModel.observeError().value == true)
        coVerify(exactly = 0) { getUsersUseCase.invoke() }
    }

    @Test
    fun `if removeUser work then request new user list`() = runTest {
        // GIVEN
        val user = UserModel(0, "Name", "Description", "AvatarUri")
        coEvery { user.id?.let { getRemoveUserUseCase.invoke(it) } } returns Unit

        // WHEN
        viewModel.removeUser(user)

        // THEN
        assert(viewModel.observeError().value == false)
        coVerify(exactly = 1) { getRemoveUserUseCase.invoke(user.id!!) }
        coVerify(exactly = 1) { getUsersUseCase.invoke() }
    }
}
