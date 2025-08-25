package com.example.myadmin.usersandbuss.addbuss

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentAddBusBinding
import com.example.myadmin.home.addtrip.Data
import com.example.myadmin.home.choseCity.CityClickLestener
import com.example.myadmin.home.choseCity.IteamHomeAdabter
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.DriverClickLestener
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.IteamDriverAdabter
import com.example.myadmin.usersandbuss.addbuss.typBus.IteamTypeAdabter
import com.example.myadmin.usersandbuss.addbuss.typBus.TypeClickLestener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.jvm.internal.Intrinsics.Kotlin

class AddBusFragment : Fragment(), CityClickLestener,TypeClickLestener,DriverClickLestener {
    lateinit var binding:FragmentAddBusBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetDialogBus: BottomSheetDialog
    private lateinit var bottomSheetDialogDriver: BottomSheetDialog
    private lateinit var adabter: IteamHomeAdabter
    private lateinit var adapterTypeBus: IteamTypeAdabter
    private lateinit var adapterDriver: IteamDriverAdabter
    private var driverId=0
    lateinit var token:String
    lateinit var location:String
    lateinit var typeBus:String
    var trips = mutableListOf<Data?>()
    var types = mutableListOf<com.example.myadmin.usersandbuss.addbuss.typBus.Data?>()
    var drivers= mutableListOf<com.example.myadmin.usersandbuss.addbuss.availableDrivers.Data?>()
    private val viewModel:ViewModelAddBus by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAddBusBinding.inflate(inflater,container,false)
        val sharedPreferen =requireActivity().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferen.getString("Token","")!!.trim()
        viewModel.getCities()
        kotlin.runCatching {
            viewModel.cities.observeForever(Observer { result ->

                if (result != null && result.success == true) {
                    trips.clear()
                    trips.addAll(result.data!!)
                    Log.i("items", "${trips.size}")
                    //
                    adabter = IteamHomeAdabter(trips, this)
                    bottomSheetDialog = BottomSheetDialog(requireContext())
                    val view = bottomSheetDialog.layoutInflater.inflate(R.layout.bottom_sheet, null)
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recview)
                    recyclerView.adapter = adabter
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.botoom_sheet)
                    bottomSheetDialog.setContentView(view)
                }

            })
        }
        //
        viewModel.getTypeBus()
        kotlin.runCatching {
            viewModel.typeBus.observe(viewLifecycleOwner, Observer { result->
                    if (result != null && result.success == true) {
                        types.clear()
                        types.addAll(result.data!!)
                        Log.i("items", "${types.size}")
                        //
                        adapterTypeBus = IteamTypeAdabter(types, this)
                        bottomSheetDialogBus = BottomSheetDialog(requireContext())
                        val view = bottomSheetDialogBus.layoutInflater.inflate(R.layout.bottom_sheet, null)
                        val textView=view.findViewById<TextView>(R.id.text_Select)
                        textView.text="Select The Bus Type"
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recview)
                        recyclerView.adapter = adapterTypeBus
                        view.background = ContextCompat.getDrawable(requireContext(), R.drawable.botoom_sheet)
                        bottomSheetDialogBus.setContentView(view)
                    }
            })
        }
        //
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.addBusBtn.isEnabled=false
        binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
        binding.place.addTextChangedListener{
            if (binding.place.text.isNotEmpty()){
                binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.addBusBtn.isEnabled=true
            }else{
                binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
                binding.addBusBtn.isEnabled=false
            }
        }
        //
        binding.typeBus.addTextChangedListener{
            if (binding.typeBus.text.isNotEmpty()){
                binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.addBusBtn.isEnabled=true
            }else{
                binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
                binding.addBusBtn.isEnabled=false
            }
        }
        //
        binding.busNumber.addTextChangedListener {
            if (binding.busNumber.text.isNotEmpty()){
                binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.addBusBtn.isEnabled=true
            }else{
                binding.addBusBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
                binding.addBusBtn.isEnabled=false
            }
        }
        binding.addBusBtn.setOnClickListener { v->
            if (location.isNotEmpty() && typeBus.isNotEmpty() && binding.busNumber.text.isNotEmpty()){
                viewModel.addBus(token,binding.busNumber.text.toString().trim().toInt(),typeBus,location,driverId)
                Log.i("infoaddbus", "$token,${binding.busNumber.text},$typeBus,$location")
                viewModel.bus.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if (result != null && result.success == true) {
                            Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                            binding.place.text=""
                            binding.typeBus.text=""
                            binding.driver.text=""
                            binding.busNumber.text.clear()
                        }else{
                            Toast.makeText(requireContext(),result!!.message,Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }
        }
        binding.cardLocation.setOnClickListener {
            showSheet()
        }
        binding.cardType.setOnClickListener {
            showTypeBuss()
        }
        binding.driverCard.setOnClickListener {
            showDriver()
        }
    }
    private fun showSheet(){

        bottomSheetDialog.show()
    }
    private fun showTypeBuss(){
        bottomSheetDialogBus.show()
    }
    private fun showDriver(){
        bottomSheetDialogDriver.show()
    }
    override fun onItemClick(item: Data) {
       binding.place.text=item.name.toString().trim()
        location= item.name.toString().trim()
        bottomSheetDialog.dismiss()
    }

    override fun onTypeClick(item: com.example.myadmin.usersandbuss.addbuss.typBus.Data) {
        binding.typeBus.text=item.type.toString().trim()
        typeBus=item.type.toString().trim()
        bottomSheetDialogBus.dismiss()
    }

    override fun onDriverClick(item: com.example.myadmin.usersandbuss.addbuss.availableDrivers.Data) {
        binding.driver.text=item.name.toString().trim()
        driverId= item.id!!
        bottomSheetDialogDriver.dismiss()

    }

}