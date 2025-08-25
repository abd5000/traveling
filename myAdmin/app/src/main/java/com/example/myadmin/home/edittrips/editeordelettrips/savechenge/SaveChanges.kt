package com.example.myadmin.home.edittrips.editeordelettrips.savechenge

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentSaveChangesBinding
import com.example.myadmin.home.edittrips.editeordelettrips.AdabterTrips
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.ToNumberStrategy
import java.util.Date
import java.util.Locale
class SaveChanges : Fragment() {
    lateinit var binding:FragmentSaveChangesBinding
    lateinit var date:String
    lateinit var time:String
    private val args:SaveChangesArgs by navArgs()
    private val viewModel:ViewModelSaveChange by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSaveChangesBinding.inflate(inflater,container,false)
        binding.apply {
            depCity.text=args.trip.starting
            arrCity.text=args.trip.destination
            history.hint=args.trip.tripDate
            time.hint=args.trip.tripTime
//            Log.e("deptimee", "${args.depTime}", )
            bus.text=" ${args.trip.numberbus}"
            typeTripe.text=args.trip.typebus
            price.text=" ${args.trip.price}"
            seatsReserved.text="${args.trip.numberdisksisFalse!!.size}"
        }
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        time= args.trip.tripTime!!
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
            val selectedTime = "$hour:$minute"

            // عرض الوقت المختار في رسالة
//            Toast.makeText(requireContext(), "الوقت المختار: $selectedTime", Toast.LENGTH_SHORT).show()
            binding.time.text=selectedTime
            time=selectedTime
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

        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(today)).toString()

        binding.history.hint = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault()).format(Date(today))
        date= args.trip.tripDate!!
        binding.history.hint=args.trip.tripDate
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
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.saveBtn.setOnClickListener { v->
            Log.i("infoSaveTrips", "$date,$time")
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
                        description.text="Do you want to save changes"
                        description.textSize= 20F
                        cancel.setOnClickListener {
                            dialog.dismiss()
                        }
                        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                        okButton.setOnClickListener {

                            viewModel.upDateTrip(date, time, args.trip.id!!)
                            viewModel.change.observe(viewLifecycleOwner, Observer { result->
                                kotlin.runCatching {
                                    if(result!=null && result.success==true){
                                        dialog.dismiss()
                                        Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                                        val action=SaveChangesDirections.actionSaveChangesToTripsEditOrRemoveFragment()
                                        findNavController().navigate(action)

                                    }else{
                                        Toast.makeText(requireContext(),result!!.message,Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    }

                                }
                            })



                        }
                    }
        }
    }

//

