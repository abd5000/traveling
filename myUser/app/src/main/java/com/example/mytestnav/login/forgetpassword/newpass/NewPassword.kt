package com.example.mytestnav.login.forgetpassword.newpass

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentNewPasswordBinding


class NewPassword : Fragment() {
    lateinit var binding:FragmentNewPasswordBinding
   private val viewModel:ViewModelNewPassword by viewModels()
    private val args:NewPasswordArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewPasswordBinding.inflate(inflater,container,false)
        binding.newPassword.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textInputNewPassword.error = null
            }
        }
        binding.confirmPassward.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textConfirmPassword.error = null
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.save.setOnClickListener { v->
            val password=binding.newPassword.text.toString().trim()
            val configpassword=binding.confirmPassward.text.toString().trim()
            if (checkTheEditText(password,configpassword)){
                val userId=args.userId.toInt()
                viewModel.newPassword(userId,password,configpassword)
                binding.progressBar.visibility=View.VISIBLE
//
                            viewModel.password.observe(viewLifecycleOwner, Observer { result->
                                kotlin.runCatching {
                                    if (result!=null){
                                        if (result.success==true){
                                            binding.progressBar.visibility=View.INVISIBLE
                                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                                            Navigation.findNavController(v).navigate(R.id.action_newPassword_to_login)
                                            viewModel.deleteObserv()
                                        }else{
                                            binding.progressBar.visibility=View.INVISIBLE
                                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                                            viewModel.deleteObserv()
                                        }

                                    }
                                }
                            })
            }

        }
    }
    private fun checkTheEditText(password: String,configpassword:String):Boolean{


        //check password
        if( password.isEmpty()){
            binding.textInputNewPassword.error="This filed must not be empty"
            return false
        }
        //check config password
        if( configpassword.isEmpty()){
            binding.textConfirmPassword.error="This filed must not be empty"
            return false
        }
        val comperpassword=configpassword==password
        if( !comperpassword){
            binding.textConfirmPassword.error="Password does not match"
            return false
        }

        return true
    }
    private fun saveToken(token:String?){
        val sharedPref =requireActivity().getSharedPreferences("ontoken", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("token",token)
        editor.apply()
    }

}