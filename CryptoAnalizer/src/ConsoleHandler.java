import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Класс ConsoleHandler предназначен для работы с консолью. Он осуществляет вывод информации
 * в консоль и обработку данных, полученных от пользователя через консоль.
 */
public class ConsoleHandler {
    private static ConsoleHandler consoleHandler;                                               // Ссылка на экзмепляр класса
    private final EncryptionManager encryptionManager = EncryptionManager.getInstance();        // Ссылка на анализатор
    private final FileDataManger fileDataManger = FileDataManger.getInstance();                 // Ссылка на обработчик файлов
    private ConsoleHandler() {}

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
        String consoleText;

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Введите номер сценария из перечисленных: \n" +
                    "1. Шифрование файла по ключу (Русский язык текста) \n" +
                    "2. Дешифрование файла по ключу (Русский язык текста) \n" +
                    "3. Дешифрование файла с помощью brute force (Русский язык текста) \n" +
                    "4. Дешифрование файла с помощью статического анализа (Русский язык текста) \n" +
                    "Номер сценария: ");

            while (isWaitingSelectScenario) {

                consoleText = scanner.nextLine();
                isWaitingSelectScenario = false;
                try {
                    switch (Integer.parseInt(consoleText)) {
                        case 1:
                            cryptoScenario(scanner);       // сценарий шифрования файла по ключу
                            break;
                        case 2:
                            decryptScenario(scanner);      // сценарий расшифровки файла по ключу
                            break;
                        case 3:
                            bruteForceScenario(scanner);   // сценарий расшифровки файла перебором всех ключей
                            break;
                        case 4:
                            statAnalyzeScenario(scanner);  // сценарий расшифровки файла статическим анализом
                            break;
                        default:
                            throw new NumberFormatException(consoleText);
                    }
                } catch (NumberFormatException ex) {
                    System.out.printf("Недопустимое значение. (%s) \n" +
                                      "Номер сценария: " ,ex.getMessage());
                    isWaitingSelectScenario = true;
                }

            }
        }
    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла по ключу
     * @param scanner ссылка на экзепляр класса сканер
     */
    private void decryptScenario(Scanner scanner) {
        final int key = getKeyFromConsole(scanner);                                                    // Получаем ключ из консоли
        final Path inputPath = getInputPathFromConsole(scanner, EncryptScenario.DECRYPT_BY_KEY);       // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner);                                     // Получаем путь к файлу для записи

        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key, EncryptScenario.DECRYPT_BY_KEY);

        // Устанавливаем пути к файлам для считывания и записи
        this.fileDataManger.initialize(inputPath,outputPath);
    }

    /**
     * Обрабатывает сценарий работы программы связанный с шифрованием файла по ключу
     * Получает от пользователя ключ и пути к файлам для чтения / записи
     * @param scanner ссылка на экзепляр класса сканер
     */
    private void cryptoScenario(Scanner scanner) {
        final int key = getKeyFromConsole(scanner);                                                    // Получаем ключ из консоли
        final Path inputPath = getInputPathFromConsole(scanner, EncryptScenario.ENCRYPT_BY_KEY);       // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner);                                     // Получаем путь к файлу для записи

        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key, EncryptScenario.ENCRYPT_BY_KEY);

        // Устанавливаем пути к файлам для считывания и записи
        this.fileDataManger.initialize(inputPath,outputPath);

    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла статическим анализом
     * @param scanner ссылка на экзепляр класса сканер
     */
    private void statAnalyzeScenario(Scanner scanner) {
        final int key = 0;                                                                            // Заглушка
        final Path inputPath = getInputPathFromConsole(scanner, EncryptScenario.STAT_ANALYZER);       // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner);                                    // Получаем путь к файлу для записи

        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key, EncryptScenario.STAT_ANALYZER);

        // Устанавливаем пути к файлам для считывания и записи
        this.fileDataManger.initialize(inputPath,outputPath);
    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла перебором всех ключей
     * @param scanner ссылка на экзепляр класса сканер
     */
    private void bruteForceScenario(Scanner scanner) {
        final int key = 0;                                                                            // Заглушка
        final Path inputPath = getInputPathFromConsole(scanner, EncryptScenario.BRUT_FORCE);          // Получаем путь к файлу для чтения
        final Path outputPath = getOutputPathFromConsole(scanner);                                    // Получаем путь к файлу для записи

        // Устанавливаем ключ и режим работы шифроватора
        this.encryptionManager.initialize(key, EncryptScenario.BRUT_FORCE);

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

        System.out.printf("Введите ключ в диапазоне от 0 до %d: \n", Alphabet.getLengthAlphabet(AlphabetType.RU));

        while (isWaitingForKey) {
            try {
                keyFromConsole = Integer.parseInt(scanner.nextLine());
                if (keyFromConsole < 0 || keyFromConsole > Alphabet.getLengthAlphabet(AlphabetType.RU))
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

        System.out.printf("Введите абсолютный путь к файлу, который нужно %s: \n",
                (encryptScenario == EncryptScenario.ENCRYPT_BY_KEY ? "зашифровать" : "дешифровать"));

        while (isWaitingFilePath) {
            try {
                inputPath = Path.of(scanner.nextLine());
                if (!inputPath.isAbsolute() || !Files.exists(inputPath))                       // Проверяем,что путь абсолютный и существует
                    throw new NumberFormatException();

                if (inputPath.toFile().length() == 0){
                    System.out.printf("Размер файла %d \n",inputPath.toFile().length());       // Проверяем, что в файле есть данные
                    throw new NumberFormatException();
                }

                isWaitingFilePath = false;

            } catch (NumberFormatException ex) {
                System.out.println("Введите корректный путь к файлу");
            } catch (InvalidPathException ex){
                System.out.printf("Введите корректный путь к файлу (%s) \n",ex.getMessage()) ;
            }
        }
        return inputPath;
    }

    /**
     * Получает из консоли путь к файлу в который нужно записать данные
     * Проверяет переданный путь на валидность
     * @param scanner  ссылка на сканнер
     * @return Path - путь к файлу для записи
     */
    private Path getOutputPathFromConsole(Scanner scanner){
        boolean isWaitingFilePath = true;
        Path outputPath = null;

        System.out.println("Введите абсолютный путь к файлу, в который необходимо записать результат:");

        while (isWaitingFilePath) {
            try {
                outputPath = Path.of(scanner.nextLine()).normalize();
                if (!outputPath.isAbsolute())                                           // Проверяем,что путь абсолютный
                    throw new NumberFormatException();

                isWaitingFilePath = false;

            } catch (NumberFormatException ex) {
                System.out.println("Введите корректный путь к файлу");
            } catch (InvalidPathException ex){
                System.out.printf("Введите корректный путь к файлу (%s) \n",ex.getMessage()) ;
            }
        }
        return outputPath;
    }

}
