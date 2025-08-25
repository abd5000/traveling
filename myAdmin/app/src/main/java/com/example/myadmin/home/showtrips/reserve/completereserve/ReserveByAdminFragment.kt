package com.example.myadmin.home.showtrips.reserve.completereserve

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentReserveByAdminBinding


class ReserveByAdminFragment : Fragment() {
    lateinit var binding: FragmentReserveByAdminBinding
    private val viewModel:ViewModelReserveByAdmin by viewModels()
    lateinit var token:String
    private val args:ReserveByAdminFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentReserveByAdminBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
         token= sharedPreferenToken.getString("Token","")!!.trim()

        binding.apply {
            arrivalCity.text=args.trip.destination
            departCity.text=args.trip.starting
            date.text=args.trip.tripDate
            time.text=args.trip.tripTime
            companyName.text=args.trip.company
            busNumber.text=args.trip.numberbus.toString()
            seatPrice.text="SYP ${args.trip.price}"

        }
        var listS=args.numberSeats
        listS.sort()
        var seats:String=""
        listS.forEach { item->
            seats+=",$item"
        }
        seats=seats.substring(1)
        binding.reservedSeats.text=seats

        val total= args.trip.price!! *(listS.size)
        binding.totalPrice.text="SYP $total"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.reserve.isEnabled=false
        binding.reserve.background = (ContextCompat.getDrawable(requireContext(), R.drawable.stat_date))

        binding.fullName.addTextChangedListener {
            if (binding.fullName.text.isEmpty()){
                binding.reserve.isEnabled=false
                binding.reserve.background = (ContextCompat.getDrawable(requireContext(), R.drawable.stat_date))
            }else{
                binding.reserve.isEnabled=true
                binding.reserve.background = (ContextCompat.getDrawable(requireContext(), R.drawable.stat_shap_login))
            }
        }
        binding.reserve.setOnClickListener { v->

//            Log.i("inforeserve", "$token,${args.trip.id},${binding.fullName.text},${args.numberSeats}")
            viewModel.reserveSeats(token, args.trip.id!!,binding.fullName.text.toString().trim(),args.numberSeats.toList())


            viewModel.reserve.observe(viewLifecycleOwner) { result ->
                kotlin.runCatching {
                    if (result != null && result.success == true) {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        val action =
                            ReserveByAdminFragmentDirections.actionReserveByAdminFragmentToTheTrips()
                        findNavController().navigate(action)

                    } else {

                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


}
