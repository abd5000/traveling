package com.example.myadmin.home.showtrips

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.myadmin.databinding.FragmentTheTripsBinding
import com.example.myadmin.home.showtrips.availabletrips.AvailableTripsFragment
import com.example.myadmin.home.showtrips.availabletrips.ViewModelAvailable
import com.example.myadmin.home.showtrips.bookable.BookableTrips
import com.example.myadmin.home.showtrips.finishedtrips.FinishedTripsFragment
import com.example.myadmin.home.showtrips.finishedtrips.ViewModelFinished
import com.google.android.material.tabs.TabLayoutMediator

class TheTrips : Fragment() {
    lateinit var binding:FragmentTheTripsBinding
    val tabTitle= listOf("Available","Bookable","Finished")
    private val viewModelAvailable: ViewModelAvailable by viewModels()
    private val viewModelFinished:ViewModelFinished by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTheTripsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        val pagerAdapter=MyPagerAdapter(listOf( AvailableTripsFragment(),BookableTrips(),FinishedTripsFragment() ), requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter=pagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
                val token= sharedPreferenToken.getString("Token","")!!.trim()
                if (position==0){

//                    viewModelAvailable.getAllTripsAvailable(token)
                }else{
                //    viewModelFinished.notPaid(token)
                }
            }
        })
        pagerAdapter.notifyDataSetChanged()

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            tab.text=tabTitle[position]
        }.attach()

    }
}