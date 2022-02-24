package com.cmlabs.sesame_test.data

data class GetCustomerResponse(
    val cust_list: List<Cust>,
    val status: Boolean
)