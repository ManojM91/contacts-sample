package samples.manoj.workcontacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address {
    @SerializedName("street")
    @Expose
    var street: String? = null

    @SerializedName("suite")
    @Expose
    var suite: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("zipcode")
    @Expose
    var zipcode: String? = null

    @SerializedName("geo")
    @Expose
    var geo: Geo? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param zipcode
     * @param geo
     * @param suite
     * @param city
     * @param street
     */
    constructor(
        street: String?,
        suite: String?,
        city: String?,
        zipcode: String?,
        geo: Geo?
    ) : super() {
        this.street = street
        this.suite = suite
        this.city = city
        this.zipcode = zipcode
        this.geo = geo
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(Address::class.java.name).append('@')
            .append(Integer.toHexString(System.identityHashCode(this))).append('[')
        sb.append("street")
        sb.append('=')
        sb.append(if (street == null) "<null>" else street)
        sb.append(',')
        sb.append("suite")
        sb.append('=')
        sb.append(if (suite == null) "<null>" else suite)
        sb.append(',')
        sb.append("city")
        sb.append('=')
        sb.append(if (city == null) "<null>" else city)
        sb.append(',')
        sb.append("zipcode")
        sb.append('=')
        sb.append(if (zipcode == null) "<null>" else zipcode)
        sb.append(',')
        sb.append("geo")
        sb.append('=')
        sb.append(if (geo == null) "<null>" else geo)
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
        result = result * 31 + if (zipcode == null) 0 else zipcode.hashCode()
        result = result * 31 + if (geo == null) 0 else geo.hashCode()
        result = result * 31 + if (suite == null) 0 else suite.hashCode()
        result = result * 31 + if (city == null) 0 else city.hashCode()
        result = result * 31 + if (street == null) 0 else street.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }
        if (other is Address == false) {
            return false
        }
        val rhs = other
        return (zipcode === rhs.zipcode || zipcode != null && zipcode == rhs.zipcode) && (geo === rhs.geo || geo != null && geo == rhs.geo) && (suite === rhs.suite || suite != null && suite == rhs.suite) && (city === rhs.city || city != null && city == rhs.city) && (street === rhs.street || street != null && street == rhs.street)
    }
}