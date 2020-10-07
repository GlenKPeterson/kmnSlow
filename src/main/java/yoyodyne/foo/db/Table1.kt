package yoyodyne.foo.db

import yoyodyne.foo.util.FooGlobals.equalsIdOrFields
import yoyodyne.foo.util.FooGlobals.hashIdOrFields
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "table1",
       uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("identifier"))])
class Table1
constructor(
        @get:Column(name = "identifier", unique = true, nullable = false, length = 20)
        var identifier: String
) : java.io.Serializable, Comparable<Table1>, Idd {

    @get:Id
    @get:GeneratedValue(strategy = IDENTITY)
    @get:Column(name = "id", unique = true, nullable = false)
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

    @Transient
    override fun toString(): String = StringBuilder("Co(").append(id).append(", ")
            .append(identifier).append(")")
            .toString()

    companion object {
        private const val serialVersionUID = 20161108163910L
    }
}
