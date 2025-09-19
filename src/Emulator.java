import java.util.*;

public class Emulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String vfsName = "myvfs"; // Название виртуальной файловой системы

        // Основной цикл эмулятора терминала
        while (true) {
            // Вывод приглашения командной строки
            System.out.print("[" + vfsName + "]$ ");
            String commandInput = scanner.nextLine();

            // Замена переменных окружения (например, $HOME) на их значения
            for (Map.Entry<String, String> env : System.getenv().entrySet()) {
                commandInput = commandInput.replace("$" + env.getKey(), env.getValue());
            }

            // Разбивка введенной команды на части (команда + аргументы)
            String[] parts = commandInput.trim().split("\\s+");
            if (parts.length == 0 || parts[0].isEmpty()) {
                continue; // Пропуск пустых команд
            }

            String cmd = parts[0]; // Основная команда (первое слово)
            String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length); // Аргументы команды

            // Обработка различных команд
            if (cmd.equals("exit")) {
                System.out.println("Выход из эмулятора...");
                break; // Завершение программы
            } else if (cmd.equals("ls")) {
                // Команда для отображения содержимого директории
                System.out.println("Вызвана команда ls с аргументами: " + String.join(", ", cmdArgs));
            } else if (cmd.equals("cd")) {
                // Команда для смены текущей директории
                System.out.println("Вызвана команда cd с аргументами: " + String.join(", ", cmdArgs));
            } else if (cmd.equals("$HOME")) {
                System.out.println(System.getProperty("user.home"));
            } else {
                // Обработка неизвестных команд
                System.out.println("Неизвестная команда: " + cmd);
            }

        }

        scanner.close(); // Закрытие сканера
    }
}