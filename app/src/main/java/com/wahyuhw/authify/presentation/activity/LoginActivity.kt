package com.wahyuhw.authify.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.wahyuhw.authify.base.BaseActivity
import com.wahyuhw.authify.data.user.source.local.AccountManager
import com.wahyuhw.authify.databinding.ActivityLoginBinding
import com.wahyuhw.authify.domain.user.model.UserLoginParam
import com.wahyuhw.authify.utils.emptyString
import com.wahyuhw.authify.utils.isValidEmail
import com.wahyuhw.authify.utils.isValidPassword
import com.wahyuhw.authify.utils.observe
import com.wahyuhw.authify.utils.onTextChange
import com.wahyuhw.authify.utils.orEmpty
import com.wahyuhw.authify.utils.showToast
import com.wahyuhw.authify.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
	companion object {
		fun start(context: Context) {
			val intent = Intent(context, LoginActivity::class.java)
			context.startActivity(intent)
		}
	}
	
	private val userViewModel: UserViewModel by inject()
	private var email: String = emptyString()
	private var password: String = emptyString()
	
	private val accountManager: AccountManager by inject()
	
	override fun getViewBinding(): ActivityLoginBinding {
		return ActivityLoginBinding.inflate(layoutInflater)
	}
	
	override fun setupIntent() {
	
	}
	
	override fun setupUI() {
		with(binding) {
		
		}
	}
	
	override fun setupAction() {
		with(binding) {
			edtEmail.onTextChange {
				email = it
				checkValidation()
			}
			edtPassword.onTextChange {
				password = it
				checkValidation()
			}
			btnLogin.setOnClickListener {
				userViewModel.loginUser(UserLoginParam(email = email, password = password))
			}
		}
	}
	
	override fun setupProcess() {
	
	}
	
	override fun setupObserver() {
		userViewModel.loginResult.observe(this,
			onLoading = {
				showLoading(message = "Sedang mencoba login...")
			},
			onError = {
				dismissLoading()
				showErrorDialog(it)
			},
			onSuccess = {
				dismissLoading()
				val token = it?.token.orEmpty()
				if (token.isNotEmpty()) {
					doLogin(token)
				}
			}
		)
	}
	
	private fun doLogin(token: String) {
		lifecycleScope.launch {
			showToast("Berhasil login!")
			accountManager.storeAccessToken(token)
		}.invokeOnCompletion {
			MainActivity.start(this)
			finish()
		}
	}
	
	private fun checkValidation() {
		val isEmailValid = binding.edtEmail.text.toString().isValidEmail()
		val isPasswordValid = binding.edtPassword.text.toString().isValidPassword()
		val isValidAuth = isEmailValid && isPasswordValid
		binding.btnLogin.isEnabled = isValidAuth
	}
}