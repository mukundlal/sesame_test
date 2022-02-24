package com.cmlabs.sesame_test.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.cmlabs.sesame_test.R
import com.cmlabs.sesame_test.common.hide
import com.cmlabs.sesame_test.common.show
import com.cmlabs.sesame_test.data.CustomerAddResponse
import com.cmlabs.sesame_test.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_add_customer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCustomerActivity : AppCompatActivity(),LocationListener {

    var lat:Double=28.644800
    var lng:Double=77.216721
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        addProgress.hide()
        latlang.text="latitude : $lat\nlongitude : $lng"
        btnAddCustomer.setOnClickListener {
            if (lat!=0.0&&lng!=0.0)
            addCustomer()
            else
           Toast.makeText(this,"Location not updated yet",Toast.LENGTH_SHORT).show()
        }

        btnGetLocation.setOnClickListener {
            getCurrentLocationGPS()


        }

    }




    private fun getCurrentLocationGPS() {
        val locationManager =getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                1000
            )

        }else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0.0f , this)

        }

    }

    override fun onResume() {
        super.onResume()
        getCurrentLocationGPS()
    }

    private fun addCustomer() {
        addProgress.show()
        btnAddCustomer.hide()

        try {
            RetrofitClient.apiInterface.addCustomer(cusName = etCustomerName.text.toString(),
            cusAdress = etAddress.text.toString(),
            phoneNo = etMobile.text.toString(),
            lat = "$lat",
            lng = "$lng")
                .enqueue(object : Callback<CustomerAddResponse>{
                    override fun onResponse(
                        call: Call<CustomerAddResponse>,
                        response: Response<CustomerAddResponse>
                    ) {
                        addProgress.hide()
                        val resp=response.body()
                       resp?.let {
                           if (it.status)
                           {
                               Toast.makeText(this@AddCustomerActivity,"Customer added",Toast.LENGTH_SHORT).show()
                               this@AddCustomerActivity.finish()
                           }else
                           {
                               btnAddCustomer.show()
                               Toast.makeText(this@AddCustomerActivity,it.message,Toast.LENGTH_SHORT).show()
                           }
                       }
                    }

                    override fun onFailure(call: Call<CustomerAddResponse>, t: Throwable) {
                        addProgress.hide()
                        btnAddCustomer.show()
                        Toast.makeText(this@AddCustomerActivity,"Server error",Toast.LENGTH_SHORT).show()
                    }


                })
        }catch (e:Exception)
        {
            //Exception
        }
    }

    override fun onLocationChanged(p0: Location) {
       try {

           lat=p0.latitude
           lng=p0.longitude
           latlang.text="latitude : $lat\nlongitude : $lng"


       }catch (
           e:java.lang.Exception
       )
       {}

    }
}