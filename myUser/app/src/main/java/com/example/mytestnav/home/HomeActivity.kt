package com.example.mytestnav.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onResume() {
        super.onResume()

        val nav= findNavController(R.id.fragmentrView)
        binding.btnNav.setupWithNavController(nav)
        binding.btnNav.isItemActiveIndicatorEnabled=false
        nav.addOnDestinationChangedListener{_,destination,_->

            when(destination.id){
                R.id.theHomeFragment ->{ binding.btnNav.visibility=View.VISIBLE
                    supportActionBar?.hide()}
                R.id.myBook2 ->{

                    binding.btnNav.visibility=View.VISIBLE
                    supportActionBar?.hide()}
                R.id.profile2 ->{ binding.btnNav.visibility=View.VISIBLE
                    supportActionBar?.hide()}
                else->{binding.btnNav.visibility=View.GONE
                    supportActionBar?.hide() }
            }

        }


       }

    }


//        binding.btnNav.visibility= View.VISIBLE
//        nav.addOnDestinationChangedListener{ _, destinayion, _->
//            when(destinayion.id){
//                R.id.homeFragment-> {
//                    binding.btnNav.visibility= View.VISIBLE
//                }
//                R.id.myBook->{
//                    binding.btnNav.visibility= View.VISIBLE
//                }
//                R.id.profile->{
//                    binding.btnNav.visibility= View.VISIBLE
//                }
//                else->{
//                    binding.btnNav.visibility= View.GONE
//                }
//            }
//
//        }