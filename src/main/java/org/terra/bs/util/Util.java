package org.terra.bs.util;

import java.util.ArrayList;
import java.util.Collection;

public class Util {

    /*
     * Hibernate provides own java.util.Collection namely
     * org.hibernate.collection.PersistenceBag However, PersistenceBag, does not
     * respect the collection API in regards to Collection.equals(Object) and
     * hence this utility
     */
    public static <T> boolean equal(Collection<T> a, Collection<T> b) {
        return new ArrayList<>(a).equals(new ArrayList<>(b));
    }

}
