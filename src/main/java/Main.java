import com.opencsv.CSVWriter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Basket basket;
        File file;
        ClientLog clientLog = new ClientLog();

        // Проверяем в настройках, нужно ли восстанавливать корзину из файла
        Parameters load = new Parameters("load");
        if (load.getEnabled()) {
            file = new File(load.getFileName());
            // восстанавливаем файл из того файла, расширение которого указано в настройках
            basket = (load.getFormat().equals("json")) ? Basket.loadFromJsonFile(file) : Basket.loadFromTxtFile(file);
        } else {
            // если восстанавливать не нужно, то создаем пустую корзину
            basket = new Basket(new String[]{"Картофель", "Капуста", "Свекла"}, new int[]{65, 40, 50});

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

                Parameters save = new Parameters("save");
                if (save.getEnabled()) {
                    // создаем файл с именем, указанным в настройках
                    File saveFile = new File(save.getFileName());
                    // сохраняем корзину в файл, расширение которого указано в настройках (json или text)
                    if (save.getFormat().equals("json")) {
                        basket.saveJson(saveFile);
                    } else {
                        basket.saveTxt(saveFile);
                    }
                }

                clientLog.log(enteredNum, amount);

                basket.addToCart(productNum, amount);
            }

            // сохраняем все выполненные действия в лог-файл, если это указано в настройках
            Parameters toLog = new Parameters("log");
            if (toLog.getEnabled()) {
                File logFile = new File(toLog.getFileName());
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
                clientLog.exportAsCSV(new File(toLog.getFileName()));
            }

            basket.printCart();
        }
    }
}