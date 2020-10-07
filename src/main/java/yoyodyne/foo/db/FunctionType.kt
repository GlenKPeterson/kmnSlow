package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields

class FunctionType(
var table1: Table1,

var identifier: String
) : java.io.Serializable, Comparable<FunctionType>, Idd {

override var id: Long = 0

    override fun compareTo(other: FunctionType): Int {
        if (this === other) {
            return 0
        }
        val ret = table1.compareTo(other.table1)
        return if (ret != 0) {
            ret
        } else identifier.compareTo(other.identifier, ignoreCase = true)
    }

    override fun equals(other: Any?): Boolean =
            equalsIdOrFields(this, other,
                             { identifier == it.identifier &&
                               table1 == it.table1 })

    override fun hashCode(): Int =
            hashIdOrFields(id, identifier, table1)

    override fun toString(): String =
            "FunctionType($id co=${table1.id} $identifier)"

    companion object {
        private const val serialVersionUID = 1L
    }
}