// ===== MAIN DEMO FILE =====
public class DesignPatternsDemo {
    public static void main(String[] args) {
        System.out.println("=== FOOD ORDERING SYSTEM - DESIGN PATTERNS DEMO ===\n");
        
        // 1. FACTORY PATTERN
        System.out.println("1. FACTORY PATTERN:");
        Food pizza = FoodFactory.createFood("Pizza");
        Food burger = FoodFactory.createFood("Burger");
        pizza.prepare();
        burger.prepare();
        
        // 2. COMMAND PATTERN
        System.out.println("\n2. COMMAND PATTERN:");
        OrderCommand pizzaCommand = new OrderCommand(pizza);
        pizzaCommand.execute();
        
        // 3. OBSERVER PATTERN
        System.out.println("\n3. OBSERVER PATTERN:");
        OrderNotifier notifier = new OrderNotifier();
        notifier.register(new Customer("Alice"));
        notifier.register(new Customer("Bob"));
        notifier.notifyAll("Your order is ready!");
        
        // 4. SINGLETON PATTERN
        System.out.println("\n4. SINGLETON PATTERN:");
        OrderManager manager1 = OrderManager.getInstance();
        OrderManager manager2 = OrderManager.getInstance();
        manager1.addOrder("Pizza Order");
        System.out.println("Same instance? " + (manager1 == manager2));
        
        // 5. ADAPTER PATTERN
        System.out.println("\n5. ADAPTER PATTERN:");
        JSONOrder jsonOrder = new JSONOrder("{item: 'Pizza'}");
        Adapter adapter = new JsonToXmlAdapter();
        adapter.convert(jsonOrder);
        
        // 6. STRATEGY PATTERN
        System.out.println("\n6. STRATEGY PATTERN:");
        PaymentContext cardPayment = new PaymentContext(new CardPayment());
        PaymentContext cashPayment = new PaymentContext(new CashPayment());
        cardPayment.pay(100);
        cashPayment.pay(100);
    }
}

// ===== 1. FACTORY PATTERN =====
interface Food { void prepare(); }

class Pizza implements Food {
    public void prepare() { System.out.println("  Making Pizza..."); }
}

class Burger implements Food {
    public void prepare() { System.out.println("  Making Burger..."); }
}

class FoodFactory {
    public static Food createFood(String type) {
        if (type.equals("Pizza")) return new Pizza();
        if (type.equals("Burger")) return new Burger();
        return null;
    }
}

// ===== 2. COMMAND PATTERN =====
interface Command { void execute(); }

class OrderCommand implements Command {
    private Food food;
    public OrderCommand(Food food) { this.food = food; }
    public void execute() { 
        System.out.println("  Executing order command...");
        food.prepare(); 
    }
}

// ===== 3. OBSERVER PATTERN =====
interface Observer { void update(String message); }

class Customer implements Observer {
    private String name;
    public Customer(String name) { this.name = name; }
    public void update(String message) { 
        System.out.println("  " + name + " received: " + message); 
    }
}

class OrderNotifier {
    private java.util.List<Observer> customers = new java.util.ArrayList<>();
    public void register(Observer customer) { customers.add(customer); }
    public void notifyAll(String message) {
        for (Observer customer : customers) {
            customer.update(message);
        }
    }
}

// ===== 4. SINGLETON PATTERN =====
class OrderManager {
    private static OrderManager instance;
    private java.util.List<String> orders = new java.util.ArrayList<>();
    
    private OrderManager() {}
    
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }
    
    public void addOrder(String order) {
        orders.add(order);
        System.out.println("  Order added: " + order);
    }
}

// ===== 5. ADAPTER PATTERN =====
class JSONOrder {
    private String data;
    public JSONOrder(String data) { this.data = data; }
    public String getData() { return data; }
}

class XMLOrder {
    public XMLOrder(String xmlData) {
        System.out.println("  XML created: " + xmlData);
    }
}

interface Adapter { XMLOrder convert(JSONOrder json); }

class JsonToXmlAdapter implements Adapter {
    public XMLOrder convert(JSONOrder json) {
        String xml = "<order>" + json.getData() + "</order>";
        return new XMLOrder(xml);
    }
}

// ===== 6. STRATEGY PATTERN =====
interface PaymentStrategy { void pay(int amount); }

class CardPayment implements PaymentStrategy {
    public void pay(int amount) { 
        System.out.println("  Paid $" + amount + " by Card"); 
    }
}

class CashPayment implements PaymentStrategy {
    public void pay(int amount) { 
        System.out.println("  Paid $" + amount + " by Cash"); 
    }
}

class PaymentContext {
    private PaymentStrategy strategy;
    public PaymentContext(PaymentStrategy strategy) { this.strategy = strategy; }
    public void pay(int amount) { strategy.pay(amount); }
}
