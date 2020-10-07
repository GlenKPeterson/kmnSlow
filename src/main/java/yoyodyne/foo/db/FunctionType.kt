package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "function_type", uniqueConstraints = [UniqueConstraint(columnNames = ["table1_id", "identifier"])])
class FunctionType(
        @get:ManyToOne(fetch = FetchType.LAZY)
        @get:JoinColumn(name = "table1_id", nullable = false)
        var table1: Table1,

        @get:Column(name = "identifier", nullable = false, length = 64)
        var identifier: String
) : java.io.Serializable, Comparable<FunctionType>, Idd {

    @get:Id
    @get:GeneratedValue(strategy = IDENTITY)
    @get:Column(name = "id", unique = true, nullable = false)
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