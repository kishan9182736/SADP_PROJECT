
1️⃣ Open PowerShell / Windows Terminal as Administrator
2️⃣ Install WSL + Default Ubuntu
wsl --install


Installs WSL 2 and Ubuntu by default.

If Ubuntu is already installed, skip to Step 4.

3️⃣ (Optional) Set WSL 2 as Default
wsl --set-default-version 2


Ensures any future Linux distros use WSL 2.

4️⃣ Check Installed Distros
wsl --list --verbose


Output example:

  NAME      STATE           VERSION
* Ubuntu    Stopped         2

5️⃣ Launch Ubuntu
wsl


First launch: it will ask you to create a Linux username & password.

6️⃣ Update Ubuntu Packages
sudo apt update -y
sudo apt upgrade -y


Ensures all packages are up-to-date.

✅ After this, WSL 2 is ready, and you can install Docker inside Ubuntu or enable Docker Desktop integration.

---

# 🟢 Step-by-Step Explanation

### 1. Update system packages

```bash
sudo apt update -y
```

* Updates the package index on Ubuntu (or Debian-based Linux).
* Ensures you get the latest versions when installing software.

---

### 2. Install Docker

```bash
sudo apt install docker.io -y
```

* Installs **Docker Engine** from Ubuntu repositories.
* `docker.io` is the package name for Docker.

---

### 3. Check Docker service status

```bash
sudo systemctl status docker
```

* Checks if Docker is **running** and **enabled at startup**.
* If it’s not running:

```bash
sudo systemctl start docker
sudo systemctl enable docker
```

---

### 4. Add user to Docker group

```bash
sudo usermod -aG docker ubuntu
```

* Adds the user `ubuntu` to the `docker` group.
* This allows running `docker` **without sudo**.
* After running, you should **logout and login again** for it to take effect.

> ⚠️ In WSL, your username might not be `ubuntu`. Use `whoami` to check your username:

```bash
whoami
sudo usermod -aG docker <your-username>
```

---

### 5. Install Docker Compose manually

```bash
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

* Downloads **Docker Compose** binary directly from GitHub.
* Makes it executable.
* After this, you can run `docker-compose` from anywhere.

---

### 6. Check Docker Compose version

```bash
docker-compose --version
```

* Confirms installation.
* Output should be something like `Docker Compose version v2.17.3`.

---

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
