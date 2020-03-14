package com.loyer.socket_example.ui.activity.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyer.socket_example.R
import com.loyer.socket_example.base.BaseFragment
import com.loyer.socket_example.data_manager.network.socket.ConnectedState
import com.loyer.socket_example.data_manager.network.socket.Connecting
import com.loyer.socket_example.data_manager.network.socket.ConnectionErrorState
import com.loyer.socket_example.data_manager.network.socket.DisconnectedState
import com.loyer.socket_example.ui.activity.data.MainViewModel
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }
    override fun layoutId(): Int {
        return R.layout.fragment_home
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveData()
        viewModel.onConnectSocket()
        viewModel.onFetchMockList(true)
    }

    private fun onObserveData() {
        viewModel.mockList.observe(viewLifecycleOwner, Observer {
            Timber.d("$it")
        })

        viewModel.socketState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Connecting -> Timber.d("socketState connection")
                is DisconnectedState -> Timber.d("socketState disconnected code: ${it.code} message: ${it.message}")
                is ConnectedState -> Timber.d("socketState connected")
                is ConnectionErrorState -> Timber.d("socketState error message: ${it.message}")
            }
        })

        viewModel.socketResponse.observe(viewLifecycleOwner, Observer {
            Timber.d("socketResponse $it")
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDisconnectSocket()
    }
}