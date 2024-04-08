import encrypto.EncryptionManager;

import java.nio.file.Path;

/**
 * Класс FileDataManager предоставляет функциональность для управления данными в файлах.
 * Он содержит методы для чтения данных из файлов, записи данных в файлы и другие операции,
 * связанные с управлением файлами данных.
 */
public class FileDataManger {

    private Path inputPath;
    private Path outputPath;

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



}
