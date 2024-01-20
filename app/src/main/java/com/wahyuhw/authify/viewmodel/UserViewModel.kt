package com.wahyuhw.authify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wahyuhw.authify.base.BaseViewModel
import com.wahyuhw.authify.domain.user.UserUseCase
import com.wahyuhw.authify.domain.user.model.Login
import com.wahyuhw.authify.domain.user.model.User
import com.wahyuhw.authify.domain.user.model.UserLoginParam
import com.wahyuhw.authify.domain.util.BasePagination
import com.wahyuhw.authify.domain.util.Resource
import com.wahyuhw.authify.utils.PERSON_ID
import com.wahyuhw.authify.utils.collectResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserViewModel(
	private val useCase: UserUseCase,
	disposable: CompositeDisposable
): BaseViewModel(disposable) {
	
	private val _loginResult: MutableLiveData<Resource<Login>> = MutableLiveData()
	val loginResult: LiveData<Resource<Login>> get() = _loginResult
	
	private val _logoutResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
	val logoutResult: LiveData<Resource<Boolean>> get() = _logoutResult
	
	private val _getUserByIdResult: MutableLiveData<Resource<User>> = MutableLiveData()
	val getUserByIdResult: LiveData<Resource<User>> get() = _getUserByIdResult
	
	private val _getListUserResult: MutableLiveData<Resource<BasePagination<User>>> = MutableLiveData()
	val getListUserResult: LiveData<Resource<BasePagination<User>>> get() = _getListUserResult
	
	init {
		_loginResult.value = Resource.Default()
		_logoutResult.value = Resource.Default()
		_getUserByIdResult.value = Resource.Default()
		_getListUserResult.value = Resource.Default()
	}
	
	fun loginUser(param: UserLoginParam) {
		_loginResult.value = Resource.Loading()
		viewModelScope.collectResult(_loginResult) {
			useCase.login(param)
		}
	}
	
	fun logoutUser() {
		_logoutResult.value = Resource.Loading()
		viewModelScope.collectResult(_logoutResult) {
			useCase.logout()
		}
	}
	
	fun getUserById() {
		_getUserByIdResult.value = Resource.Loading()
		viewModelScope.collectResult(_getUserByIdResult) {
			useCase.getUserById(PERSON_ID)
		}
	}
	
	fun getListUser(page: Int, pageSize: Int) {
		_getListUserResult.value = Resource.Loading()
		viewModelScope.collectResult(_getListUserResult) {
			useCase.getListUser(page, pageSize)
		}
	}
}