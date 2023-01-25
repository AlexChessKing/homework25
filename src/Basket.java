import java.io.*;

public class Basket implements Serializable {
    private final String[] products; // массив продуктов
    private final int[] prices; // массив цен
    private final int[] arrCountProducts; // массив количества товаров

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.arrCountProducts = new int[products.length];
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

    // метод сохранения в файл в бинарном формате
    public void saveBin (File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // метод загрузки корзины из бинарного файла
    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }

    public String[] getProducts() {
        return products;
    }
    public int[] getPrices() {
        return prices;
    }
}
