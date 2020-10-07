package yoyodyne.foo.db

import yoyodyne.CompareToContract.testCompareTo
import yoyodyne.EqualsContract.equalsDistinctHashCode
import kotlin.test.Test

class FunctionTest {

    @Test
    fun testBasics() {
        val coA = Table1("a")
        val coB = Table1("b")
        val ftC = FunctionType(coA, "c")
        val ftD = FunctionType(coA, "d")
        val buE = BusinessUnit(coA, null, "e")
        val buF = BusinessUnit(coA, null, "f")

        equalsDistinctHashCode(Function(coA, ftC, buE),
                               Function(coA, ftC, buE),
                               Function(coA, ftC, buE),
                               Function(coB, ftC, buE))

        equalsDistinctHashCode(Function(coA, ftC, buE),
                               Function(coA, ftC, buE),
                               Function(coA, ftC, buE),
                               Function(coA, ftD, buE))

        equalsDistinctHashCode(Function(coA, ftC, buE),
                               Function(coA, ftC, buE),
                               Function(coA, ftC, buE),
                               Function(coA, ftC, buF))

        testCompareTo(Function(coA, ftC, buE),
                      Function(coA, ftC, buE),
                      Function(coA, ftD, buE),
                      Function(coA, ftD, buE),
                      Function(coA, ftD, buF),
                      Function(coA, ftD, buF))
    }
}