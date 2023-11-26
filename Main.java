import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()";

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Usage: java Main <passwordLength> <languageCount> <hasUpperCase> <hasSpecialCharacters> <requiredDigits>");
            return;
        }

        int passwordLength = Integer.parseInt(args[0]);
        int languageCount = Integer.parseInt(args[1]);
        boolean hasUpperCase = Boolean.parseBoolean(args[2]);
        boolean hasSpecialCharacters = Boolean.parseBoolean(args[3]);
        String requiredDigits = args[4];

        StringBuilder password = generatePassword(passwordLength, languageCount, hasUpperCase, hasSpecialCharacters, requiredDigits);
        if (passwordLength > 0) {
            System.out.println("Сгенерированный пароль: " + password);
        } else {
            System.out.println("Некорректные значения длинны");
        }
    }

    private static StringBuilder generatePassword(int length, int languageCount, boolean hasUpperCase, boolean hasSpecialCharacters, String requiredDigits) {
        if (length <= 0 || languageCount < 0) {
            System.out.println("Некорректные значения параметров");
            return new StringBuilder();
        }

        StringBuilder password = new StringBuilder();
        List<String> characterPool = createCharacterPool(languageCount, hasSpecialCharacters, requiredDigits);
        if (characterPool.isEmpty()) {
            System.out.println("Не выбраны никакие типы символов");
            return new StringBuilder();
        }

        addRandomCharacters(characterPool, password, length);
        if (hasUpperCase) {
            convertToUpperCase(password);
        }

        return password;
    }

    private static List<String> createCharacterPool(int languageCount, boolean hasSpecialCharacters, String requiredDigits) {
        List<String> characterPool = new ArrayList<>();
        if (languageCount >= 1) {
            characterPool.add(ENGLISH_ALPHABET);
        }
        if (languageCount >= 2) {
            characterPool.add(RUSSIAN_ALPHABET);
        }
        if (hasSpecialCharacters) {
            characterPool.add(SPECIAL_CHARACTERS);
        }
        for (int i = 0; i < requiredDigits.length(); i++) {
            characterPool.add(String.valueOf(requiredDigits.charAt(i)));
        }
        return characterPool;
    }

    private static void addRandomCharacters(List<String> characterPool, StringBuilder password, int length) {
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            String randomCharacterSet = characterPool.get(rand.nextInt(characterPool.size()));
            char randomChar = getRandomCharacterFromString(randomCharacterSet, rand);
            password.append(randomChar);
        }
    }

    private static void convertToUpperCase(StringBuilder password) {
        Random rand = new Random();
        for (int i = 0; i < password.length(); i++) {
            if (rand.nextBoolean()) {
                char currentChar = password.charAt(i);
                password.setCharAt(i, Character.toUpperCase(currentChar));
            }
        }
    }

    private static char getRandomCharacterFromString(String source, Random rand) {
        int randomIndex = rand.nextInt(source.length());
        return source.charAt(randomIndex);
    }
}