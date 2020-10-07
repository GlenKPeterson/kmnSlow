package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields

class Table1
constructor(
var identifier: String
) : java.io.Serializable, Comparable<Table1>, Idd {

override var id: Long = 0

    override fun compareTo(other: Table1): Int {
        if (this === other) {
            return 0
        }
        return this.identifier.compareTo(other.identifier, ignoreCase = true)
    }

    override fun equals(other: Any?): Boolean =
            equalsIdOrFields(this, other,
                             { identifier == it.identifier })

    override fun hashCode(): Int =
            hashIdOrFields(id, identifier)

    override fun toString(): String = StringBuilder("Co(").append(id).append(", ")
            .append(identifier).append(")")
            .toString()

    companion object {
        private const val serialVersionUID = 20161108163910L
    }
}
