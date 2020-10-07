package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields
import java.util.Objects
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "metric_type",
       uniqueConstraints = [UniqueConstraint(columnNames = ["table1_id", "identifier"])])
class MetricType(
        @get:ManyToOne(fetch = FetchType.LAZY)
        @get:JoinColumn(name = "table1_id", nullable = false)
        var table1: Table1,

        @get:Column(name = "identifier", nullable = false)
        var identifier: String,
) : java.io.Serializable, Comparable<MetricType>, Idd {

    @get:Id
    @get:GeneratedValue(strategy = IDENTITY)
    @get:Column(name = "id", unique = true, nullable = false)
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
