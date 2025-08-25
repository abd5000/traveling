package com.example.myadmin

import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.runBlocking

class MapsFragment2 : Fragment() {

        private lateinit var socket: Socket
        private val args:MapsFragment2Args by navArgs()
        private lateinit var googleMap: GoogleMap
         private var marker: Marker? = null
    val requestPermissionLocation=registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if(isGranted){

        }else{

        }
    }
        private val callback = OnMapReadyCallback { googleMap ->
            this.googleMap=googleMap
            socket = IO.socket("http://192.168.43.77:3000")
            socket.connect()
            //
            socket.emit("adminSelectDriver",args.diverId)
            //

            kotlin.runCatching {
            socket.on("adminCoordinates") { args ->
                Log.i("locationadmin", "$args")
                if (args[0] != null) {
                    val location = Gson().fromJson(args[0].toString(), LocaiontResponce::class.java)
                   val driver = LatLng(location.latitude!!,location.longitude!!)
            Log.i("diverlocation", "$driver")
                    requireActivity().runOnUiThread {
                        val customIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_24)
                    if (marker != null) {
                        marker!!.position = driver
                    } else {

                        marker = googleMap.addMarker(MarkerOptions().position(driver).title("driver").icon(customIcon))

                    }
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(driver))
                        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driver, 15F))
}
//                    requireActivity().runOnUiThread {
//            googleMap.addMarker(MarkerOptions().position(driver).title(""))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(driver))}
                }
            }
            }

        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_maps2, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }

    override fun onPause() {
        super.onPause()
        socket.disconnect()
    }
    }
