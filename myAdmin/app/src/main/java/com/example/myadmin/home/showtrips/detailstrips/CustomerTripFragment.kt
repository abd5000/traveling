package com.example.myadmin.home.showtrips.detailstrips

import android.content.Context
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myadmin.databinding.FragmentDetailsTripBinding
import com.example.myadmin.home.showtrips.MyPagerAdapter
import com.example.myadmin.home.showtrips.availabletrips.Data

import com.example.myadmin.home.showtrips.detailstrips.customernotpid.NotPaidFragment
import com.example.myadmin.home.showtrips.detailstrips.customerpaid.PaidFragment
import com.google.android.material.tabs.TabLayoutMediator

class CustomerTripFragment : Fragment() {
    lateinit var binding:FragmentDetailsTripBinding
    val tabTitle= listOf("NotPaid","Paid")
    private val args: CustomerTripFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailsTripBinding.inflate(inflater,container,false)
        val sharedPreferentrip=requireContext().getSharedPreferences("onTrip", Context.MODE_PRIVATE)
      sharedPreferentrip.edit().putInt("tripId", args.trip.id!!).apply()
        binding.apply {
            timeFrom.text=args.trip.tripTime
            timeTo.text=args.trip.arrivalTime
            cityFrom.text=args.trip.starting
            cityTo.text=args.trip.destination
            date.text=args.trip.tripDate
            price.text=" ${args.trip.price}"
            bus.text=" ${args.trip.numberbus}"
            time.text=args.trip.destination
            seatsReserved.text=" ${args.trip.numberdisksisFalse!!.size}"
            typeTripe.text=args.trip.typebus
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        val pagerAdapter=
            MyPagerAdapter(listOf( NotPaidFragment(), PaidFragment()), requireActivity().supportFragmentManager, lifecycle)
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
        binding.detailsBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.tripCard.visibility=View.VISIBLE
            }else{
                binding.tripCard.visibility=View.GONE
            }
        }
    }




}