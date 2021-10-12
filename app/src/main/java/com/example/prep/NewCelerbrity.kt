package com.example.prep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewCelerbrity : AppCompatActivity() {
    lateinit var edname:EditText
    lateinit var edtaboo1:EditText
    lateinit var edtaboo2:EditText
    lateinit var edtaboo3:EditText
  lateinit var btnadd: Button
    lateinit var btBack: Button
    private lateinit var progressDialog: ProgressDialog
    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }


    private lateinit var existingCelebrities: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_celerbrity)
        //existingCelebrities = intent.extras!!.getStringArrayList("celebrityNames")!!
        edname = findViewById(R.id.edname)
        edtaboo1 = findViewById(R.id.edtaboo1)
        edtaboo2 = findViewById(R.id.edtaboo2)
        edtaboo3 = findViewById(R.id.edtaboo3)
        btnadd = findViewById(R.id.btnaddcel)

        btnadd.setOnClickListener {
            if (edname.text.isNotEmpty() && edtaboo1.text.isNotEmpty() &&
                edtaboo2.text.isNotEmpty() && edtaboo3.text.isNotEmpty()
            ) {
                addCelebrity()
            } else {
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
        }

    }



    private fun addCelebrity(){
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.show()
         var f=Celebrity(edname.text.toString().capitalize(), edtaboo1.text.toString(), edtaboo2.text.toString(),
            edtaboo3.text.toString(), 0)
        apiInterface.addcelebrity(f).enqueue(object: Callback<Celebrity> {
            override fun onResponse(
                call: Call<Celebrity>,
                response: Response<Celebrity>) {
                Toast.makeText(applicationContext, "adding Sucess!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss()

                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }




            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
              progressDialog.dismiss()
              Toast.makeText(this@NewCelerbrity, "Unable to get data", Toast.LENGTH_LONG).show()
           } })
   }

    fun back2main(view: View) {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}


