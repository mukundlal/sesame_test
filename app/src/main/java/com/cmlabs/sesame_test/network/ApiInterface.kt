package com.cmlabs.sesame_test.network

import com.cmlabs.sesame_test.data.CustomerAddResponse
import com.cmlabs.sesame_test.data.GetCustomerResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("customer/save-details/")
    fun addCustomer(@Field("custName") cusName:String,
                  @Field("custAddress") cusAdress:String,
                  @Field("phoneNo") phoneNo:String,
                  @Field("latitude") lat:String,
                  @Field("longitude") lng:String):Call<CustomerAddResponse>

    @GET("customer/get-details/")
    fun getCustomer():Call<GetCustomerResponse>
}