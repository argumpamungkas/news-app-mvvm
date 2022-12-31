package com.argumelar.newsapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.argumelar.newsapp.databinding.ActivityLoginBinding
import com.argumelar.newsapp.ui.news.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

var moduleLoginActivity = module {
    factory { LoginActivity() }
}

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CHECK LOGIN
        viewModel.isLoginValue.observe(this, Observer {
            if (it == true){
                moveHome()
            }
        })

        binding.btnLogin!!.setOnClickListener {
            if (validate()){
                viewModel.fetchLogin(binding.etUsername!!.text.toString(), binding.etPassword!!.text.toString())
                viewModel.loginResponse.observe(this, Observer { resp ->
                    Log.i("TOKEN", resp.token.toString())
                    moveHome()
                })
            }
        }

        viewModel.message.observe(this, Observer {
            binding.tvWarning!!.visibility = View.VISIBLE
            binding.tvWarning!!.text = it.toString()
            binding.etUsername!!.setText("")
            binding.etPassword!!.setText("")
        })
    }

    private fun validate() : Boolean{
        val username = binding.etUsername!!.text.toString()
        val password = binding.etPassword!!.text.toString()

        if (username.isEmpty() && password.isEmpty()){
            binding.tvWarning!!.visibility = View.VISIBLE
            binding.tvWarning!!.text = "Masukkan Username dan Password anda"
            return false
        } else if (username.isEmpty() || username==""){
            binding.tvWarning!!.visibility = View.VISIBLE
            binding.tvWarning!!.text = "Masukkan Username anda"
            return false
        } else if (password.isEmpty() || password==""){
            binding.tvWarning!!.visibility = View.VISIBLE
            binding.tvWarning!!.text = "Masukkan Password anda"
            return false
        } else {
            return true
        }
    }

    private fun moveHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}



