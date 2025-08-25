package com.example.mydriver.home.detailstrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydriver.databinding.FragmentDetailsTripBinding

class DetailsTripFragment : Fragment() {
    lateinit var binding: FragmentDetailsTripBinding
    private lateinit var  adabter: AdabtarReserve
    private val listAll= mutableListOf<Int>()
    private val seatList= mutableListOf<Int>()
    private val args:DetailsTripFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentDetailsTripBinding.inflate(inflater,container,false)
        binding.TicketPrice.text="SYP ${args.trip.price}"
        val numberSeat=args.trip.numberdisksisFalse!!.size
        binding.typeTripe.text= args.trip.typebus
        binding.numberSeatNotReserves.text="$numberSeat"
        binding.departCity.text=args.trip.starting
        binding.arrivalCity.text= args.trip.destination
        binding.depart.text= args.trip.tripTime
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(listAll.isEmpty() ){
            if( args.trip.typebus=="Vip"){
                for(i in 1..30){
                    listAll.add(i)
                }
            }else{
                for(i in 1..40){
                    listAll.add(i)
                }
            }
        }

        val listReserve=args.trip.numberdisksisFalse
        adabter= AdabtarReserve(listReserve,listAll)
        if (args.trip.typebus=="Vip"){
            binding.recyclerview.layoutManager= GridLayoutManager(requireContext(),3)
        }else{
            binding.recyclerview.layoutManager= GridLayoutManager(requireContext(),4)
        }
        binding.recyclerview.adapter=adabter
        binding.recyclerview.addItemDecoration(SpanItemDecoration(30,args.trip.typebus!!))
        adabter.notifyDataSetChanged()
    }
    override fun onResume() {
        super.onResume()

        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
    }
}