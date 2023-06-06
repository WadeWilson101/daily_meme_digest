package id.ac.ubaya.informatika.dailymemedigest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //val fragments: ArrayList<Fragment> = ArrayList()




//    val fragments = arrayListOf<Fragment>(
//        HomeFragment(username_id!!) // passing username_id ?? (2)
//
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // fullscreen
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // shared prefs Key
        val SHARED_USERNAME_ID = "usernameid"
        val SHARED_USERNAME = "username"
        val FIRST_NAME = "firstname"
        val LAST_NAME = "lastname"
        val REG_DATE = "regdate"
        val AVT_IMG = "avtimage"
        val PRIVACY_SET = "privacyset"

        val sharedPrefences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        // check if user is logged in or not
        // retrieve username that are logged in
        // HATI HATI KALO LOGOUT INI DI KOSONGI !!!!!
        val username_ = sharedPrefences.getString(SHARED_USERNAME,"")
        val username_id = sharedPrefences.getString(SHARED_USERNAME_ID,"")
        val first_name_ = sharedPrefences.getString(FIRST_NAME,"")
        val last_name_ = sharedPrefences.getString(LAST_NAME,"")
        val reg_date_ = sharedPrefences.getString(REG_DATE,"")
        val avt_img_ = sharedPrefences.getString(AVT_IMG,"")
        val privacy_set_ = sharedPrefences.getString(PRIVACY_SET,"")

        val fragments = arrayListOf<Fragment>(
            HomeFragment(username_id!!), // passing username_id ?? (2)
            MyHomeFragment(username_id!!),
            LeaderFragment(),
            SettingsFragment(username_id!!,username_!!,first_name_!!,last_name_!!,reg_date_!!,avt_img_!!,privacy_set_!!) // passing all user data



        )

        /// viewpager fragments stuff

        viewPager.adapter = MyAdapter(this, fragments)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
                bottomNav.selectedItemId = bottomNav.menu[position].itemId

            }

        })

        // listener stuff

        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemMyCreation -> 1
                R.id.itemLeaderboard -> 2
                R.id.itemSettings -> 3
                else -> 0
            }
            true

        }

        fabAddMeme.setOnClickListener {
            val intent = Intent(this, Create_MyCreation::class.java)
            startActivity(intent)
        }




        if (username_ == ""){
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }





        else{

            // to delete sharedPrefs username and ID
            sharedPrefences.edit().remove("username").commit()
            //sharedPrefences.edit().remove("usernameid").commit()



            Toast.makeText(this, username_+"haha "+username_id,
                Toast.LENGTH_LONG).show();
        }






//        fragments.add(MainActivity())
//        fragments.add(MyCreation())
//        fragments.add(Leaderboard())
//        fragments.add(Settings())


    }


}