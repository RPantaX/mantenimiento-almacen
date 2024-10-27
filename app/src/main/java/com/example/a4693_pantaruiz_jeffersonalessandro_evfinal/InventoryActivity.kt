package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ActivityInventoryBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Inventory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InventoryActivity : AppCompatActivity() {
    private lateinit var database: UserDatabase
    private lateinit var dao: InventoryDAO
    private var data: Inventory? = null
    private  lateinit var binding: ActivityInventoryBinding
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database= UserDatabase.getDatabase(this)
        dao = database.inventoryDao()
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = InventoryAdapter(emptyList(), { select ->
            binding.etNombreProducto.setText(select.productName)
            binding.etPrecio.setText(select.price.toString())
            binding.etCantidad.setText(select.quantity.toString())
            binding.etDescripcion.setText(select.description.toString())
            data = select
        }) {
            deleteInventory(it)
        }
        binding.rv.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val precio = binding.etPrecio.text.toString().trim()
            val cantidad = binding.etCantidad.text.toString().trim()
            val descripcion = binding.etDescripcion.text.toString().trim()
            val nombreProducto = binding.etNombreProducto.text.toString().trim()

            // Validación de campos vacíos o nulos
            if (precio.isEmpty() || cantidad.isEmpty() || descripcion.isEmpty() || nombreProducto.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener  // Salir del método si la validación falla
            }
            if(data != null){
                data.let {
                    it?.price = binding.etPrecio.text.toString().toDouble()
                    it?.productName = binding.etNombreProducto.text.toString()
                    it?.quantity = binding.etCantidad.text.toString().toInt()
                    it?.description = binding.etDescripcion.text.toString()
                }
                updateInventory(data)
            }else{
                val inventoryTest = Inventory(price = binding.etPrecio.text.toString().toDouble(),
                    productName = binding.etNombreProducto.text.toString(),
                    quantity = binding.etCantidad.text.toString().toInt(),
                    description = binding.etDescripcion.text.toString())
                createInventory(inventoryTest)
            }
        }

        updateDate()
        // Acción al hacer clic en el botón de Logout
        binding.btnLogOut.setOnClickListener {
            UtilsSharedPreferences.logoutSesion(this)  // Cerramos la sesión
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))  // Redirigir al inicio de sesión
        }
    }
    fun createInventory(inventory: Inventory){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(inventory)
            updateDate()
        }
    }
    fun updateInventory(inventory: Inventory?){
        CoroutineScope(Dispatchers.IO).launch {
            inventory?.let{
                dao.update(it)
            }
            updateDate()
            data = null
        }
    }
    fun updateDate(){
        CoroutineScope(Dispatchers.IO).launch {
            val inventory = dao.getAllInventory()
            withContext(Dispatchers.Main){
                adapter.updateInventory(inventory)
            }
        }
    }
    fun deleteInventory(inventory: Inventory){
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(inventory)
            updateDate()
        }
    }
}