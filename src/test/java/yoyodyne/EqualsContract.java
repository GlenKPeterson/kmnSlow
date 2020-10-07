package yoyodyne;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

/**
 Tests Reflexive, Symmetric, Transitive, Consistent, and non-nullity properties of the equals()
 contract.  If you think this is confusing, realize that there is no way to implement a
 one-sided equals() correctly with inheritance - it's a broken concept, but it's still used so
 often that you have to do your best with it.

 I got the idea of contract-based testing from watching Bill Venners:
 https://www.youtube.com/watch?v=bCTZQi2dpl8
 */
public class EqualsContract {

    /**
     Apply the given function against all unique pairings of items in the list.  Does this belong on Function2 instead
     of List?
     */
    static <T> void permutations(List<T> items, BiFunction<? super T,? super T,?> f) {
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                f.apply(items.get(i), items.get(j));
            }
        }
    }

    /**
     Tests Reflexive, Symmetric, Transitive, Consistent, and non-nullity properties of the equals()
     contract.  See note in class documentation.

     @param equiv1 First equivalent (but unique) object
     @param equiv2 Second equivalent (but unique) object (could be a different class)
     @param equiv3 Third equivalent (but unique) object (could be a different class)
     @param different Non-equivalent object with a (maybe) different hashCode (should be an otherwise compatible class)
     @param requireDistinctHashes if true, require that the fourth object have a different hashCode.  Otherwise,
     require that it have the same hashCode.
     @param <S> The super-class of all these objects - an interface or super-class within which they should be equal.
     */
    @SuppressWarnings("SimplifiableJUnitAssertion")
    public static <S, T1 extends S, T2 extends S, T3 extends S, T4 extends S>
    void equalsHashCode(T1 equiv1, T2 equiv2, T3 equiv3, T4 different, boolean requireDistinctHashes) {
        if ( (equiv1 == equiv2) ||
             (equiv1 == equiv3) ||
             (equiv1 == different) ||
             (equiv2 == equiv3) ||
             (equiv2 == different) ||
             (equiv3 == different) ) {
            throw new IllegalArgumentException("You must provide four different (having different memory locations) but 3 equivalent objects");
        }
        List<S> equivs = Arrays.asList(equiv1, equiv2, equiv3);

        //noinspection ObjectEqualsNull,ConstantConditions
        assertFalse("The different param should not allow itself to equal null",
                    different.equals(null));
        assertEquals("The different param must have the same hashCode as itself",
                     different.hashCode(), different.hashCode());
        //noinspection EqualsWithItself
        assertTrue("The different param must equal itself",
                   different.equals(different));

        int i = 0;
        // Reflexive
        for(S equiv : equivs) {
            i++;
            assertEquals("Param " + i + " must have the same hashCode as itself",
                         equiv.hashCode(), equiv.hashCode());
            if (requireDistinctHashes) {
                assertNotEquals("The hashCode of param " + i + " must not equal the" +
                                " hashCode of the different param.  If you meant to do that, use equalsSameHashCode()" +
                                " instead.",
                                equiv.hashCode(), different.hashCode());
            } else {
                assertEquals("The hashCode of param " + i + " must equal the" +
                             " hashCode of the different param  If you meant to do that, use equalsDistinctHashCode()" +
                             " instead.",
                             equiv.hashCode(), different.hashCode());
            }
            //noinspection EqualsWithItself
            assertTrue("Param " + i + " must be equal to itself",
                       equiv.equals(equiv));
            assertFalse("Param " + i + " cannot be equal to the different param",
                        equiv.equals(different));
            assertFalse("The different param cannot be equal to param " + i,
                        different.equals(equiv));

            // Check null
            //noinspection ObjectEqualsNull,ConstantConditions
            assertFalse("Param " + i + " cannot allow itself to equal null",
                        equiv.equals(null));
        }

        // Symmetric (effectively covers Transitive as well)
        permutations(equivs, (a, b) -> {
            assertEquals("Found an unequal hashCode while inspecting permutations: a=" + a + " b=" + b,
                         a.hashCode(), b.hashCode());
            assertTrue("Failed equals while inspecting permutations: a=" + a + " b=" + b,
                       a.equals(b));
            assertTrue("Failed reflexive equals while inspecting permutations",
                       b.equals(a));
            return null;
        });
    }

    /**
     Tests Reflexive, Symmetric, Transitive, Consistent, and non-nullity properties of the equals()
     contract.  See note in class documentation.

     @param equiv1 First equivalent (but unique) object
     @param equiv2 Second equivalent (but unique) object (could be a different class)
     @param equiv3 Third equivalent (but unique) object (could be a different class)
     @param different Non-equivalent object with the same hashCode as the previous three
     @param <S> The super-class of all these objects - an interface or super-class within which they should be equal.
     */
    public static <S, T1 extends S, T2 extends S, T3 extends S, T4 extends S>
    void equalsSameHashCode(T1 equiv1, T2 equiv2, T3 equiv3, T4 different) {
        equalsHashCode(equiv1, equiv2, equiv3, different, false);
    }

    /**
     Tests Reflexive, Symmetric, Transitive, Consistent, and non-nullity properties of the equals()
     contract.  See note in class documentation.

     @param equiv1 First equivalent (but unique) object
     @param equiv2 Second equivalent (but unique) object (could be a different class)
     @param equiv3 Third equivalent (but unique) object (could be a different class)
     @param different Non-equivalent object with a different hashCode (should be an otherwise compatible class)
     @param <S> The super-class of all these objects - an interface or super-class within which they should be equal.
     */
    public static <S, T1 extends S, T2 extends S, T3 extends S, T4 extends S>
    void equalsDistinctHashCode(T1 equiv1, T2 equiv2, T3 equiv3, T4 different) {
        equalsHashCode(equiv1, equiv2, equiv3, different, true);
    }
}
