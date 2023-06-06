package id.ac.ubaya.informatika.dailymemedigest


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.card_myhome.view.*
import org.json.JSONObject
import kotlinx.android.synthetic.main.fragment_myhome.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlaylistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyHomeFragment(val usr_id: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var user_id: String? = usr_id //passing user_id (3)
    //private var param2: String? = null

    var homes: ArrayList<Home> = ArrayList() // fungsi untuk menyimpan array, dan di pasingkan ke homeadapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160420152/memes_api/get_memes_user.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                Log.d("apiresult", it)
                // convert whole json string into jsonobject
                val obj = JSONObject(it)
                // check the result value
                if (obj.getString("result") == "OK"){
                    // OK proceed to read the data json array
                    val data = obj.getJSONArray("data")
                    // for each JSON Object in the json Array..
                    for (i in 0 until data.length() ){
                        // retrieve the JSON object
                        val playObj = data.getJSONObject(i)
                        // Create a Playlist kotlin object, retrieveing the values from the json object
                        val home = Home(
                            playObj.getInt("idmemes"),
                            playObj.getString("meme_img"),
                            playObj.getString("top_txt"),
                            playObj.getString("bot_txt"),
                            playObj.getInt("idusers_UAS"),
                            playObj.getInt("num_likes"),
                            playObj.getInt("num_comments"),






                        )
                        // add the playlist to the ArrayList
                        homes.add(home)
                    }
                    updateList()

                    // TODO: update the UI to show the values
                    Log.d("playlists", homes.toString())
                }

            },
            Response.ErrorListener {
                Log.d("checkparams", it.message.toString())
            }
        )
        /// body
        {
            override fun getParams(): MutableMap<String, String>? {
                return hashMapOf("user_id" to user_id.toString()

                )
            }
        }

        q.add(stringRequest)


    }

    fun updateList(){
        val llm = LinearLayoutManager(activity)
        view?.homeView?.let {
            it.layoutManager = llm
            it.setHasFixedSize(true)
            it.adapter = MyHomeAdapter(homes,user_id!!) // passing user_id (4)


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myhome, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlaylistFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic tanpa ini bisa
//        fun newInstance(param1: String, param2: String) =
//            PlaylistFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}