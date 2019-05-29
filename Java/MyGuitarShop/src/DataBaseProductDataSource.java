import BusinessLogic.DAO;
import BusinessLogic.Product;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**This class implements methods for Data Access Object Interface**/
public class DataBaseProductDataSource implements DAO<Product> {

    private Connection con;

    public DataBaseProductDataSource() throws ClassNotFoundException {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=MyGuitarShop;user=bvcAdmin;password=Admin$2018";
        try {
            con = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is running, yeeeeeeeaaaaaaahhhhhhhh!!!");
    }

    @Override
    public void add(Product product) {
        Product productToAdd = product;
        int categoryId = 0;
        if (productToAdd.getCategoryName().equals("Guitars")) {
            categoryId = 1;
        }
        if (productToAdd.getCategoryName().equals("Basses")) {
            categoryId = 2;
        }
        if (productToAdd.getCategoryName().equals("Drums")) {
            categoryId = 3;
        }
        if (productToAdd.getCategoryName().equals("Keyboards")) {
            categoryId = 4;
        }

        try {
            PreparedStatement addNewProduct = con.prepareStatement("INSERT INTO Products\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, GETDATE());");
            addNewProduct.setInt(1, categoryId);
            addNewProduct.setString(2, productToAdd.getProductCode());
            addNewProduct.setString(3, productToAdd.getProductName());
            addNewProduct.setString(4, productToAdd.getDescription());
            addNewProduct.setDouble(5, productToAdd.getListPrice());
            addNewProduct.setDouble(6, productToAdd.getDiscount());
            addNewProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product get(long id) {
        try {
            PreparedStatement getById = con.prepareStatement("SELECT ProductID, CategoryName, ProductCode, ProductName, [Description], ListPrice, DiscountPercent, DateAdded\n" +
                    "FROM Products\n" +
                    "JOIN Categories\n" +
                    "ON Categories.CategoryID = Products.CategoryID\n" +
                    "WHERE ProductID = ?;");

            getById.setLong(1, id);
            ResultSet results = getById.executeQuery();
            results.next();
            return fromResultSet(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getAll() {
        try {
            PreparedStatement getAll = con.prepareStatement("SELECT ProductID,  CategoryName, ProductCode, ProductName, [Description], ListPrice, DiscountPercent, DateAdded\n" +
                    "FROM Products\n" +
                    "JOIN Categories\n" +
                    "ON Categories.CategoryID = Products.CategoryID;");
            ResultSet results = getAll.executeQuery();

            List<Product> toReturn = new ArrayList<>();

            while (results.next()) {
                Product currentPerson = fromResultSet(results);
                toReturn.add(currentPerson);
            }

            return toReturn;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void update(Product product) {
        Product productToUpdate = product;
        int categoryId = 0;
        if (productToUpdate.getCategoryName().equals("Guitars")) {
            categoryId = 1;
        }
        if (productToUpdate.getCategoryName().equals("Basses")) {
            categoryId = 2;
        }
        if (productToUpdate.getCategoryName().equals("Drums")) {
            categoryId = 3;
        }
        if (productToUpdate.getCategoryName().equals("Keyboards")) {
            categoryId = 4;
        }

        try {
            PreparedStatement updateById = con.prepareStatement("UPDATE Products\n" +
                    "SET CategoryID = ?, ProductCode = ?,  ProductName = ?, [Description] = ?, ListPrice = ?, DiscountPercent = ?, DateAdded = GETDATE()\n" +
                    "WHERE ProductID = ?;");
            updateById.setInt(1, categoryId);
            updateById.setString(2, productToUpdate.getProductCode());
            updateById.setString(3, productToUpdate.getProductName());
            updateById.setString(4, productToUpdate.getDescription());
            updateById.setDouble(5, productToUpdate.getListPrice());
            updateById.setDouble(6, productToUpdate.getDiscount());
            updateById.setLong(7, productToUpdate.getId());
            updateById.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement deleteById = con.prepareStatement("DELETE FROM OrderItems WHERE ProductID = ?; DELETE FROM Products WHERE ProductID = ?");
            deleteById.setLong(1, id);
            deleteById.setLong(2, id);
            deleteById.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is supplementary method that packs each row of a fetched table from database into a single Product object
     * and returns this object back.
     * @param set - table fetched from database with cursor set on current row.
     * @return current row packed into Product object.
     * @throws SQLException
     */
    private static Product fromResultSet(ResultSet set) throws SQLException {
        long id = set.getLong(1);
        String categoryName = set.getString(2);
        String productCode = set.getString(3);
        String productName = set.getString(4);
        String description = set.getString(5);
        double listPrice = set.getDouble(6);
        double discount = set.getDouble(7);
        String dateAdded = set.getString(8);

        return new Product(id, categoryName, productCode, productName, description, listPrice, discount, dateAdded);
    }
}
