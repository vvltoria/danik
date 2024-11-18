import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FoodOrderServer {
    private static final int PORT = 12345;
    private Menu menu;

    public FoodOrderServer() {
        menu = new Menu();
        menu.addDish(new Dish("Пицца Маргарита", 8.99));
        menu.addDish(new Dish("Борщ", 5.49));
        menu.addDish(new Dish("Салат Цезарь", 6.99));
        menu.addDish(new Dish("Бургер", 7.99));
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен и слушает порт " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новое подключение от " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, menu).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private Menu menu;

        public ClientHandler(Socket socket, Menu menu) {
            this.socket = socket;
            this.menu = menu;
        }

        @Override
        public void run() {
            try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {

                out.writeObject(menu);
                out.flush();


                Object obj = in.readObject();
                if (obj instanceof Order) {
                    Order order = (Order) obj;
                    System.out.println("Получен заказ от клиента:");
                    System.out.println(order);
                    out.writeObject("Ваш заказ принят и будет доставлен по адресу: " + order.getDeliveryAddress());
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка при обработке клиента: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                    System.out.println("Соединение с клиентом закрыто.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        FoodOrderServer server = new FoodOrderServer();
        server.start();
    }
}
