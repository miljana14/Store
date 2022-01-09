import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

public class Cashier {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void printReceipt(Map<Product, Double> cart, LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(formatter);
        System.out.println("Date: " + formatDateTime);
        System.out.println("---Products---\n");
        double subtotal = 0;
        double discount = 0;
        double total;
        for (Map.Entry<Product, Double> obj: cart.entrySet()){
            Product key = obj.getKey();
            Double quantity = obj.getValue();
            double sum = key.getPrice() * quantity;
            double disc = 0;
            String sumDecimal = df.format(sum);

            if (key instanceof PerishableProduct){
                System.out.print(key.getName() + " - " + key.getBrand() + "\n" +
                        quantity + " x $" + key.getPrice() + " = $" + sumDecimal + "\n");
                if (((PerishableProduct) key).getExpirationDate().equals(dateTime.toLocalDate())){
                    key.setPrice(sum/2);
                    disc = key.getPrice();
                    sumDecimal = df.format(disc);
                    System.out.println("#discount 50%" +" -$" +  df.format(key.getPrice()) + "\n");
                }
                else if ((dateTime.toLocalDate()).plusDays(5).equals(((PerishableProduct) key).getExpirationDate()) ||
                        (dateTime.toLocalDate()).plusDays(4).equals(((PerishableProduct) key).getExpirationDate()) ||
                        (dateTime.toLocalDate()).plusDays(3).equals(((PerishableProduct) key).getExpirationDate()) ||
                        (dateTime.toLocalDate()).plusDays(2).equals(((PerishableProduct) key).getExpirationDate()) ||
                        (dateTime.toLocalDate()).plusDays(1).equals(((PerishableProduct) key).getExpirationDate())){
                    key.setPrice(sum*0.1);
                    disc = key.getPrice();
                    sumDecimal = df.format(disc);
                    System.out.println("#discount 10%" +" -$" + df.format(key.getPrice()) + "\n");
                }
                else {
                    System.out.println();
                }

            }
            else if (key instanceof Clothes){
                System.out.print(key.getName() + " - " + key.getBrand() + " " +
                        ((Clothes) key).getSize() + " " + ((Clothes) key).getColor() + "\n" +
                        quantity + " x $" + key.getPrice() + " = $" + sumDecimal + "\n");
                if (!isWeekend(dateTime.toLocalDate())){
                    key.setPrice(sum*0.1);
                    disc = key.getPrice();
                    sumDecimal = df.format(disc);
                    System.out.println("#discount 10%" +" -$" + df.format(key.getPrice()) + "\n");
                }
                else {
                    System.out.println();
                }
            }
            else if (key instanceof Appliance){
                System.out.print(key.getName() + " - " + key.getBrand() + " " + ((Appliance) key).getModel() + "\n" +
                        quantity + " x $" + key.getPrice() + " = $" + sumDecimal + "\n");
                if (isWeekend(dateTime.toLocalDate()) && key.getPrice()>999){
                    key.setPrice(sum*0.05);
                    disc = key.getPrice();
                    sumDecimal = df.format(disc);
                    System.out.println("#discount 5%" +" -$" + df.format(key.getPrice()) + "\n");
                }
                else {
                    System.out.println();
                }
            }
            subtotal += sum;
            discount += disc;
        }
        df.setRoundingMode(RoundingMode.UP);
        String sub = df.format(subtotal);
        String dis = df.format(discount);
        total = subtotal - discount;
        String tot = df.format(total);

        System.out.println("-----------------------------------------------------------");
        System.out.println("SUBTOTAL:$"  + sub);
        System.out.println("DISCOUNT:-$" + dis + "\n");
        System.out.println("TOTAL:$" + tot);
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    public static void main(String[] args) {
        Map <Product, Double> map = new HashMap<>();
        map.put(new Food("apple","Brand A", 1.5, LocalDate.of(2021,06,14)), 2.45);
        map.put(new Beverage("milk","Brand M", 0.99, LocalDate.of(2022, 2, 2)), 3.0);
        map.put(new Clothes("T-shirt","Brand T", 15.99, "M", "violet"), 2.0);
        map.put(new Appliance("Laptop","Brand L", 2345, "Model L", LocalDate.of(2021, 3, 3), 1.125), 1.0);
        printReceipt(map,LocalDateTime.of(2021,06,14,12,34,56));
    }
}
