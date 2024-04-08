import encrypto.EncryptScenario;
import encrypto.EncryptionManager;

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
     * и обрабатывает полученные от пользователя данные.
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
                            cryptoScenario(scanner);                // сценарий шифрования файла по ключу
                            break;
                        case 2:
                            decryptoScenario(scanner);              // сценарий расшифровки файла по ключу
                            break;
                        case 3:
                            bruteForceScenario(scanner);            // сценарий расшифровки файла перебором всех ключей
                            break;
                        default:
                            System.out.println("Введите корректный номер сценария.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Недопустимое значение.");
                }
            }
        }
    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла по ключу
     * @param scanner - ссылка на экзепляр класса сканер
     */
    private void decryptoScenario(Scanner scanner) {
        System.out.println("расшифровка файла по ключу");
    }

    /**
     * Обрабатывает сценарий работы программы связанный с шифрованием файла по ключу
     * @param scanner - ссылка на экзепляр класса сканер
     */
    private void cryptoScenario(Scanner scanner) {
        encryptionManager.initialize(getKeyFromConsole(scanner),EncryptScenario.ENCRYPT_BY_KEY);

    }

    /**
     * Обрабатывает сценарий работы программы связанный с расшифровкой файла перебором всех ключей
     * @param scanner - ссылка на экзепляр класса сканер
     */
    private void bruteForceScenario(Scanner scanner) {
        System.out.println("расшифровка файла перебором всех ключей");
    }

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
     * @param scanner - ссылка на сканнер
     * @param encryptMode - сенарий шифрования
     * @return Path - путь к файлу для считывания
     */
    private Path getInputPathFromConsole(Scanner scanner, EncryptScenario encryptMode){
        //TODO - доделать метод
        return null;
    }

    /**
     * Получает из консоли путь к файлу в который нужно записать данные
     * @param scanner - ссылка на сканнер
     * @param encryptMode - сенарий шифрования
     * @return Path - путь к файлу для записи
     */
    private Path getOutputPathFromConsole(Scanner scanner, EncryptScenario encryptMode){
        //TODO - доделать метод
        return null;
    }

}
