import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс FileDataManager предоставляет функциональность для управления данными в файлах.
 * Он содержит методы для чтения данных из файлов, записи данных в файлы и другие операции,
 * связанные с управлением файлами данных.
 */
public class FileDataManger {

    private Path inputPath;                             // Путь к файлу для считывания
    private Path outputPath;                            // Путь к файлу для записи
    private final EncryptionManager encryptionManager         // Ссылка на крипто-анализатор
            = EncryptionManager.getInstance();
    private static FileDataManger fileDataManger;
    private FileDataManger(){}

    /**
     * Создает единственный экзмепляр класс FileDataManger, если он еще не создан.
     * @return FileDataManger - ссылка на экзмепляр класса.
     */
    public static FileDataManger getInstance(){
        if (fileDataManger == null)
            return fileDataManger = new FileDataManger();
        else
            return fileDataManger;
    }

    /**
     * Инициализирует экземпляр класса
     * @param inputPath - путь к файлу для считывания
     * @param outputPath - путь к файлу для записи
     */
    public void initialize(Path inputPath, Path outputPath){
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    /**
     * Запускает процесс считывания и записи измененных файлов в зависимости от сценария работы программы
     */
    public void launchFileDataManager() {
        if (inputPath == null || outputPath == null)     // Если нет пути к файлу - ошибка
            throw new RuntimeException("Отсутствует путь к файлу");
        else
            writeToFile();
    }

    /**
     * Считывает текст из файла, вызывает метод класса EncryptionManager для шифровки/расшифровки
     * результат выполнения записывает в файл
     * (используется при работе с разными файлами для считывания и записи)
     */
    public void writeToFile(){
        LocalDateTime timeStartCryptoAnalyze;
        LocalDateTime timeFinishCryptoAnalyze;

        timeStartCryptoAnalyze = LocalDateTime.now();       // Фиксирует время начало работы крипто-анализатора
        //-------------------------------------------
        try {
            List <String> textFromFile = Files.readAllLines(inputPath);
            textFromFile = encryptionManager.launchCryptoAnalyzer(textFromFile);
            Files.write(outputPath,textFromFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //-------------------------------------------
        timeFinishCryptoAnalyze = LocalDateTime.now();      // Фиксирует время окончания работы крипто-анализатора
        System.out.printf("Работа анализатора завершена, длительность работы - %s",
                Duration.between(timeStartCryptoAnalyze,timeFinishCryptoAnalyze).toString().substring(2));
    }

    /**
     * Считывает текст из файла, вызывает метод класса EncryptionManager для шифровки/расшифровки
     * результат выполнения записывает в файл
     * (используется при работе с разными файлами для считывания и записи)
     */
    @Deprecated
    public void writeToOtherFile(){
        String textAfterCryptoAnalyze = null;
        LocalDateTime timeStartCryptoAnalyze;
        LocalDateTime timeFinishCryptoAnalyze;

        timeStartCryptoAnalyze = LocalDateTime.now();       // Фиксирует время начало работы крипто-анализатора
        //-------------------------------------------
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath.toFile()),StandardCharsets.UTF_8));
             BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath.toFile()),StandardCharsets.UTF_8)))  {

            while (inputStream.ready()){
            //    textAfterCryptoAnalyze = encryptionManager.launchCryptoAnalyzer(inputStream.readLine(),null);
                outputStream.write(textAfterCryptoAnalyze);
                outputStream.write('\n');
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //-------------------------------------------
        timeFinishCryptoAnalyze = LocalDateTime.now();      // Фиксирует время окончания работы крипто-анализатора
        System.out.printf("Работа анализатора завершена,длительность работы - %s",
                Duration.between(timeStartCryptoAnalyze,timeFinishCryptoAnalyze).toString().substring(2));
    }

}
