package com.example.mytestnav.login.loginInformation

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
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentLoginBinding

class Login : Fragment() {
lateinit var binding:FragmentLoginBinding
    val viewModel:ViewModelLogin by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmentLoginBinding.inflate(inflater,container,false)

        // زر الرجوع
        binding.floatingButtonBack.setOnClickListener { v->
            Navigation.findNavController(v).popBackStack()
        }
        // زر sign up
        binding.signUp.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_login_to_signup)
        }
        // زر الباسوور المنسية
        binding.forgetPassword.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_login_to_forgetThePassword)
        }
        //عند الكتبة على الحقل اخفي الخطأ
        binding.emial.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputEmail.error = null
            }
        }
        binding.password.addTextChangedListener { text ->
            if (text!!.isNotBlank()) {
                binding.textinputPassword.error = null
            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //login button
        binding.login.setOnClickListener { v->

            val email=binding.emial.text.toString().trim()
            val password=binding.password.text.toString().trim()
            // اذا كان الايميل فارغ و كان لا يحوي علي الايميل اظهر خطأ
            if (checkTheEditText(email,password)) {
                binding.progressBar.visibility=View.VISIBLE
                viewModel.login(binding.emial.text.toString().trim(), binding.password.text.toString().trim())
                kotlin.runCatching {
                    viewModel.user.observe(requireActivity(), Observer { user ->
                        if (user!=null) {

                            //  Log.e("error404", "onCreateView: ${user?.code()}", )

                            if (user.success == true) {

                                // لا تعرض صفحفة تسجيل الدخول مرة اخري اذا تم تسجيل الدخول بنجاح
                                onLoginFinished()
                                //حفظ ال token
                                saveToken(user.data?.token!!)
                                saveUserId(user.data.result?.id!!)
                                saveName(user.data.result.fullname!!)
                                saveRefreshToken(user.data.refreshtoken!!)
                                Toast.makeText(
                                    requireContext(),
                                    user.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                //انقل للواحهات الرئيسية
                                binding.progressBar.visibility = View.INVISIBLE
                                Navigation.findNavController(v)
                                    .navigate(R.id.action_login_to_homeActivity)
                                requireActivity().finish()
                            } else {
                                // Login failed
                                binding.progressBar.visibility = View.INVISIBLE
                                Log.e("erorrrmaseg", " ${user.message}",)
                                Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                                viewModel.deleteObserv()

                            }


                        }
                    })
                }

            }
        }
    }
    //
    private fun onLoginFinished(){
        val sharedPref =requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean("Login",true)
        editor.apply()
    }
    //
    private fun saveToken(token:String){
        val sharedPref =requireActivity().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("Token",token)
        editor.apply()

    }
    private fun saveRefreshToken(token:String){
        val sharedPref =requireActivity().getSharedPreferences("onRefresh", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("Refresh",token)
        editor.apply()

    }
    private fun saveUserId(id:Int){
        val sharedPrefId =requireActivity().getSharedPreferences("onUserId", Context.MODE_PRIVATE)
        val editorId=sharedPrefId.edit()
        editorId.putInt("UserId",id)
        editorId.apply()
    }
    private fun saveName(fullName:String){
        val sharedPref =requireActivity().getSharedPreferences("onName", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("FullName",fullName)
        editor.apply()}
    //
    private fun checkTheEditText(email: String,password: String):Boolean{
        //check email
        binding.textinputEmail.error=null
        binding.textinputPassword.error=null
        var emailcheck=true
        var passwordcheck=true
        if( email.isEmpty()){
            binding.textinputEmail.error="This filed must not be empty"
            emailcheck=false
            return emailcheck
        }
        else if(!email.contains("@gmail.com")){
            binding.textinputEmail.error="Enter the email correctly"
            emailcheck=false
            return emailcheck
        }

        //check password
        if( password.isEmpty()){
            binding.textinputPassword.error="This filed must not be empty"
             passwordcheck=false
            return passwordcheck
        }

        return passwordcheck&&emailcheck
    }
}