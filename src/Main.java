import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final CarRepository repository = new CarRepository();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        while (true) {
            System.out.println("\n=== УЧЁТ АВТОМОБИЛЕЙ ===");
            System.out.println("1. Добавить автомобиль вручную");
            System.out.println("2. Загрузить автомобили из файла");
            System.out.println("3. Показать все автомобили");
            System.out.println("4. Найти автомобили по мощности");
            System.out.println("5. Показать модели заглавными буквами");
            System.out.println("6. Сортировать по году выпуска");
            System.out.println("7. Показать автомобиль по номеру");
            System.out.println("8. Удалить автомобиль по номеру");
            System.out.println("9. Количество автомобилей");
            System.out.println("0. Выход");

            try {
                int choice = readInt("Команда: ");
                switch (choice) {
                    case 1 -> addManually();
                    case 2 -> load();
                    case 3 -> printCars(repository.all());
                    case 4 -> printCars(repository.filterByMinPower(readInt("Минимальная мощность: ")));
                    case 5 -> printStrings(repository.modelsUppercase());
                    case 6 -> printCars(repository.sortedByYear());
                    case 7 -> System.out.println(repository.get(readInt("Индекс (с 0): ")));
                    case 8 -> System.out.println("Удалён: " + repository.remove(readInt("Индекс (с 0): ")));
                    case 9 -> System.out.println("Количество: " + repository.size());
                    case 0 -> { System.out.println("Завершение работы."); return; }
                    default -> System.out.println("Неизвестная команда.");
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
                System.out.println("Ошибка: " + exception.getMessage());
            }
        }
    }


    private void addManually() {
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        int power = readInt("Мощность (л.с.): ");
        int year = readInt("Год выпуска: ");

        repository.add(new Car(model, power, year));
        System.out.println("Автомобиль добавлен.");
    }

    private void load() {
        System.out.print("Путь к TXT/CSV (например, data/cars.csv): ");
        String path = scanner.nextLine().trim();
        try {
            int count = repository.loadFromFile(Path.of(path));
            System.out.println("Загружено автомобилей: " + count);
        } catch (IOException | InvalidPathException exception) {
            System.out.println("Ошибка чтения файла: " + exception.getMessage());
        }
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Введите целое число.", exception);
        }
    }

    private void printCars(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ": " + cars.get(i));
        }
    }

    private void printStrings(List<String> values) {
        if (values.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        values.forEach(System.out::println);
    }
}
