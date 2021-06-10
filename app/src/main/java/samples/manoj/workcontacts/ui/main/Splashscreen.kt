package samples.manoj.workcontacts.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import samples.manoj.workcontacts.R

class Splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val homeIntent = Intent(this@Splashscreen, EmployeesListActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(homeIntent)
            finish()
        }, 2000)
    }
}