package com.example.myadmin.usersandbuss

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentUsersAndBussBinding

class UsersAndBuss : Fragment() {
    lateinit var binding:FragmentUsersAndBussBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUsersAndBussBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref =requireActivity().getSharedPreferences("onName", Context.MODE_PRIVATE)
        binding.company.text=sharedPref.getString("FullName","")
        binding.users.setOnClickListener { v->
            val action=UsersAndBussDirections.actionUsersAndBussToUsersFragment()
            Navigation.findNavController(v).navigate(action)
        }
        binding.addBus.setOnClickListener { v->
            val action=UsersAndBussDirections.actionUsersAndBussToAddBusFragment()
            Navigation.findNavController(v).navigate(action)
        }
        binding.deleteBus.setOnClickListener { v->
            val action=UsersAndBussDirections.actionUsersAndBussToDeleteBusFragment()
            Navigation.findNavController(v).navigate(action)
        }
        binding.addDriver.setOnClickListener { v->
            val action=UsersAndBussDirections.actionUsersAndBussToAddDriverFragment()
            Navigation.findNavController(v).navigate(action)
        }
        binding.deleteDriver.setOnClickListener { v->
            val action=UsersAndBussDirections.actionUsersAndBussToDeleteDriverFragment2()
            Navigation.findNavController(v).navigate(action)
        }
    }

}