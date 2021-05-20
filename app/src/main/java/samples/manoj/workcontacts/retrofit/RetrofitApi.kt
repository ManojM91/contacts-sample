package samples.manoj.workcontacts.retrofit

import retrofit2.http.GET
import samples.manoj.workcontacts.retrofit.api.EmployeesApi
import samples.manoj.workcontacts.model.Employee

interface RetrofitApi : EmployeesApi {

    @GET("/users")
    override suspend fun getEmployees(): List<Employee>
}