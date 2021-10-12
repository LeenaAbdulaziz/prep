package com.example.prep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var rec_View: RecyclerView
    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }

    private lateinit var progressDialog: ProgressDialog
    private lateinit var btAdd: Button
    private lateinit var etCelebrity: EditText
    private lateinit var btsubmit: Button
    var nameList=ArrayList<Celebrity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameList= arrayListOf()
        rec_View=findViewById(R.id.recyclerView)
        btAdd=findViewById(R.id.btnadd)
        etCelebrity = findViewById(R.id.edsearch)
        btsubmit = findViewById(R.id.btnsearch)
        rec_View.adapter=RVAdapter(nameList)
        rec_View.layoutManager= LinearLayoutManager(this)

        btAdd.setOnClickListener {
            intent = Intent(applicationContext, NewCelerbrity::class.java)
            val celebrityNames = arrayListOf<String>()
            for(c in nameList){
                celebrityNames.add(c.name.lowercase())
            }
            intent.putExtra("celebrityNames", celebrityNames)
            startActivity(intent)
        }
        etCelebrity = findViewById(R.id.edsearch)

        btsubmit.setOnClickListener {
            if(etCelebrity.text.isNotEmpty()){
                updateCelebrity()
            }else{
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
            }
        }
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.show()

        getCelebrities()
    }

    private fun getCelebrities(){
        apiInterface.getCelebrities().enqueue(object: Callback<ArrayList<Celebrity>> {
            override fun onResponse(
                call: Call<ArrayList<Celebrity>>,
                response: Response<ArrayList<Celebrity>>
            ) {

                for(name in response.body()!!) {
                    nameList.add(name)
                    Log.d("responseName","$name")
                }

                rec_View.adapter?.notifyDataSetChanged()
           progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ArrayList<Celebrity>>, t: Throwable) {
              progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Unable to get data", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun updateCelebrity(){
        var celebrityID = 0
        for(celebrity in nameList){

            if(etCelebrity.text.toString().lowercase() == celebrity.name.lowercase()){
                Toast.makeText(this, "Celebrity is found", Toast.LENGTH_LONG).show()
                Log.d("UpdateName","${celebrity.name} ${celebrity.pk}")

                celebrityID = celebrity.pk
                intent = Intent(applicationContext, UpdateDeleteCelebrity::class.java)
                intent.putExtra("celebrityID", celebrityID)
                startActivity(intent)

                break


            }
        }

        if(celebrityID==0){
            Toast.makeText(this, "${etCelebrity.text.toString().capitalize()} not found", Toast.LENGTH_LONG).show()

        }

    }



    }
