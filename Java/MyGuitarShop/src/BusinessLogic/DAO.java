package BusinessLogic;

import java.util.List;

/**Data Access Object Interface**/
public interface DAO<T> {

    /**
     * Method adds new record to database.
     *
     * @param toAdd - Product object that will be transformed into a record in Product table in database.
     */
    void add(T toAdd);

    /**
     * Method return product by it's id.
     * If there is no such product, returns null
     *
     * @param id - ID of a product to be retrieved.
     * @return one row from database packed into single Product object.
     */
    T get(long id);

    /**
     * Method returns all products from database.
     *
     * @return all records from Products table in database as a list of Product objects
     * where each row is packed into a single Product object.
     */
    List<T> getAll();

    void update(T product);

    /**
     * Deletes product by ID if exists.
     *
     * @param id - ID of a product to be deleted.
     */
    void delete(long id);
}
