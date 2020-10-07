package yoyodyne.foo.db

class User(
        var identifier: String,
        var lastNameC: String,
) : Comparable<User> {
    var firstNameC: String? = null
    var middleNameC: String? = null

    override fun equals(other: Any?): Boolean =
            (this === other) ||
            (other is User &&
            identifier == other.identifier)

    override fun hashCode(): Int =
            identifier.hashCode()

    override fun compareTo(other: User): Int {

        if (this === other) {
            return 0
        }

        var ret = lastNameC.compareTo(other.lastNameC, ignoreCase = true)
        if (ret != 0) {
            return ret
        }

        if (this.firstNameC == null) {
            if (other.firstNameC != null) {
                return -1
            }
        } else if (other.firstNameC == null) {
            return 1
        } else {
            ret = this.firstNameC!!.compareTo(other.firstNameC!!, ignoreCase = true)
            if (ret != 0) {
                return ret
            }
        }

        if (this.middleNameC == null) {
            if (other.middleNameC != null) {
                return -1
            }
        } else if (other.middleNameC == null) {
            return 1
        } else {
            ret = this.middleNameC!!.compareTo(other.middleNameC!!, ignoreCase = true)
            if (ret != 0) {
                return ret
            }
        }

        return this.identifier.compareTo(other.identifier)
    }

    override fun toString(): String =
            "User($identifier)"
}
