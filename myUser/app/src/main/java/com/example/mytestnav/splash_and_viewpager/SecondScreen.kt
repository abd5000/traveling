package com.example.mytestnav.splash_and_viewpager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentSecondScreenBinding

class SecondScreen : Fragment() {
    lateinit var binding: FragmentSecondScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentSecondScreenBinding.inflate(inflater,container,false)
        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewp_pager)
        binding.next2.setOnClickListener {
            viewPager?.currentItem=2
        }
        binding.textSkip2.setOnClickListener {
            findNavController().navigate(R.id.action_viewBagirFragment_to_welcoamFragment)
            onBoardingFinshed()
        }
        return binding.root
    }
    private fun onBoardingFinshed(){
        val sharedPref =requireActivity().getSharedPreferences("onBording", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

}