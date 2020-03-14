package com.loyer.socket_example.ui.activity.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loyer.socket_example.R
import com.loyer.socket_example.base.BaseFragment
import com.loyer.socket_example.data_manager.network.socket.ConnectedState
import com.loyer.socket_example.data_manager.network.socket.Connecting
import com.loyer.socket_example.data_manager.network.socket.ConnectionErrorState
import com.loyer.socket_example.data_manager.network.socket.DisconnectedState
import com.loyer.socket_example.data_manager.network.util.Status
import com.loyer.socket_example.ui.activity.data.MainViewModel
import com.loyer.socket_example.ui.activity.fragments.adapter.MockRvAdapter
import com.loyer.socket_example.util.hideKeyboardFrom
import com.loyer.socket_example.vo.Mock
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }
    private val adapter by lazy {
        MockRvAdapter(mutableListOf())
    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMockList()
        onObserveData()
        initClickListeners()
        viewModel.onConnectSocket()
        viewModel.onFetchMockList(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDisconnectSocket()
    }

    private fun onObserveData() {
        viewModel.mockList.observe(viewLifecycleOwner, Observer {
            progress.isVisible = it.status == Status.Loading
            if (it.status == Status.Success && it.data != null) {
                adapter.submitList(it.data)
            }
        })

        viewModel.socketState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Connecting -> Timber.d("socketState connection")
                is DisconnectedState -> Timber.d("socketState disconnected code: ${it.code} message: ${it.message}")
                is ConnectedState -> Timber.d("socketState connected")
                is ConnectionErrorState -> Timber.d("socketState error message: ${it.message}")
            }
        })

        viewModel.socketResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.Success && it.data != null) {
                updateValue(it.data)
            } else if (it.status == Status.Error) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initMockList() {
        mockList.adapter = adapter
        mockList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun initClickListeners() {
        send.setOnClickListener {
            if (value.text.toString().isNotEmpty()) {
                viewModel.sendData(value.text.toString())
                hideKeyboardFrom(context, value.windowToken)
                value.setText("")
            }
        }
    }

    private fun updateValue(modal: Mock) {
        viewModel.mockList.value?.data?.find { data ->
            data.id == modal.id
        }?.apply {
            id = modal.id
            name = modal.name
            adapter.updateItem(modal.id)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}