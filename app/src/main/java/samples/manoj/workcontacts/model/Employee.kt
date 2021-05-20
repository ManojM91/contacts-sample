package samples.manoj.workcontacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Employee {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("address")
    @Expose
    var address: Address? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("website")
    @Expose
    var website: String? = null

    @SerializedName("company")
    @Expose
    var company: Company? = null

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param website
     * @param address
     * @param phone
     * @param name
     * @param company
     * @param id
     * @param email
     * @param username
     */
    constructor(
        id: Int,
        name: String?,
        username: String?,
        email: String?,
        address: Address?,
        phone: String?,
        website: String?,
        company: Company?
    ) : super() {
        this.id = id
        this.name = name
        this.username = username
        this.email = email
        this.address = address
        this.phone = phone
        this.website = website
        this.company = company
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(Employee::class.java.name).append('@')
            .append(Integer.toHexString(System.identityHashCode(this))).append('[')
        sb.append("id")
        sb.append('=')
        sb.append(id)
        sb.append(',')
        sb.append("name")
        sb.append('=')
        sb.append(if (name == null) "<null>" else name)
        sb.append(',')
        sb.append("username")
        sb.append('=')
        sb.append(if (username == null) "<null>" else username)
        sb.append(',')
        sb.append("email")
        sb.append('=')
        sb.append(if (email == null) "<null>" else email)
        sb.append(',')
        sb.append("address")
        sb.append('=')
        sb.append(if (address == null) "<null>" else address)
        sb.append(',')
        sb.append("phone")
        sb.append('=')
        sb.append(if (phone == null) "<null>" else phone)
        sb.append(',')
        sb.append("website")
        sb.append('=')
        sb.append(if (website == null) "<null>" else website)
        sb.append(',')
        sb.append("company")
        sb.append('=')
        sb.append(if (company == null) "<null>" else company)
        sb.append(',')
        if (sb[sb.length - 1] == ',') {
            sb.setCharAt(sb.length - 1, ']')
        } else {
            sb.append(']')
        }
        return sb.toString()
    }

    override fun hashCode(): Int {
        var result = 1
        result = result * 31 + if (website == null) 0 else website.hashCode()
        result = result * 31 + if (address == null) 0 else address.hashCode()
        result = result * 31 + if (phone == null) 0 else phone.hashCode()
        result = result * 31 + if (name == null) 0 else name.hashCode()
        result = result * 31 + if (company == null) 0 else company.hashCode()
        result = result * 31 + id
        result = result * 31 + if (email == null) 0 else email.hashCode()
        result = result * 31 + if (username == null) 0 else username.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }
        if (other is Employee == false) {
            return false
        }
        val rhs = other
        return (website === rhs.website || website != null && website == rhs.website) && (address === rhs.address || address != null && address!!.equals(
            rhs.address
        )) && (phone === rhs.phone || phone != null && phone == rhs.phone) && (name === rhs.name || name != null && name == rhs.name) && (company === rhs.company || company != null && company!!.equals(
            rhs.company
        )) && id == rhs.id && (email === rhs.email || email != null && email == rhs.email) && (username === rhs.username || username != null && username == rhs.username)
    }
}