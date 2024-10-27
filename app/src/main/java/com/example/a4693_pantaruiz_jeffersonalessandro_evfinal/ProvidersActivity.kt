package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ActivityProvidersBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProvidersActivity : AppCompatActivity() {
    private lateinit var database : UserDatabase
    private lateinit var dao: ProviderDAO
    private var data: Provider? = null
    private lateinit var binding : ActivityProvidersBinding
    private lateinit var adapter: ProviderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProvidersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database=UserDatabase.getDatabase(this)
        dao = database.providerDao()
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = ProviderAdapter(emptyList(), { select ->
            binding.etProviderName.setText(select.providerName)
            binding.etProviderDNI.setText(select.providerDNI)
            binding.etEmail.setText(select.email)
            binding.etProducts.setText(select.products)
            binding.etDireccion.setText(select.direccion)
            data = select
        }) {
            deleteProviders(it)
        }
        binding.rv.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val providerName = binding.etProviderName.text.toString().trim()
            val providerDNI = binding.etProviderDNI.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val products = binding.etProducts.text.toString().trim()
            val direccion = binding.etDireccion.text.toString().trim()

            // Validación de campos vacíos o nulos
            if (providerName.isEmpty() || providerDNI.isEmpty() || email.isEmpty() || products.isEmpty() || direccion.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener  // Salir del método si la validación falla
            }
            if(data != null){
                data.let {
                    it?.providerName = binding.etProviderName.text.toString()
                    it?.providerDNI = binding.etProviderDNI.text.toString()
                    it?.email = binding.etEmail.text.toString()
                    it?.products = binding.etProducts.text.toString()
                    it?.direccion = binding.etDireccion.text.toString()
                }
                updateProvider(data)
            }else{
                val providerTest = Provider(providerName = binding.etProviderName.text.toString(),
                    providerDNI = binding.etProviderDNI.text.toString(),
                    email = binding.etEmail.text.toString(),
                    products = binding.etProducts.text.toString(),
                    direccion = binding.etDireccion.text.toString())
                createProvider(providerTest)
            }
        }

        updateDate()
        // Acción al hacer clic en el botón de Logout
        binding.btnLogOut.setOnClickListener {
            UtilsSharedPreferences.logoutSesion(this)  // Cerramos la sesión
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))  // Redirigir al inicio de sesión
        }
    }
    fun createProvider(provider: Provider){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(provider)
            updateDate()
        }
    }
    fun updateProvider(provider: Provider?){
        CoroutineScope(Dispatchers.IO).launch {
            provider?.let{
                dao.update(it)
            }
            updateDate()
            data = null
        }
    }
    fun updateDate(){
        CoroutineScope(Dispatchers.IO).launch {
            val providers = dao.getAllProvider()
            withContext(Dispatchers.Main){
                adapter.updateProvider(providers)
            }
        }
    }
    fun deleteProviders(provider: Provider){
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(provider)
            updateDate()
        }
    }
}