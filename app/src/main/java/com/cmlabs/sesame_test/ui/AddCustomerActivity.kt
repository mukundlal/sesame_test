package com.cmlabs.sesame_test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cmlabs.sesame_test.R
import com.cmlabs.sesame_test.common.hide
import com.cmlabs.sesame_test.common.show
import com.cmlabs.sesame_test.data.CustomerAddResponse
import com.cmlabs.sesame_test.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_add_customer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCustomerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        addProgress.hide()

        btnAddCustomer.setOnClickListener {
            addCustomer()
        }

    }

    private fun addCustomer() {
        addProgress.show()
        btnAddCustomer.hide()

        try {
            RetrofitClient.apiInterface.addCustomer(cusName = etCustomerName.text.toString(),
            cusAdress = etAddress.text.toString(),
            phoneNo = etMobile.text.toString(),
            lat = "15.0",
            lng = "17.8")
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
}