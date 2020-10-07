package yoyodyne.foo.db

import yoyodyne.CompareToContract.testCompareTo
import kotlin.test.Test

// TODO: This file is SLOW in the editor due to testCompareTo!
class UserTest {
    @Test
    fun testBasics() {
        testCompareTo(User("c", "d"),
                      User("c", "d"),
                      User("d", "d"),
                      User("d", "d"),
                      User("c", "e"),
                      User("c", "e"))

        testCompareTo(User("c", "d"),
                      User("c", "d"),
                      User("d", "d"),
                      User("d", "d"),
                      User("c", "e"),
                      User("c", "e"))

        testCompareTo(User("c", "d"),
                      User("c", "d"),
                      User("d", "d"),
                      User("d", "d"),
                      User("c", "e"),
                      User("c", "e"))

        testCompareTo(User("c", "d"),
                      User("c", "d"),
                      User("d", "d"),
                      User("d", "d"),
                      User("c", "e"),
                      User("c", "e"))

        testCompareTo(User("c", "d"),
                      User("c", "d"),
                      User("d", "d"),
                      User("d", "d"),
                      User("c", "e"),
                      User("c", "e"))

        testCompareTo(User("c", "d"),
                      User("c", "d"),
                      User("d", "d"),
                      User("d", "d"),
                      User("c", "e"),
                      User("c", "e"))

    }
}
