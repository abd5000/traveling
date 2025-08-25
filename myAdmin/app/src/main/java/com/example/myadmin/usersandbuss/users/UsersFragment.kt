package com.example.myadmin.usersandbuss.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myadmin.databinding.FragmentUsersBinding
import com.example.myadmin.home.showtrips.MyPagerAdapter
import com.example.myadmin.usersandbuss.users.banned.UserBannedFragment
import com.example.myadmin.usersandbuss.users.toReview.UserToReviewFragment
import com.google.android.material.tabs.TabLayoutMediator

class UsersFragment : Fragment() {
    lateinit var binding:FragmentUsersBinding
    val tabTitle= listOf("To Review","Banned")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentUsersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        val pagerAdapter=
            MyPagerAdapter(listOf(UserToReviewFragment(), UserBannedFragment()), requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter=pagerAdapter
        pagerAdapter.notifyDataSetChanged()

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            tab.text=tabTitle[position]
        }.attach()
    }

}