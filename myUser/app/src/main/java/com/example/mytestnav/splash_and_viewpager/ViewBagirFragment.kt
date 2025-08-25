package com.example.mytestnav.splash_and_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytestnav.databinding.FragmentViewBagirBinding

class ViewBagirFragment : Fragment() {

    lateinit var binding: FragmentViewBagirBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= FragmentViewBagirBinding.inflate(inflater,container,false)
      val fragmentlist= arrayListOf<Fragment>(
          FirstScreen(), SecondScreen(), TherdScreen()
      )
        val adabter=viewPagerAdabter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewpPager.adapter=adabter
        return binding.root
    }
}