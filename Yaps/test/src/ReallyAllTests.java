
import com.barkbank.verifier.VerifyCreditCardServletTest;
import com.yaps.petstore.common.delegate.CatalogDelegateTest;
import com.yaps.petstore.common.delegate.CustomerDelegateTest;
import com.yaps.petstore.common.delegate.OrderDelegateTest;
import com.yaps.petstore.server.domain.*;
import com.yaps.petstore.server.service.CatalogServiceTest;
import com.yaps.petstore.server.service.CustomerServiceTest;
import com.yaps.petstore.server.service.OrderServiceTest;
import com.yaps.petstore.server.util.uidgen.UniqueIdGeneratorTest;
import com.yaps.petstore.server.cart.ShoppingCartTest;
import com.yaps.petstore.web.VisualiseCatalogTest;
import com.yaps.petstore.web.WebTest;
import com.yaps.petstore.web.servlet.CreateCustomerServletTest;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class launches all the tests of the application
 */
public final class ReallyAllTests extends TestCase {

    public ReallyAllTests() {
        super();
    }

    public ReallyAllTests(final String s) {
        super(s);
    }

    //==================================
    //=            Test suite          =
    //==================================
    public static TestSuite suite() {

        final TestSuite suite = new TestSuite();

        // Domain
        suite.addTest(AllDomainTests.suite());

        // Service
        suite.addTest(CatalogServiceTest.suite());
        suite.addTest(CustomerServiceTest.suite());
        suite.addTest(OrderServiceTest.suite());

        // Shopping Cart
        suite.addTest(ShoppingCartTest.suite());

          // Delegate
        suite.addTest(CatalogDelegateTest.suite());
        suite.addTest(CustomerDelegateTest.suite());
        suite.addTest(OrderDelegateTest.suite());

        // Util
        suite.addTest(UniqueIdGeneratorTest.suite());

        // Web
        suite.addTest(WebTest.suite());
        suite.addTest(CreateCustomerServletTest.suite());

        // JSP
        suite.addTest(VisualiseCatalogTest.suite());

        // Bank
        suite.addTest(VerifyCreditCardServletTest.suite());

        return suite;
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
