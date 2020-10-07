package yoyodyne.foo.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yoyodyne.foo.db.Idd;
import java.util.Arrays;
import java.util.function.Predicate;

public final class FooGlobals {

    public static int hashIdOrFields(long id, Object... fields) {
        if (id == 0) {
            return Arrays.hashCode(fields);
        }

        return (int) id;
    }

    public static <T extends  Idd> boolean equalsIdOrFields(
            @NotNull T orig,
            @Nullable Object other,
            @NotNull Predicate<T> tests) {
        if (orig == other) {
            return true;
        }
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) orig.getClass();
        if ( !clazz.isInstance(other) ) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final T that = (T) other;

        if ( (orig.getId() != 0) && (that.getId() != 0) ) {
            return (orig.getId() == that.getId());
        }

        return tests.test(that);
    }
}