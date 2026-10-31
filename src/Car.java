import java.time.Year;

public class Car {
    private final String model;
    private final int power;
    private final int year;

    public Car(String model, int power, int year) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Модель не может быть пустой");
        }
        if (model.contains(";")) {
            throw new IllegalArgumentException("Модель не должна содержать ';'");
        }
        if (power <= 0 || power > 3000) {
            throw new IllegalArgumentException("Мощность должна быть от 1 до 3000 л.с.");
        }
        if (year < 1886 || year > Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Некорректный год выпуска");
        }

        this.model = model.trim();
        this.power = power;
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return model + " | " + power + " л.с. | " + year + " г.";
    }
}
