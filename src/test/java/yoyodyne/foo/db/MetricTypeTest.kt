package yoyodyne.foo.db

import yoyodyne.CompareToContract.testCompareTo
import yoyodyne.EqualsContract.equalsDistinctHashCode
import yoyodyne.TestGlobals
import yoyodyne.foo.util.HibernateUtil
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class MetricTypeTest {

    @BeforeTest
    fun before() { TestGlobals.testDbInMemory() }

    @AfterTest
    fun after() { HibernateUtil.commit() }

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