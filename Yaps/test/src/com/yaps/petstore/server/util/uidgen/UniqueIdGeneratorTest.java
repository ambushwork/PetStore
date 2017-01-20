package com.yaps.petstore.server.util.uidgen;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import junit.framework.TestSuite;

/**
 * This class tests the Unique id generator class
 */
public final class UniqueIdGeneratorTest extends AbstractTestCase {

    public UniqueIdGeneratorTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(UniqueIdGeneratorTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    public void testUtilUniqueId() throws Exception {
        // Creates an unknown name
        final String name = "name" + getUniqueId();

        // Make sure this name doesn't exist
        try {
            findName(name);
            fail();
        } catch (ObjectNotFoundException e) {
        }

        // Gets the id
        final int firstValue = Integer.parseInt(getUniqueId(name));
        assertEquals("The value must be equal to 1", firstValue, 1);

        // Make sure this name exists
        try {
            findName(name);
        } catch (ObjectNotFoundException e) {
            fail();
        }

        // Calls the method twice
        getUniqueId(name);
        final int lastValue = Integer.parseInt(getUniqueId(name));

        assertEquals("The value must be equal to 3", lastValue, 3);

        deleteName(name);

        // Make sure the name doesn't exist anymore
        try {
            findName(name);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private String getUniqueId(final String name) {
        final String value;
        value = UniqueIdGenerator.getInstance().getUniqueId(name);
        return value;
    }
    
    private int getUniqueId() {
        return (int) (Math.random() * 100000);
    }


    private void findName(final String name) throws ObjectNotFoundException {
        final UniqueIdGeneratorDAO dao = new UniqueIdGeneratorDAO();
        dao.select(name);
    }

    private void deleteName(final String name) throws ObjectNotFoundException {
        final UniqueIdGeneratorDAO dao = new UniqueIdGeneratorDAO();
        dao.remove(name);
    }

}
