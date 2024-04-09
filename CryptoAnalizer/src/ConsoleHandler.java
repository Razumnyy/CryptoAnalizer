import encrypto.EncryptScenario;
import encrypto.EncryptionManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Класс ConsoleHandler предназначен для работы с консолью. Он осуществляет вывод информации
 * в консоль и обработку данных, полученных от пользователя через консоль.
 */
public class ConsoleHandler {

    private final EncryptionManager encryptionManager = EncryptionManager.getInstance();
    private final FileDataManger fileDataManger = FileDataManger.getInstance();
    private static ConsoleHandler consoleHandler;          // Ссылка на экзмепляр класса


    private ConsoleHandler() {
    }

    /**
     * Создает единственный экзмепляр класс ConsoleHandler, если он еще не создан.
     * @return ConsoleHandler - ссылка на экзмепляр класса.
     */
    public static ConsoleHandler getInstance() {
        if (consoleHandler == null)
            return consoleHandler = new ConsoleHandler();
        else
            return consoleHandler;
    }

    /**
     * Метод выводит информацию в консоль для пользователя
     * и обрабатывает полученные данные.
     */
    public void showConsoleIfo() {
        boolean isWaitingSelectScenario = true;
        String consoleText = null;

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Введите номер сценария из перечисленных: \n" +
                    "1. Шифрование файла по ключу \n" +
                    "2. Дешифрование файла по ключу \n" +
                    "3. Дешифрование файла с помощью brute force \n");

            while (isWaitingSelectScenario) {

                consoleText = scanner.nextLine();

                try {
                    switch (Integer.parseInt(consoleText)) {
                        case 1:
                            cryptoScenario(scanner,EncryptScenario.ENCRYPT_BY_KEY);       // сценарий шифрования файла по ключу
                            break;
                        case 2:
                            decryptScenario(scanner,EncryptScenario.DECRYPT_BY_KEY);      // сценарий расшифровки файла по ключу
                            break;
                        case 3:
                            bruteForceScenario(scanner,EncryptScenario.BRUT_FORCE);       // сценарий расшифровки файла перебором всех ключей
                            break;
                        default:
                            System.out.println("Введите корректный номер сценария.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Недопустимое значение.");
                }

                isWaitingSelectScenario = false;

            }
        }
    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла по ключу
     * @param scanner  ссылка на экзепляр класса сканер
     */
    private void decryptScenario(Scanner scanner, EncryptScenario encryptScenario) {
        final int key = getKeyFromConsole(scanner);                                    // Получаем ключ из консоли
        final Path inputPath = getInputPathFromConsole(scanner,encryptScenario);       // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner,encryptScenario);     // Получаем путь к файлу для записи

        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key,encryptScenario);

        // Устанавливаем пути к файлам для считывания и записи
        this.fileDataManger.initialize(inputPath,outputPath);
    }

    /**
     * Обрабатывает сценарий работы программы связанный с шифрованием файла по ключу
     * Получает от пользователя ключ и пути к файлам для чтения / записи
     * @param scanner  ссылка на экзепляр класса сканер
     */
    private void cryptoScenario(Scanner scanner,EncryptScenario encryptScenario) {
        final int key = getKeyFromConsole(scanner);                                    // Получаем ключ из консоли
        final Path inputPath = getInputPathFromConsole(scanner,encryptScenario);       // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner,encryptScenario);     // Получаем путь к файлу для записи


        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key,encryptScenario);

        // Устанавливаем пути к файлам для считывания и записи
        this.fileDataManger.initialize(inputPath,outputPath);

    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла перебором всех ключей
     * @param scanner  ссылка на экзепляр класса сканер
     */
    private void bruteForceScenario(Scanner scanner,EncryptScenario encryptScenario) {
        final int key = 0;                                                                // Заглушка
        final Path inputPath = getInputPathFromConsole(scanner,encryptScenario);          // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner,encryptScenario);        // Получаем путь к файлу для записи


        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key,encryptScenario);

        // Устанавливаем пути к файлам для считывания и записи
        this.fileDataManger.initialize(inputPath,outputPath);
    }

    /**
     * Запрашивает у пользователя ключ для шифрования / дешифрования файла по ключу
     * проверяет переданный ключ на валидность
     * @param scanner - ссылка на экземпляр класса Scanner для вывода инф в консоль
     * @return int - валидный ключ
     */
    private int getKeyFromConsole(Scanner scanner){
        boolean isWaitingForKey = true;
        int keyFromConsole = 0;

        System.out.printf("[Негативное значение = смещение в лево, положительное = смещение в право]\n " +
                "Введите ключ в диапазоне от -%d до %d \n",Alphabet.rusAlphabet.length-1,Alphabet.rusAlphabet.length-1);

        while (isWaitingForKey) {
            try {
                keyFromConsole = Integer.parseInt(scanner.nextLine());
                if (Math.abs(keyFromConsole) > Alphabet.rusAlphabet.length - 1)
                    throw new NumberFormatException();

                isWaitingForKey = false;

            } catch (NumberFormatException ex) {
                System.out.println("Введите корректный номер ключа");
            }
        }

        return keyFromConsole;
    }

    /**
     * Получает из консоли путь к файлу из которого нужно считать данные
     * проверяет переданный путь на валидность
     * @param scanner  ссылка на сканнер
     * @param encryptScenario  сенарий шифрования
     * @return Path  путь к файлу для считывания
     */
    private Path getInputPathFromConsole(Scanner scanner, EncryptScenario encryptScenario){
        boolean isWaitingFilePath = true;
        Path inputPath = null;

        System.out.printf("Введите абсолютный путь к файлу, который нужно %S: \n",
                (encryptScenario == EncryptScenario.ENCRYPT_BY_KEY ? "зашифровать" : "дешифровать"));

        while (isWaitingFilePath) {
            try {
                inputPath = Path.of(scanner.nextLine());
                if (!inputPath.isAbsolute() || !Files.exists(inputPath))
                    throw new NumberFormatException();

                isWaitingFilePath = false;

            } catch (NumberFormatException ex) {
                System.out.println("Введите корректный путь к файлу");
            }
        }

        return inputPath;
    }

    /**
     * Получает из консоли путь к файлу в который нужно записать данные
     * Проверяет переданный путь на валидность
     * @param scanner  ссылка на сканнер
     * @param encryptScenario  сенарий шифрования
     * @return Path - путь к файлу для записи
     */
    private Path getOutputPathFromConsole(Scanner scanner, EncryptScenario encryptScenario){
        boolean isWaitingFilePath = true;
        Path outputPath = null;

        System.out.println("Введите абсолютный путь к файлу, в который необходимо записать результат:");

        while (isWaitingFilePath) {
            try {
                outputPath = Path.of(scanner.nextLine()).normalize();
                if (!outputPath.isAbsolute() || !Files.exists(outputPath))
                    throw new NumberFormatException();

                isWaitingFilePath = false;

            } catch (NumberFormatException ex) {
                System.out.println("Введите корректный путь к файлу");
            }
        }

        return outputPath;
    }

}
