package com.example.mytestnav.mybook.paid.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentPaidDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PaidDetails : Fragment() {
    lateinit var binding: FragmentPaidDetailsBinding
    private val viewModel:ViewModelRating by viewModels()
    private val args:PaidDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPaidDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            arrivalCity.text=args.cityTo
            departCity.text=args.cityFrom
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

        binding.rate.setOnClickListener {
            val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
            val token= sharedPreferenToken.getString("Token","")!!
          val view=  View.inflate(requireContext(), R.layout.dialog_view,null)
            val builder= MaterialAlertDialogBuilder(requireContext())
            builder.setView(view)
            val dialog=builder.create()
            dialog.setCancelable(false)
            dialog.show()
            val rat= view.findViewById<RatingBar>(R.id.ratingBar)
            val button=view.findViewById<Button>(R.id.rating_confirm)
            rat.rating=0f
            rat.stepSize=1f

            rat.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->

                button.setOnClickListener {

                    viewModel.rating(token,args.tripId,rating.toInt())
                    viewModel.rateTrip.observe(viewLifecycleOwner, Observer { result->
                        kotlin.runCatching {
                        if(result!=null ){
                            if(result.success==true){
                                Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                findNavController().popBackStack()
                            }else{
                                Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                        }
                        }
                    })

                }
            }

        }
    }

}