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
import com.example.mytestnav.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {
    lateinit var binding:FragmentFirstScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFirstScreenBinding.inflate(inflater,container,false)
        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewp_pager)
        binding.next.setOnClickListener {
            viewPager?.currentItem=1
        }
        binding.textSkip.setOnClickListener {
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