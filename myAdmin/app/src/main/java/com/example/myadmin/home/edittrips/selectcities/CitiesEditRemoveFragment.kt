package com.example.myadmin.home.edittrips.selectcities

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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentCitiesEditRemoveBinding
import com.example.myadmin.home.addtrip.Data
import com.google.android.material.bottomsheet.BottomSheetDialog

class CitiesEditRemoveFragment : Fragment() , CityClickLestener {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var animation: Animation
    private lateinit var binding:FragmentCitiesEditRemoveBinding
    private lateinit var adabter: IteamHomeAdabter
    private  lateinit var state:String
    var trips = mutableListOf<Data?>()
    private var idFrom = 50
    private var idTo=50
    private val viewModel: ViewModelCities by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCitiesEditRemoveBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    override fun onResume() {
        super.onResume()
        binding.search.isEnabled=false
        binding.search.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
        binding.fromText.addTextChangedListener{
            if (binding.fromText.text.isNotEmpty() && binding.toText.text.toString().isNotEmpty()){
//                binding.search.background=resources.getDrawable(R.drawable.stat_search_btn)
                binding.search.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.search.isEnabled=true
            }
        }
        binding.toText.addTextChangedListener {
            if (binding.fromText.text.toString().isNotEmpty()&& binding.toText.text.toString().isNotEmpty()){
                binding.search.background=resources.getDrawable(R.drawable.stat_search_btn)
                binding.search.isEnabled=true
            }
        }
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }

        binding.search.setOnClickListener {v->
            val action=CitiesEditRemoveFragmentDirections.actionCitiesEditRemoveFragmentToTripsEditOrRemoveFragment(binding.fromText.text.toString(),binding.toText.text.toString(),idFrom,idTo)
            Navigation.findNavController(v).navigate(action)
        }
        binding.cardFrom.setOnClickListener{
            state="from"
            showSheet()

        }
        binding.cardTo.setOnClickListener {
            state="to"
            showSheet()

        }
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
    private fun showSheet(){

        bottomSheetDialog.show()
    }


    override fun onItemClick(item: Data) {
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

}