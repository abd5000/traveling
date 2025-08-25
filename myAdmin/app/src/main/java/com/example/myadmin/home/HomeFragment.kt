package com.example.myadmin.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        val sharedPref =requireActivity().getSharedPreferences("onName", Context.MODE_PRIVATE)
        binding.company.text=sharedPref.getString("FullName","")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.addTrip.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_addTrip2)
        }
        binding.editTrip.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_tripsEditOrRemoveFragment
            )
        }

        binding.showTrip.setOnClickListener {v->
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_theTrips)
        }
        binding.tracking.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_trackingFragment)
        }
    }

}