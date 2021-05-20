package samples.manoj.workcontacts.retrofit.api

import samples.manoj.workcontacts.model.Employee

interface EmployeesApi {
    suspend fun getEmployees(): List<Employee>
}