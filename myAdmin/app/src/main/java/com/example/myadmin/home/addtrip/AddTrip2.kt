package com.example.myadmin.home.addtrip

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentAddTrip2Binding
import com.example.myadmin.home.choseCity.CityClickLestener
import com.example.myadmin.home.choseCity.IteamHomeAdabter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Collections.rotate
import java.util.Date
import java.util.Locale

class AddTrip2 : Fragment(),CityClickLestener ,BusClickLestener{
    lateinit var binding:FragmentAddTrip2Binding
    private lateinit var animation: Animation
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetDialogBus: BottomSheetDialog
    private lateinit var adabter: IteamHomeAdabter
    private lateinit var adapterBus:BusAdabter
    private  lateinit var state:String
    var trips = mutableListOf<Data?>()
    private var idFrom = 50
    private var idTo=50
    private var busId=9999
    lateinit var date:String
    lateinit var time:String
    lateinit var token:String
    private  val viewModel:ViewModelAddTrip by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddTrip2Binding.inflate(inflater,container,false)
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
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        binding.timeTextview.text="$hour:$minute"
        time="$hour:$minute"
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText("Select trip time")
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build()
        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val selectedTime ="$hour:$minute"

            // عرض الوقت المختار في رسالة
//            Toast.makeText(requireContext(), "الوقت المختار: $selectedTime", Toast.LENGTH_SHORT).show()
            binding.timeTextview.text=selectedTime
            time=selectedTime
            Log.i("thetime", "$time")
        }

        binding.timeCard.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, "tag")}

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ///history
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        val janThisYear = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        val decThisYear = calendar.timeInMillis
        calendar[Calendar.MONTH] = Calendar.FEBRUARY
        //val february = calendar.timeInMillis

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(janThisYear)
                .setEnd(decThisYear)
                .setValidator(DateValidatorPointForward.now())

        // تعيين قيمة التاريخ الافتراضية إلى تاريخ اليوم
        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(today)).toString()

        binding.history.text = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault()).format(Date(today))
        binding.history.addTextChangedListener {
            if ( binding.history.text == SimpleDateFormat("EEEE dd MMMM", Locale.getDefault()).format(
                    Date(today)
                )){
                binding.today.visibility=View.VISIBLE

            }else{ binding.today.visibility=View.GONE}
        }
        binding.date.setOnClickListener {

            val datePicker=
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Trip start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setTheme(R.style.ThemeOverlay_App_DatePicker)
                    .build()

            datePicker.show(requireActivity().supportFragmentManager,"tag")

            datePicker.addOnPositiveButtonClickListener {selection ->
                val selectedDate = Date(selection)
                // تنسيق التاريخ حسب الرغبة باستخدام SimpleDateFormat
                //لارسالها لل api
                val dateFormat2 = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate2=dateFormat2.format(selectedDate).toString()
                date=formattedDate2
                binding.history.addTextChangedListener {
                    //listBusSpinner.clear()
                  //  busObserve(token, depCity, arrCity, date, time)
                }
                //
                val dateFormat = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)
                // تعيين التاريخ المُعدَّد إلى history
                binding.history.text = formattedDate

            }

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
        val sharedPreferen =requireActivity().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferen.getString("Token","")!!.trim()
//        Log.i("abdo", "$token,$depCity,$arrCity,$date,$time")
//        busObserve(token, depCity, arrCity, date, time)

        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.addTripBtn.isEnabled=false
        binding.addTripBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_date))
        binding.fromText.addTextChangedListener{
            if (binding.fromText.text.isNotEmpty() && binding.toText.text.toString().isNotEmpty()){
                binding.addTripBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.addTripBtn.isEnabled=true
                durationObserve(binding.fromText.text.toString(),binding.toText.text.toString())
                busObserve(token,binding.toText.text.toString(),binding.fromText.text.toString(),date,time)
                binding.bus.text=""
           }
        }
        binding.toText.addTextChangedListener {
            if (binding.fromText.text.toString().isNotEmpty()&& binding.toText.text.toString().isNotEmpty()){
                binding.addTripBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.stat_search_btn))
                binding.addTripBtn.isEnabled=true
                durationObserve(binding.fromText.text.toString(),binding.toText.text.toString())
                busObserve(token,binding.toText.text.toString(),binding.fromText.text.toString(),date,time)
    //            Log.i("infotrps", "$token,${binding.toText.text.toString().trim()},${binding.fromText.text.toString().trim()},$date.$time")
                binding.bus.text=""
            }
        }
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.history.addTextChangedListener {
            busObserve(token,binding.toText.text.toString(),binding.fromText.text.toString(),date,time)

        }
        binding.timeTextview.addTextChangedListener {
            busObserve(token,binding.toText.text.toString(),binding.fromText.text.toString(),date,time)

        }



        binding.addTripBtn.setOnClickListener { v->
         //  Log.e("addtrips", "$startId,$destId,$date,$time,$price,$busId", )
            Log.i("addtripinfo", "${idTo},$idFrom,$date,$busId,$time")
            if(busId==9999){
                Toast.makeText(requireContext(),"Please Enter Select", Toast.LENGTH_SHORT).show()

            }else if (idTo!=null || idFrom!=null || date!=null|| busId!=null){
               val isPeriodic =binding.periodic.isChecked
                viewModel.addTrip(idTo,idFrom,date,"$time:00",busId,isPeriodic)
                Log.i("infoaddtrip", "$idTo,$idFrom,date,$time:00,$busId,$isPeriodic")
                kotlin.runCatching {
                    viewModel.trip.observe(viewLifecycleOwner, Observer { result->
                        if (result!=null && result.success==true){
                            Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
                           binding.apply {
                               fromText.text=""
                               toText.text=""
                               bus.text=""
                               durationTextview.text=""

                           }
                        }
                    })
                }
            }
        }
        binding.cardFrom.setOnClickListener{
            state="from"
            showSheet()

        }
        binding.cardTo.setOnClickListener {
            state="to"
            showSheet()

        }
        binding.busCard.setOnClickListener {

            if (binding.fromText.text.isNotEmpty()&&binding.toText.text.isNotEmpty()){
                Log.i("infoBus", "$token,${binding.toText.text.toString()},${ binding.fromText.text.toString() },$date,$time")

                showBuss()
            }else{
                Toast.makeText(requireContext(),"Enter the cities first",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun durationObserve(depCity:String,arrCity:String){
        viewModel.getDuration(depCity,arrCity)
        kotlin.runCatching {
            viewModel.duration.observe(viewLifecycleOwner, Observer { result->
                if (result!=null && result.success==true){
                    if (result.data !=null){
                        binding.durationTextview.text=result.data
                    }
                }
            })
        }

    }


private fun busObserve(token:String,cityTo:String,cityFrom:String,data:String,time:String){
    viewModel.getBus(token,cityTo,cityFrom,data,time)
    kotlin.runCatching {
        viewModel.bus.observe(viewLifecycleOwner, Observer { result ->
            if (result!=null && result.success==true){
                if (result.data !=null){
                    val  listBus= result.data
                    result.data.forEach { item->
                        Log.e("buslist", "${item?.number},", )
                    }
                    adapterBus = BusAdabter(listBus, this)
                    adapterBus.notifyDataSetChanged()
                    bottomSheetDialogBus = BottomSheetDialog(requireContext())
                    val view = bottomSheetDialogBus.layoutInflater.inflate(R.layout.bottom_sheet, null)
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recview)
                    val textView=view.findViewById<TextView>(R.id.text_Select)
                    textView.text="Select Bus"
                    recyclerView.adapter = adapterBus
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.botoom_sheet)
                    bottomSheetDialogBus.setContentView(view)

                }else{

                    Toast.makeText(requireContext(),result.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), result!!.message.toString(),Toast.LENGTH_SHORT).show()
            }
        })
    }
}

    private fun showSheet(){

        bottomSheetDialog.show()
    }
    private fun showBuss(){

        bottomSheetDialogBus.show()
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

    override fun onBusClick(item: com.example.myadmin.home.addtrip.buss.Data) {
        binding.bus.text="${item.number},${item.type}"
        busId= item.id!!
        bottomSheetDialogBus.dismiss()
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


}