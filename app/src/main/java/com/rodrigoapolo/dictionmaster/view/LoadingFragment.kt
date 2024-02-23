package com.rodrigoapolo.dictionmaster.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rodrigoapolo.dictionmaster.R

class LoadingFragment : Fragment(R.layout.fragment_loading) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_loadingFragment_to_homeFragment)
        }, 2000)
    }
}