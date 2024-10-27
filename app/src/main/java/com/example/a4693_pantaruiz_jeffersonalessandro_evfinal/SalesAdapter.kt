package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ItemSalesBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Sales

class SalesAdapter(var lstSales: List<Sales>, private val actionUpdate: (sales:Sales) -> Unit, private val actionDelete: (sales:Sales) -> Unit): RecyclerView.Adapter<SalesAdapter.SalesAdapteriewHolder>() {
    class SalesAdapteriewHolder (val binding:ItemSalesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalesAdapteriewHolder {
        val binding = ItemSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SalesAdapteriewHolder(binding)
    }
    override fun getItemCount(): Int {
        return lstSales.size
    }
    override fun onBindViewHolder(holder: SalesAdapteriewHolder, position: Int) {
        val sales = lstSales[position]
        holder.binding.txtPrice.text = sales.price.toString()
        holder.binding.txtProviderDNI.text=sales.dniCustomer
        holder.binding.txtProducts.text=sales.products
        holder.binding.txtQuantity.text=sales.quantity.toString()
        holder.binding.txtDescription.text=sales.description
        holder.binding.btnUserDelete.setOnClickListener {
            actionDelete(sales)
        }
        holder.binding.btnSalesEdit.setOnClickListener {
            actionDelete(sales)
        }
    }

   fun updateSales(newList: List<Sales>){
       lstSales=newList
       notifyDataSetChanged()
   }

}