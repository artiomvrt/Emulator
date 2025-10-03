import java.io.*;
import java.util.*;

public class Emulator {

    public static void main(String[] args) {
        // 1️⃣ Проверяем, что переданы 2 аргумента
        if (args.length < 2) {
            System.out.println("Использование: java Emulator <путь_к_VFS> <путь_к_стартовому_скрипту>");
            return;
        }

        String vfsPath = args[0];
        String scriptPath = args[1];

        // 2️⃣ Отладочный вывод параметров
        System.out.println("=== Отладочный вывод параметров ===");
        System.out.println("Путь к VFS: " + vfsPath);
        System.out.println("Путь к стартовому скрипту: " + scriptPath);
        System.out.println("====================================\n");

        // Имя виртуальной файловой системы берём из пути
        String vfsName = new File(vfsPath).getName();

        // 3️⃣ Выполняем команды из стартового скрипта, если он существует
        runScript(scriptPath, vfsName);

        // 4️⃣ Переходим в интерактивный режим
        runInteractive(vfsName);
    }

    // === Чтение и выполнение команд из скрипта ===
    private static void runScript(String scriptPath, String vfsName) {
        File scriptFile = new File(scriptPath);
        if (!scriptFile.exists()) {
            System.out.println("⚠️ Стартовый скрипт не найден: " + scriptPath + "\n");
            return;
        }

        System.out.println("=== Выполнение стартового скрипта ===");

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Пропускаем пустые строки и комментарии
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) continue;

                // Показываем как будто пользователь ввёл команду
                System.out.println("[" + vfsName + "]$ " + line);
                executeCommand(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения скрипта: " + e.getMessage());
        }

        System.out.println("=== Конец выполнения скрипта ===\n");
    }

    // === Интерактивный режим ===
    private static void runInteractive(String vfsName) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[" + vfsName + "]$ ");
            String input = scanner.nextLine();
            executeCommand(input);
        }
    }

    // === Выполнение одной команды ===
    private static void executeCommand(String commandInput) {
        // Подстановка переменных окружения
        for (Map.Entry<String, String> env : System.getenv().entrySet()) {
            commandInput = commandInput.replace("$" + env.getKey(), env.getValue());
        }

        // Разбиваем строку на команду и аргументы
        String[] parts = commandInput.trim().split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) return;

        String cmd = parts[0];
        String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);

        // Обработка команд
        switch (cmd) {
            case "exit":
                System.out.println("Выход из эмулятора...");
                System.exit(0);
                break;
            case "ls":
                System.out.println("Вызвана команда ls с аргументами: " + String.join(", ", cmdArgs));
                break;
            case "cd":
                System.out.println("Вызвана команда cd с аргументами: " + String.join(", ", cmdArgs));
                break;
            case "$HOME":
                System.out.println(System.getProperty("user.home"));
                break;
            default:
                System.out.println("Неизвестная команда: " + cmd);
        }
    }
}
