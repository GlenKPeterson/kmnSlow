package yoyodyne;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static yoyodyne.ComparatorContract.CompToZero.*;

/**
 Created by gpeterso on 3/28/17.
 */
public class ComparatorContract {
    public enum CompToZero {
        LTZ {
            @Override public String english() { return "less than"; }
            @Override public boolean vsZero(int i) { return i < 0; }
        },
        GTZ {
            @Override public String english() { return "greater than"; }
            @Override public boolean vsZero(int i) { return i > 0; }
        },
        EQZ {
            @Override public String english() { return "equal to"; }
            @Override public boolean vsZero(int i) { return i == 0; }
        };
        public abstract String english();
        public abstract boolean vsZero(int i);
    }

    private static class Named<T> {
        final T a;
        final String name;
        Named(T theA, String nm) {a = theA;name = nm; }
    }

    private static <T> Named<T> t2(T a, String c) {
        return new Named<>(a, c);
    }

    private static <T>
    void pairComp(Named<T> first, CompToZero comp, Named<T> second,
                  Comparator<T> comparator) {
        assertTrue("The " + first.name + " item must be " + comp.english() +
                   " the " + second.name,
                   comp.vsZero(comparator.compare(first.a, second.a)));
    }

    /**
     Tests the various properties the Comparable contract is supposed to uphold.  Also tests that
     the behavior of compareTo() is compatible with equals() and hashCode() which is strongly
     suggested, but not actually required.
     Write your own test if you don't want that.
     Expects three unique objects.
     The first must be less than the second, which in turn is less than the third.

     See note in class documentation.
     */
    // Many of the comments in this method are paraphrases or direct quotes from the Javadocs for
    // the Comparable interface.  That is where this contract is specified.
    // https://docs.oracle.com/javase/8/docs/api/
    public static <T>
    void testComparator(T least1, T middle1, T greatest1, Comparator<T> comparator) {

        // TODO: Do we want to ensure that comparators are serializable?

        AtomicBoolean anySame = new AtomicBoolean();
        EqualsContract.permutations(Arrays.asList(least1, middle1, greatest1),
                                    (T a, T b) -> {
                                        if (a == b) {
                                            anySame.set(true);
                                        }
                                        return null;
                                    });
        if (anySame.get()) {
            throw new IllegalArgumentException("You must provide three pair of different objects in order");
        }

        int i = 0;
        for (T item : Arrays.asList(least1, middle1, greatest1)) {
            i++;
            // null is not an instance of any class, and e.compareTo(null) should throw a
            // NullPointerException
            try {
                //noinspection ResultOfMethodCallIgnored
                comparator.compare(item, null);
                fail("comparator.compare(item, null) should throw an exception " +
                     "even though e.equals(null) returns false, but item " + i + " did not.");
            } catch (RuntimeException ignore) {
                // Previously we had allowed NullPointerException and IllegalArgumentException.
                // Kotlin throws IllegalStateException, so we now expect any RuntimeException
                // to be thrown.
            }

            try {
                //noinspection ResultOfMethodCallIgnored
                comparator.compare(null, item);
                fail("comparator.compare(null, item) should throw an exception " +
                     "even though e.equals(null) returns false, but item " + i + " did not.");
            } catch (RuntimeException ignore) {
                // Previously we had allowed NullPointerException and IllegalArgumentException.
                // Kotlin throws IllegalStateException, so we now expect any RuntimeException
                // to be thrown.
            }
        }

        Named<T> least = t2(least1, "Least");
        Named<T> middle = t2(middle1, "Middle");
        Named<T> greatest = t2(greatest1, "Greatest");

        for (Named<T> pair : Arrays.asList(least, middle, greatest)) {
            // Consistent with equals: (e1.compareTo(e2) == 0) if and only if e1.equals(e2)
            pairComp(pair, EQZ, pair, comparator);
        }

        pairComp(least, LTZ, middle, comparator);
        pairComp(least, LTZ, greatest, comparator);
        pairComp(middle, LTZ, greatest, comparator);

        pairComp(greatest, GTZ, middle, comparator);
        pairComp(greatest, GTZ, least, comparator);
        pairComp(middle, GTZ, least, comparator);
    }
}
