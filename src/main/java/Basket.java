import java.io.*;

public class Basket {
    private String[] products; // массив продуктов
    private int[] prices; // массив цен
    private int[] arrCountProducts; // массив количества товаров

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.arrCountProducts = new int[products.length];
    }

    private Basket() {

    }

    // метод добавления amount штук продукта номер productNum в корзину
    public void addToCart(int productNum, int amount) {
        arrCountProducts[productNum] += amount;
    }

    // метод вывода на экран покупательской корзины
    public void printCart() {
        System.out.println("Ваша корзина:");
        int sumProducts = 0; // итоговая сумма чека
        for (int i = 0; i < arrCountProducts.length; i++) {
            if (arrCountProducts[i] != 0) {
                sumProducts += prices[i] * arrCountProducts[i];
                System.out.println(products[i] + " " + arrCountProducts[i] + " кг " + prices[i]
                        + " руб/кг " + arrCountProducts[i] * prices[i] + " руб в сумме");
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    // метод сохранения корзины в текстовый файл
    public void saveTxt(File textFile) throws IOException {
        try (FileWriter writer = new FileWriter(textFile)) {
            writer.write(products.length + "\n"); // записываем в первой строке размер массива продуктов
            for (int i = 0; i < products.length; i++) {
                writer.write(products[i] + "\t" + prices[i] + "\t" + arrCountProducts[i] + "\n");
            }
        }
    }

    // метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена
    public static Basket loadFromTxtFile(File textFile) {
        String[] products = null;
        int[] prices = null;
        int[] arrCountProducts = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            int size = Integer.parseInt(reader.readLine()); // считываем число для размера создаваемых массивов

            products = new String[size];
            prices = new int[size];
            arrCountProducts = new int[size];

            String s;

            for (int i = 0; i < size; i++) {
                s = reader.readLine();
                String[] parts = s.split("\t");
                products[i] = parts[0];
                prices[i] = Integer.parseInt(parts[1]);
                arrCountProducts[i] = Integer.parseInt(parts[2]);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        Basket basket = new Basket();
        basket.products = products;
        basket.prices = prices;
        basket.arrCountProducts = arrCountProducts;

        return basket;
    }

    public String[] getProducts() {
        return products;
    }
    public int[] getPrices() {
        return prices;
    }
}
