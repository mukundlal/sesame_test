package com.cmlabs.sesame_test.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cmlabs.sesame_test.R
import com.cmlabs.sesame_test.adapters.CustomerListAdapter
import com.cmlabs.sesame_test.common.OnCardClick
import com.cmlabs.sesame_test.common.hide
import com.cmlabs.sesame_test.common.show
import com.cmlabs.sesame_test.data.Cust
import com.cmlabs.sesame_test.data.GetCustomerResponse
import com.cmlabs.sesame_test.network.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_customer_list.*
import kotlinx.android.synthetic.main.customer_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomerList : AppCompatActivity(),OnCardClick {
    lateinit var customerListAdapter: CustomerListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)
        cusProgress.hide()
        //initAdapter

        customerListAdapter= CustomerListAdapter(this)
        cusList.adapter=customerListAdapter


        //call Api
        getCustomerList()


    }

    private fun getCustomerList() {
            cusProgress.show()
        try {
            RetrofitClient.apiInterface.getCustomer()
                .enqueue(object : Callback<GetCustomerResponse>{
                    override fun onResponse(
                        call: Call<GetCustomerResponse>,
                        response: Response<GetCustomerResponse>
                    ) {

                        val resp=response.body()
                        cusProgress.hide()
                        resp?.let {

                            if (resp.status)
                            customerListAdapter.setData(data = it.cust_list)
                            else
                                Toast.makeText(this@CustomerList,"No customers available",Toast.LENGTH_SHORT).show()


                        }

                    }

                    override fun onFailure(call: Call<GetCustomerResponse>, t: Throwable) {
                        cusProgress.hide()
                    }

                })
        }
        catch (e:Exception)
        {

        }

    }

    override fun onCardClicked(data: Cust) {

        val dialog=BottomSheetDialog(this)
            dialog.setContentView(R.layout.customer_details)
        dialog.custName.text=data.custName
        dialog.custMobile.text=data.phoneNo
        dialog.custAddress.text=data.custAddress
        dialog.btnMap.setOnClickListener {

            val i = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?q=loc:" + data.latitude + "," + data.longitude)
            )
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Only if initiating from a Broadcast Receiver


            if (isPackageExisted()) {//Checking if map app exists
                i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
                i.setPackage("com.google.android.apps.maps")
            }
            startActivity(i)
        }
        dialog.show()

    }

    private fun isPackageExisted( ): Boolean {
        val pm: PackageManager = this.packageManager
        try {
            val info = pm.getPackageInfo("com.google.android.apps.maps", PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
        return true
    }

}