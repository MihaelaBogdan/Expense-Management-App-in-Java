import java.time.LocalDate;

public class Expense {
    private final int id;
    private final String category;
    private final String description;
    private final double amount;
    private final LocalDate date;

    public Expense(int id, String category, String description, double amount, LocalDate date) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
