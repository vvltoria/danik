import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FoodOrderClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public void start() {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
        ) {
            Object obj = in.readObject();
            if (obj instanceof Menu) {
                Menu menu = (Menu) obj;
                System.out.println(menu);

                System.out.print("Введите ваше имя: ");
                String clientName = scanner.nextLine();

                List<Dish> orderedDishes = new ArrayList<>();
                while (true) {
                    System.out.print("Введите номер блюда для добавления в заказ (0 для завершения): ");
                    int choice;
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Пожалуйста, введите числовое значение.");
                        continue;
                    }
                    if (choice == 0) {
                        break;
                    }
                    if (choice > 0 && choice <= menu.getDishes().size()) {
                        orderedDishes.add(menu.getDishes().get(choice - 1));
                        System.out.println("Добавлено: " + menu.getDishes().get(choice - 1).getName());
                    } else {
                        System.out.println("Неверный выбор. Попробуйте снова.");
                    }
                }

                if (orderedDishes.isEmpty()) {
                    System.out.println("Вы не выбрали ни одного блюда.");
                    return;
                }

                System.out.print("Введите адрес доставки: ");
                String address = scanner.nextLine();

                Date orderDate = new Date();


                Order order = new Order(clientName, orderDate, orderedDishes, address);
                out.writeObject(order);
                out.flush();

                Object response = in.readObject();
                if (response instanceof String) {
                    System.out.println("Сервер: " + response);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка клиента: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FoodOrderClient client = new FoodOrderClient();
        client.start();
    }
}
