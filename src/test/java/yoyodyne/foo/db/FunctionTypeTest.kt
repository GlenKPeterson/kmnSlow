package yoyodyne.foo.db

import yoyodyne.CompareToContract.testCompareTo
import yoyodyne.EqualsContract.equalsDistinctHashCode
import kotlin.test.Test

class FunctionTypeTest {

    @Test
    fun testBasics() {
        testCompareTo(FunctionType(Table1("a"), "c"),
                      FunctionType(Table1("a"), "c"),
                      FunctionType(Table1("a"), "d"),
                      FunctionType(Table1("a"), "d"),
                      FunctionType(Table1("a"), "e"),
                      FunctionType(Table1("a"), "e"))

        testCompareTo(FunctionType(Table1("a"), "c"),
                      FunctionType(Table1("a"), "c"),
                      FunctionType(Table1("b"), "a"),
                      FunctionType(Table1("b"), "a"),
                      FunctionType(Table1("b"), "b"),
                      FunctionType(Table1("b"), "b"))

        equalsDistinctHashCode(FunctionType(Table1("a"), "b"),
                               FunctionType(Table1("a"), "b"),
                               FunctionType(Table1("a"), "b"),
                               FunctionType(Table1("a"), "c"))

        equalsDistinctHashCode(FunctionType(Table1("a"), "b"),
                               FunctionType(Table1("a"), "b"),
                               FunctionType(Table1("a"), "b"),
                               FunctionType(Table1("c"), "b"))
    }
}