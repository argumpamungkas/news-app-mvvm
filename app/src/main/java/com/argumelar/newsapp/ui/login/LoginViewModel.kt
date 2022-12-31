package com.argumelar.newsapp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.network.model.*
import kotlinx.coroutines.launch
import org.koin.dsl.module

var moduleLoginViewModel = module {
    factory { LoginViewModel(get(),get()) }
}

class LoginViewModel(val repository: NewsRepository,private val context : Context) : ViewModel() {


    private var sharedPref = PreferencesLogin(context)

    val isLoginValue by lazy { MutableLiveData<Boolean>(false) }
    val loginResponse by lazy { MutableLiveData<LoginResponse>() }
    val message by lazy { MutableLiveData<String>() }

    init {
        isLogin()
    }


    fun fetchLogin(username: String, password: String){
        viewModelScope.launch {
            val loginReq = LoginUser(username, password)
            try {
                val response = repository.fetchLogin(loginReq)
                loginResponse.value = response
                sharedPref.putToken(Constant.TOKEN, response.token!!)
                sharedPref.putLogin(Constant.IS_LOGIN, true)
            } catch (e:Exception){
                e.toString()
                message.value = "Username dan Password anda Salah"
            }
        }
    }


    private fun isLogin() {
        isLoginValue.value = sharedPref.getLogin(Constant.IS_LOGIN)
    }

}