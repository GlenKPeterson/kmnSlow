package yoyodyne.foo.db

import org.organicdesign.testUtils.CompareToContract.testCompareTo
import org.organicdesign.testUtils.EqualsContract.equalsDistinctHashCode
import kotlin.test.Test
import kotlin.test.assertEquals

// TODO: This file is SLOW in the editor due to testCompareTo!
class UserTest {
    @Test
    fun testBasics() {
        val coA = Table1("a")
        val coB = Table1("b")
        equalsDistinctHashCode(User(coA, "c", "x@yoyodyne.com", "g"),
                               User(coA, "c", "x@yoyodyne.com", "g"),
                               User(coA, "c", "x@yoyodyne.com", "g"),
                               User(coB, "c", "x@yoyodyne.com", "g"))

        equalsDistinctHashCode(User(coA, "c", "x@yoyodyne.com", "g"),
                               User(coA, "c", "x@yoyodyne.com", "g"),
                               User(coA, "c", "x@yoyodyne.com", "g"),
                               User(coA, "d", "x@yoyodyne.com", "g"))

        testCompareTo(User(coA, "c", "x@yoyodyne.com", "d"),
                      User(coA, "c", "x@yoyodyne.com", "d"),
                      User(coA, "d", "x@yoyodyne.com", "d"),
                      User(coA, "d", "x@yoyodyne.com", "d"),
                      User(coA, "c", "x@yoyodyne.com", "e"),
                      User(coA, "c", "x@yoyodyne.com", "e"))

        val alice1 = User(coA, "alice", "x@yoyodyne.com", "g")
        alice1.firstNameC = "Alice"
        val alice2 = User(coA, "alice", "x@yoyodyne.com", "g")
        alice2.firstNameC = "Alice"
        val bob1 = User(coA, "bob", "x@yoyodyne.com", "g")
        bob1.firstNameC = "Bob"
        val bob2 = User(coA, "bob", "x@yoyodyne.com", "g")
        bob2.firstNameC = "Bob"

        val clara1 = User(coA, "clara", "x@yoyodyne.com", "g")
        val clara2 = User(coA, "clara", "x@yoyodyne.com", "g")

        testCompareTo(clara1, clara2,
                      alice1, alice2,
                      bob1, bob2)

        val clara3 = User(coA, "clara", "x@yoyodyne.com", "g")
        clara3.middleNameC = "m"
        val clara4 = User(coA, "clara", "x@yoyodyne.com", "g")
        clara4.middleNameC = "m"
        val clara5 = User(coA, "clara", "x@yoyodyne.com", "g")
        clara5.middleNameC = "n"
        val clara6 = User(coA, "clara", "x@yoyodyne.com", "g")
        clara6.middleNameC = "n"

        testCompareTo(clara1, clara2,
                      clara3, clara4,
                      clara5, clara6)

        testCompareTo(User(coA, "a", "x@yoyodyne.com", "g"),
                      User(coA, "a", "x@yoyodyne.com", "g"),
                      User(coA, "b", "x@yoyodyne.com", "g"),
                      User(coA, "b", "x@yoyodyne.com", "g"),
                      User(coA, "c", "x@yoyodyne.com", "g"),
                      User(coA, "c", "x@yoyodyne.com", "g"))

        assertEquals("x@yoyodyne.com", clara6.email)
    }
}
