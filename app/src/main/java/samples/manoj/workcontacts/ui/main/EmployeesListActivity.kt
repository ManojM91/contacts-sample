package samples.manoj.workcontacts.ui.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import samples.manoj.workcontacts.R
import samples.manoj.workcontacts.databinding.EmployeeActivityBinding
import samples.manoj.workcontacts.ui.main.fragments.EmployeesListFragment


class EmployeesListActivity : AppCompatActivity() {

    private lateinit var binding: EmployeeActivityBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = EmployeeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragment = EmployeesListFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }


    }

    //  manage  actionbar title
    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (fragment is EmployeesListFragment) {
            val employeesListFragment: EmployeesListFragment = fragment as EmployeesListFragment
            // Pass intent or its data to the fragment's method
            employeesListFragment.processVoiceData(intent!!.getStringExtra(SearchManager.QUERY))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}