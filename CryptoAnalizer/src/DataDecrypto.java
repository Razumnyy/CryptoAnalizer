import java.util.Arrays;

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
        int symbol = 0;
        char [] arrChars = textForDecrypt.toCharArray();
        System.out.println(Arrays.toString(arrChars));
        for (int i = 0; i < arrChars.length; i++) {
            symbol = (int) arrChars[i] & 0xFF;

            if (symbol > Alphabet.alphabetByASCII.length) {                                          // Если элемент не найден в алфавите
                throw new RuntimeException("Не корректный символ + " + symbol);
            }else if ( (symbol+key) > (Alphabet.alphabetByASCII.length-1)) {                         // Если индекс элемента + ключ выходят за длину алфавита
                arrChars[i] = (char) Math.abs(symbol + key - (Alphabet.alphabetByASCII.length-1));
            }else                                                                                    // Во всех остальных случаях
                arrChars[i] = (char) (symbol+key);
        }

        return new String(arrChars);
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
