package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ItemProvidersBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Provider

class ProviderAdapter(var lstProvider: List<Provider>, private val actionUpdate: (provider: Provider) -> Unit, private val actionDelete: (provider: Provider) -> Unit): RecyclerView.Adapter<ProviderAdapter.ProviderAdapterViewHolder>() {
    class ProviderAdapterViewHolder (val binding: ItemProvidersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderAdapterViewHolder {
        val binding = ItemProvidersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProviderAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return lstProvider.size
    }

    override fun onBindViewHolder(holder: ProviderAdapterViewHolder, position: Int) {
        val provider = lstProvider[position]
        holder.binding.txtProviderDNI.text=provider.providerDNI
        holder.binding.txtProviderName.text=provider.providerName
        holder.binding.txtEmail.text=provider.email
        holder.binding.txtProducts.text=provider.products
        holder.binding.btnUserDelete.setOnClickListener {
            actionDelete(provider)
        }
        holder.binding.btnProvidersEdit.setOnClickListener {
            actionUpdate(provider)
        }
    }
    fun updateProvider(newList: List<Provider>){
        lstProvider=newList
        notifyDataSetChanged()
    }
}