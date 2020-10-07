package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields

class User(
        var table1: Table1,
        var identifier: String,
        var email: String,
        var lastNameC: String,
) : java.io.Serializable,
    Comparable<User>,
    Idd {
    override var id: Long = 0
    var firstNameC: String? = null
    var middleNameC: String? = null

    override fun equals(other: Any?): Boolean =
            equalsIdOrFields(this, other, {
                (table1 == it.table1) &&
                (identifier == it.identifier)
            })

    override fun hashCode(): Int =
            hashIdOrFields(id, table1, identifier)

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
            "User($id, co=${table1.id})"

    companion object {

        private const val serialVersionUID = 20160113141655L
    }
}
