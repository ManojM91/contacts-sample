package samples.manoj.workcontacts.retrofit.datasource

import samples.manoj.workcontacts.retrofit.api.EmployeesApi
import samples.manoj.workcontacts.model.Employee

class EmployeeDataRepository(private var employeesApi: EmployeesApi) {

    suspend fun getEmployees() : List<Employee>{
        return employeesApi.getEmployees()
    }
}