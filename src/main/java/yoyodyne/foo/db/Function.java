package yoyodyne.foo.db;

import org.jetbrains.annotations.NotNull;
import yoyodyne.foo.util.FooGlobals;
import java.util.Objects;

public class Function implements Comparable<Function>,
                                 BarOwnable {

    private long id;
    private FunctionType functionType;
    private BusinessUnit businessUnit;
    private Table1 table1;

    public Function(
            @NotNull Table1 co,
            @NotNull FunctionType ft,
            @NotNull BusinessUnit bu
    ) {
        table1 = co;functionType = ft;businessUnit = bu;
    }

    @Override
    public long getId() { return id; }
    public void setId(long x) { id = x; }

    public FunctionType getFunctionType() { return functionType; }
    public void setFunctionType(FunctionType x) { functionType = x; }

    @NotNull
    public BusinessUnit getBusinessUnit() { return businessUnit; }
    public void setBusinessUnit(@NotNull BusinessUnit x) { businessUnit = x; }

    @Override
    public @NotNull Table1 getTable1() { return table1; }
    public void setTable1(Table1 x) { table1 = x; }

    @Override
    public int compareTo(@NotNull Function that) {
        if (this == that) {
            return 0;
        }

        int ret = this.getBusinessUnit().compareTo(that.getBusinessUnit());
        if (ret != 0) {
            return ret;
        }

        return this.getFunctionType().compareTo(that.getFunctionType());
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object other) {
        return FooGlobals.equalsIdOrFields(this, other,
                                           (that) -> Objects.equals(table1, that.table1) &&
                                                     Objects.equals(functionType, that.functionType) &&
                                                     Objects.equals(businessUnit, that.businessUnit));
    }

    @Override
    public int hashCode() {
        return FooGlobals.hashIdOrFields(id, table1, functionType, businessUnit);
    }

    @Override public String toString() {
        return "Function(" + id + " " + businessUnit + " " + functionType + ")";
    }

}

