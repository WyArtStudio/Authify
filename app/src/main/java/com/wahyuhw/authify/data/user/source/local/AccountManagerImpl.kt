package com.wahyuhw.authify.data.user.source.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.wahyuhw.authify.utils.ACCESS_TOKEN_PREFS
import com.wahyuhw.authify.utils.emptyString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManagerImpl @Inject constructor(context: Context) : AccountManager {
	
	private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
	private val editor = preferences.edit()
	
	override fun storeAccessToken(accessToken: String) {
		editor.putString(ACCESS_TOKEN_PREFS, accessToken).commit()
	}
	
	override fun getAccessToken(): String {
		return preferences.getString(ACCESS_TOKEN_PREFS, emptyString()).orEmpty()
	}
}