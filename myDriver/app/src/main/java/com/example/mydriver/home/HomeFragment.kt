package com.example.mydriver.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mydriver.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    lateinit var trip:Data
    private val viewModel:ViewModelInfo by viewModels()
    val requestPermissionLocation=registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if(isGranted){

        }else{

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentHomeBinding.inflate(inflater,container,false)
        val sharedPref =requireActivity().getSharedPreferences("onName", Context.MODE_PRIVATE)
       binding.name.text= sharedPref.getString("FullName","")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref =requireActivity().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        val token=sharedPref.getString("Token","")
        Log.i("thetoken", "$token")
        viewModel.getInfo(token!!)
        viewModel.info.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if (result!=null && result.success==true){
                    val firstTrip=result.data!![0]!!
                    trip=firstTrip
                    binding.timeFrom.text= firstTrip.tripTime
                    binding.timeTo.text=firstTrip.arrivalTime
                    binding.cityFrom.text=firstTrip.starting
                    binding.cityTo.text=firstTrip.destination
                    binding.detailsBtn.isEnabled=true
                }else{
                    binding.timeFrom.text=""
                    binding.timeTo.text=""
                    binding.cityFrom.text=""
                    binding.cityTo.text=""
                    binding.detailsBtn.isEnabled=false
                    Toast.makeText(requireContext(),result!!.message,Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.detailsBtn.setOnClickListener { v->
            val action=HomeFragmentDirections.actionHomeFragmentToDetailsTripFragment(trip)
            findNavController().navigate(action)

        }
    }

    override fun onResume() {
        super.onResume()
        val intent=Intent(requireContext(), MyService::class.java)
        val sharedPrefstate =requireActivity().getSharedPreferences("onState", Context.MODE_PRIVATE)
       val isCheckedState= sharedPrefstate.getBoolean("State",false)
        binding.startBox.isChecked=isCheckedState
        if (isCheckedState){
            binding.lottieStart.visibility=View.GONE
            binding.lottieStop.visibility=View.VISIBLE
            binding.textStart.text="Stop"

            startForegroundService(requireContext(),intent)
        }else{
            binding.lottieStart.visibility=View.VISIBLE
            binding.lottieStop.visibility=View.GONE
            binding.textStart.text="Start"
            requireContext().stopService(intent)
        }

        binding.startBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val locationManager= requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Toast.makeText(requireActivity(),"GPS not connected", Toast.LENGTH_SHORT).show()
            }else{
                if (ContextCompat.checkSelfPermission(requireActivity().applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                {
                  //  Toast.makeText(requireActivity(),"GPS connected", Toast.LENGTH_SHORT).show()


                    if (isChecked){
                        binding.lottieStart.visibility=View.GONE
                        binding.lottieStop.visibility=View.VISIBLE
                        binding.textStart.text="Stop"

                        startForegroundService(requireContext(),intent)
                    }else{
                        binding.lottieStart.visibility=View.VISIBLE
                        binding.lottieStop.visibility=View.GONE
                        binding.textStart.text="Start"
                        requireContext().stopService(intent)
                    }

                }else{
                    requestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    binding.startBox.isChecked=false
                }
            }


        }




    }

    override fun onPause() {
        super.onPause()
        val sharedPref =requireActivity().getSharedPreferences("onState", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean("State",binding.startBox.isChecked)
        editor.apply()
        Log.i("stateInfo", "${binding.startBox.isChecked}")
    }



}