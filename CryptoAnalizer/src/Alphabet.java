import java.util.Arrays;
import java.util.List;

/**
 * Класс, представляющий алфавит, который используется для шифрования и дешифрования текста.
 * Алфавит может быть различным: английский, русский т.д.
 * Этот класс предоставляет методы для работы с алфавитом
 */
public class Alphabet {
    private static boolean isSortedArrays = false;

    private static final List<String> frequentWordsRepository = Arrays.asList("им","их","из","он","она","они","оно","мы","вы","тот","этот",
            "но","на","или","быть","делать","идти","себя","свой","этот","мой","ваш","как","так","чтобы","потому что","поскольку","также","том","там",
            "ними","через","над","под","за","не","нет","да","уже","как","когда","где","что","ей","по");
    private static final char [] rusAlphabet = {'—','*',']','[',')','(','…','-',';','́','/','|','\\','–',
            ' ','!','"','\'',',','.',':','?','«','»', 'Ё','А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И','Й','К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю','Я','а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','й','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю','я', 'ё'};

    private static final char [] engAlphabet = {'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','q','r','s','t','u','v','w','x','y','z','.', ',',
            '«', '»', '"', '\'', ':', '!', '?', ' '};

    private Alphabet(){}

    /**
     * Возвращает индекс символа в массиве
     * @param symbol искомый символ
     * @param alphabet тип алфавита (RU - русский, ENG - английский)
     * @return int индекс символа в массиве
     */
    public static int getIndexBySymbol(char symbol, AlphabetType alphabet){
        int index;
        //--------------------------------------------------
        // Сортируем массивы, если они еще не отсортированны
        if (!isSortedArrays){
            Arrays.sort(rusAlphabet);
            Arrays.sort(engAlphabet);
            isSortedArrays = true;
        }
        //--------------------------------------------------
        // Получаем индекс элемента
        index = switch (alphabet){
            case RU -> Arrays.binarySearch(rusAlphabet,symbol);
            case ENG -> Arrays.binarySearch(engAlphabet,symbol);
        };
        //--------------------------------------------------
        // Проверяем валидность элемента и возвращаем его
        if (index < 0)
            throw new IllegalStateException("Некорректное значение: " + symbol);
        else
            return index;
    }

    /**
     * Находит символ по индексу учитывая размер массива и избегая выхода за его размер
     * @param indexSymbol индекс символа
     * @return char - символ согласно индексу
     */
    public static char getSymbolByIndex(int indexSymbol){
        if (indexSymbol < 0)                                                 // Если индекс отрицательный
            return rusAlphabet[rusAlphabet.length - Math.abs(indexSymbol)];
        else if (indexSymbol >= rusAlphabet.length)                          // Если индекс больше длины массива
            return rusAlphabet[indexSymbol - rusAlphabet.length];
        else                                                                 // Во всех остальных случаях
            return rusAlphabet[indexSymbol];
    }

    /**
     * Возвращает длину алфавита
     * @param alphabet тип алфавита (RU - русский, ENG - английский)
     * @return int длина алфавита
     */
    public static int getLengthAlphabet(AlphabetType alphabet){
        return switch (alphabet){
            case RU -> rusAlphabet.length;
            case ENG -> engAlphabet.length;
        };
    }

    public static List<String> getFrequentWordsRepository() {return frequentWordsRepository;}
}
