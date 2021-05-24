package samples.manoj.workcontacts.ui.main.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import samples.manoj.workcontacts.R
import samples.manoj.workcontacts.databinding.EmployeeFragmentBinding
import samples.manoj.workcontacts.model.Employee
import samples.manoj.workcontacts.ui.main.EmployeesListActivity
import samples.manoj.workcontacts.ui.main.adapters.EmployeesListAdapter
import samples.manoj.workcontacts.ui.main.observer.RecyclerViewDataObserver
import samples.manoj.workcontacts.ui.main.viewmodel.EmployeesListViewModel
import java.util.*


class EmployeesListFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var employeelistAdapter: EmployeesListAdapter
    private lateinit var viewModel: EmployeesListViewModel
    private var fragmentBinding: EmployeeFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        fragmentBinding  = EmployeeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager  =
            LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)

        viewModel = ViewModelProvider(activity!!).get(EmployeesListViewModel::class.java)

        viewModel.getEmployees().observe(activity!!){
            //giving the received objects to adapter
            employeelistAdapter  =  EmployeesListAdapter(it as ArrayList<Employee>)
            employeelistAdapter.registerAdapterDataObserver(
                RecyclerViewDataObserver(
                    binding.recyclerView,
                    binding.emptyListImageView, binding.emptyViewText
                )
            )
            binding.recyclerView.adapter = employeelistAdapter
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.employee_list_menu, menu)

        //setup of voice search in menu
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?

        val searchViewMenuItem = menu.findItem(R.id.action_search)
        searchView = searchViewMenuItem.actionView as SearchView

        val searchButton: AppCompatImageView = searchView.findViewById(androidx.appcompat.R.id.search_button)
        searchButton.setColorFilter(getResources().getColor(R.color.black),
            android.graphics.PorterDuff.Mode.SRC_IN)

        val searchCloseIcon :ImageView = searchView
                .findViewById(androidx.appcompat.R.id.search_close_btn);
        // changing close icon color
        searchCloseIcon.setColorFilter(
            getResources().getColor(R.color.black),
            android.graphics.PorterDuff.Mode.SRC_IN
        );

        val searchVoiceIcon :ImageView = searchView
            .findViewById(androidx.appcompat.R.id.search_voice_btn);
        // changing close icon color
        searchVoiceIcon.setColorFilter(
            getResources().getColor(R.color.black),
            android.graphics.PorterDuff.Mode.SRC_IN
        );

        val searchAutoComplete: SearchView.SearchAutoComplete  =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        // changing hint and text color of search text box
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.black));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.black));
        // set  hint
        searchView.queryHint  = getString(R.string.action_search)



        searchView.setSearchableInfo(
            searchManager!!.getSearchableInfo(activity?.componentName)
        )
        searchView.setMaxWidth(Int.MAX_VALUE)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                employeelistAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                employeelistAdapter.filter.filter(query)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        // Set title bar
        (activity as EmployeesListActivity?)?.setActionBarTitle("Contact List")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding  = null
    }

    fun processVoiceData(voiceData: String?) {
        searchView.setQuery(voiceData, false)
    }

}