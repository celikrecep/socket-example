package com.loyer.socket_example

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyer.socket_example.base.BaseFragment
import com.loyer.socket_example.network.Api
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
        viewModel.onFetchMockList(true)
    }

    private fun onObserveData() {
        viewModel.mockList.observe(viewLifecycleOwner, Observer {
            Timber.d("$it")
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}