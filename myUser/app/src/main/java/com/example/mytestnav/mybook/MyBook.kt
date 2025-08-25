package com.example.mytestnav.mybook

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.mytestnav.databinding.FragmentMyBookBinding
import com.example.mytestnav.mybook.notpaid.NotPaid
import com.example.mytestnav.mybook.notpaid.ViewModelNotPaid
import com.example.mytestnav.mybook.paid.Paid
import com.example.mytestnav.mybook.paid.ViewModelPaid
import com.google.android.material.tabs.TabLayoutMediator


class MyBook : Fragment() {
    lateinit var binding: FragmentMyBookBinding
    lateinit var  viewPager:ViewPager2
    private val viewModelPaid: ViewModelPaid by viewModels()
    private val viewModelNotPaid:ViewModelNotPaid by viewModels()
    val tabTitle= listOf("Not paid","Paid")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentMyBookBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(v).popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        val pagerAdapter=MyPagerAdapter(listOf( NotPaid(),Paid() ), requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter=pagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
                val token= sharedPreferenToken.getString("Token","")!!.trim()
                if (position==0){

                viewModelPaid.paid(token)
                }else{
                    viewModelNotPaid.notPaid(token)
                }
            }
        })
        pagerAdapter.notifyDataSetChanged()

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            tab.text=tabTitle[position]
        }.attach()

    }



}