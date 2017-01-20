
import com.yaps.petstore.server.domain.*;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class launches all the domain tests of the application
 */
public final class AllDomainTests extends TestCase {

    public AllDomainTests() {
        super();
    }

    public AllDomainTests(final String s) {
        super(s);
    }

    //==================================
    //=            Test suite          =
    //==================================
    public static TestSuite suite() {

        final TestSuite suite = new TestSuite();

        // Domain
        suite.addTest(CustomerTest.suite());

        // JPA
        suite.addTest(JPACustomerTest.suite());
        suite.addTest(JPANamedQueriesTest.suite());
        
        // DAO
        suite.addTest(CustomerDAOTest.suite());
        suite.addTest(CategoryDAOTest.suite());
        suite.addTest(ProductDAOTest.suite());
        suite.addTest(ItemDAOTest.suite());
        suite.addTest(OrderLineDAOTest.suite());
        suite.addTest(OrderDAOTest.suite());

        return suite;
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
