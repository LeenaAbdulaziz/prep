package com.example.prep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteCelebrity : AppCompatActivity() {
    private lateinit var edname: EditText
    private lateinit var edtaboo1: EditText
    private lateinit var edtaboo2: EditText
    private lateinit var edtaboo3: EditText

    private lateinit var btDelete: Button
    private lateinit var btUpdate: Button
    private lateinit var btBack: Button

    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }

    private var celebrityID = 0

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_celebrity)

       celebrityID = intent.extras!!.getInt("celebrityID")

        edname = findViewById(R.id.ednameU)
        edtaboo1 = findViewById(R.id.edtaboo1U)
        edtaboo2 = findViewById(R.id.edtaboo2U)
        edtaboo3 = findViewById(R.id.edtaboo3U)

        btDelete = findViewById(R.id.btndelete)
        btDelete.setOnClickListener {
            // Make sure to add a confirmation alert here
            deleteCelebrity()
        }
        btUpdate = findViewById(R.id.btnupdate)
        btUpdate.setOnClickListener {
            if(edname.text.isNotEmpty() && edtaboo1.text.isNotEmpty() &&
                edtaboo2.text.isNotEmpty() && edtaboo3.text.isNotEmpty()){
                updateCelebrity()
            }else{
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
        }
        btBack = findViewById(R.id.btnmain)
        btBack.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        getCelebrity()
    }
    private fun getCelebrity(){
        progressDialog.show()

        apiInterface.getCelebrity(celebrityID).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                progressDialog.dismiss()
                val celebrity = response.body()!!
                edname.setText(celebrity.name)
                edtaboo1.setText(celebrity.taboo1)
                edtaboo2.setText(celebrity.taboo2)
                edtaboo3.setText(celebrity.taboo3)
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteCelebrity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateCelebrity(){
        progressDialog.show()
//var f= Celebrity(edname.text.toString(), edtaboo1.text.toString(), edtaboo2.text.toString(), edtaboo3.text.toString(), celebrityID)
        apiInterface.updatecelebrity(celebrityID,
            Celebrity(edname.text.toString(),
                edtaboo1.text.toString(),
                edtaboo2.text.toString(),
                edtaboo3.text.toString(),
                celebrityID)
            ).enqueue(object: Callback<Celebrity> {
            override fun onResponse(
                call: Call<Celebrity>,
                response: Response<Celebrity>) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteCelebrity, "Celebrity Updated", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteCelebrity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteCelebrity(){
        progressDialog.show()

        apiInterface.deletecelebrity(celebrityID).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteCelebrity, "Celebrity Deleted", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteCelebrity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
}