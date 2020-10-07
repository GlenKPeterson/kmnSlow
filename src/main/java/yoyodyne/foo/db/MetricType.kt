package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields
import java.util.Objects

class MetricType(
var table1: Table1,

var identifier: String,
) : java.io.Serializable, Comparable<MetricType>, Idd {

override var id: Long = 0

    override fun compareTo(other: MetricType): Int {
        if (this === other) {
            return 0
        }
        return when (val ret = table1.compareTo(other.table1)) {
            0 -> identifier.compareTo(other.identifier, ignoreCase = true)
            else -> ret
        }
    }

    override fun equals(other: Any?): Boolean =
            equalsIdOrFields(this, other,
                             { Objects.equals(table1, it.table1) &&
                               Objects.equals(identifier, it.identifier) })

    override fun hashCode(): Int =
            hashIdOrFields(id, table1, identifier)

    override fun toString(): String =
            "MetricType($id co=${table1.id} ${identifier})"

    companion object {
        private const val serialVersionUID = 20160115184716L
    }
}
