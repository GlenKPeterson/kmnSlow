package yoyodyne.foo.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yoyodyne.foo.util.FooGlobals;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="business_unit",
       uniqueConstraints=@UniqueConstraint(columnNames={"table1_id", "identifier"}))
public class BusinessUnit implements java.io.Serializable,
                                     Comparable<BusinessUnit>,
                                     BarOwnable,
                                     Idd {

    private static final long serialVersionUID = 20150123171505L;

    private long id;
    private @Nullable BusinessUnit parent;
    private Table1 table1;
    private int numAncestors;
    private String identifier;
    private Set<BusinessUnit> children = new HashSet<>(0);
    private Set<Function> functions = new HashSet<>(0);

    public BusinessUnit() {
    }

    public BusinessUnit(Table1 c, @Nullable BusinessUnit p, String ident) {
        table1 = c; parent = p; identifier = ident;
        numAncestors = (p == null) ? 0 : p.getNumAncestors() + 1;
    }

    @Id @GeneratedValue(strategy=IDENTITY)

    @Column(name="id", unique=true, nullable=false)
    @Override
    public long getId() { return id; }
    public void setId(long x) { id = x; }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    public @Nullable BusinessUnit getParent() { return parent; }
    public void setParent(@Nullable BusinessUnit x) { parent = x; }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="table1_id", nullable=false)
    @Override
    public @NotNull Table1 getTable1() { return table1; }
    public void setTable1(Table1 x) { table1 = x; }

    @Column(name="num_ancestors", nullable=false)
    public int getNumAncestors() { return numAncestors; }
    public void setNumAncestors(int x) { numAncestors = x; }

    @Column(name="identifier", nullable=false, length=128)
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String x) { identifier = x; }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="parent")
    public Set<BusinessUnit> getChildren() { return children; }
    public void setChildren(Set<BusinessUnit> x) { children = x; }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="businessUnit")
    public Set<Function> getFunctions() { return functions; }
    public void setFunctions(Set<Function> x) { functions = x; }

    @Override
    public int compareTo(@NotNull BusinessUnit that) {

        if (this == that) {
            return 0;
        }

        int ret = this.getTable1().compareTo(that.getTable1());
        if (ret != 0) {
            return ret;
        }

        BusinessUnit b1 = this;
        BusinessUnit b2 = that;

        while (b1.getNumAncestors() > b2.getNumAncestors()) {

            b1 = b1.getParent();

            if (Objects.equals(b1, b2)) {

                return 1;
            }
        }
        while (b1.getNumAncestors() < b2.getNumAncestors()) {

            b2 = b2.getParent();

            if (b1.equals(b2)) {

                return -1;
            }
        }

        if (b1.getNumAncestors() > 0) {

            BusinessUnit p1 = b1.getParent();
            BusinessUnit p2 = b2.getParent();
            while ( (p1 != null) &&
                    (p2 != null) ) {

                if (p1.equals(p2)) {

                    ret = b1.getIdentifier().compareToIgnoreCase(b2.getIdentifier());
                    if (ret != 0) {
                        return ret;
                    }
                    return b1.getIdentifier().compareTo(b2.getIdentifier());
                }
                b1 = p1;
                b2 = p2;
                p1 = p1.getParent();
                p2 = p2.getParent();
            }
        }

        ret = b1.getIdentifier().compareToIgnoreCase(b2.getIdentifier());
        if (ret != 0) {
            return ret;
        }
        return b1.getIdentifier().compareTo(b2.getIdentifier());
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object other) {
        return FooGlobals.equalsIdOrFields(this, other,
                                           (that) -> Objects.equals(identifier, that.identifier) &&
                                                     Objects.equals(table1, that.table1));
    }

    @Override
    public int hashCode() {
        return FooGlobals.hashIdOrFields(id, identifier, table1);
    }

    @Override
    public String toString() {
        return "BusinessUnit(" + id + " co=" + ((table1) == null ? "null" : table1.getId()) +
               " " + identifier + ")";
    }

}
