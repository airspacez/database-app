package com.example.dbapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dbapplication.DAO.UserDao
import com.example.dbapplication.Database.AppDatabase
import com.example.dbapplication.Entity.User
import java.util.concurrent.Executors

class AddUserActivity : AppCompatActivity() {

    private lateinit var dao: UserDao
    private lateinit var databaseName: String

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val buttonSave = findViewById<Button>(R.id.buttonSave)

        databaseName = intent.getStringExtra("databaseName")!!
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            databaseName
        ).build()

        buttonSave.setOnClickListener {
            val firstname = findViewById<EditText>(R.id.editTextFirstName).text.toString()
            val secondName = findViewById<EditText>(R.id.editTextSecondName).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()

            dao = db.userDao()
            val user = User(0, firstname, secondName, email)

            executor.execute {
                dao.insertAll(user)
                finish()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}