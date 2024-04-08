package encrypto;

/**
 * Класс encrypto.EncryptionManager предоставляет функциональность для шифрования и дешифрования данных.
 * Он управляет операциями шифрования и дешифрования с использованием различных алгоритмов и ключей.
 */
public class EncryptionManager   {

    private int key;                                        // Ключ для шифрования / дешифрование данных
    private EncryptScenario encryptScenario;
    private DataDecrypto dataDecrypto;                      // Ссылка на экземпляр класса, отвечающего за расшифровку данных
    private DataEncrypto dataEncrypto;                      // Ссылка на экземпляр класса, отвечающего за шифрование данных
    private static EncryptionManager encryptionManager;

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
     * Инициализирует экземпляр класса
     * @param key - ключ шифрования
     * @param encryptMode - режим шифрования / дешифрования
     */
    public void initialize(int key, EncryptScenario encryptMode){
        this.key = key;
        this.encryptScenario = encryptMode;
    }



    private EncryptionManager(){}

    public void setKey(int key) {this.key = key; }
    public void setEncryptMode(EncryptScenario encryptScenario) { this.encryptScenario = encryptScenario; }
}
