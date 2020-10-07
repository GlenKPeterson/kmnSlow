package yoyodyne.foo.db

import yoyodyne.CompareToContract.testCompareTo
import yoyodyne.EqualsContract.equalsDistinctHashCode
import kotlin.test.Test

class TestBusinessUnit {

    @Test
    fun testBasics() {
        val coA = Table1("a")
        val coB = Table1("b")
        testCompareTo(BusinessUnit(coA, null, "c"),
                      BusinessUnit(coA, null, "c"),
                      BusinessUnit(coA, null, "d"),
                      BusinessUnit(coA, null, "d"),
                      BusinessUnit(coA, null, "e"),
                      BusinessUnit(coA, null, "e"))

        testCompareTo(BusinessUnit(coA, null, "c"),
                      BusinessUnit(coA, null, "c"),
                      BusinessUnit(coB, null, "a"),
                      BusinessUnit(coB, null, "a"),
                      BusinessUnit(coB, null, "b"),
                      BusinessUnit(coB, null, "b"))

        val buC = BusinessUnit(coA, null, "c")

        testCompareTo(buC,
                      BusinessUnit(coA, null, "c"),
                      BusinessUnit(coA, buC, "a"),
                      BusinessUnit(coA, buC, "a"),
                      BusinessUnit(coA, buC, "b"),
                      BusinessUnit(coA, buC, "b"))

        equalsDistinctHashCode(BusinessUnit(coA, null, "c"),
                               BusinessUnit(coA, null, "c"),
                               BusinessUnit(coA, null, "c"),
                               BusinessUnit(coA, null, "d"))

        equalsDistinctHashCode(BusinessUnit(coA, null, "c"),
                               BusinessUnit(coA, null, "c"),
                               BusinessUnit(coA, null, "c"),
                               BusinessUnit(coB, null, "c"))
    }
}
