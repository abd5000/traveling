package com.example.mytestnav.splash_and_viewpager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentTherdScreenBinding


class TherdScreen : Fragment() {
    lateinit var binding: FragmentTherdScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTherdScreenBinding.inflate(inflater,container,false)


        binding.finsh.setOnClickListener {
          findNavController().navigate(R.id.action_viewBagirFragment_to_welcoamFragment)
            onBoardingFinshed()
        }
        return binding.root

    }
    private fun onBoardingFinshed(){
        val sharedPref =requireActivity().getSharedPreferences("onBording",Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

}