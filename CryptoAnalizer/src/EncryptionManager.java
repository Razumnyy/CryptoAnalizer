import java.util.List;

/**
 * Класс EncryptionManager предоставляет функциональность для шифрования и дешифрования данных.
 * Он управляет операциями шифрования и дешифрования с использованием различных алгоритмов и ключей.
 */
public class EncryptionManager   {
    private int key;                                                            // Ключ для шифрования / дешифрование данных
    private EncryptScenario encryptScenario;                                    // Сценарий работы шифровщика
    private static EncryptionManager encryptionManager;                         // Ссылка на экзмепляр класса крипто-анализатора
    private final DataDecrypt dataDecrypt = DataDecrypt.getInstance();          // Ссылка на экземпляр класса, отвечающего за расшифровку данных
    private final DataEncrypt dataEncrypt = DataEncrypt.getInstance();          // Ссылка на экземпляр класса, отвечающего за шифрование данных
    private EncryptionManager(){}

    /**
     * Создает единственный экзмепляр класс EncryptionManager, если он еще не создан.
     * @return EncryptionManager - ссылка на экзмепляр класса.
     */
    public static EncryptionManager getInstance(){
        if (encryptionManager == null)
            return encryptionManager = new EncryptionManager();
        else
            return encryptionManager;
    }

    /**
     * Запускает процесс шифрования или дешифрования данных в зависимости от выбранного пользователем сценария работы программы
     * @param textFromFile коллекция с текстом, который необходимо шифровать или расшифровать
     * @return String результат обработки строки
     */
    public List<String> launchCryptoAnalyzer(List<String> textFromFile){
        return switch (encryptScenario){
            case ENCRYPT_BY_KEY ->
                dataEncrypt.encryptByKey(textFromFile,this.key);
            case DECRYPT_BY_KEY ->
                dataDecrypt.decryptByKey(textFromFile,this.key);
            case BRUT_FORCE ->
                dataDecrypt.decryptBrutForce(textFromFile);
            case STAT_ANALYZER ->
                dataDecrypt.decryptStatAnalyze(textFromFile);
        };
    }

    /**
     * Инициализирует экземпляр класса
     * @param key ключ шифрования
     * @param encryptMode режим шифрования / дешифрования
     */
    public void initialize(int key, EncryptScenario encryptMode){
        this.key = key;
        this.encryptScenario = encryptMode;
    }

}
