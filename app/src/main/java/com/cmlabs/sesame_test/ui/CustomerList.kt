package com.cmlabs.sesame_test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cmlabs.sesame_test.R
import com.cmlabs.sesame_test.adapters.CustomerListAdapter
import com.cmlabs.sesame_test.common.OnCardClick
import com.cmlabs.sesame_test.common.hide
import com.cmlabs.sesame_test.common.show
import com.cmlabs.sesame_test.data.Cust
import com.cmlabs.sesame_test.data.GetCustomerResponse
import com.cmlabs.sesame_test.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_customer_list.*
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

    }

}