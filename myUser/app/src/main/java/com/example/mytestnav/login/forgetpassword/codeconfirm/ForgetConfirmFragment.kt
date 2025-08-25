package com.example.mytestnav.login.forgetpassword.codeconfirm

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentForgetConfirmBinding


class ForgetConfirmFragment : Fragment() {
    lateinit var binding:FragmentForgetConfirmBinding
   private val args:ForgetConfirmFragmentArgs by navArgs()
   private val viewModel:ViewModelCodePassword by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentForgetConfirmBinding.inflate(inflater,container,false)
        binding.floatingButtonBack.setOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        addTextChangeListener()


        binding.resend.setOnClickListener {
            resendCode()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.verify.setOnClickListener {v->
            val typecode=binding.inputCode1.text.toString()+binding.inputCode2.text.toString()+binding.inputCode3.text.toString()+binding.inputCode4.text.toString()+binding.inputCode5.text.toString()
            binding.progressBar.visibility=View.VISIBLE
            if(typecode.isNotEmpty()){
                if(typecode.length==5){
                    //

                     val userId=args.userId.toInt()
                    viewModel.confirmEmailForget(args.userId.toInt(),typecode)


                    Log.e("forgetpassword", "onViewCreated: ${args.userId.toInt()}"+typecode )
                    viewModel.code.observe(viewLifecycleOwner, Observer {result->
                        Log.e("codeforget", "onViewCreated: ${result.toString()}" )
                        kotlin.runCatching {
                            if (result != null) {
                                if (result.success==true) {

                                  val action=ForgetConfirmFragmentDirections.actionForgetConfirmFragmentToNewPassword(args.userId)
                                    Navigation.findNavController(v).navigate(action)
                                    binding.progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(
                                        requireContext(),
                                        result.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewModel.deleteObserv()
                                } else {
                                    binding.progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(
                                        requireContext(),
                                        "${result.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewModel.deleteObserv()
                                }

                            }
                        }
                    })
                }
                else{
                    binding.progressBar.visibility=View.INVISIBLE
                    Toast.makeText(requireActivity(),"Please Enter Correct The code", Toast.LENGTH_SHORT).show()
                }
            }else{
                binding.progressBar.visibility=View.INVISIBLE
                Toast.makeText(requireActivity(),"Please Enter The code", Toast.LENGTH_SHORT).show()
            }

        }
        binding.textDecoration.text="Please Enter The 5 Digit Code Sent To ${args.email}"


    }

    //للانتقال التلقائي بين حقول الادخال
    private fun addTextChangeListener(){
        binding.inputCode1.addTextChangedListener(EditTextWatcher(binding.inputCode1))
        binding.inputCode2.addTextChangedListener(EditTextWatcher(binding.inputCode2))
        binding.inputCode3.addTextChangedListener(EditTextWatcher(binding.inputCode3))
        binding.inputCode4.addTextChangedListener(EditTextWatcher(binding.inputCode4))
        binding.inputCode5.addTextChangedListener(EditTextWatcher(binding.inputCode5))
    }
    inner class EditTextWatcher(private val view: View): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val text=s.toString()
            when(view.id){
                R.id.input_code1-> if(text.length==1)binding.inputCode2.requestFocus()
                R.id.input_code2-> if(text.length==1)binding.inputCode3.requestFocus()
                else if (text.isEmpty()) binding.inputCode1.requestFocus()
                R.id.input_code3-> if(text.length==1)binding.inputCode4.requestFocus()
                else if (text.isEmpty()) binding.inputCode2.requestFocus()
                R.id.input_code4-> if(text.length==1)binding.inputCode5.requestFocus()
                else if (text.isEmpty()) binding.inputCode3.requestFocus()
                R.id.input_code5-> if (text.isEmpty()) binding.inputCode4.requestFocus()
            }
        }

    }

    // اعادة ارسال الرمز
    private fun resendCode(){

        binding.inputCode1.setText("")
        binding.inputCode2.setText("")
        binding.inputCode3.setText("")
        binding.inputCode4.setText("")
        binding.inputCode5.setText("")
        binding.inputCode1.requestFocus()

        binding.resend.isEnabled=false
        binding.resend.setTextColor(ContextCompat.getColor(requireActivity(),R.color.colocr_text_skip))
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.resend.isEnabled=true
            binding.resend.setTextColor(ContextCompat.getColor(requireActivity(),R.color.red))
        },60000)

    }

}