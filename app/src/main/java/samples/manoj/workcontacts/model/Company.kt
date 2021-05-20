package samples.manoj.workcontacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Company {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("catchPhrase")
    @Expose
    var catchPhrase: String? = null

    @SerializedName("bs")
    @Expose
    var bs: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param bs
     * @param catchPhrase
     * @param name
     */
    constructor(name: String?, catchPhrase: String?, bs: String?) : super() {
        this.name = name
        this.catchPhrase = catchPhrase
        this.bs = bs
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(Company::class.java.name).append('@')
            .append(Integer.toHexString(System.identityHashCode(this))).append('[')
        sb.append("name")
        sb.append('=')
        sb.append(if (name == null) "<null>" else name)
        sb.append(',')
        sb.append("catchPhrase")
        sb.append('=')
        sb.append(if (catchPhrase == null) "<null>" else catchPhrase)
        sb.append(',')
        sb.append("bs")
        sb.append('=')
        sb.append(if (bs == null) "<null>" else bs)
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
        result = result * 31 + if (name == null) 0 else name.hashCode()
        result = result * 31 + if (bs == null) 0 else bs.hashCode()
        result = result * 31 + if (catchPhrase == null) 0 else catchPhrase.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }
        if (other is Company == false) {
            return false
        }
        val rhs = other
        return (name === rhs.name || name != null && name == rhs.name) && (bs === rhs.bs || bs != null && bs == rhs.bs) && (catchPhrase === rhs.catchPhrase || catchPhrase != null && catchPhrase == rhs.catchPhrase)
    }
}