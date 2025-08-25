package com.example.mytestnav.splash_and_viewpager

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytestnav.R
import com.example.mytestnav.home.refreshtoken.ViewModelToken


class SplashFragment : Fragment() {
    private val viewModel: ViewModelToken by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onResume() {
        super.onResume()
     //   viewModel.token.observe(viewLifecycleOwner, Observer { result->
//            if(result==null){
//                val sharedPref =requireActivity().getSharedPreferences("onRefresh", Context.MODE_PRIVATE)
//                val name=sharedPref.getString("Refresh","")
//                Log.i("nameToken", "$name")
//                viewModel.updateToken(name!!)
//            }else{
//                saveRefreshToken(result.data?.refreshtoken!!)
//                saveToken(result.data.token!!)
//
//            }
//        })


        Handler().postDelayed({
            if(onBordarFished()){
                if(onLoginFinished())
                {
                    findNavController().navigate(R.id.action_splashFragment_to_homeActivity)

                    requireActivity().finish()
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_welcoamFragment)

                }
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewBagirFragment)
            }



        },3000)
    }

    private fun onBordarFished():Boolean{
        val sharedPref=requireActivity().getSharedPreferences("onBording",Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
    private fun onLoginFinished():Boolean{
        val sharedPref=requireActivity().getSharedPreferences("onLogin",Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Login",false)
    }
    private fun saveRefreshToken(token:String){
        val sharedPref =requireActivity().getSharedPreferences("onRefresh", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("Refresh",token)
        editor.apply()

    }
    private fun saveToken(token:String?){
        val sharedPref =requireActivity().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("Token",token)
        editor.apply()
    }
}