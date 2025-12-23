# 🍕 Food Ordering Patterns – Java Design Patterns Demo

A fun, interactive demo showcasing **6 classic Design Patterns** applied to a simple food ordering system.

Built with **pure Java 17** (no frameworks) for the backend and static HTML/CSS/JS for the frontend. Everything runs in Docker.

### Design Patterns Demonstrated (6 Total)

1. **Factory** – Creates different food items (Pizza, Burger)
2. **Command** – Encapsulates placing an order as a command
3. **Observer** – Notifies customers when their order is ready
4. **Singleton** – Ensures only one global OrderManager exists
5. **Adapter** – Converts JSON order data to XML format
6. **Strategy** – Switches between payment methods (Card vs Cash)

### How It Works

- A lightweight **HTTP server** written in plain Java listens on port 9999
- The frontend (served by Nginx) has buttons for each pattern
- Clicking a button sends a request like `http://localhost:9999/?request=factory:pizza`
- The Java server processes the request and returns a nicely formatted text response with emojis and explanations
- All pattern logic is simulated in the response – perfect for learning!

### Quick Start (Docker – Recommended)

```bash
docker compose up --build

Access the Demo

Frontend (Web UI): Open your browser and go to
http://localhost:3000
Backend API (direct test):
http://localhost:9999Example requests:
http://localhost:9999/?request=test
http://localhost:9999/?request=factory:pizza
http://localhost:9999/?request=all  (runs all patterns)


Ports Used

3000 → Frontend (Nginx serving your HTML/JS)
9999 → Java backend server

Project Structure
textFoodOrderingPatterns/
├── backend/          → All Java source files (SimpleServer.java + ClientHandler)
├── frontend/         → index.html, CSS, JS (the interactive UI)
├── Dockerfile        → Multi-stage build (compile with JDK → run with JRE)
├── docker-compose.yml
└── README.md
Stop the Application
Bashdocker compose down
Enjoy exploring the patterns – click the buttons and see how each one works in action! 🚀
Happy learning!
