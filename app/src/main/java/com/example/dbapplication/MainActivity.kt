package com.example.dbapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.dbapplication.DAO.UserDao
import com.example.dbapplication.Database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), UserAdapter.OnDeleteClickListener {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var recyclerView: RecyclerView

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fabAddUser = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fabAddUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            intent.putExtra("databaseName", "my-database")
            startActivity(intent)
        }

        executor.execute{
            db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "my-database"
            ).build()

            userDao = db.userDao()

            val userList = userDao.getAll()

            for (user in userList) {
                Log.d("USER", "User: $user")
            }

            runOnUiThread {
                val userAdapter = UserAdapter(userList, userDao)
                userAdapter.setOnDeleteClickListener(object : UserAdapter.OnDeleteClickListener {
                    override fun onDeleteClick(id: Int) {
                        executor.execute {
                            userDao.deleteUserById(id)
                            val updatedUserList = userDao.getAll()
                            runOnUiThread {
                                userAdapter.updateUsers(updatedUserList)
                            }
                        }
                    }

                })
                recyclerView.adapter = userAdapter
            }
        }
    }

    override fun onDeleteClick(id: Int) {
    }
}