package samples.manoj.workcontacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Geo {
    @SerializedName("lat")
    @Expose
    var lat: String? = null

    @SerializedName("lng")
    @Expose
    var lng: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param lng
     * @param lat
     */
    constructor(lat: String?, lng: String?) : super() {
        this.lat = lat
        this.lng = lng
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(Geo::class.java.name).append('@')
            .append(Integer.toHexString(System.identityHashCode(this))).append('[')
        sb.append("lat")
        sb.append('=')
        sb.append(if (lat == null) "<null>" else lat)
        sb.append(',')
        sb.append("lng")
        sb.append('=')
        sb.append(if (lng == null) "<null>" else lng)
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
        result = result * 31 + if (lng == null) 0 else lng.hashCode()
        result = result * 31 + if (lat == null) 0 else lat.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }
        if (other is Geo == false) {
            return false
        }
        val rhs = other
        return (lng === rhs.lng || lng != null && lng == rhs.lng) && (lat === rhs.lat || lat != null && lat == rhs.lat)
    }
}