package samples.manoj.workcontacts.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import samples.manoj.workcontacts.R
import samples.manoj.workcontacts.model.Employee
import samples.manoj.workcontacts.ui.main.fragments.EmployeeDetailFragment


class EmployeesListAdapter(
    private var employeeList: ArrayList<Employee>
) : RecyclerView.Adapter<EmployeesListAdapter.ViewHolder>() , Filterable {

    private var employeeListFiltered: ArrayList<Employee> = ArrayList()

    init {
        employeeListFiltered = employeeList
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.employee_list_viewholder,
            parent,
            false
        )
        return ViewHolder(v)
    }

    //this method is binding the data on the recyclerview
    override fun onBindViewHolder(holder: EmployeesListAdapter.ViewHolder, position: Int) {
        holder.bindItems(employeeListFiltered[position])

        // on click pass the selected onject to details fragment
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                //move to details screen
                val employeeDetailFragment = EmployeeDetailFragment(employeeListFiltered[position])
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, employeeDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return employeeListFiltered.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(employee: Employee) {
            val textViewName = itemView.findViewById(R.id.txtEmployeeName) as TextView
            val textViewAddress  = itemView.findViewById(R.id.txtPhoneNumber) as TextView
            val materialButton  = itemView.findViewById(R.id.initials) as MaterialButton
            textViewName.text = employee.name
            textViewAddress.text = employee.phone

            // getting the initials of the name
            materialButton.text = employee.name!!
                .split(' ')
                .mapNotNull { it.firstOrNull()?.toString() }
                .reduce { acc, s -> acc + s }
                .toUpperCase()

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    employeeListFiltered = employeeList
                } else {
                    val filteredList: ArrayList<Employee> = ArrayList()
                    for (employee in employeeList) {
                        if (employee.name?.toLowerCase()?.contains(charString.toLowerCase())!!) {
                            filteredList.add(employee)
                        } else if (employee.username?.toLowerCase()?.contains(charString.toLowerCase())!!) {
                            filteredList.add(employee)
                        }
                    }
                    employeeListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = employeeListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                employeeListFiltered = (filterResults.values as ArrayList<Employee>?)!!
                notifyDataSetChanged()
            }
        }
    }

}