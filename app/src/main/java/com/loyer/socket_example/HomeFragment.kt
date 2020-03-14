package com.loyer.socket_example

import android.os.Bundle
import android.view.View
import com.loyer.socket_example.base.BaseFragment


class HomeFragment : BaseFragment() {
    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}