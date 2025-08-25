package com.example.mytestnav.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentWelcoamBinding

class WelcoamFragment : Fragment() {
    lateinit var binding:FragmentWelcoamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWelcoamBinding.inflate(inflater,container,false)
        binding.login1.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_welcoamFragment_to_login)
        }
        binding.signUp.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_welcoamFragment_to_signup)
        }

        return binding.root
    }

}