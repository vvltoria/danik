import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String clientName;      
    private Date orderDate;      
    private List<Dish> orderedDishes;
    private String deliveryAddress;

    public Order(String clientName, Date orderDate, List<Dish> orderedDishes, String deliveryAddress) {
        this.clientName = clientName;
        this.orderDate = orderDate;
        this.orderedDishes = orderedDishes;
        this.deliveryAddress = deliveryAddress;
    }


    public String getClientName() {
        return clientName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Dish> getOrderedDishes() {
        return orderedDishes;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Заказ:\n");
        sb.append("Имя клиента: ").append(clientName).append("\n");
        sb.append("Дата заказа: ").append(orderDate).append("\n");
        sb.append("Блюда:\n");
        for (Dish dish : orderedDishes) {
            sb.append("- ").append(dish).append("\n");
        }
        sb.append("Адрес доставки: ").append(deliveryAddress);
        return sb.toString();
    }
}
