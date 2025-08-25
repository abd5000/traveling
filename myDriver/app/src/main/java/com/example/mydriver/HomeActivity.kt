package com.example.mydriver

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mydriver.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        val nav= findNavController(R.id.fragmentview)
        binding.btnNav.setupWithNavController(nav)
        binding.btnNav.isItemActiveIndicatorEnabled=false
        nav.addOnDestinationChangedListener{_,destination,_->

            when(destination.id){
                R.id.homeFragment ->{ binding.btnNav.visibility= View.VISIBLE
               }
                R.id.settingFragment ->{
                    binding.btnNav.visibility= View.VISIBLE
                    supportActionBar?.hide()}
//                R.id.profile2 ->{ binding.btnNav.visibility= View.VISIBLE
//                    supportActionBar?.hide()}
                else->{binding.btnNav.visibility= View.GONE
                   }
            }

        }
    }
}