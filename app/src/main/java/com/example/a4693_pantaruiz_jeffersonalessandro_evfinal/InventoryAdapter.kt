package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ItemInventoryBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Inventory

class InventoryAdapter(var lstInventory: List<Inventory>, private val actionUpdate: (inventory: Inventory) -> Unit, private val actionDelete: (inventory: Inventory) -> Unit): RecyclerView.Adapter<InventoryAdapter.InventoryAdapterViewHolder>()  {
    class InventoryAdapterViewHolder(val binding: ItemInventoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryAdapterViewHolder {
        val binding = ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InventoryAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lstInventory.size
    }

    override fun onBindViewHolder(holder: InventoryAdapterViewHolder, position: Int) {
        val inventory = lstInventory[position]
        holder.binding.txtCantidad.text=inventory.quantity.toString()
        holder.binding.txtPrecio.text=inventory.price.toString()
        holder.binding.txtDescripcion.text=inventory.description
        holder.binding.txtNombreProducto.text=inventory.productName
        holder.binding.btnUserDelete.setOnClickListener {
            actionDelete(inventory)
        }
        holder.binding.btnInventoryEdit.setOnClickListener {
            actionUpdate(inventory)
        }
    }
    fun updateInventory(newList: List<Inventory>){
        lstInventory=newList
        notifyDataSetChanged()
    }
}