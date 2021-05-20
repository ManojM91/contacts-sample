package samples.manoj.workcontacts.ui.main.fragments

import android.Manifest.permission.READ_CONTACTS
import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import samples.manoj.workcontacts.databinding.FragmentEmployeeDetailBinding
import samples.manoj.workcontacts.model.Employee
import samples.manoj.workcontacts.ui.main.EmployeesListActivity


class EmployeeDetailFragment(private val employee: Employee) : Fragment(), OnMapReadyCallback {

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    private var fragmentBinding: FragmentEmployeeDetailBinding? = null
    private val binding get() = fragmentBinding!!

    private var lat: Double? = null
    private var lng: Double? = null

    private lateinit var map: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentEmployeeDetailBinding.inflate(inflater, container, false)
        map = binding.mapView
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set textviews
        binding.txtEmployeeName.text = employee.name
        binding.txtPhoneNumber.text = employee.phone
        binding.txtEmail.text = employee.email
        binding.txtWebsite.text = employee.website
        binding.initials.text  = employee.name!!
            .split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .reduce { acc, s -> acc + s }
            .toUpperCase()

        // assign lat  and  long to a var of type double
        lat = employee.address?.geo?.lat?.toDouble()
        lng = employee.address?.geo?.lng?.toDouble()

        //send phoneNumber to dialer
        binding.callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            val temp = "tel:${employee.phone}"
            intent.data = Uri.parse(temp)
            startActivity(intent)
        }

        // send email to email app
        binding.emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(employee.email))
            if (intent.resolveActivity(activity?.packageManager!!) != null) {
                startActivity(intent)
            }
        }

        //open webpage in browser
        binding.website.setOnClickListener {
            var url = employee.website

            if (url != null) {
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url
            };
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        //save contact
        binding.saveContact.setOnClickListener {
            askForContactPermission(employee.phone)
        }
    }

    fun askForContactPermission(phone: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity!!,
                        READ_CONTACTS
                    )
                ) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(activity!!)
                    builder.setTitle("Contacts access needed")
                    builder.setPositiveButton(R.string.ok, null)
                    builder.setMessage("please confirm Contacts access") //TODO put real question
                    builder.setOnDismissListener(DialogInterface.OnDismissListener {
                        requestPermissions(
                            arrayOf(READ_CONTACTS),
                            PERMISSIONS_REQUEST_READ_CONTACTS
                        )
                    })
                    builder.show()
                } else {
                    // No explanation needed, we can request the permission.
                    requestPermissions(
                        activity!!, arrayOf(READ_CONTACTS),
                        PERMISSIONS_REQUEST_READ_CONTACTS
                    )
                }
            } else {
                checkContactAvailableAndSave(context!!, phone)
            }
        } else {
            checkContactAvailableAndSave(context!!, phone)
        }
    }

    fun createContact(){
        val intent =
            Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
                putExtra(
                    ContactsContract.Intents.Insert.NAME,
                    employee.name
                )
                putExtra(
                    ContactsContract.Intents.Insert.EMAIL,
                    employee.email
                )
                putExtra(
                    ContactsContract.Intents.Insert.EMAIL_TYPE,
                    ContactsContract.CommonDataKinds.Email.TYPE_WORK
                )
                putExtra(
                    ContactsContract.Intents.Insert.PHONE,
                    employee.phone
                )
                putExtra(
                    ContactsContract.Intents.Insert.PHONE_TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK
                )
            }
        startActivity(intent)
    }

    fun checkContactAvailableAndSave(context: Context, number: String?) {

            /// number is the phone number
            val lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number))
            val mPhoneNumberProjection =
                arrayOf(PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME)
            val cur: Cursor? =
                context.contentResolver.query(lookupUri, mPhoneNumberProjection, null, null, null)
            try {
                if (cur!!.moveToFirst()) {
                    Toast.makeText(
                        activity,
                        "Contact already exists in your phone book",
                        Toast.LENGTH_SHORT
                    ).show()
                }  else{
                    createContact()
                }
            } finally {
                cur!!.close()
            }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    checkContactAvailableAndSave(context!!, employee.phone)
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(activity, "No permission for contacts", Toast.LENGTH_SHORT)
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {

        val latLng = LatLng(
            lat!!, lng!!
        )

        googleMap.uiSettings.isMyLocationButtonEnabled = false

        //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
        try {
            MapsInitializer.initialize(context);
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace();
        }


        // Updates the location and zoom of the MapView
        val marker: Marker = googleMap.addMarker(
            MarkerOptions().position(latLng).title(employee.name)
        )
        marker.showInfoWindow()
        //marker.position  = googleMap.cameraPosition.target

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2.0f));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(2.0f), 2000, null);
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
        // Set title bar
        (activity as EmployeesListActivity?)?.setActionBarTitle("Contact")
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }
}
