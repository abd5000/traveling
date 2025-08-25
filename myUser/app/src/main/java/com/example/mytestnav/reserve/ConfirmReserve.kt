package com.example.mytestnav.reserve

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentConfirmReserveBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmReserve : Fragment() {
private val args:ConfirmReserveArgs by navArgs()
    private val viewModel:ViewModelReserve by viewModels()
    lateinit var binding:FragmentConfirmReserveBinding
    lateinit var token:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentConfirmReserveBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          val sharedPrefId =requireActivity().getSharedPreferences("onName", Context.MODE_PRIVATE)
        val name=sharedPrefId.getString("FullName","")
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken",Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!
       binding.fullName.text=name
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.apply {
            arrivalCity.text=args.cityTo
            departCity.text=args.cityFrom
           date.text=args.date
            time.text=args.time
            companyName.text=args.company
            busNumber.text=args.busNumber.toString()
            seatPrice.text="SYP ${args.seatPrice}"

        }
        var listS=args.reservedSeats
        listS.sort()
        var seats:String=""
        listS.forEach { item->
            seats+=",$item"
        }
        seats=seats.substring(1)
        binding.reservedSeats.text=seats

        val total=args.seatPrice*(listS.size)
        binding.totalPrice.text="SYP $total"


        binding.reserve.setOnClickListener {
            showDialog()

        }
    }


    private fun showDialog() {
        val view=  View.inflate(requireContext(), R.layout.dialog_reserv,null)
        val builder= MaterialAlertDialogBuilder(requireContext())
        builder.setView(view)
        val dialog=builder.create()
        dialog.setCancelable(false)
        dialog.show()
        val cancel=view.findViewById<Button>(R.id.cancel_reserve)
        val confirem=view.findViewById<Button>(R.id.confirm_reserve)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        confirem.setOnClickListener {
            val seats=args.reservedSeats.toList()
            viewModel.reserve(token,args.tripId,seats)
                viewModel.trip.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if(result!=null){
                            if(result.success==true){
                                Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                val navController=findNavController()
                                val action= ConfirmReserveDirections.actionConfirmReserveToTheHomeFragment()
                                navController.navigate(action)


                            }else if (result.success==false) {
                                Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
        }




    }
}
// val builder = MaterialAlertDialogBuilder(requireContext())
//        builder.apply {
//            setCancelable(false)
//            setTitle("Chose a payment Type")
//            setMessage("Electronic payment is currently not available." +
//                    "Please be at the reservation office half an hour before the flight departure time to confirm your reservation." +
//                    "Confirm,click Ok")
//
//
//            setNeutralButton("Cansel", DialogInterface.OnClickListener { dialogInterface, i ->
//                dialogInterface.dismiss()
//
//
//            })
//            setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
//                val seats=args.reservedSeats.toList()
//
//               viewModel.reserve(token,args.tripId,seats)
//                viewModel.trip.observe(viewLifecycleOwner, Observer { result->
//                    kotlin.runCatching {
//                        if(result!=null){
//                            if(result.success==true){
//                                Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
//                                val navController=findNavController()
//                                val action= ConfirmReserveDirections.actionConfirmReserveToTheHomeFragment()
//                                navController.navigate(action)
//
//
//                            }else if (result.success==false) {
//                                Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                })
//            })
//
//        }
//        val dialog=builder.create()
//        dialog.show()