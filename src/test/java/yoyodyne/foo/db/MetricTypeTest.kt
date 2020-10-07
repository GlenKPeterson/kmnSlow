package yoyodyne.foo.db

import yoyodyne.CompareToContract.testCompareTo
import yoyodyne.EqualsContract.equalsDistinctHashCode
import kotlin.test.Test

class MetricTypeTest {

    @Test
    fun testBasics() {
        val coA = Table1("a")
        val coB = Table1("b")
        equalsDistinctHashCode(MetricType(coA, "c"),
                               MetricType(coA, "c"),
                               MetricType(coA, "c"),
                               MetricType(coA, "d"))

        equalsDistinctHashCode(MetricType(coA, "c"),
                               MetricType(coA, "c"),
                               MetricType(coA, "c"),
                               MetricType(coB, "c"))

        testCompareTo(MetricType(coA, "c"),
                      MetricType(coA, "c"),
                      MetricType(coB, "a"),
                      MetricType(coB, "a"),
                      MetricType(coB, "b"),
                      MetricType(coB, "b"))
    }
}