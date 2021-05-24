package samples.manoj.workcontacts.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import samples.manoj.workcontacts.retrofit.datasource.EmployeeDataRepository
import samples.manoj.workcontacts.model.Employee
import samples.manoj.workcontacts.retrofit.RetrofitBuilder
import java.util.*


class EmployeesListViewModel  : ViewModel() {

    // setup retrofit builder
    private var dataRepository =  EmployeeDataRepository(RetrofitBuilder.getService())

    private val employeeLiveData = MutableLiveData<List<Employee>>()

    fun getEmployees() : MutableLiveData<List<Employee>> {
        viewModelScope.launch {
            val employee = withContext(Dispatchers.IO){
                dataRepository.getEmployees()
            }
            //sort data based on name before giving to the View
            Collections.sort(employee,
                Comparator<Employee?> { lhs, rhs -> rhs.name?.let { lhs.name?.compareTo(it) }!! })
            employeeLiveData.value = employee
        }
        return employeeLiveData
    }

}
