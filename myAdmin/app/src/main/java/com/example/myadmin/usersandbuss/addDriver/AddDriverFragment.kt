package com.example.myadmin.usersandbuss.addDriver

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.myadmin.databinding.FragmentAddDriverBinding


class AddDriverFragment : Fragment() {
   lateinit var binding:FragmentAddDriverBinding
  private lateinit var token:String
  private val viewModel:ViewModelAddDriver by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddDriverBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        binding.email.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputEmail.error = null
            }
        }
        binding.password.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputPassword.error = null
            }
        }
        binding.fullName.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputFull.error = null
            }
        }
        binding.confirmPassword.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputConfirmPassword.error = null
            }
        }
        binding.phone.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputPhone.error = null
            }
        }



    }

    override fun onResume() {
        super.onResume()
        binding.addDriverBtn.setOnClickListener { v->
            val email=binding.email.text.toString().trim()
            val password=binding.password.text.toString().trim()
            val fullName=binding.fullName.text.toString().trim()
            val configPassword=binding.confirmPassword.text.toString().trim()
            val phone=binding.phone.text.toString().trim()
            if (checkTheEditText(fullName, email, password, configPassword,phone)) {
                viewModel.addDriver(token,fullName,email,password,configPassword,phone)
                kotlin.runCatching {
                    viewModel.driver.observe(viewLifecycleOwner, Observer { result->
                        if (result!=null && result.success==true){
                            Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(v).popBackStack()
                        }else{
                            Toast.makeText(requireContext(),result!!.message,Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

        }
    }
    private fun checkTheEditText(fullName:String,email: String,password: String,configPassword:String,phone:String):Boolean{

        //check first and last name
        if(fullName.isEmpty()){
            binding.fullName.error="This filed must not be empty"
            return false
        }


        //check email
        if( email.isEmpty()){
            binding.textinputEmail.error="This filed must not be empty"
            return false
        }
        else if(!email.contains("@gmail.com")){
            binding.textinputEmail.error="Enter the email correctly"
            return false
        }

        //check password
        if( password.isEmpty()){
            binding.textinputPassword.error="This filed must not be empty"
            return false
        }
        //check config password
        if( configPassword.isEmpty()){
            binding.textinputPassword.error="This filed must not be empty"
            return false
        }
        val comperPassword=configPassword==password
        if( !comperPassword){
            binding.textinputConfirmPassword.error="Password does not match"
            return false
        }

        if(phone.isEmpty()){
            binding.fullName.error="This filed must not be empty"
            return false
        }
        return true
    }

}