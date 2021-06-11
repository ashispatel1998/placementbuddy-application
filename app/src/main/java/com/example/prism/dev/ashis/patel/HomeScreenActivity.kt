package com.example.prism.dev.ashis.patel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prism.dev.ashis.patel.Adapters.JobNotificationAdapter
import com.example.prism.dev.ashis.patel.Adapters.MNCHorizontalAdapter
import com.example.prism.dev.ashis.patel.Model.JobDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        supportActionBar?.title = "PlacementBuddy"
        auth = Firebase.auth

        // Horizontal Recyclerview
        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontal_recyclerview)

        // Vertical Recyclerview
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recyclerview)

        //setting recycler to horizontal scroll
        horizontalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // setting recycler to vertical scroll
        verticalRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        //creating a  arraylist of data
        val mncHiringData: ArrayList<String> = ArrayList()


        mncHiringData.add("https://pixelz.cc/wp-content/uploads/2018/07/google-logo-redesign-uhd-4k-wallpaper.jpg")
        mncHiringData.add("https://www.wallpaperuse.com/wallp/8-81348_m.jpg")
        mncHiringData.add("https://mma.prnewswire.com/media/1477373/TCS_Logo.jpg?p=facebook")
        mncHiringData.add("https://www.cognizant.com/content/dam/cognizant_foundation/Dotcomimage/CognizantLogo400.jpg")
        mncHiringData.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzSLsg0CIjToAVStAfA5rdeHpY9XHuYYfrtQ&usqp=CAU")
        mncHiringData.add("https://www.pega.com/sites/default/files/styles/1024/public/media/images/2018-08/capgemini-prevcard.jpg?itok=5fA6BlNv")
        mncHiringData.add("https://www2.deloitte.com/content/dam/Deloitte/us/Images/promo_images/deloitte/us-deloitte-logo.jpg")



        // Creating arraylist of job notifications
        val jobNotifications: ArrayList<JobDetails> = ArrayList()

        var post1=JobDetails("1","https://www.wallpaperuse.com/wallp/8-81348_m.jpg","Facebook","MNC","Product based company","This is job post by facebook","1126537","Develop scalable web applications","C,JAVA,PYTHON,DATA STRUCTURES","2021/2022","Freshers","Software Developer Engineer 1","Hydrabad","BE/BTech/ME/MTech","60% Throughout academics","0","No","18","Online Test , TR  , HR","Online","0","05/06/2021","15/06/2021","apply here")
        var post2=JobDetails("2","https://mma.prnewswire.com/media/530945/Hexaware.jpg?p=facebook","Hexaware","MNC","Product based company","This is job post by facebook","15327ERD78","Develop scalable web applications","C,JAVA,PYTHON,DATA STRUCTURES","2021/2022","Freshers","Product Designer","Hydrabad","BE/BTech","60% Throughout academics","0","No","3.5","Online Test , TR  , HR","Online","0","05/06/2021","15/06/2021","apply here")
        var post3=JobDetails("3","https://www.cognizant.com/content/dam/cognizant_foundation/Dotcomimage/CognizantLogo400.jpg","Cognizant","MNC","Product based company","This is job post by facebook","123797","Develop scalable web applications","C,JAVA,PYTHON,DATA STRUCTURES","2021/2022","Freshers","Programmer Analyst Trainee","Hydrabad","BE/BTech","60% Throughout academics","0","No","4-6","Online Test , TR  , HR","Online","0","05/06/2021","15/06/2021","apply here")
        var post4=JobDetails("4","https://www2.deloitte.com/content/dam/Deloitte/us/Images/promo_images/deloitte/us-deloitte-logo.jpg","Deloitte","MNC","Associate System Engineer","This is job post by facebook","98356","Develop scalable web applications","C,JAVA,PYTHON,DATA STRUCTURES","2021/2022","Freshers","Programmer Analyst Trainee","Hydrabad","BE/BTech","60% Throughout academics","0","No","8","Online Test , TR  , HR","Online","0","05/06/2021","15/06/2021","apply here")

        jobNotifications.add(post1)
        jobNotifications.add(post2)
        jobNotifications.add(post3)
        jobNotifications.add(post4)

        //setting adapter to recycler
        horizontalRecyclerView.adapter = MNCHorizontalAdapter(mncHiringData)

        var adapter = JobNotificationAdapter(jobNotifications)
        verticalRecyclerView.adapter = adapter
        adapter.setOnItemClickListner(object : JobNotificationAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
              val intent = Intent(applicationContext,PostDetailsScreen::class.java)
                intent.putExtra("companyName",jobNotifications[position].companyName)
                intent.putExtra("companyImageUrl",jobNotifications[position].companyImageUrl)
                startActivity(intent)

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.admin_Login -> {
                Toast.makeText(applicationContext, "Admin", Toast.LENGTH_LONG).show()
                true
            }
            R.id.search ->{
                Toast.makeText(applicationContext, "Search", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.logout ->{
                Firebase.auth.signOut()
                var intent : Intent = Intent(applicationContext,LoginScreenActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}