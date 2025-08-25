package com.example.mytestnav.login.forgetpassword.sendEmail

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
import com.example.mytestnav.databinding.FragmentForgetThePasswordBinding


class ForgetThePassword : Fragment() {
    lateinit var binding:FragmentForgetThePasswordBinding
    val viewModel: ViewModelFPassword by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentForgetThePasswordBinding.inflate(inflater,container,false)
        binding.floatingButtonBack.setOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.send.setOnClickListener { v->
            val email=binding.emial.text.toString().trim()
            if(checkTheEditText(email)){
                binding.progressBar.visibility=View.VISIBLE
                //
                    viewModel.sendEmail(email)


                viewModel.email.observe(viewLifecycleOwner, Observer { result->
                    Log.e("result", "onCreateView:${result.toString()} ", )
                    kotlin.runCatching {
                        if (result!=null){
                            if(result.success==true) {
                                val emails = binding.emial.text.toString().trim()
                                binding.progressBar.visibility = View.INVISIBLE
                                val user_id=result.data?.id

                                val action =
                                    ForgetThePasswordDirections.actionForgetThePasswordToForgetConfirmFragment(
                                        emails,
                                        "${result.data?.id}"
                                    )
                                Log.e("result_id", "onCreateView:${result.data?.id} ", )
                                Navigation.findNavController(v).navigate(action)
                                viewModel.deleteObserv()

                            } else{
                                binding.progressBar.visibility=View.INVISIBLE
                                Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                                viewModel.deleteObserv()
                            }
                        }
                    }

                })


            }
            binding.emial.setOnClickListener{
                binding.textinputEmail.error=null
            }


        }


        //


    }

    override fun onResume() {
        super.onResume()
        binding.emial.addTextChangedListener {
            binding.textinputEmail.error=null

        }
    }



    private fun checkTheEditText(email: String):Boolean{
        //check email
        binding.textinputEmail.error=null
        if( email.isEmpty()){
            binding.textinputEmail.error="This filed must not be empty"
            return false
        }
        else if(!email.contains("@gmail.com")){
            binding.textinputEmail.error="Enter the email correctly"
            return false
        }
        return true
    }
    private fun saveId(user_id:Int){
        val sharedPref =requireActivity().getSharedPreferences("onId", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putInt("user_id",user_id)
        editor.apply()
    }
}