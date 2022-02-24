package com.cmlabs.sesame_test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmlabs.sesame_test.R
import com.cmlabs.sesame_test.common.OnCardClick
import com.cmlabs.sesame_test.data.Cust
import kotlinx.android.synthetic.main.customer_item.view.*

class CustomerListAdapter(val callBack:OnCardClick):RecyclerView.Adapter<CustomerListAdapter.VieHolder>() {
     var dataList=ArrayList<Cust>()
    class VieHolder(view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        return VieHolder(LayoutInflater.from(parent.context).inflate(R.layout.customer_item,parent,false))
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.itemView?.let {
            val data=dataList[position]
            it.cusName.text=data.custName
            it.cusNumber.text=data.phoneNo
            it.setOnClickListener {
                callBack.onCardClicked(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun setData(data:List<Cust>)
    {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}