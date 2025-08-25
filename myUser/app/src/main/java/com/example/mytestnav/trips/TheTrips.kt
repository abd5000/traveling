package com.example.mytestnav.trips

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentTheTripsBinding
import com.example.mytestnav.trips.filter.FilterAdabtar
import com.example.mytestnav.trips.filter.FilterClickListener
import com.example.mytestnav.trips.filter.FilterCompanTypeAdabter
import com.example.mytestnav.trips.filter.TypeFilterClick
import com.example.mytestnav.trips.filter.typeTrips.AdapterTypsTrips
import com.example.mytestnav.trips.filter.typeTrips.DataX
import com.example.mytestnav.trips.filter.typeTrips.TypeTripLestenar
import com.google.android.material.bottomsheet.BottomSheetDialog


class TheTrips : Fragment() ,TripListener,FilterClickListener,TypeFilterClick,TypeTripLestenar{
lateinit var binding:FragmentTheTripsBinding
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var adabter: FilterAdabtar
    lateinit var token:String
    private lateinit var  recyclerViewSheet:RecyclerView
    private val viewModel: ViewModelTrips by viewModels()

    private lateinit var viewSheet:View
private val args:TheTripsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTheTripsBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
         token= sharedPreferenToken.getString("Token","")!!.trim()
        viewModel.getTrips(args.idFrom,args.idTo,args.date,token)
        Log.i("idFrom", "${args.idFrom}and ${args.idTo}")
        viewModel.getCompanies(token)
        viewModel.getTypeTrips()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.trips.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
            if(result!=null && result.success==true){
            val trips=result.data
                if (trips.isNullOrEmpty()){
                    binding.notFound.visibility=View.VISIBLE
                }else{
                    binding.notFound.visibility=View.GONE
                }
                binding.recyclerview.visibility=View.VISIBLE
                 val adapter=AdabterTrips(trips,this)
                binding.recyclerview.adapter=adapter
                adapter.notifyDataSetChanged()
            }else{
                binding.notFound.visibility=View.VISIBLE
            }
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    override fun onResume() {
        super.onResume()

        adabter= FilterAdabtar(listOf("Company","Trip Type"),this)
        bottomSheetDialog= BottomSheetDialog(requireContext())
        viewSheet=bottomSheetDialog.layoutInflater.inflate(R.layout.filter_sheet,null)
        recyclerViewSheet=viewSheet.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerViewSheet.adapter=adabter



        viewSheet.background= ContextCompat.getDrawable(requireContext(), R.drawable.botoom_sheet)
        bottomSheetDialog.setContentView(viewSheet)
        binding.toolbar.setOnMenuItemClickListener { item ->

            adabter= FilterAdabtar(listOf("Company","Trip Type"),this)
            recyclerViewSheet.adapter=adabter
            when (item.itemId) {
                R.id.filter -> {
                    bottomSheetDialog.show()
                    true
                }

                else -> {
                    false
                }
            }
        }


        binding.toolbar.title="${args.cityFrom} - ${args.cityTo}"
        binding.toolbar.subtitle= args.date
        // اغلاق الصفحة
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }



    }


    override fun onClickItem(trip: Data) {
        val list= trip.numberdisksisFalse

        val number= IntArray(list!!.size)
        for (i in list.indices){
            number[i]= list[i]!!

        }
        val navController=findNavController()
        val action=TheTripsDirections.actionTheTripsToReserve("${trip.company}","${trip.starting}","${trip.destination}",number,trip.price!!,trip.tripDate!!,"${trip.tripTime}",trip.numberbus!!,trip.arrivalTime!!,
            trip.typebus!!,
            trip.id!!
        )
        navController.navigate(action)


    }



    override fun onTypeClick(item: String) {
        when(item){
            "Company"->{
                bottomSheetDialog.dismiss()
                viewModel.company.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if (result!=null && result.success==true){
                           val company=result.data
                            val adabter= FilterCompanTypeAdabter(company,this)
                            recyclerViewSheet.adapter=adabter
                        }
                    }
                })

                bottomSheetDialog.show()

            }
            "Trip Type"->{
               bottomSheetDialog.dismiss()
                viewModel.typeTrip.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if (result!=null && result.success==true){
                           val type=result.data

                            val adabter= AdapterTypsTrips(type,this)
                            recyclerViewSheet.adapter=adabter
                        }
                    }
                })

                bottomSheetDialog.show()
            }
        }
    }

    override fun onItemClick(company: com.example.mytestnav.trips.companys.Data) {
        viewModel.companyFliter(company.id.toString(),args.idFrom,args.idTo,args.date,token)

        viewModel.companyFilter.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if(result?.success==false){
                    Log.i("tripss", "${result?.message}+${result?.success} ")
                    binding.recyclerview.visibility=View.GONE
                    binding.notFound.visibility=View.VISIBLE
                }
                if( result?.success==true){
                    val trips=result.data
                    if (trips.isNullOrEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                    }else{
                        binding.notFound.visibility=View.GONE
                    }
                    binding.recyclerview.visibility=View.VISIBLE
                    val adapter=AdabterTrips(trips,this)
                    binding.recyclerview.adapter=adapter
                    adapter.notifyDataSetChanged()
                }

            }

        })
        bottomSheetDialog.dismiss()
    }

    override fun onTypeClick(item: DataX) {
        viewModel.typeTripFilter(item.id.toString(),args.idFrom,args.idTo,args.date,token)
        viewModel.filterTypeTrip.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if(result?.success==false){
                   // Log.i("tripss", "${result?.message}+${result?.success} ")
                    binding.recyclerview.visibility=View.GONE
                    binding.notFound.visibility=View.VISIBLE
                }
                if( result?.success==true){
                    val trips=result.data
                    if (trips.isNullOrEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                    }else{
                        binding.notFound.visibility=View.GONE
                    }
                    binding.recyclerview.visibility=View.VISIBLE
                    val adapter=AdabterTrips(trips,this)
                    binding.recyclerview.adapter=adapter
                    adapter.notifyDataSetChanged()
                }

            }
        })
        bottomSheetDialog.dismiss()
    }
}