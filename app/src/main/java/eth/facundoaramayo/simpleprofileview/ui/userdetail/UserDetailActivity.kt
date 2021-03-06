package eth.facundoaramayo.simpleprofileview.ui.userdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import eth.facundoaramayo.simpleprofileview.R
import eth.facundoaramayo.simpleprofileview.databinding.ActivityUserDetailBinding
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import eth.facundoaramayo.simpleprofileview.utils.ImageChooser
import eth.facundoaramayo.simpleprofileview.utils.ext.setImage

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityUserDetailBinding
    private val viewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setListeners()
        setObservers()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.add_user)

        setContentView(viewBinding.root)

        intent.extras?.apply {
            (get(USER_EXTRA_KEY) as? UserModel)?.let {
                supportActionBar?.setTitle(R.string.edit_user)
                viewModel.avatarUri = it.avatar
                viewBinding.etName.setText(it.name)
                viewBinding.etDescription.setText(it.description)
                viewBinding.image.setImage(viewModel.avatarUri)
                viewModel.isEditing = true
                viewModel.currentUser = it
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setListeners() {
        viewBinding.apply {
            pickImageButton.setOnClickListener {
                startActivityForResult(ImageChooser.getImageChooser(), PICK_IMAGE_CODE)
            }
            save.setOnClickListener {
                viewModel.submitUser(etName.text.toString(), etDescription.text.toString())
            }
        }
    }

    private fun setObservers() {
        viewModel.apply {
            observeLoading().observe(this@UserDetailActivity) { isLoading ->
                viewBinding.loader.root.visibility = if (isLoading) View.VISIBLE else View.GONE
                viewBinding.pickImageButton.visibility = if (isLoading) View.GONE else View.VISIBLE
                viewBinding.save.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
            observeError().observe(this@UserDetailActivity) { isError ->
                viewBinding.errorMessage.visibility = if (isError) View.VISIBLE else View.GONE
            }
            observeIsCompleted().observe(this@UserDetailActivity) { isCompleted ->
                if (isCompleted) {
                    onBackPressed()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_CODE) {
            viewModel.avatarUri = data?.data.toString()
            viewBinding.image.setImage(data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val PICK_IMAGE_CODE = 1
        private const val USER_EXTRA_KEY = "USER_EXTRA_KEY"

        @JvmStatic
        fun navigateToDetailActivity(context: Context, user: UserModel? = null) {
            context.startActivity(Intent(context, UserDetailActivity::class.java).apply {
                putExtra(USER_EXTRA_KEY, user)
            })
        }
    }
}
