package eth.facundoaramayo.simpleprofileview.ui.userslist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import eth.facundoaramayo.simpleprofileview.databinding.ActivityUsersListBinding
import eth.facundoaramayo.simpleprofileview.ui.userdetail.UserDetailActivity

@AndroidEntryPoint
class UsersListActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityUsersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUsersListBinding.inflate(layoutInflater)

        setListeners()

        setContentView(viewBinding.root)
    }

    private fun setListeners() {
        viewBinding.btnAddUser.setOnClickListener {
            startActivity(Intent(this, UserDetailActivity::class.java))
        }
    }
}