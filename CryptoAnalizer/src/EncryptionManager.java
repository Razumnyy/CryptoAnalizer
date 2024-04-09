/**
 * Класс EncryptionManager предоставляет функциональность для шифрования и дешифрования данных.
 * Он управляет операциями шифрования и дешифрования с использованием различных алгоритмов и ключей.
 */
public class EncryptionManager   {

    private int key;                                                         // Ключ для шифрования / дешифрование данных
    private EncryptScenario encryptScenario;                                 // Сценарий работы шифровщика
    private DataDecrypto dataDecrypto = DataDecrypto.getInstance();          // Ссылка на экземпляр класса, отвечающего за расшифровку данных
    private DataEncrypto dataEncrypto = DataEncrypto.getInstance();          // Ссылка на экземпляр класса, отвечающего за шифрование данных
    private static EncryptionManager encryptionManager;

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
     * Запускает процесс шифрования или дешифрования данных в зависимости от сценария работы программы
     * @param textForCrypto текст, который необходимо шифровать или расшифровать
     * @return String результат обработки строки
     */
    public String launchCryptoAnalyzer(String textForCrypto){
        return switch (encryptScenario){
            case ENCRYPT_BY_KEY ->
                dataEncrypto.encryptByKey(textForCrypto,this.key);
            case DECRYPT_BY_KEY ->
                dataDecrypto.decryptByKey(textForCrypto,this.key);
            case BRUT_FORCE ->
                dataDecrypto.decryptBrutForce(textForCrypto);
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

    public void setKey(int key) {this.key = key; }
    public void setEncryptMode(EncryptScenario encryptScenario) { this.encryptScenario = encryptScenario; }
}
