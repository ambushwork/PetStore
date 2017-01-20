package com.yaps.petstore.server.util.persistence;

import com.yaps.petstore.server.domain.DomainObject;
import com.yaps.petstore.common.exception.DataAccessException;
import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.util.uidgen.UniqueIdGenerator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This class follows the Data Access Object (DAO) Design Pattern. It uses JPA
 * to store entity values in a database. Every concrete DAO class should extends
 * this class.
 */
public abstract class AbstractDataAccessObject<K, E> {

    // ======================================
    // =             Attributes             =
    // ======================================
    protected Class<E> _entityClass;

    protected EntityManager _em;
    protected EntityTransaction _tx;
    private boolean isUnmanagedTransactionStarted;

    // Used for logging
    private final transient String _cname = this.getClass().getName();
    private static final String sname = AbstractDataAccessObject.class.getName();

    // ======================================
    // =            Constructors            =
    // ======================================
    public AbstractDataAccessObject() {
        try {
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
            this._entityClass = (Class<E>) actualTypeArguments[1];
        } catch (ClassCastException e) {
            this._entityClass = null;
        }
    }

    public AbstractDataAccessObject(Class<E> entityClass) {
       // setEntityClass(entityClass); // Too early!
        _entityClass = entityClass;
    }

    public AbstractDataAccessObject(EntityManager em, Class<E> entityClass) {
        _entityClass = entityClass;
        _em = em;
    }

    public AbstractDataAccessObject(String persistenceUnitName) {
        Type superclass = getClass().getGenericSuperclass();
        try {
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
            this._entityClass = (Class<E>) actualTypeArguments[1];
        } catch (ClassCastException e) {
            this._entityClass = null;
        }
        initEntityManager(persistenceUnitName);
    }


    private void initEntityManager(String persistenceUnitName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        _em = emf.createEntityManager();
        try {
            _tx = _em.getTransaction();
        } catch (Exception e) {
            _tx = null;
        }
    }

    public void setEntityManager(EntityManager em) {
        _em = em;
    }

    public void setEntityClass(Class<E> entityClass) {
        _entityClass = entityClass;
    }

    private void beginTransaction() {
        if (_tx != null && !_tx.isActive()) {
            _tx.begin();
        }
    }

    private void endTransaction() {
        if (_tx != null) {
            _tx.commit();
        }
    }

    // ======================================
    // =       Generic CRUD methods         =
    // ======================================
    public void persist(E entity) {
        beginTransaction();
        _em.persist(entity);
        endTransaction();
    }

    public void remove(E entity) {
        beginTransaction();
        _em.remove(entity);
        endTransaction();
    }

    public E findById(K id) throws ObjectNotFoundException {
        E result;
        if (id == null) {
            throw new ObjectNotFoundException();
        }
        result = _em.find(_entityClass, id);
        if (result == null) {
            throw new ObjectNotFoundException();
        }
        return result;
    }

    public void merge(E entity) {
        beginTransaction();
        _em.merge(entity);
        endTransaction();
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets all the attributes for the entity from the database.
     *
     * @param id Object identifier to be found in the persistent layer
     * @return DomainObject the entity with all its attributs set
     * @throws ObjectNotFoundException is thrown if the entity id not found in
     * the persistent layer
     * @throws DataAccessException is thrown if there's a persistent problem
     */
    public final DomainObject findByPrimaryKey(final String id) throws ObjectNotFoundException {
        return this.select(id);
    }

    /**
     * This method gets all the attributes for the entity from the database.
     *
     * @param id Object identifier to be found in the persistent layer
     * @return DomainObject the entity with all its attributs set
     * @throws ObjectNotFoundException is thrown if the entity id not found in
     * the persistent layer
     */
    public final DomainObject select(final String id) throws ObjectNotFoundException {
        final String mname = "select";
        Trace.entering(getCname(), mname, id);

        E result = findById((K) id);
        DomainObject entity = (DomainObject) result;

        Trace.exiting(getCname(), mname, entity);
        return (DomainObject) result;
    }

    /**
     * This method return all the entities from the database.
     *
     * @return collection of DomainObject
     * @throws ObjectNotFoundException is thrown if the collection is empty
     */
    public Collection<E> findAll() throws ObjectNotFoundException {
        return selectAll();
    }

    /**
     * This method return all the entities from the database.
     *
     * @return collection of DomainObject
     * @throws ObjectNotFoundException is thrown if the collection is empty
     */
    public final Collection<E> selectAll() throws ObjectNotFoundException {
        final String mname = "selectAll";
        Trace.entering(getCname(), mname);

        int beginIndex = _entityClass.getName().lastIndexOf('.');
        beginIndex++;
        String shortClassName = _entityClass.getName().substring(beginIndex);
        Query query = _em.createNamedQuery(shortClassName + ".findAll");
        List<E> entities = query.getResultList();
        if (entities.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        Trace.exiting(getCname(), mname, new Integer(entities.size()));
        return entities;
    }

    /**
     * This method inserts an entity into the database.
     *
     * @param entity Domain entity to be inserted
     * @throws DuplicateKeyException is thrown when an identical entity is
     * already in the persistent layer
     */
    public final void insert(final DomainObject entity) throws DuplicateKeyException {
        final String mname = "insert";
        Trace.entering(getCname(), mname, entity);

        // Sets the entity Id if necessary
        // e.g. if the @GeneratedValue strategy of the @Id column is not defined
        /* 
         * if ( entity.getId() == null )
         entity.setId("" + getUniqueId());
         */
        try {
            findById((K) entity.getId());
            throw new DuplicateKeyException();
        } catch (ObjectNotFoundException e) {
            /* TODO - possible PersistenceException with hibernate:
             * org.hibernate.PersistentObjectException: detached entity passed to persist: ...
             * see https://forum.hibernate.org/viewtopic.php?p=2404032
             */
            if (entity.getId() == null) {
                persist((E) entity);
            } else {
                merge((E) entity);
            }
        }

    }

    /**
     * This method updates an entity in the database.
     *
     * @param entity Object to be updated in the database
     * @throws ObjectNotFoundException is thrown if the entity id not found in
     * the database
     */
    public final void update(final DomainObject entity) throws ObjectNotFoundException {
        final String mname = "update";
        Trace.entering(getCname(), mname, entity);

        findById((K) entity.getId());
        merge((E) entity);
    }

    /**
     * This method deletes an entity from the database.
     *
     * @param id identifier of the entity to be deleted
     * @throws ObjectNotFoundException is thrown if the entity id not found in
     * the persistent layer
     */
    public void remove(final String id) throws ObjectNotFoundException {
        final String mname = "remove";
        Trace.entering(getCname(), mname, id);

        E entity = findById((K) id);
        merge(entity); // mandatory!
        remove(entity);
    }

    /**
     * This method returns a unique identifer generated by the system.
     *
     * @return a unique identifer
     */
    public final String getUniqueId() {
        return UniqueIdGenerator.getInstance().getUniqueId(getCounterName());
    }

    /**
     * This method returns a unique identifer generated by the system.
     *
     * @param domainClassName name of a domain class (e.g. Customer, Product,
     * Order, ...
     * @return a unique identifer
     */
    public final String getUniqueId(final String domainClassName) {
        return UniqueIdGenerator.getInstance().getUniqueId(domainClassName);
    }

    protected abstract String getCounterName();

    protected String getCname() {
        return _cname;
    }
}
