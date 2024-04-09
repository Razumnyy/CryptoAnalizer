import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Класс DataEncrypto обеспечивает функциональность для шифрования данных.
 */
public class DataEncrypto {


    private static DataEncrypto dataEncrypto;

    private DataEncrypto(){}

    /**
     * Шифрует переданное значение в качестве аргуента по ключу
     * @param textForEncrypt текст для шифрования
     * @param key ключ для шифрования
     * @return String зашифрованный текст
     */
    public String encryptByKey(String textForEncrypt,int key){
        int symbol = 0;
        char [] arrChars = textForEncrypt.toCharArray();
        System.out.println(Arrays.toString(arrChars));
        for (int i = 0; i < arrChars.length; i++) {
            symbol = (int) arrChars[i] & 0xFF;

            if (symbol > Alphabet.alphabetByASCII.length-1) {                                         // Если элемент не найден в алфавите
                throw new RuntimeException("Не корректный символ + " + symbol);
            }else if ( (symbol - key) < (Alphabet.alphabetByASCII.length-1)) {                       // Если индекс элемента - ключ выходят за длину алфавита
                arrChars[i] = (char) ((Alphabet.alphabetByASCII.length-1) - Math.abs(symbol - key));
            }else                                                                                    // Во всех остальных случаях
                arrChars[i] = (char) (symbol-key);
        }

        return new String(arrChars);
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
