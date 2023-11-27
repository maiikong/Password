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

        int passwordLength;
        int languageCount;
        boolean hasUpperCase;
        boolean hasSpecialCharacters;
        String requiredDigits;
        try {
            passwordLength = Integer.parseInt(args[0]);
            languageCount = Integer.parseInt(args[1]);
            hasUpperCase = parseBooleanArgument(args[2]);
            hasSpecialCharacters = parseBooleanArgument(args[3]);
            requiredDigits = args[4];
        } catch (NumberFormatException e) {
            System.out.println("Неверные значения: используйте цифры вместо букв");
            return;
        }

        long startTime = System.currentTimeMillis();
        StringBuilder password = generatePassword(passwordLength, languageCount, hasUpperCase, hasSpecialCharacters, requiredDigits);
        long endTime = System.currentTimeMillis();

        if (passwordLength > 0) {
            System.out.println("Сгенерированный пароль: " + password);
            System.out.println("Время генерации пароля: " + (endTime - startTime) + " мс");
        } else {
            System.out.println("Некорректные значения длины");
        }
    }

    private static boolean parseBooleanArgument(String arg) {
        if (arg.equalsIgnoreCase("true")) {
            return true;
        } else if (arg.equalsIgnoreCase("false")) {
            return false;
        } else {
            System.out.println("Неверное значение. Используйте только 'true' или 'false'.");
            System.exit(1);
            return false;
        }
    }

    private static StringBuilder generatePassword(int length, int languageCount, boolean hasUpperCase, boolean hasSpecialCharacters, String requiredDigits) {
        if (length <= 0 || languageCount < 0) {
            System.out.println("Некорректные значения параметров");
            return new StringBuilder();
        }

        List<String> characterPool = createCharacterPool(languageCount, hasSpecialCharacters, requiredDigits);
        if (characterPool.isEmpty()) {
            System.out.println("Не выбраны никакие типы символов");
            return new StringBuilder();
        }

        return addCharactersAndConvertToUpperCase(characterPool, length, hasUpperCase);
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
        for (char digit : requiredDigits.toCharArray()) {
            characterPool.add(String.valueOf(digit));
        }
        return characterPool;
    }

    private static StringBuilder addCharactersAndConvertToUpperCase(List<String> characterPool, int length, boolean hasUpperCase) {
        StringBuilder password = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            String randomCharacterSet = characterPool.get(rand.nextInt(characterPool.size()));
            char randomChar = getRandomCharacterFromString(randomCharacterSet, rand);
            password.append(randomChar);
        }
        if (hasUpperCase) {
            randomizeUpperCase(password);
        }
        return password;
    }

    private static void randomizeUpperCase(StringBuilder password) {
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
