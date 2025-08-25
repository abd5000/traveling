package com.example.mytestnav.home

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.home.cites.Data
import com.example.mytestnav.home.cites.ViewModelCity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date
import java.util.Locale

class TheHomeFragment : Fragment(), CityClickLestener {
    lateinit var binding:com.example.mytestnav.databinding.FragmentTheHomeBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var animation:Animation
    private val viewModel:ViewModelCity by viewModels()
   private lateinit var adabter: IteamHomeAdabter
 private  lateinit var state:String
 lateinit var date:String
 private var idFrom = 50
    private var idTo=50
    var trips = mutableListOf<Data?>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = com.example.mytestnav.databinding.FragmentTheHomeBinding.inflate(inflater, container, false)
        viewModel.getCities()
        val sharedPref =requireActivity().getSharedPreferences("fcmToken", Context.MODE_PRIVATE)
        val fcmToken=sharedPref.getString("FCMToken","")
        Log.i("FCMToken", "$fcmToken")
       val x= kotlin.runCatching {
            viewModel.cities.observeForever(Observer { result ->

                if (result != null && result.success == true) {
                    trips.clear()
                    trips.addAll(result.data)
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.move.setOnClickListener {
            rotate()
            val to =binding.toText.text.toString().trim()
            val from=binding.fromText.text.toString().trim()
            var idfrom=idFrom
            var idto=idTo

            if (from.isNotEmpty() && to.isNotEmpty()){
                //animation for from
            val animationSet=AnimationSet(true)
            val alphaAnimation=AlphaAnimation(1.0f,0.0f)
            alphaAnimation.duration=500
            animationSet.addAnimation(alphaAnimation)
            binding.fromText.startAnimation(animationSet)
            binding.fromText.text=to
                idFrom=idto

                val alphaAnimation2=AlphaAnimation(0.0f,1.0f)
                alphaAnimation2.duration=500
                binding.fromText.startAnimation(alphaAnimation2)

                    //animation for to
                val animationSet1=AnimationSet(true)
                val alphaAnimation3=AlphaAnimation(1.0f,0.0f)
                alphaAnimation3.duration=500
                animationSet1.addAnimation(alphaAnimation3)
                binding.toText.startAnimation(animationSet1)
                binding.toText.text=from
                idTo=idfrom

                val alphaAnimation4=AlphaAnimation(0.0f,1.0f)
                alphaAnimation4.duration=500
                binding.toText.startAnimation(alphaAnimation4)

                binding.toText.text=from
            }

        }

        binding.search.isEnabled=false
        binding.search.background=resources.getDrawable(R.drawable.stat_date)
        binding.fromText.addTextChangedListener {
            if (binding.fromText.text.isNotEmpty() && binding.toText.text.toString().isNotEmpty()
                &&binding.history.text.toString().trim().isNotEmpty()){
                binding.search.background=resources.getDrawable(R.drawable.stat_search_btn)
                binding.search.isEnabled=true
            }
        }
        binding.toText.addTextChangedListener {
            if (binding.fromText.text.toString().isNotEmpty()&& binding.toText.text.toString().isNotEmpty()
                &&binding.history.text.toString().trim().isNotEmpty()){
                binding.search.background=resources.getDrawable(R.drawable.stat_search_btn)
                binding.search.isEnabled=true
            }
        }
        binding.history.addTextChangedListener {
            if (binding.fromText.text.toString().isNotEmpty()&& binding.toText.text.toString().isNotEmpty()
                &&binding.history.text.toString().trim().isNotEmpty()){
                binding.search.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.stat_search_btn
                ))
                binding.search.isEnabled=true
            }
        }

        binding.search.setOnClickListener {v->
            Log.i("start+dist", "start:${binding.fromText.text},end ${binding.toText.text.toString()}")
           val action=TheHomeFragmentDirections.actionTheHomeFragmentToTheTrips(binding.fromText.text.toString(),binding.toText.text.toString(),date,idFrom,idTo)
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


// Build constraints.
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(janThisYear)
                .setEnd(decThisYear)
                .setValidator(DateValidatorPointForward.now())

        // تعيين قيمة التاريخ الافتراضية إلى تاريخ اليوم
        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(today)).toString()

        binding.history.text = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault()).format(Date(today))
        binding.history.addTextChangedListener {

            if ( binding.history.text == SimpleDateFormat("EEEE dd MMMM", Locale.getDefault()).format(Date(today))){
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
                //
                val dateFormat = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)
                // تعيين التاريخ المُعدَّد إلى history
                binding.history.text = formattedDate

            }

        }
        //تاريخ العودة
        binding.returnCard.setOnClickListener {

            val datePicker=
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Trip return date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setTheme(R.style.ThemeOverlay_App_DatePicker)
                    .build()
            datePicker.show(requireActivity().supportFragmentManager,"tag")

            datePicker.addOnPositiveButtonClickListener {selection ->
                val selectedDate = Date(selection)
                // تنسيق التاريخ حسب الرغبة باستخدام SimpleDateFormat
                val dateFormat = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)
                // تعيين التاريخ المُعدَّد إلى history
                binding.historyReturn.text = formattedDate
            }
        }
    }
    private fun showSheet(){

        bottomSheetDialog.show()
    }

    override fun onItemClick(item: Data) {
        if (state=="from"){
            if(item.name != binding.toText.text.toString()){
        binding.fromText.text = item.name
        idFrom=item.id}
        else
             Toast.makeText(requireContext(),"Choose anther city",Toast.LENGTH_SHORT).show()
        }else if(state=="to"){
            if (item.name !=binding.fromText.text.toString()){
                idTo=item.id
            binding.toText.text=item.name}
            else Toast.makeText(requireContext(),"Choose anther city",Toast.LENGTH_SHORT).show()
        }
        state="non"
        bottomSheetDialog.dismiss()
    }
    private fun rotate(){
        animation=RotateAnimation(
            0f,180f,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f
        )
        animation.duration=400
        binding.move.startAnimation(animation)
    }

}