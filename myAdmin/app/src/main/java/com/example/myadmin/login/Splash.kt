package com.example.myadmin.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentSplashBinding

class Splash : Fragment() {
    lateinit var binding:FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({

                if(onLoginFinished())
                {
                    findNavController().navigate(R.id.action_splash_to_homeActivity)
                    requireActivity().finish()
                }else{
                    findNavController().navigate(R.id.action_splash_to_login)

                }
        },3000)
    }
    private fun onLoginFinished():Boolean{
        val sharedPref=requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Login",false)
    }


}