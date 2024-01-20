package com.wahyuhw.authify.presentation.activity

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import com.wahyuhw.authify.base.BaseActivity
import com.wahyuhw.authify.data.user.source.local.AccountManager
import com.wahyuhw.authify.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
	private val accountManager: AccountManager by inject()
	
	override fun getViewBinding(): ActivitySplashBinding {
		return ActivitySplashBinding.inflate(layoutInflater)
	}
	
	override fun setupIntent() {
	
	}
	
	override fun setupUI() {
		lifecycleScope.launch {
			delay(1000)
			checkSession()
		}
	}
	
	private fun checkSession() {
		val token = accountManager.getAccessToken()
		if (token.isNotEmpty())
			MainActivity.start(this)
		else LoginActivity.start(this)
		finish()
	}
	
	override fun setupAction() {
	
	}
	
	override fun setupProcess() {
	
	}
	
	override fun setupObserver() {
	
	}
}