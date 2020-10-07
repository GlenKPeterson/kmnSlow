package yoyodyne;

import yoyodyne.ComparatorContract.CompToZero;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static yoyodyne.ComparatorContract.CompToZero.*;

/**
 Tests the various properties the Comparable contract is supposed to uphold.  If you think this is
 confusing, realize that like equals(), it is often not possible to implement a one-sided
 compareTo() correctly with inheritance - it's a broken concept, but it's still used so often that
 you have to do your best with it.

 I got the idea of contract-based testing from watching Bill Venners:
 https://www.youtube.com/watch?v=bCTZQi2dpl8
 */
@SuppressWarnings("WeakerAccess")
public class CompareToContract {

    @SuppressWarnings("rawtypes")
    private static class NamedPair {
        final Comparable a;
        final Comparable b;
        final String name;
        NamedPair(Comparable theA, Comparable theB, String nm) { a = theA; b = theB; name = nm; }
    }

    @SuppressWarnings("rawtypes")
    private static NamedPair t3(Comparable a, Comparable b, String c) {
        return new NamedPair(a, b, c);
    }

    @SuppressWarnings("unchecked")
    private static void pairComp(NamedPair first, CompToZero comp, NamedPair second) {
        assertTrue("Item A in the " + first.name + " pair must be " + comp.english() +
                   " item A in the " + second.name + " pair",
                   comp.vsZero(first.a.compareTo(second.a)));
        assertTrue("Item A in the " + first.name + " pair must be " + comp.english() +
                   " item B in the " + second.name + " pair",
                   comp.vsZero(first.a.compareTo(second.b)));
        assertTrue("Item B in the " + first.name + " pair must be " + comp.english() +
                   " item A in the " + second.name + " pair",
                   comp.vsZero(first.b.compareTo(second.a)));
        assertTrue("Item B in the " + first.name + " pair must be " + comp.english() +
                   " item B in the " + second.name + " pair",
                   comp.vsZero(first.b.compareTo(second.b)));
    }

    /**
     Tests the various properties the Comparable contract is supposed to uphold.  Also tests that
     the behavior of compareTo() is compatible with equals() and hashCode() which is strongly
     suggested, but not actually required.  Write your own test if you don't want that.  Expects
     three pair of unique objects.  Within a pair, the two objects should be equal.  Both objects in
     the first pair are less than the ones in the second pair, which in turn are less than the
     objects in the third pair.

     See note in class documentation.
     */
    // Many of the comments in this method are paraphrases or direct quotes from the Javadocs for
    // the Comparable interface.  That is where this contract is specified.
    // https://docs.oracle.com/javase/8/docs/api/
    @SuppressWarnings("unchecked")
    public static <S extends Comparable<? super S>, T1 extends S, T2 extends S, T3 extends S>
    void testCompareTo(T1 least1, T1 least2, T2 middle1, T2 middle2, T3 greatest1, T3 greatest2) {
        AtomicBoolean anySame = new AtomicBoolean();
        EqualsContract.permutations(Arrays.asList(least1, least2, middle1, middle2, greatest1, greatest2),
                                    (S a, S b) -> {
                                        if (a == b) {
                                            anySame.set(true);
                                        }
                                        return null;
                                    });
        if (anySame.get()) {
            throw new IllegalArgumentException("You must provide three pair of different objects in order");
        }

        NamedPair least = t3(least1, least2, "Least");
        NamedPair middle = t3(middle1, middle2, "Middle");
        NamedPair greatest = t3(greatest1, greatest2, "Greatest");

        for (NamedPair comp : Arrays.asList(least, middle, greatest)) {
            // Consistent with equals: (e1.compareTo(e2) == 0) if and only if e1.equals(e2)
            pairComp(comp, EQZ, comp);
            assertEquals(comp.name + " A must be compatibly equal to its paired B element", comp.a, comp.b);
            assertEquals(comp.name + " B must be compatibly equal to its paired A element", comp.b, comp.a);
        }

        int i = 0;
        for (@SuppressWarnings("rawtypes")
                Comparable comp : Arrays.asList(least1, least2, middle1, middle2, greatest1, greatest2)
        ) {
            i++;
            assertEquals("item.equals(itself) should have return true for item " + i, comp, comp);

            // It is strongly recommended (though not required) that natural orderings be consistent
            // with equals.

            // One exception is java.math.BigDecimal, whose natural ordering equates BigDecimal
            // objects with equal values and different precisions (such as 4.0 and 4.00).

            // null is not an instance of any class, and e.compareTo(null) should throw a
            // NullPointerException even though e.equals(null) returns false.
            try {
                //noinspection ConstantConditions,ResultOfMethodCallIgnored
                comp.compareTo(null);
                fail("e.compareTo(null) should throw a NullPointerException even though e.equals(null)" +
                     " returns false, but item " + i + "did not.");
            } catch (NullPointerException | IllegalArgumentException ignore) {
            }
            assertNotEquals("item.equals(null) should always be false.  Item " + i + " failed", null, comp);
        }

        pairComp(least, LTZ, middle);
        pairComp(least, LTZ, greatest);
        pairComp(middle, LTZ, greatest);

        pairComp(greatest, GTZ, middle);
        pairComp(greatest, GTZ, least);
        pairComp(middle, GTZ, least);
    }
}
