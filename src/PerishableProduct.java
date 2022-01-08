import java.time.LocalDate;

public class PerishableProduct extends Product{
    private LocalDate expirationDate;

    public PerishableProduct(String name, String brand, double price, LocalDate expirationDate) {
        super(name, brand, price);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

}
