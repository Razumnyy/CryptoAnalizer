import encrypto.EncryptionManager;

public class Runner {

    public static void main(String[] args) {

        // Отвечает за работу с пользователем
        // Обрабатывает полученные данные
        ConsoleHandler consoleHandler = ConsoleHandler.getInstance();
        consoleHandler.showConsoleIfo();

        // Отвечает за работу с файлом (чтение / запись)
        // Вызывает методы по шифрованию / дешефрованию файла
        FileDataManger fileDataManger = FileDataManger.getInstance();
        fileDataManger.launchFileDataManager();

    }
}