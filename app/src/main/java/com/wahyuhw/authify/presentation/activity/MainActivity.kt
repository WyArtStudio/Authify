package com.wahyuhw.authify.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyuhw.authify.R
import com.wahyuhw.authify.base.BaseActivity
import com.wahyuhw.authify.data.user.source.local.AccountManager
import com.wahyuhw.authify.databinding.ActivityMainBinding
import com.wahyuhw.authify.presentation.adapter.UserAdapter
import com.wahyuhw.authify.utils.emptyString
import com.wahyuhw.authify.utils.observe
import com.wahyuhw.authify.utils.setSpannableText
import com.wahyuhw.authify.utils.showAlertDialog
import com.wahyuhw.authify.utils.showContent
import com.wahyuhw.authify.utils.showEmptyList
import com.wahyuhw.authify.utils.showErrorState
import com.wahyuhw.authify.utils.showLoading
import com.wahyuhw.authify.utils.showToast
import com.wahyuhw.authify.utils.ui.PaginateLoadingItem
import com.wahyuhw.authify.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.alexbykov.nopaginate.paginate.NoPaginate
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {
	companion object {
		fun start(context: Context) {
			val intent = Intent(context, MainActivity::class.java)
			context.startActivity(intent)
		}
	}
	
	private val userViewModel: UserViewModel by inject()
	private var page = 1
	private var pageSize = 10
	private val userAdapter by lazy { UserAdapter(this) }
	private var paginate: NoPaginate? = null
	
	private val accountManager: AccountManager by inject()
	
	override fun getViewBinding(): ActivityMainBinding {
		return ActivityMainBinding.inflate(layoutInflater)
	}
	
	override fun setupIntent() {
	
	}
	
	override fun setupUI() {
		with(binding) {
			rvUser.apply {
				adapter = userAdapter
				layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
				paginate = NoPaginate.with(this)
					.setLoadingTriggerThreshold(8)
					.setOnLoadMoreListener {
						loadMore()
					}
					.setCustomLoadingItem(PaginateLoadingItem())
					.build()
			}
		}
	}
	
	private fun loadMore() {
		page+=10
		userViewModel.getListUser(page, pageSize)
	}
	
	override fun setupAction() {
		binding.btnLogout.setOnClickListener {
			showAlertDialog(
				title = getString(R.string.title_logout_confirmation),
				message = getString(R.string.message_logout_confirmation),
				onConfirmClickAction = { userViewModel.logoutUser() }
			)
		}
	}
	
	override fun setupProcess() {
		userViewModel.getUserById()
		userViewModel.getListUser(page, pageSize)
	}
	
	override fun setupObserver() {
		userViewModel.getUserByIdResult.observe(this,
			onLoading = {},
			onError = {},
			onSuccess = {
				val name = getString(R.string.label_first_last_name_format, it?.firstName, it?.lastName)
				binding.tvName.setSpannableText(
					context = this,
					normalString = getString(R.string.label_hello),
					highlightString = name
				)
			}
		)
		
		userViewModel.getListUserResult.observe(this,
			onLoading = {
				if (page == 1) showLoadingState() else setPaginateLoading(true)
			},
			onError = {
				binding.msvUser.showErrorState(
					title = getString(R.string.message_error_load),
					message = it,
					imgResourceId = R.drawable.img_error_load
				) {
					userViewModel.getListUser(page, pageSize)
				}
			},
			onSuccess = {
				setPaginateLoading(false)
				if (page == it?.totalPages) finishLoadMore()
				userAdapter.addOrUpdate(it?.data.orEmpty())
				if (userAdapter.currentList.isEmpty()) showEmptyState()
				else binding.msvUser.showContent()
			}
		)
		
		userViewModel.logoutResult.observe(this,
			onLoading = {
				showLoading("Sedang logout...")
			},
			onError = {
				showErrorDialog("Error: $it")
			},
			onSuccess = { isLogoutSuccess ->
				if (isLogoutSuccess == true) doLogout()
			}
		)
	}
	
	private fun doLogout() {
		lifecycleScope.launch {
			showToast("Berhasil logout!")
			accountManager.storeAccessToken(emptyString())
		}.invokeOnCompletion {
			LoginActivity.start(this)
			finish()
		}
	}
	
	private fun showEmptyState() {
		binding.msvUser.showEmptyList(
			title = getString(R.string.title_empty_data),
			message = getString(R.string.message_empty_data),
			imageResId = R.drawable.img_empty_data
		)
	}
	
	private fun showLoadingState() {
		binding.msvUser.showLoading()
	}
	
	private fun setPaginateLoading(isVisible: Boolean) {
		paginate?.showLoading(isVisible)
	}
	
	private fun finishLoadMore() {
		paginate?.setNoMoreItems(true)
	}
	
	override fun onDestroy() {
		super.onDestroy()
		paginate?.unbind()
	}
}
