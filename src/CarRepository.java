import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class CarRepository {
    private final MyArrayList<Car> cars = new MyArrayList<>();

    public void add(Car car) {
        cars.add(car);
    }

    public Car get(int index) {
        return cars.get(index);
    }

    public Car remove(int index) {
        return cars.remove(index);
    }

    public int size() {
        return cars.size();
    }

    public List<Car> all() {
        return cars;
    }

    public List<Car> filterByMinPower(int minPower) {
        return cars.stream().filter(car -> car.getPower() >= minPower).toList();
    }

    public List<String> modelsUppercase() {
        return cars.stream().map(car -> car.getModel().toUpperCase(java.util.Locale.ROOT)).toList();
    }

    public List<Car> sortedByYear() {
        return cars.stream().sorted(Comparator.comparingInt(Car::getYear)).toList();
    }


    public int loadFromFile(Path path) throws IOException {
        int added = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank() || line.stripLeading().startsWith("#")) {
                    continue;
                }
                try {
                    String[] fields = line.split(";", -1);
                    if (fields.length != 3) {
                        throw new IllegalArgumentException("Ожидаются 3 поля через ';'");
                    }
                    Car car = new Car(fields[0], Integer.parseInt(fields[1].trim()),
                            Integer.parseInt(fields[2].trim()));
                    cars.add(car);
                    added++;
                } catch (IllegalArgumentException exception) {
                    System.out.println("Строка " + lineNumber + " пропущена: " + exception.getMessage());
                }
            }
        }
        return added;
    }
}
