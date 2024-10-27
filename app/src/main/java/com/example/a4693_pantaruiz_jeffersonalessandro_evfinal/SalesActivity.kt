package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ActivitySalesBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Inventory
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Sales
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SalesActivity : AppCompatActivity() {
    private lateinit var database: UserDatabase
    private lateinit var dao: SalesDAO
    private var data: Sales?=null
    private lateinit var binding : ActivitySalesBinding
    private lateinit var adapter: SalesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database=UserDatabase.getDatabase(this)
        dao = database.salesDao()
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = SalesAdapter(emptyList(), { select ->
            binding.etPrice.setText(select.price.toString())
            binding.etDNICustomer.setText(select.dniCustomer)
            binding.etProducts.setText(select.products)
            binding.etQuantity.setText(select.quantity)
            binding.etDescription.setText(select.description)
            data = select
        }) {
            deleteSales(it)
        }
        binding.rv.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val price = binding.etPrice.text.toString().trim()
            val dniCustomer = binding.etDNICustomer.text.toString().trim()
            val products = binding.etProducts.text.toString().trim()
            val quantity = binding.etQuantity.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            // Validación de campos vacíos o nulos
            if (price.isEmpty() || dniCustomer.isEmpty() || products.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener  // Salir del método si la validación falla
            }
            if(data != null){
                data.let {
                    it?.price = binding.etPrice.text.toString().toDouble()
                    it?.dniCustomer = binding.etDNICustomer.text.toString()
                    it?.products = binding.etProducts.text.toString()
                    it?.quantity = binding.etQuantity.text.toString().toInt()
                    it?.description = binding.etDescription.text.toString()
                }
                updateSales(data)
            }else{
                val salesTest = Sales(
                    price = binding.etPrice.text.toString().toDouble(),
                    products = binding.etProducts.text.toString(),
                    dniCustomer = binding.etDNICustomer.text.toString(),
                    quantity = binding.etQuantity.text.toString().toInt(),
                    description = binding.etDescription.text.toString())
                createSales(salesTest)
            }
        }

        updateDate()
        // Acción al hacer clic en el botón de Logout
        binding.btnLogOut.setOnClickListener {
            UtilsSharedPreferences.logoutSesion(this)  // Cerramos la sesión
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))  // Redirigir al inicio de sesión
        }
    }
    fun createSales(sales: Sales){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(sales)
            updateDate()
        }
    }
    fun updateSales(sales: Sales?){
        CoroutineScope(Dispatchers.IO).launch {
            sales?.let{
                dao.update(it)
            }
            updateDate()
            data = null
        }
    }
    fun updateDate(){
        CoroutineScope(Dispatchers.IO).launch {
            val sales = dao.getAllSales()
            withContext(Dispatchers.Main){
                adapter.updateSales(sales)
            }
        }
    }
    fun deleteSales(sales: Sales){
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(sales)
            updateDate()
        }
    }
}