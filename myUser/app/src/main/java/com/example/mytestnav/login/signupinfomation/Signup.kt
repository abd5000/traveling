package com.example.mytestnav.login.signupinfomation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mytestnav.databinding.FragmentSignupBinding


class Signup : Fragment() {
    lateinit var binding:FragmentSignupBinding
    val viewModel:ViewModelSignup by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignupBinding.inflate(inflater,container,false)
        // زر الرجوع
        binding.floatingButtonBack2.setOnClickListener { v->
            Navigation.findNavController(v).popBackStack()

        }
        // زر log in
        binding.login.setOnClickListener { v->
            val action=SignupDirections.actionSignupToLogin()
            Navigation.findNavController(v).navigate(action)
        }
        // signup button
        //عند الضغظ على الحقل اخفي الخطأ
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






        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.signup.setOnClickListener {v->


            val email=binding.email.text.toString().trim()
            val password=binding.password.text.toString().trim()
            val fullName=binding.fullName.text.toString().trim()
            val configPassword=binding.confirmPassword.text.toString().trim()

            // اذا كان الايميل فارغ و كان لا يحوي علي الايميل اظهر خطأ وكل الحقول معبئة
            if (checkTheEditText(fullName, email, password, configPassword)) {
                val sharedPref =requireActivity().getSharedPreferences("fcmToken", Context.MODE_PRIVATE)
                val fcmToken=sharedPref.getString("FCMToken","")
                viewModel.signup(fullName,binding.email.text.toString().trim(),password,configPassword,fcmToken!!)
                binding.progressBar2.visibility=View.VISIBLE

                //

                        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
                            Log.e("abd", "onCreateView: ${user?.data?.userinfo?.id}")
                            kotlin.runCatching {
                                if (user != null) {
                                    if (user.success == true) {
                                        Log.e("signup", "onCreateView: ${user.data?.userinfo?.id}")
                                        val id=user.data?.userinfo?.id
                                        val action = SignupDirections.actionSignupToConfirmEmail(
                                            binding.email.text.toString().trim(),
                                            binding.password.text.toString().trim(),
                                            binding.confirmPassword.text.toString().trim(),
                                            id!!,
                                            binding.fullName.text.toString().trim()
                                        )
                                        Navigation.findNavController(v).navigate(action)
                                        binding.progressBar2.visibility = View.INVISIBLE
                                        viewModel.deleteObserv()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            user.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.e("massge", "onResume: ${user.message}",)
                                        binding.progressBar2.visibility = View.INVISIBLE
                                        viewModel.deleteObserv()
                                    }
                                }
                            }
                            binding.progressBar2.visibility=View.INVISIBLE


                        })





            }

        }
    }


    private fun checkTheEditText(fullName:String,email: String,password: String,configPassword:String):Boolean{

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

        return true
    }



}
