package samples.manoj.workcontacts.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import samples.manoj.workcontacts.R

class Splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val SPLASH_TIME_OUT = 2000
        val homeIntent = Intent(this@Splashscreen, EmployeesListActivity::class.java)
        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}