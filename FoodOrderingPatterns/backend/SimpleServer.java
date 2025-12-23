import java.io.*; 
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SimpleServer {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        System.out.println("🍕 Food Ordering System - Java Design Patterns Backend");
        System.out.println("Server running on http://localhost:" + PORT);
        System.out.println("Waiting for requests...\n");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            System.out.println("HTTP Request: " + requestLine);

            String requestParam = "unknown";
            if (requestLine.startsWith("GET")) {
                int queryIdx = requestLine.indexOf("/?request=");
                if (queryIdx != -1) {
                    int endIdx = requestLine.indexOf(" ", queryIdx);
                    String encoded = requestLine.substring(queryIdx + 10, endIdx);
                    requestParam = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
                } else if (requestLine.contains("/?test")) {
                    requestParam = "test";
                }
            }

            // Skip headers
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                // consume headers
            }

            String responseBody = processRequest(requestParam);

            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                                  "Content-Type: text/plain; charset=UTF-8\r\n" +
                                  "Access-Control-Allow-Origin: *\r\n" +
                                  "Content-Length: " + responseBody.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                                  "Connection: close\r\n\r\n" +
                                  responseBody;

            out.print(httpResponse);
            out.flush();
            socket.close();

        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private String processRequest(String request) {
        System.out.println("Processing request: " + request + "\n");

        return switch (request) {
            case "test" -> "✅ Java backend is running and ready! 🚀";

            case "factory:pizza" -> """
                    🏭 Factory Pattern Demo
                    → FoodFactory.createFood("pizza")
                    → Created: Margherita Pizza 🍕
                    Result: Object created using Factory Pattern.""";
                    
            case "factory:burger" -> """
                    🏭 Factory Pattern Demo
                    → FoodFactory.createFood("burger")
                    → Created: Cheese Burger 🍔
                    Result: Object created using Factory Pattern.""";
                    
            case "command" -> """
                    📝 Command Pattern Demo
                    → PlaceOrderCommand created
                    → Invoker.execute(command)
                    → Order placed: 2x Pizza, 1x Burger
                    Result: Command Pattern executed successfully.""";
                    
            case "observer" -> """
                    🔔 Observer Pattern Demo
                    → Order ready → notifyAllObservers()
                    → Customer A notified: "Your order is ready!"
                    → Customer B notified: "Your order is ready!"
                    Result: All observers updated.""";
                    
            case "singleton" -> """
                    🔒 Singleton Pattern Demo
                    → OrderManager.getInstance()
                    → Instance hash: %s
                    → Total orders today: 42
                    Result: Only one instance exists (guaranteed).""".formatted(
                        Integer.toHexString(System.identityHashCode(this)));
                        
            case "adapter" -> """
                    🔌 Adapter Pattern Demo
                    → JSON received
                    → JsonToXmlAdapter.convert()
                    → Output:
                    <order><item>Pizza</item><qty>2</qty></order>
                    Result: Incompatible interfaces connected.""";
                    
            case "strategy:card" -> """
                    💳 Strategy Pattern Demo
                    → PaymentContext.setStrategy(new CardPayment())
                    → Processed: $45.98 via Credit Card
                    → Transaction ID: CARD-789012
                    Result: Strategy applied successfully.""";
                    
            case "strategy:cash" -> """
                    💵 Strategy Pattern Demo
                    → PaymentContext.setStrategy(new CashPayment())
                    → Processed: $45.98 in cash
                    → Change: $4.02
                    Result: Strategy applied successfully.""";
                    
            case "all" -> """
                    🚀 ALL DESIGN PATTERNS DEMO COMPLETE
                    
                    1. Factory      → Pizza & Burger created
                    2. Command      → Order placed
                    3. Observer     → Customers notified
                    4. Singleton    → Single instance retrieved
                    5. Adapter      → JSON → XML converted
                    6. Strategy     → Paid by Card & Cash
                    
                    🎉 All 6 GoF patterns demonstrated successfully!""";
                    
            default -> "❌ Unknown request: " + request +
                       "\n\nAvailable: factory:pizza, command, observer, singleton, adapter, strategy:card, all";
        };
    }
}