package id.ac.ubaya.informatika.dailymemedigest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.edit
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Create_Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        // fullscreen
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar()?.hide()

        // SHARED PREFS KEY

        val SHARED_USERNAME_ID = "usernameid"
        val SHARED_USERNAME = "username"
        val FIRST_NAME = "firstname"
        val LAST_NAME = "lastname"
        val REG_DATE = "regdate"
        val AVT_IMG = "avtimage"
        val PRIVACY_SET = "privacyset"
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        /////

        button_Create_CreateAccount.setOnClickListener {

            if (editText_Create_FirstName.text.toString() != ""  && editText_Create_password.text.toString() != "" && editText_Create_RepeatPassword.text.toString() != ""){
                if (editText_Create_password.text.toString() != editText_Create_RepeatPassword.text.toString()){
                    Toast.makeText(this, "Password do not match with the Password field ",
                        Toast.LENGTH_LONG).show();
                }
                else{
                    // condition if right
                    val queue = Volley.newRequestQueue(this)
                    val url = "https://ubaya.fun/native/160420152/memes_api/get_user.php"

                    val stringReq : StringRequest =
                        object : StringRequest(
                            Method.POST, url,
                            Response.Listener { response ->
                                // response
                                Log.d("checkparams", response)
                                if (response == "error"){
                                    ///////////////  register user ( INSERT )

                                    val queue2 = Volley.newRequestQueue(this)
                                    val url2 = "https://ubaya.fun/native/160420152/memes_api/add_user.php"

                                    val stringReq2 : StringRequest =
                                        object : StringRequest(Method.POST, url2,
                                            Response.Listener { response ->
                                                // response
                                                Toast.makeText(this, response,
                                                    Toast.LENGTH_LONG).show();
                                                Log.d("checkparams", response)
                                                val obj = JSONObject(response)
                                                if (obj.getString("result")=="OK"){

                                                    Toast.makeText(this, "register sucessfull "+editText_Create_username.text.toString(),
                                                        Toast.LENGTH_LONG).show();
                                                    val editor = sharedPreferences.edit {
                                                        putString(SHARED_USERNAME, editText_Create_username.text.toString())
                                                        putString(SHARED_USERNAME_ID, obj.getString("id").toString())
                                                        putString(FIRST_NAME, editText_Create_FirstName.text.toString())
                                                        putString(LAST_NAME, editText_Create_LastName.text.toString())
                                                        putString(REG_DATE, getDateTime())
                                                        putString(AVT_IMG, "https://media.istockphoto.com/id/519078723/photo/male-silhouette-as-avatar-profile-picture.jpg?s=612x612&w=0&k=20&c=USSwffkuSQY7gfsbmQ_Sntkpcf4__ie4d068Gug97AQ=")
                                                        putString(PRIVACY_SET, "0")
                                                    }
                                                    val intent = Intent(this, MainActivity::class.java)
                                                    startActivity(intent)


                                                }

                                                else{
                                                    Toast.makeText(this, "regis error jancok",
                                                        Toast.LENGTH_LONG).show();
                                                }



                                            },
                                            Response.ErrorListener { error ->
                                                Log.d("API", "error => $error")
                                            }
                                        ){
                                            override fun getParams(): MutableMap<String, String>? {
                                                return hashMapOf("username" to editText_Create_username.text.toString(),
                                                    "password" to editText_Create_password.text.toString(),
                                                    "first_name" to editText_Create_FirstName.text.toString(),
                                                    "last_name" to editText_Create_LastName.text.toString(),
                                                    "reg_date" to getDateTime()

                                                )
                                            }
                                        }
                                    queue2.add(stringReq2)








                                    //////////////////////////////////////////////////////

                                }

                                else{
                                    Toast.makeText(this, "username already exists",
                                        Toast.LENGTH_LONG).show();
                                }



                            },
                            Response.ErrorListener { error ->
                                Log.d("API", "error => $error")
                            }
                        ){
                            override fun getParams(): MutableMap<String, String>? {
                                return hashMapOf("username" to editText_Create_username.text.toString(),
                                    "password" to editText_Create_password.text.toString()
                                )
                            }
                        }
                    queue.add(stringReq)







                    ///////////////////
                }
            }

            else{
                Toast.makeText(this, "Please fill all the fields ( Last Name is not Mandatory )",
                    Toast.LENGTH_LONG).show();

            }






        }
    }

    /// to get current time
    private fun getDateTime(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        return dateFormat.format(date)
    }
}