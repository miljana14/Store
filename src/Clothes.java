public class Clothes extends Product{
    private String size;
    private String color;

    public Clothes(String name, String brand, double price, String size, String color) {
        super(name, brand, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
