import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {
    private List<Dish> dishes;

    public Menu() {
        dishes = new ArrayList<>();
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Меню:\n");
        for (int i = 0; i < dishes.size(); i++) {
            sb.append((i + 1)).append(". ").append(dishes.get(i)).append("\n");
        }
        return sb.toString();
    }
}
