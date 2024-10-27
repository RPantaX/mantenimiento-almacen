package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ItemUserBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.User

class UserAdapter(var lstUser: List<User>, private val actionUpdate: (user: User) -> Unit, private val actionDelete: (user: User) -> Unit): RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder>() {
    class UserAdapterViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapterViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  UserAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lstUser.size
    }

    override fun onBindViewHolder(holder: UserAdapterViewHolder, position: Int) {
        val user = lstUser[position]
        holder.binding.txtUserName.text = user.name
        holder.binding.txtUserApellido.text = user.apellido
        holder.binding.textUserAnio.text= user.anio.toString()
        holder.binding.btnUserDelete.setOnClickListener {
            actionDelete(user)
        }
        holder.binding.btnUserEdit.setOnClickListener {
            actionUpdate(user)
        }
    }

    fun updateUsers(newList: List<User>){
        lstUser = newList
        notifyDataSetChanged() //Notifica al adapter los cambios
    }

}