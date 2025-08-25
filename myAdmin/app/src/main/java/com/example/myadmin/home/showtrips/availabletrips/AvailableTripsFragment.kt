package com.example.myadmin.home.showtrips.availabletrips

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentAvailableTripsBinding
import com.example.myadmin.home.choseCity.CityClickLestener
import com.example.myadmin.home.choseCity.IteamHomeAdabter
import com.example.myadmin.home.showtrips.TheTripsDirections
import com.example.myadmin.home.showtrips.reserve.reservapi.ViewModelSeats
import com.google.android.material.bottomsheet.BottomSheetDialog

class AvailableTripsFragment : Fragment(),AvailableTripListener, CityClickLestener {
    lateinit var binding:FragmentAvailableTripsBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var animation: Animation
    private val viewOneTrip: ViewModelSeats by viewModels()
    private lateinit var adabter: IteamHomeAdabter
    var cities = mutableListOf<com.example.myadmin.home.addtrip.Data?>()
    private  lateinit var state:String
    private var idFrom = 50
    private var idTo=50
    private lateinit var token:String
    var trips= mutableListOf<Data?>()
    private val viewModel:ViewModelAvailable by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAvailableTripsBinding.inflate(inflater,container,false)
        viewModel.getCities()
        kotlin.runCatching {
            viewModel.cities.observeForever(Observer { result ->

                if (result != null && result.success == true) {
                    cities.clear()
                    cities.addAll(result.data!!)
                    Log.i("items", "${trips.size}")
                    //
                    adabter = IteamHomeAdabter(cities, this)
                    bottomSheetDialog = BottomSheetDialog(requireContext())
                    val view = bottomSheetDialog.layoutInflater.inflate(R.layout.bottom_sheet, null)
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recview)
                    recyclerView.adapter = adabter
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.botoom_sheet)
                    bottomSheetDialog.setContentView(view)
                }

            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        viewModel.getAllTripsAvailable(token)
        viewModel.allTrips.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if(result!=null && result.success==true){
                    trips= result.data as MutableList<Data?>
                    Log.e("infoTrips", "${trips[0]!!.tripDate.toString()}", )
                    if (trips.isNullOrEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                    }else{
                        binding.notFound.visibility=View.GONE
                    }
                    binding.recyclerview.visibility=View.VISIBLE
                    val adapter= AdapterTripsAvailable(trips,this)
                    adapter.notifyDataSetChanged()
                    binding.recyclerview.adapter=adapter

                }else{
                    binding.notFound.visibility=View.VISIBLE
                }
            }
        })
        binding.cardFrom.setOnClickListener{
            state="from"
            showSheet()

        }
        binding.cardTo.setOnClickListener {
            state="to"
            showSheet()

        }
    }

    override fun onResume() {
        super.onResume()
        binding.move.setOnClickListener {
            rotate()
            val to =binding.toText.text.toString().trim()
            val from=binding.fromText.text.toString().trim()

            if (from.isNotEmpty() && to.isNotEmpty()){
                //animation for from
                val animationSet= AnimationSet(true)
                val alphaAnimation= AlphaAnimation(1.0f,0.0f)
                alphaAnimation.duration=500
                animationSet.addAnimation(alphaAnimation)
                binding.fromText.startAnimation(animationSet)
                binding.fromText.text=to
                val temp=idFrom
                idFrom=idTo
                idTo=temp
                val alphaAnimation2= AlphaAnimation(0.0f,1.0f)
                alphaAnimation2.duration=500
                binding.fromText.startAnimation(alphaAnimation2)

                //animation for to
                val animationSet1= AnimationSet(true)
                val alphaAnimation3= AlphaAnimation(1.0f,0.0f)
                alphaAnimation3.duration=500
                animationSet1.addAnimation(alphaAnimation3)
                binding.toText.startAnimation(animationSet1)
                binding.toText.text=to

                val alphaAnimation4= AlphaAnimation(0.0f,1.0f)
                alphaAnimation4.duration=500
                binding.toText.startAnimation(alphaAnimation4)

                binding.toText.text=from
            }

        }
        binding.applyingFilter.isEnabled=false
        binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
        binding.fromText.addTextChangedListener {
            if ((binding.fromText.text.isNotEmpty()&&binding.toText.text.isNotEmpty())||binding.search.text.isNotEmpty()){
                binding.applyingFilter.isEnabled=true
                binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_shap_login))
            }else{
                binding.applyingFilter.isEnabled=false
                binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
            }
        }
        binding.toText.addTextChangedListener {
            if ((binding.fromText.text.isNotEmpty()&&binding.toText.text.isNotEmpty())||binding.search.text.isNotEmpty()){
                binding.applyingFilter.isEnabled=true
                binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_shap_login))
            }else{
                binding.applyingFilter.isEnabled=false
                binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
            }
        }
        binding.search.addTextChangedListener {
            if ((binding.fromText.text.isNotEmpty()&&binding.toText.text.isNotEmpty())||binding.search.text.isNotEmpty()){
                binding.applyingFilter.isEnabled=true
                binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_shap_login))
            }else{
                binding.applyingFilter.isEnabled=false
                binding.applyingFilter.background = (ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
            }
        }

        binding.applyingFilter.setOnClickListener {
            binding.reset.setTextColor(ContextCompat.getColor(requireContext(),R.color.blue))
            if ((binding.fromText.text.isNotEmpty()&&binding.toText.text.isNotEmpty()) && binding.search.text.isNotEmpty()){
                viewModel.filterBuBusAndCites(token,idFrom,idTo,binding.search.text.toString().toInt())
                viewModel.busAndCites.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if(result!=null && result.success==true){
                            trips= result.data as MutableList<Data?>
                            Log.e("infoTrips", "${trips[0]!!.tripDate.toString()}", )
                            if (trips.isEmpty()){
                                binding.notFound.visibility=View.VISIBLE
                            }else{
                                binding.notFound.visibility=View.GONE
                            }
                            binding.recyclerview.visibility=View.VISIBLE
                            val adapter= AdapterTripsAvailable(trips,this)
                            adapter.notifyDataSetChanged()
                            binding.recyclerview.adapter=adapter
                            binding.filterCard.visibility=View.GONE

                        }else{
                            binding.notFound.visibility=View.VISIBLE
                        }
                    }
                })
            }
            if((binding.fromText.text.isNotEmpty()&& binding.toText.text.isNotEmpty())){
                Log.i("resultfilter", "$idFrom,$idTo")
                viewModel.filterByCites(token, idFrom, idTo)

                viewModel.tripsByCites.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if(result!=null && result.success==true){
                            trips= result.data as MutableList<Data?>
                            Log.e("infoTrips", "${trips[0]!!.tripDate.toString()}", )
                            if (trips.isEmpty()){
                                binding.notFound.visibility=View.VISIBLE
                            }else{
                                binding.notFound.visibility=View.GONE
                            }
                            binding.recyclerview.visibility=View.VISIBLE
                            val adapter= AdapterTripsAvailable(trips,this)
                            adapter.notifyDataSetChanged()
                            binding.recyclerview.adapter=adapter
                            binding.filterCard.visibility=View.GONE

                        }else{
                            binding.notFound.visibility=View.VISIBLE
                        }
                    }
                })
            }
            if ((binding.fromText.text.isEmpty()&& binding.toText.text.isEmpty()) && binding.search.text.isNotEmpty()){
                viewModel.filterByBus(token,binding.search.text.toString().trim().toInt())
                viewModel.tripsByBus.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if(result!=null && result.success==true){
                            trips= result.data as MutableList<Data?>
                            Log.e("infoTrips", "${trips[0]!!.tripDate.toString()}", )
                            if (trips.isEmpty()){
                                binding.notFound.visibility=View.VISIBLE
                            }else{
                                binding.notFound.visibility=View.GONE
                            }
                            binding.recyclerview.visibility=View.VISIBLE
                            val adapter= AdapterTripsAvailable(trips,this)
                            adapter.notifyDataSetChanged()
                            binding.recyclerview.adapter=adapter
                            binding.filterCard.visibility=View.GONE

                        }else{
                            binding.notFound.visibility=View.VISIBLE
                        }
                    }
                })
            }

        }
        binding.reset.setOnClickListener {
            binding.reset.setTextColor(ContextCompat.getColor(requireContext(),R.color.colocr_text_skip))
            binding.fromText.text=""
            binding.toText.text=""
            binding.search.text.clear()
            binding.filterCard.visibility=View.GONE
            viewModel.getAllTripsAvailable(token)
            viewModel.allTrips.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if(result!=null && result.success==true){
                        trips= result.data as MutableList<Data?>
                        Log.e("infoTrips", "${trips[0]!!.tripDate.toString()}", )
                        if (trips.isEmpty()){
                            binding.notFound.visibility=View.VISIBLE
                        }else{
                            binding.notFound.visibility=View.GONE
                        }
                        binding.recyclerview.visibility=View.VISIBLE
                        val adapter= AdapterTripsAvailable(trips,this)
                        adapter.notifyDataSetChanged()
                        binding.recyclerview.adapter=adapter


                    }else{
                        binding.notFound.visibility=View.VISIBLE
                    }
                }
            })
        }
        binding.detailsBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.filterCard.visibility=View.VISIBLE
            }else{
                binding.filterCard.visibility=View.GONE
            }
        }
    }

    override fun onClickItem(trip: com.example.myadmin.home.showtrips.availabletrips.Data) {
       val action=TheTripsDirections.actionTheTripsToDetailsTripFragment(trip)
        val bundle=Bundle()
        bundle.putSerializable("trip",trip)
        findNavController().navigate(action)
    }


    override fun onItemClick(item: com.example.myadmin.home.addtrip.Data) {
        if (state=="from"){
            if(item.name != binding.toText.text.toString()){
                binding.fromText.text = item.name

                idFrom= item.id!!
            }
            else
                Toast.makeText(requireContext(),"Choose anther city", Toast.LENGTH_SHORT).show()
        }else if(state=="to"){
            if (item.name !=binding.fromText.text.toString()){
                idTo= item.id!!
                binding.toText.text=item.name}
            else Toast.makeText(requireContext(),"Choose anther city", Toast.LENGTH_SHORT).show()
        }
        state="non"
        bottomSheetDialog.dismiss()
    }
    private fun showSheet(){

        bottomSheetDialog.show()
    }
    private fun rotate(){
        animation= RotateAnimation(
            0f,180f,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f
        )
        animation.duration=400
        binding.move.startAnimation(animation)
    }

    override fun onReserveItem(trip: Data) {
        viewOneTrip.getOneTrip(trip.id!!)
        viewOneTrip.trips.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                Log.i("infotripone", "${result!!.data}")
                if (result != null && result.success == true) {
                    val action=TheTripsDirections.actionTheTripsToReserveSeats(result.data!!)
                    findNavController().navigate(action)
                }
            }
        })


    }
}