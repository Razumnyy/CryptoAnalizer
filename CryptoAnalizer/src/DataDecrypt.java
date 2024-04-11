import java.util.*;
/**
 * Класс DataDecrypt предоставляет функциональность для расшифровки данных.
 */
public class DataDecrypt {
    private static DataDecrypt dataDecrypt;
    private int keyForBrutForce = -1;                       // Значение > 0 - подобраный ключ, -1 - не удалось подобрать ключ
    private int keyForStatAnalyze = -1;                     // Значение > 0 - подобраный ключ, -1 - не удалось подобрать ключ
    private final TreeMap<Integer,Integer> repeatCountMap
            = new TreeMap<>(Collections.reverseOrder());    // Ключ - количество валидных слов в тексе, значение - ключ дешифровки
    private DataDecrypt(){}

    /**
     * Создает единственный экзмепляр класс DataDecrypt, если он еще не создан.
     * @return DataDecrypt - ссылка на экзмепляр класса.
     */
    public static DataDecrypt getInstance(){
        if (dataDecrypt == null)
            return dataDecrypt = new DataDecrypt();
        else
            return dataDecrypt;
    }

    /**
     * Расшифровывает переданное значение в качестве аргуента по ключу
     * @param textListFromFile коллекция с текстом, который нужно расшифровать
     * @param key ключ для расшифвровки
     * @return String расшифрованный текст
     */
    public List<String> decryptByKey(List<String> textListFromFile,int key){
        char [] arrChars;
        int indexSymbolInAlphabet;
        List<String> listWithDecryptText = new ArrayList<>();

        // Итерируемся по строкам текста
        for (String textFromFile : textListFromFile) {

            arrChars = textFromFile.toCharArray();
            for (int i = 0; i < arrChars.length; i++) {
                indexSymbolInAlphabet = Alphabet.getIndexBySymbol(arrChars[i],AlphabetType.RU);
                arrChars[i] = Alphabet.getSymbolByIndex(indexSymbolInAlphabet + key);
            }
            listWithDecryptText.add(new String(arrChars));

        }
        return listWithDecryptText;
    }

    /**
     * Расшифровывает переданное значение в качестве аргуента методом подбора
     * @param textListFromFile коллекция с текстом, который нужно расшифровать
     * @return String расшифрованный текст
     */
    public List<String> decryptBrutForce(List<String> textListFromFile){

        if (searchDecryptionKey(textListFromFile))            // Подбираем ключ
            return decryptByKey(textListFromFile, keyForBrutForce);
        else
            throw new RuntimeException("Не удалось подобрать ключ");
    }

    /**
     * Подбирает ключ для расшифровки текста
     * @param textListFromFile коллекция с текстом, который нужно расшифровать
     * @return true - ключ найден, false - нет
     */
    private boolean searchDecryptionKey(List<String> textListFromFile) {
        String textAfterDecrypt;                                            // Текст после дешифровки
        int qtyContains = 0;                                                // Количество валидных слов
        int alphabetLength = Alphabet.getLengthAlphabet(AlphabetType.RU);   // Длина алфавита
        int indexSymbolInAlphabet;                                          // Индекс символа в алфавите
        char[] arrChars;                                                    // Массив символов, создается на основе текста для дешифровки
        //----------------------------------------------
        for (int key = 0; key < alphabetLength; key++) {

            // Итерируемся по строкам текста
            for (String textFromFile : textListFromFile) {

                arrChars = textFromFile.toCharArray();

                // Перебираем смещение для каждого символа в строчке на i позицию цикла
                for (int i = 0; i < arrChars.length; i++) {
                    indexSymbolInAlphabet = Alphabet.getIndexBySymbol(arrChars[i],AlphabetType.RU);
                    arrChars[i] = Alphabet.getSymbolByIndex(indexSymbolInAlphabet + key);
                }

                //Преобразуем результат в текст
                textAfterDecrypt = new String(arrChars);

                //Проверяем валидность получившегося текста (считаем количество совпадений в тексте)
                for (String targetWord : Alphabet.getFrequentWordsRepository()) {
                    if (textAfterDecrypt.contains(targetWord))
                        qtyContains++;
                }

            }
            //Записываем количество валидных слов и ключ
            if (qtyContains > 0)
                this.repeatCountMap.put(qtyContains,key);
            //Обнуляем кол-во совпадений при данном ключе
            qtyContains = 0;
        }
        //----------------------------------------------
        // Проверяем, что ключ найден
        if (!repeatCountMap.isEmpty()) {
            System.out.printf("Ключ подобран: %d \n" +
                            "Количество валидных слов: %d \n",
                    repeatCountMap.firstEntry().getValue(), repeatCountMap.firstEntry().getKey());
            keyForBrutForce = repeatCountMap.firstEntry().getValue();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Расшифровывает переданное значение в качестве аргуента методом статистического анализа
     * @param textListFromFile коллекция с текстом, который нужно расшифровать
     * @return String расшифрованный текст
     */
    public List<String> decryptStatAnalyze(List<String> textListFromFile){
        if (launchStatAnalyze(textListFromFile))                        // Подбираем ключ
            return decryptByKey(textListFromFile, keyForStatAnalyze);
        else
            throw new RuntimeException("Не удалось подобрать ключ");
    }

    /**
     * Проводит статический анализ текста, на основе анализа подбирает ключ для расшифровки
     * @param textListFromFile коллекция с текстом, который нужно расшифровать
     * @return String расшифрованный текст
     */
    private boolean launchStatAnalyze(List<String> textListFromFile){
        Map<Character, Integer> charFrequency = new HashMap<>();                                    // Коллекция содержит количество символов в тексте
        int alphabetLength = Alphabet.getLengthAlphabet(AlphabetType.RU);                           // Длина алфавита
        int maxFrequency = 0;
        int indexByAlphabet;
        char mainChar = ' ';
        //------------------------------------------------
        // Записываем количество вхождений каждого символа
        for (String textForDecrypt : textListFromFile) {
            for (char symbol : textForDecrypt.toCharArray()){
                charFrequency.put(symbol, charFrequency.getOrDefault(symbol, 0) + 1);
            }
        }
        //------------------------------------------------
        // Находим символ с максимальным количеством вхождений
        for (Character symbol : charFrequency.keySet()){
            if (maxFrequency == 0) {
                maxFrequency = charFrequency.get(symbol);
                mainChar = symbol;
            } else if (maxFrequency < charFrequency.get(symbol)){
                maxFrequency = charFrequency.get(symbol);
                mainChar = symbol;
            }
        }
        //------------------------------------------------
        // Считаем, что символ с максимальным количестом вхождений
        // .... это пробел и подбираем к нему ключ
        for (int key = 0; key < alphabetLength; key++) {

            indexByAlphabet = Alphabet.getIndexBySymbol(mainChar,AlphabetType.RU);
            char symbol = Alphabet.getSymbolByIndex(indexByAlphabet + key);

            if (symbol == ' '){
                keyForStatAnalyze = key;
                break;
            }

        }
        //------------------------------------------------
        return keyForStatAnalyze != -1;
    }

}
