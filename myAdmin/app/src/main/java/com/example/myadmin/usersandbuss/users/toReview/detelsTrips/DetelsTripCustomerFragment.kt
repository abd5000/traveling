package com.example.myadmin.usersandbuss.users.toReview.detelsTrips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myadmin.databinding.FragmentDetelsTripCustomerBinding

class DetelsTripCustomerFragment : Fragment() {
    lateinit var binding:FragmentDetelsTripCustomerBinding
    private val args:DetelsTripCustomerFragmentArgs by navArgs()
    private lateinit var adapter:AdapteDetalis
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetelsTripCustomerBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text=args.customer.fullname
        adapter=AdapteDetalis(args.customer.trips)
        binding.recyclerview.adapter=adapter
        adapter.notifyDataSetChanged()
        //
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
    }


}