import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Класс, представляющий алфавит, который используется для шифрования и дешифрования текста.
 * Алфавит может быть различным: английский, русский т.д.
 * Этот класс предоставляет методы для работы с алфавитом, такие как получение символов,
 * определение индекса символа в алфавите и проверку принадлежности символа алфавиту.
 */
public class Alphabet {

    public static final char [] rusAlphabet = {' ','!','"','\'',',','.',':','?','«','»','а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я'};

    public static final char [] engAlphabet = {'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','q','r','s','t','u','v','w','x','y','z','.', ',',
            '«', '»', '"', '\'', ':', '!', '?', ' '};

    public static final int [] alphabetByASCII = new int[255];

    private Alphabet(){
    }

    /**
     * Возвращает индекс символа в массиве
     * @return int - индекс символа в массиве
     */
    public static int getIndexBySymbol(char symbol, String alphabet){
        return switch (alphabet){
            case "ru" -> Arrays.binarySearch(rusAlphabet,symbol);
            case "eng" -> Arrays.binarySearch(engAlphabet,symbol);
            default -> throw new IllegalStateException("Unexpected value: " + alphabet);
        };
    }

}
