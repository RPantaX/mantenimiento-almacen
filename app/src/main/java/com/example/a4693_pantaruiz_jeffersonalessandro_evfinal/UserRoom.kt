package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.databinding.ActivityUserRoomBinding
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRoom : AppCompatActivity() {
    private lateinit var database: UserDatabase
    private lateinit var userDao: UserDAO
    private var userData: User? = null
    private  lateinit var binding: ActivityUserRoomBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database = UserDatabase.getDatabase(this)
        userDao = database.userDao()
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList(), { userSelect ->
            binding.etUserName.setText(userSelect.name)
            binding.etUserApellido.setText(userSelect.apellido)
            userData = userSelect
        }) {
            deleteUser(it)
        }

        binding.rv.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val name = binding.etUserName.text.toString().trim()
            val apellido = binding.etUserApellido.text.toString().trim()

            // Validación de campos vacíos o nulos
            if (name.isEmpty() || apellido.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this, "El nombre y el apellido no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener  // Salir del método si la validación falla
            }
            if(userData != null){
                userData.let {
                    it?.name = binding.etUserName.text.toString()
                    it?.apellido = binding.etUserApellido.text.toString()
                }
                updateUser(userData)
            }else{
                val userTest = User(name = binding.etUserName.text.toString(), apellido = binding.etUserApellido.text.toString(), anio = 2020)
                createUser(userTest)
            }
        }

        updateDate()
        // Acción al hacer clic en el botón de Logout
        binding.btnLogOut.setOnClickListener {
            UtilsSharedPreferences.logoutSesion(this)  // Cerramos la sesión
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))  // Redirigir al inicio de sesión
        }
    }
    fun createUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insert(user)
            updateDate()
        }
    }
    fun updateUser(user: User?){
        CoroutineScope(Dispatchers.IO).launch {
            user?.let{
                userDao.update(it)
            }
            updateDate()
            userData = null
        }
    }

    fun updateDate(){
        CoroutineScope(Dispatchers.IO).launch {
            val users = userDao.getAllUser()
            withContext(Dispatchers.Main){
                adapter.updateUsers(users)
            }
        }
    }

    fun deleteUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao.delete(user)
            updateDate()
        }
    }

}