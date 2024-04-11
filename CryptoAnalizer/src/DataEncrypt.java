import java.util.ArrayList;
import java.util.List;

/**
 * Класс DataEncrypt обеспечивает функциональность для шифрования данных.
 */
public class DataEncrypt {
    private static DataEncrypt dataEncrypt;
    private DataEncrypt(){}

    /**
     * Создает единственный экзмепляр класс DataEncrypt, если он еще не создан.
     * @return DataEncrypt - ссылка на экзмепляр класса.
     */
    public static DataEncrypt getInstance(){
        if (dataEncrypt == null)
            return dataEncrypt = new DataEncrypt();
        else
            return dataEncrypt;
    }

    /**
     * Шифрует переданное значение в качестве аргуента по ключу
     * @param textListFromFile коллекция с текстом для шифрования
     * @param key ключ для шифрования
     * @return String зашифрованный текст
     */
    public List<String> encryptByKey(List<String> textListFromFile, int key){
        char [] arrChars;
        int indexSymbolInAlphabet;
        List<String> listWithDecryptText = new ArrayList<>();

        // Итерируемся по строкам текста
        for (String textFromFile : textListFromFile) {

            arrChars = textFromFile.toCharArray();
            for (int i = 0; i < arrChars.length; i++) {
                indexSymbolInAlphabet = Alphabet.getIndexBySymbol(arrChars[i],AlphabetType.RU);
                arrChars[i] = Alphabet.getSymbolByIndex(indexSymbolInAlphabet - key);
            }
            listWithDecryptText.add(new String(arrChars));

        }
        return listWithDecryptText;
    }
}
