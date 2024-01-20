package com.wahyuhw.authify.data.user.source.local

interface AccountManager {
	fun storeAccessToken(accessToken: String)
	
	fun getAccessToken(): String
}