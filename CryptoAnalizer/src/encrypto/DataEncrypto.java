package encrypto;
/**
 * Класс DataEncrypto обеспечивает функциональность для шифрования данных.
 */
public class DataEncrypto {


    private static DataEncrypto dataEncrypto;

    private DataEncrypto(){}

    /**
     * Шифрует переданное значение в качестве аргуента по ключу
     * @param textForDecrypt текст для шифрования
     * @param key ключ для шифрования
     * @return String зашифрованный текст
     */
    public String encryptByKey(String textForDecrypt,int key){
        return null;
    }

    /**
     * Создает единственный экзмепляр класс DataEncrypto, если он еще не создан.
     * @return DataEncrypto - ссылка на экзмепляр класса.
     */
    public static DataEncrypto getInstance(){
        if (dataEncrypto == null)
            return dataEncrypto = new DataEncrypto();
        else
            return dataEncrypto;
    }
}
