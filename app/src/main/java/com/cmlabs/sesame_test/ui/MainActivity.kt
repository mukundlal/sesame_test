package com.cmlabs.sesame_test.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cmlabs.sesame_test.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAddCustomer.setOnClickListener{
            startActivity(Intent(this,AddCustomerActivity::class.java))

        }
        btnGetCustomers.setOnClickListener{
            startActivity(Intent(this,CustomerList::class.java))

        }
    }
}