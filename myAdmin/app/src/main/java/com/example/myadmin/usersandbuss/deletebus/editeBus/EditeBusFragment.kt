package com.example.myadmin.usersandbuss.deletebus.editeBus

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentEditeBusBinding
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.Data
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.DriverClickLestener
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.IteamDriverAdabter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditeBusFragment : Fragment(), DriverClickLestener {
 lateinit var binding:FragmentEditeBusBinding
 private val viewModel:ViewModelEditeBus by viewModels()
    private val args:EditeBusFragmentArgs by navArgs()
    private var driverId=0
    private lateinit var bottomSheetDialogDriver: BottomSheetDialog
    private lateinit var adapterDriver: IteamDriverAdabter
    private lateinit var token:String
    var drivers= mutableListOf<com.example.myadmin.usersandbuss.addbuss.availableDrivers.Data?>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEditeBusBinding.inflate(inflater,container,false)
        val sharedPreferen =requireActivity().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferen.getString("Token","")!!.trim()

        viewModel.getAvailableDrivers(token)
        viewModel.driver.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if (result!=null && result.success==true){
                    drivers.clear()
                    drivers.addAll(result.data!!)

                    adapterDriver = IteamDriverAdabter(drivers, this)
                    bottomSheetDialogDriver = BottomSheetDialog(requireContext())
                    val view = bottomSheetDialogDriver.layoutInflater.inflate(R.layout.bottom_sheet, null)
                    val textView=view.findViewById<TextView>(R.id.text_Select)
                    textView.text="Select The Driver"
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recview)
                    recyclerView.adapter = adapterDriver
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.botoom_sheet)
                    bottomSheetDialogDriver.setContentView(view)
                }
            }
        })
        binding.busNumber.text="${args.bus.number}"
        binding.typeBus.text=args.bus.type
        binding.driver.hint=args.bus.driver
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.driverCard.setOnClickListener {
            showDriver()
        }
        binding.editeBusBtn.isEnabled=false
        binding.editeBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
        binding.driver.addTextChangedListener{
                binding.editeBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.editeBusBtn.isEnabled=true
        }
        binding.editeBusBtn.setOnClickListener {
            if (drivers.isNotEmpty()){
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
            description.text="Do you want to edite this bus?"
            description.textSize= 20F
                cancel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            okButton.setOnClickListener {
                viewModel.upDateBus(args.bus.id!!,driverId)
                viewModel.edite.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                    if(result!=null && result.success==true){
                       Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                        val action=EditeBusFragmentDirections.actionEditeBusFragmentToDeleteBusFragment()
                        findNavController().navigate(action)
                    }else{
                        Toast.makeText(requireContext(),result!!.message,Toast.LENGTH_SHORT).show()
                    }

                    }
                })
                dialog.dismiss()
            }


            }
        }
    }
    private fun showDriver(){
        bottomSheetDialogDriver.show()
    }

    override fun onDriverClick(item: Data) {
        binding.driver.text=item.name.toString().trim()
        driverId= item.id!!
        bottomSheetDialogDriver.dismiss()

    }


}