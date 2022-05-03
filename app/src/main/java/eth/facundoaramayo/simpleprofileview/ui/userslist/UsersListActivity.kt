package eth.facundoaramayo.simpleprofileview.ui.userslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import eth.facundoaramayo.simpleprofileview.databinding.ActivityUsersListBinding
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import eth.facundoaramayo.simpleprofileview.ui.adapter.OnUserItemClickListener
import eth.facundoaramayo.simpleprofileview.ui.adapter.UserAdapter
import eth.facundoaramayo.simpleprofileview.ui.userdetail.UserDetailActivity

@AndroidEntryPoint
class UsersListActivity : AppCompatActivity(), OnUserItemClickListener {

    private lateinit var viewBinding: ActivityUsersListBinding
    private val viewModel by viewModels<UsersListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUsersListBinding.inflate(layoutInflater)

        setListeners()
        setObservers()

        viewBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@UsersListActivity)
                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }
        }.also {
            setContentView(it.root)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUsers()
    }

    private fun setListeners() {
        viewBinding.btnAddUser.setOnClickListener {
            startActivity(Intent(this, UserDetailActivity::class.java))
        }
    }

    private fun setObservers() {
        viewModel.apply {
            observeError().observe(this@UsersListActivity) { isError ->

            }
            observeLoading().observe(this@UsersListActivity) { isLoading ->
                viewBinding.loader.root.visibility = if (isLoading) View.VISIBLE else View.GONE

            }
            observeUsers().observe(this@UsersListActivity) { list ->
                if (list.isEmpty().not()) {
                    val adapter = UserAdapter(list, this@UsersListActivity)
                    viewBinding.recyclerView.adapter = adapter
                    viewBinding.emptyList.visibility = View.GONE
                }
            }
            observeEmpty().observe(this@UsersListActivity) { isEmpty ->
                viewBinding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
                viewBinding.emptyList.visibility = if (isEmpty) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onItemClicked(userModel: UserModel) {
        UserDetailActivity.navigateToDetailActivity(this, userModel)
    }

    override fun onEditClicked(userModel: UserModel) {
        UserDetailActivity.navigateToDetailActivity(this, userModel)
    }

    override fun onRemoveClicked(userModel: UserModel) {
        viewModel.removeUser(userModel)
    }
}
