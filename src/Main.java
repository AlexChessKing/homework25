import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Basket basket;
        File file = new File("basket.bin");

        // проверяем существует ли файл basket.bin
        if (file.exists()) {
            // если существует, то восстанавливаем корзину из файла
            basket = Basket.loadFromBinFile(file);
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

            basket.addToCart(productNum, amount);
            basket.saveBin(file);
        }

        basket.printCart();
    }
}
