package id.ac.ubaya.informatika.dailymemedigest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.card_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment(val usr_id: String, val usr_name: String, val usr_frst_name: String,
                       val usr_lst_name: String, val usr_reg_date: String, val usr_avt_img: String,
                       val usr_priv_set: String
                        ) : Fragment() { // passing data user dari sini
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(usr_avt_img).into(profImg)
        textViewNameOfUser.text = usr_frst_name+" "+usr_lst_name
        textViewActiveAkun.text = "Active since "+usr_reg_date
        textViewUsername.text = usr_name
        editText_Settings_FirstName.setText(usr_frst_name)
        editText_Settings_LastName.setText(usr_lst_name)
        if (usr_priv_set=="0"){ // default hide my name
            checkBoxHide.isChecked = true

        }


        /// for checkbox
        var cond_checked = usr_priv_set

        checkBoxHide.setOnClickListener {
            if (checkBoxHide.isChecked){
                cond_checked = "0"

            }

            else{
                cond_checked = "1"

            }


        }


        button_SaveChanged.setOnClickListener {
            val queue = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/native/160420152/memes_api/set_user.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    Log.d("checkparams", it)
                    //val obj = JSONObject(it)
                    val obj = it
                    if (obj=="OK"){
                        //playlist.num_likes++ // i changed num_likes to var
                        //holder.v.txtLikes.text = "${playlist.num_likes} LIKES"
                        textViewNameOfUser.text = editText_Settings_FirstName.text.toString() + " " + editText_Settings_LastName.text.toString()

                    }
//                    else{
//                        Log.e("updateerror",obj.getString("message"))
//                    }
                    else if (obj=="NG"){

                    }

                },
                Response.ErrorListener {
                    Log.d("checkparams", it.message.toString())
                }
            )
            /// body
            {
                override fun getParams(): MutableMap<String, String>? {
                    return hashMapOf("first_name" to editText_Settings_FirstName.text.toString(),
                        "last_name" to editText_Settings_LastName.text.toString(),
                        "privacy_set" to cond_checked,
                        "username" to usr_name,

                    )
                }
            }

            queue.add(stringRequest)

        }





    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SettingsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}