import java.util.Scanner;

public class Main {

    // Romanian alphabet with 31 letters (uppercase)
    private static final String ROMANIAN_ALPHABET = "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";
    private static final int ALPHABET_SIZE = 31;
    private static final int MIN_KEY_LENGTH = 7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Vigenère Cipher - Romanian Alphabet ===");

        // Choose operation
        System.out.println("Alegeți operația:");
        System.out.println("1. Criptare");
        System.out.println("2. Decriptare");
        System.out.print("Introduceți opțiunea (1 sau 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice != 1 && choice != 2) {
            System.out.println("Opțiune invalidă! Vă rugăm să alegeți 1 sau 2.");
            scanner.close();
            return;
        }

        // Get and validate key
        String key = getValidKey(scanner);

        // Get message or ciphertext
        String input;
        if (choice == 1) {
            System.out.print("Introduceți mesajul pentru criptare: ");
            input = scanner.nextLine();
        } else {
            System.out.print("Introduceți criptograma pentru decriptare: ");
            input = scanner.nextLine();
        }

        // Validate and process input
        String processedInput = preprocessText(input);
        if (processedInput.isEmpty()) {
            System.out.println("Mesajul nu conține caractere valide!");
            scanner.close();
            return;
        }

        // Validate all characters
        if (!validateText(processedInput)) {
            System.out.println("Caracterele trebuie să fie din intervalul: A-Z, Ă, Â, Î, Ș, Ț");
            System.out.println("Alfabetul valid: " + ROMANIAN_ALPHABET);
            scanner.close();
            return;
        }

        // Perform encryption or decryption
        String result;
        if (choice == 1) {
            result = encrypt(processedInput, key);
            System.out.println("\nMesaj original (procesat): " + processedInput);
            System.out.println("Cheie: " + key);
            System.out.println("Criptogramă: " + result);
        } else {
            result = decrypt(processedInput, key);
            System.out.println("\nCriptogramă: " + processedInput);
            System.out.println("Cheie: " + key);
            System.out.println("Mesaj decriptat: " + result);
        }

        scanner.close();
    }

    /**
     * Gets and validates the key from user input
     */
    private static String getValidKey(Scanner scanner) {
        String key;
        while (true) {
            System.out.print("Introduceți cheia (minimum " + MIN_KEY_LENGTH + " caractere): ");
            key = scanner.nextLine().trim();

            // Remove spaces and convert to uppercase
            key = key.replaceAll("\\s+", "").toUpperCase();

            if (key.length() < MIN_KEY_LENGTH) {
                System.out.println("Cheia trebuie să aibă cel puțin " + MIN_KEY_LENGTH + " caractere!");
                continue;
            }

            if (!validateText(key)) {
                System.out.println("Cheia conține caractere invalide!");
                System.out.println("Caracterele valide: " + ROMANIAN_ALPHABET);
                continue;
            }

            break;
        }
        return key;
    }

    /**
     * Preprocesses text: removes spaces and converts to uppercase
     */
    private static String preprocessText(String text) {
        return text.replaceAll("\\s+", "").toUpperCase();
    }

    /**
     * Validates that all characters are in the Romanian alphabet
     */
    private static boolean validateText(String text) {
        for (char c : text.toCharArray()) {
            if (ROMANIAN_ALPHABET.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Encrypts plaintext using Vigenère cipher
     * Formula: C_i = (M_i + K_(i mod m)) mod ℓ
     */
    private static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            // Get positions in alphabet
            int plainPos = ROMANIAN_ALPHABET.indexOf(plainChar);
            int keyPos = ROMANIAN_ALPHABET.indexOf(keyChar);

            // Apply Vigenère encryption formula
            int cipherPos = (plainPos + keyPos) % ALPHABET_SIZE;

            ciphertext.append(ROMANIAN_ALPHABET.charAt(cipherPos));
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts ciphertext using Vigenère cipher
     * Formula: M_i = (C_i - K_(i mod m)) mod ℓ
     */
    private static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            // Get positions in alphabet
            int cipherPos = ROMANIAN_ALPHABET.indexOf(cipherChar);
            int keyPos = ROMANIAN_ALPHABET.indexOf(keyChar);

            // Apply Vigenère decryption formula
            int plainPos = (cipherPos - keyPos + ALPHABET_SIZE) % ALPHABET_SIZE;

            plaintext.append(ROMANIAN_ALPHABET.charAt(plainPos));
        }

        return plaintext.toString();
    }
}
