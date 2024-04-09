package encrypto;
/**
 * Класс DataDecrypto предоставляет функциональность для расшифровки данных.
 */
public class DataDecrypto {

    private static DataDecrypto dataDecrypto;

    private DataDecrypto(){}

    /**
     * Расшифровывает переданное значение в качестве аргуента по ключу
     * @param textForDecrypt текст для расшифровки
     * @param key ключ для расшифвровки
     * @return String расшифрованный текст
     */
    public String decryptByKey(String textForDecrypt,int key){
        return null;
    }

    /**
     * Расшифровывает переданное значение в качестве аргуента методом подбора
     * @param textForDecrypt текст для расшифровки
     * @return String расшифрованный текст
     */
    public String decryptBrutForce(String textForDecrypt){
        return null;
    }

    /**
     * Создает единственный экзмепляр класс DataDecrypto, если он еще не создан.
     * @return DataDecrypto - ссылка на экзмепляр класса.
     */
    public static DataDecrypto getInstance(){
        if (dataDecrypto == null)
            return dataDecrypto = new DataDecrypto();
        else
            return dataDecrypto;
    }
}
