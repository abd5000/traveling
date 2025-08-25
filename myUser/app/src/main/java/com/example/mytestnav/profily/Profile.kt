package com.example.mytestnav.profily

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mytestnav.R
import com.example.mytestnav.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Profile : Fragment() {
    lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener {
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
            description.text="Do you want to logout?"
            description.textSize= 20F
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            okButton.setOnClickListener {
                val sharedPref=requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
                val editor=sharedPref.edit()
                editor.putBoolean("Login",false)
                editor.apply()
                dialog.dismiss()
                Toast.makeText(requireContext(),"Logout successfully",Toast.LENGTH_SHORT).show()
                val action=ProfileDirections.actionProfile2ToNavLogin()
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.notification.setOnClickListener { v->
            val action=ProfileDirections.actionProfile2ToNotificationActivity()
            Navigation.findNavController(v).navigate(action)
        }
    }

}