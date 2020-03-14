package com.loyer.socket_example.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyer.socket_example.R
import com.loyer.socket_example.base.BaseActivity
import com.loyer.socket_example.data_manager.shared_pref.SharedPref
import com.loyer.socket_example.ui.activity.data.MainViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var sharedPref: SharedPref
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onObserveData()
    }


    private fun updateToolbarTitle() {
        supportActionBar?.title = if (sharedPref.getToken() == null) getString(R.string.logout) else
            getString(R.string.login)
    }


    private fun onObserveData() {
        viewModel.loginState.observe(this, Observer {
            if (it) sharedPref.saveToken(getString(R.string.login))
            else sharedPref.removeToken()
            updateToolbarTitle()
        })
    }
}
