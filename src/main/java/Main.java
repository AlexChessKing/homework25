import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Basket basket;
        File jsonFile = new File("basket.json");
        ClientLog clientLog = new ClientLog();

        // проверяем существует ли файл basket.json
        if (jsonFile.exists()) {
            // если существует, то восстанавливаем корзину из файла
            basket = Basket.loadFromJsonFile(jsonFile);
        } else {
            // если не существует, то создаем новую пустую корзину
            basket = new Basket(new String[] {"Картофель", "Капуста", "Свекла"}, new int[] {65, 40, 50});
        }

        System.out.println("Список продуктов для покупки:");
        for (int i = 0; i < basket.getProducts().length; i++) {
            System.out.println(i + 1 + ". " + basket.getProducts()[i] + " " + basket.getPrices()[i] + " руб/кг");
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String inputString = scanner.nextLine();

            if (inputString.equals("end")) {
                break;
            }

            String[] parts = inputString.split(" ");

            int productNum = Integer.parseInt(parts[0]) - 1; // номер продукта
            int amount = Integer.parseInt(parts[1]); // количество продукта
            int enteredNum = Integer.parseInt(parts[0]);

            clientLog.log(enteredNum, amount);

            basket.addToCart(productNum, amount);
            basket.saveJson(jsonFile);
        }

        File logFile = new File("log.csv");
        // Проверяем существует ли файл log.csv
        if (!logFile.exists()) {
            // если лог-файл не существует, то создаем его и передаем первую строку с заголовками
            String[] head = {"productNum", "amount"};
            try (CSVWriter writer = new CSVWriter(new FileWriter(logFile))) {
                writer.writeNext(head);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        clientLog.exportAsCSV(logFile);
        basket.printCart();
    }
}
