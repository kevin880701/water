package com.lhr.water.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lhr.water.R
import com.lhr.water.databinding.ActivityLoginBinding
import com.lhr.water.ui.main.MainActivity


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(this.application)
        )[LoginViewModel::class.java]
        binding.viewModel = viewModel

        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonLogin -> {
                //用於檢查是否有資料
//                val found = hospitalEntityList.find { it.hospitalName == currentSpinnerText && it.number == binding.editNumber.text.toString() }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}