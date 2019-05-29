package BusinessLogic;

import java.util.ArrayList;
import java.util.List;

public class MockProductDataSource implements DAO<Product> {
    private List<Product> products;

    public MockProductDataSource() {
        products = new ArrayList<>();
        products.add(new Product(1, "strat","Guitars", "Fender Stratocaster", "The Fender Stratocaster is the electric guitar design that changed the world.", 699.00, 30.00, "2018-11-24 14:56:22.057"));
        //products.add(new Product(2,));
        products.add(new Product(2, "les_paul","Guitars", "Gibson Les Paul", "This Les Paul guitar offers a carved top and humbucking pickups.", 1199.00, 30.00, "2018-11-24 14:56:22.057"));
    }

    @Override
    public void add(Product toAdd) {
        Product newProduct;
        newProduct = toAdd;
        newProduct.setId(products.size() + 1);
        products.add(newProduct);
    }

    @Override
    public Product get(long id) {
        for (Product currentProduct : products) {
            if (currentProduct.getId() == id) {
                return currentProduct;
            }
        }

        return null;
    }

    @Override
    public List<Product> getAll() {

        return products;
    }

    @Override
    public void update(Product product) {
        Product currentProduct = get(product.getId());

        if (currentProduct != null) {
            currentProduct.setProductCode(product.getProductCode());
            currentProduct.setProductName(product.getProductName());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setListPrice(product.getListPrice());
            currentProduct.setDiscount(product.getDiscount());
            currentProduct.setDateAdded(product.getDateAdded());
        }
    }


    @Override
    public void delete(long id) {
        int intId = Math.toIntExact(id);
        products.remove((intId - 1));
    }
}