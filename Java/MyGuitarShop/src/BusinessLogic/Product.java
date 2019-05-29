package BusinessLogic;

/**
 * This class contains set of constructors used to facilitate data exchange, through encapsulation,
 * between Products table in MyGuitarShop SQL database and GUI.
 */
public class Product {
    private long id;
    private String categoryName;
    private String productCode;
    private String productName;
    private String description;
    private double listPrice, discount;
    private String dateAdded;

    public Product(long id, String categoryName, String productCode, String productName, String description, double listPrice, double discount, String dateAdded) {
        this.id = id;
        this.categoryName = categoryName;
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.listPrice = listPrice;
        this.discount = discount;
        this.dateAdded = dateAdded;
    }

    public Product(String categoryName, String productCode, String productName, String description, double listPrice, double discount) {
        this.id = 0;
        this.categoryName = categoryName;
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.listPrice = listPrice;
        this.discount = discount;
        this.dateAdded = null;
    }

    public Product() {
        this.id = 0;
        this.categoryName = null;
        this.productCode = null;
        this.productName = null;
        this.description = null;
        this.listPrice = 0;
        this.discount = 0;
        this.dateAdded = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", listPrice=" + listPrice +
                ", discount=" + discount +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
