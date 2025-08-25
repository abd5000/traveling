package com.example.mytestnav.mybook.notpaid.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentNotPaidDetailsBinding
import com.example.mytestnav.mybook.paid.MyPaidAdabter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotPaidDetails : Fragment() {
    var skip=1
    lateinit var binding:FragmentNotPaidDetailsBinding
    lateinit var  adapter: MyPaidAdabter
     var delete:Boolean=false
    private val viewModel:ViewModelCancelReserve by viewModels()
   val args:NotPaidDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNotPaidDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fromCity.text=args.cityFrom
            toCity.text=args.cityTo
            date.text=args.date
            time.text=args.timeTrip
            companyName.text=args.company
            busNumber.text=args.busNumper.toString()
            seatPrice.text="SYP ${args.price}"
            type.text=args.typeTrip
        }
        var listS=args.numberSeats
        listS.sort()
        var seats:String=""
        listS.forEach { item->
            seats+=",$item"
        }
        seats=seats.substring(1)
        binding.reservedSeats.text=seats

        val total=args.price*(listS.size)
        binding.totalPrice.text="SYP $total"
        val sharedPrefId =requireActivity().getSharedPreferences("onName", Context.MODE_PRIVATE)
        val name=sharedPrefId.getString("FullName","")
        binding.fullName.text=name


        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.cancelReserve.setOnClickListener { v->

            val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
            val token= sharedPreferenToken.getString("Token","")!!.trim()
           showDialog(token)

        }

    }


    private fun showDialog(token:String) {
        val view=  View.inflate(requireContext(), R.layout.dialog_reserv,null)
        val builder= MaterialAlertDialogBuilder(requireContext())
        builder.setView(view)
        val dialog=builder.create()
        dialog.setCancelable(false)
        dialog.show()
        val cancel=view.findViewById<Button>(R.id.cancel_reserve)
        val okButton=view.findViewById<Button>(R.id.confirm_reserve)
        val titel=view.findViewById<TextView>(R.id.titel)
        val description=view.findViewById<TextView>(R.id.text_descrptioj)
        titel.visibility=View.GONE
        description.text="Do you want to cancel your reservation?"
        description.textSize= 20F
        okButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
        cancel.setOnClickListener {
            dialog.dismiss()

        }

        okButton.setOnClickListener {
            viewModel.cancelReserve(token,args.tripId)
        viewModel.trip.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if(result!=null){
                    if(result.success==true){
                        dialog.dismiss()
                        Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
                        delete=true

                        val navController=findNavController()
                        val action= NotPaidDetailsDirections.actionNotPaidDetailsToMyBook2()
                        navController.navigate(action
                        )
                    }else if (result.success==false) {
                        Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        }


    }



}

//val builder = MaterialAlertDialogBuilder(requireContext())
//builder.apply {
//    setCancelable(false)
//    setTitle("Do you want to cancel your reservation?")
//    setNeutralButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
//        dialogInterface.dismiss()
//
//
//    })
//    setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
//        viewModel.cancelReserve(token,args.tripId)
//        viewModel.trip.observe(viewLifecycleOwner, Observer { result->
//            kotlin.runCatching {
//                if(result!=null){
//                    if(result.success==true){
//                        Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
//                        val navController=findNavController()
//                        val action= NotPaidDetailsDirections.actionNotPaidDetailsToMyBook2()
//                        navController.navigate(action)
//
//
//                    }else if (result.success==false) {
//                        Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        })
//
//
//    })
//
//
//}
//val dialog=builder.create()
//dialog.show()