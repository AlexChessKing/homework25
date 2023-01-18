import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Картофель", "Капуста", "Свекла"};
        int[] prices = {65, 40, 50};

        System.out.println("Список продуктов для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/кг");
        }

        int[] arrCountProduct = new int[products.length]; // массив количества товаров
        int sumProducts = 0; // итоговая сумма чека

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String inputString = scanner.nextLine();

            if (inputString.equals("end")) {
                System.out.println("Ваша корзина:");
                for (int i = 0; i < arrCountProduct.length; i++) {
                    if (arrCountProduct[i] != 0) {
                        System.out.println(products[i] + " " + arrCountProduct[i] + " кг " + prices[i]
                                + " руб/кг " + arrCountProduct[i] * prices[i] + " руб в сумме");
                    }
                }
                System.out.println("Итого " + sumProducts + " руб");
                break;
            }

            String[] parts = inputString.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1; // номер продукта
            int productCount = Integer.parseInt(parts[1]); // количество продукта

            arrCountProduct[productNumber] += productCount;
            sumProducts += prices[productNumber] * productCount;
        }
    }
}
