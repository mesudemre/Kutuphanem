package com.mesutemre.kutuphanem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.AccountCredentials
import com.mesutemre.kutuphanem.util.*
import com.mesutemre.kutuphanem.viewmodels.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel:LoginActivityViewModel by viewModels();
    private lateinit var kullaniciAdi:String;

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cirLoginButton.setOnClickListener {
            kullaniciAdi = editTextKullaniciAdi.text.toString().trim();
            val sifre:String = editTextSifre.text.toString().trim();

            if(TextUtils.isEmpty(kullaniciAdi)){
                textInputKullaniciAdi.error = resources.getString(R.string.bosKullaniciAdiHata);
                return@setOnClickListener;
            }

            if(TextUtils.isEmpty(sifre)) {
                textInputSifre.error= resources.getString(R.string.bosSifreHata);
                return@setOnClickListener;
            }
            editTextKullaniciAdi.hideKeyboard(editTextKullaniciAdi);
            val accountCredentials = AccountCredentials(kullaniciAdi,sifre);
            girisProgressBar.showComponent();
            viewModel.doLogin(accountCredentials);
            observeLiveData();
        }

        textInputKullaniciAdi.editText!!.addTextChangedListener(TextInputErrorClearListener(textInputKullaniciAdi));
        textInputSifre.editText!!.addTextChangedListener(TextInputErrorClearListener(textInputSifre));
    }

    private fun observeLiveData(){
        viewModel.token.observe(this, Observer {token->
            token?.let {
                customSharedPreferences.putStringToSharedPreferences(APP_TOKEN_KEY,token);
                customSharedPreferences.putStringToSharedPreferences(KULLANICI_ADI_KEY,kullaniciAdi);
                val intent = Intent(this@LoginActivity,MainActivity::class.java);
                startActivity(intent);
            }
        });

        viewModel.isLoading.observe(this, Observer {isLoading->
            isLoading?.let {
                cirLoginButton.isEnabled = !it;
                errorTextView.hideComponent();
                if(it){
                    girisProgressBar.showComponent();
                }else{
                    girisProgressBar.hideComponent();
                }
            }
        });

        viewModel.loginError.observe(this,Observer{loginError->
            loginError?.let {
                girisProgressBar.hideComponent();
                if(it){
                    errorTextView.showComponent();
                }else{
                    errorTextView.hideComponent();
                }
            }
        });
    }
}