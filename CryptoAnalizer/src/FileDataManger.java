import encrypto.EncryptionManager;
import java.io.*;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * Класс FileDataManager предоставляет функциональность для управления данными в файлах.
 * Он содержит методы для чтения данных из файлов, записи данных в файлы и другие операции,
 * связанные с управлением файлами данных.
 */
public class FileDataManger {

    private Path inputPath;             // Путь к файлу для считывания
    private Path outputPath;            // Путь к файлу для записи
    private EncryptionManager encryptionManager = EncryptionManager.getInstance();
    private static FileDataManger fileDataManger;


    private FileDataManger(){}


    /**
     * Запускает процесс считывания и записи измененных файлов в зависимости от сценария работы программы
     */
    public void launchFileDataManager(){
        //TODO - добавить свое исключение
        if (inputPath == null || outputPath == null)
            throw new RuntimeException("Отсутствует путь к файлу");


        // Если одинаковый путь для чтения и записи, то используем  channel
        if (inputPath.equals(outputPath))
            processAndWriteToMainFile();
        else // иначе через поток
            processAndWriteToOtherFile();
    }

    /**
     * Считывает текст из файла, вызывает метод класса EncryptionManager для шифровки/расшифровки
     * результат выполнения записывает в файл
     * (используется при работе с разными файлами для считывания и записи)
     */
    public void processAndWriteToOtherFile(){
        String textAfterCryptoAnalyze = null;

        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath.toFile())));
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath.toFile()))))  {

            while (inputStream.ready()){
                textAfterCryptoAnalyze = encryptionManager.launchCryptoAnalyzer(inputStream.readLine());
                outputStream.write(textAfterCryptoAnalyze);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Считывает текст из файла, вызывает метод класса EncryptionManager для шифровки/расшифровки
     * результат выполнения записывает в файл
     * (используется при работе с одним файлом как для чтения так и для записи)
     */
    public void processAndWriteToMainFile(){
        String textAfterCryptoAnalyze = null;

        /*try (RandomAccessFile randomAccessFile = new RandomAccessFile(this.inputPath.toFile(),"rw");
             FileChannel fileChannel = randomAccessFile.getChannel()){

            CharBuffer charBuffer = CharBuffer.allocate(1024);
            int charRead = fileChannel.read(charBuffer);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
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
    public void setInputPath(Path inputPath) { this.inputPath = inputPath; }
    public void setOutputPath(Path outputPath) { this.outputPath = outputPath; }

}
