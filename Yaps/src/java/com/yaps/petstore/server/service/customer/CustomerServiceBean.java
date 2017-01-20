package com.yaps.petstore.server.service.customer;

import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.customer.CustomerDAO;
import com.yaps.petstore.server.service.AbstractRemoteService;
/* Do not check credit cart data here anymore
import com.yaps.petstore.common.locator.ejb.ServiceLocator;
import com.yaps.petstore.server.service.creditcard.CreditCardServiceLocal;
import com.yaps.petstore.server.service.creditcard.CreditCardServiceLocalHome;
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * This class is a session facade for all customer services.
 */
// @TransactionManagement(value=TransactionManagementType.CONTAINER)
@Stateless (name="CustomerSB")
public class CustomerServiceBean extends AbstractRemoteService implements CustomerService {

    // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "petstorePU", type = PersistenceContextType.TRANSACTION)
    private EntityManager _injectedEntityManager;
    private static final CustomerDAO _dao = new CustomerDAO();

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerServiceBean() {
    }

    @PostConstruct
    public void init() {
        _dao.setEntityManager(_injectedEntityManager);
    }
    // ======================================
    // =           Business methods         =
    // ======================================
    //@TransactionAttribute(value = TransactionAttributeType.NEVER)
    public CustomerDTO authenticate(final String customerId, final String password) throws FinderException, CheckException {
        final String mname = "authenticate";
        Trace.entering(getCname(), mname, new Object[]{customerId, password});

        checkId(customerId);
        if (password == null || "".equals(password))
            throw new CheckException("Invalid password");
        
        // Finds the object
        final Customer customer = (Customer)_dao.findByPrimaryKey(customerId);

        // Check if it's the right password, if not, a CheckException is thrown
        customer.matchPassword(password);

        // Transforms domain object into DTO
        final CustomerDTO customerDTO = transformCustomer2DTO(customer);

        Trace.exiting(getCname(), mname, customerDTO);
        return customerDTO;
    }

    //@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException {
        final String mname = "createCustomer";
        Trace.entering(getCname(), mname, customerDTO);

        if (customerDTO == null)
            throw new CheckException("Customer object is null");

        // Transforms DTO into domain object
        final Customer customer = new Customer(customerDTO.getId(), customerDTO.getFirstname(), customerDTO.getLastname());
        customer.setPassword(customerDTO.getPassword());
        customer.setCity(customerDTO.getCity());
        customer.setCountry(customerDTO.getCountry());
        customer.setState(customerDTO.getState());
        customer.setStreet1(customerDTO.getStreet1());
        customer.setStreet2(customerDTO.getStreet2());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setZipcode(customerDTO.getZipcode());
        customer.setEmail(customerDTO.getEmail());
        customer.setCreditCardExpiryDate(customerDTO.getCreditCardExpiryDate());
        customer.setCreditCardNumber(customerDTO.getCreditCardNumber());
        customer.setCreditCardType(customerDTO.getCreditCardType());

        customer.checkData();

        /* Do not check here if the credit card is valid
        	getCreditCardService().verifyCreditCard(customer.getCreditCard());
         */
        // Creates the object
        _dao.insert(customer);

        // Transforms domain object into DTO
        final CustomerDTO result = transformCustomer2DTO(customer);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    //@TransactionAttribute(value = TransactionAttributeType.NEVER)
    public CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException {
        final String mname = "findCustomer";
        Trace.entering(getCname(), mname, customerId);

    	checkId(customerId);
        // Finds the object
        final Customer customer = (Customer)_dao.findByPrimaryKey(customerId);

        // Transforms domain object into DTO
        final CustomerDTO customerDTO = transformCustomer2DTO(customer);

        Trace.exiting(getCname(), mname, customerDTO);
        return customerDTO;
    }

    //@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void deleteCustomer(final String customerId) throws RemoveException, CheckException {
        final String mname = "deleteCustomer";
        Trace.entering(getCname(), mname, customerId);

    	checkId(customerId);

        // Checks if the object exists
        try {
        	_dao.findByPrimaryKey(customerId);
        } catch (FinderException e) {
            throw new CheckException("Customer must exist to be deleted");
        }

        // Deletes the object
        try {
        	_dao.remove(customerId);
        } catch (ObjectNotFoundException e) {
            throw new RemoveException("Customer must exist to be deleted");
        }
    }

    //@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void updateCustomer(final CustomerDTO customerDTO) throws UpdateException, CheckException {
        final String mname = "updateCustomer";
        Trace.entering(getCname(), mname, customerDTO);

        if (customerDTO == null)
            throw new CheckException("Customer object is null");

    	checkId(customerDTO.getId());

    	final Customer customer;

        // Checks if the object exists
        try {
        	customer = (Customer)_dao.findByPrimaryKey(customerDTO.getId());
        } catch (FinderException e) {
            throw new CheckException("Customer must exist to be updated");
        }

        // Transforms DTO into domain object
        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setPassword(customerDTO.getPassword());
        customer.setCity(customerDTO.getCity());
        customer.setCountry(customerDTO.getCountry());
        customer.setState(customerDTO.getState());
        customer.setStreet1(customerDTO.getStreet1());
        customer.setStreet2(customerDTO.getStreet2());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setZipcode(customerDTO.getZipcode());
        customer.setEmail(customerDTO.getEmail());
        customer.setCreditCardExpiryDate(customerDTO.getCreditCardExpiryDate());
        customer.setCreditCardNumber(customerDTO.getCreditCardNumber());
        customer.setCreditCardType(customerDTO.getCreditCardType());

        customer.checkData();
        
        /* Do not check if the credit card is valid
        	getCreditCardService().verifyCreditCard(customer.getCreditCard());
		*/
        // Updates the object
        try {
        	_dao.update(customer);
        } catch (ObjectNotFoundException e) {
            throw new UpdateException("Customer must exist to be updated");
        }
    }

    //@TransactionAttribute(value = TransactionAttributeType.NEVER)
    public Collection<CustomerDTO> findCustomers() throws FinderException {
        final String mname = "findCustomers";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection<Customer> customers = _dao.selectAll();

        // Transforms domain objects into DTOs
        final Collection<CustomerDTO> customersDTO = transformCustomers2DTOs(customers);

        Trace.exiting(getCname(), mname, new Integer(customersDTO.size()));
        return customersDTO;
    }

    // ======================================
    // =          Private Methods           =
    // ======================================
    private CustomerDTO transformCustomer2DTO(final Customer customer) {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setCity(customer.getCity());
        customerDTO.setCountry(customer.getCountry());
        customerDTO.setFirstname(customer.getFirstname());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setLastname(customer.getLastname());
        customerDTO.setState(customer.getState());
        customerDTO.setStreet1(customer.getStreet1());
        customerDTO.setStreet2(customer.getStreet2());
        customerDTO.setTelephone(customer.getTelephone());
        customerDTO.setZipcode(customer.getZipcode());
        customerDTO.setCreditCardNumber(customer.getCreditCardNumber());
        customerDTO.setCreditCardType(customer.getCreditCardType());
        customerDTO.setCreditCardExpiryDate(customer.getCreditCardExpiryDate());
        return customerDTO;
    }

    private Collection<CustomerDTO> transformCustomers2DTOs(final Collection<Customer> customers) {
        final Collection<CustomerDTO> customersDTO = new ArrayList<CustomerDTO>();
        for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
            final Customer customer = iterator.next();
            customersDTO.add(transformCustomer2DTO(customer));
        }
        return customersDTO;
    }

    /**
     * This method returns a unique identifer generated by the system. 
     *
     * @return a unique identifer
     */
    public final String getUniqueId() {
        return _dao.getUniqueId();
    }
    /**
     * This method returns a unique identifer generated by the system. 
     *
     * @param domainClassName name of a domain class (Category, Product or Item)
     * @return a unique identifer
     */
    public final String getUniqueId(final String domainClassName) {
        return _dao.getUniqueId();
    }

}
