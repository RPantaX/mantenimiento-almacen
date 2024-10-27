package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnInventory = findViewById<Button>(R.id.btnInventory)
        val btnSales = findViewById<Button>(R.id.btnSales)
        val btnUsers = findViewById<Button>(R.id.btnUsers)
        val btnProviders = findViewById<Button>(R.id.btnProviders)

        btnInventory.setOnClickListener {
            startActivity(Intent(this, InventoryActivity::class.java))
        }

        btnSales.setOnClickListener {
            startActivity(Intent(this, SalesActivity::class.java))
        }

        btnUsers.setOnClickListener {
            startActivity(Intent(this, UserRoom::class.java))
        }

        btnProviders.setOnClickListener {
            startActivity(Intent(this, ProvidersActivity::class.java))
        }
}
}