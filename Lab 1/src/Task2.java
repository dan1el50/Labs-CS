import java.util.Scanner;

public class Task2 {
    private static final String ORIGINAL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String generatePermutedAlphabet(String key2) {
        key2 = key2.toUpperCase();
        if (!key2.matches("[A-Z]+") || key2.length() < 7) {
            return null; // Invalid key2
        }

        StringBuilder permuted = new StringBuilder();
        boolean[] used = new boolean[26];

        // Add unique letters from key2 in order
        for (char c : key2.toCharArray()) {
            int index = c - 'A';
            if (!used[index]) {
                permuted.append(c);
                used[index] = true;
            }
        }

        // Add remaining letters A-Z
        for (char c = 'A'; c <= 'Z'; c++) {
            int index = c - 'A';
            if (!used[index]) {
                permuted.append(c);
            }
        }

        return permuted.toString();
    }

    private static String formatAlphabet(String alph) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alph.length(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(alph.charAt(i));
        }
        return sb.toString();
    }

    public static String encrypt(String text, int key1, String alphabet) {
        text = text.toUpperCase().replaceAll("\\s", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int originalIndex = alphabet.indexOf(c);
            if (originalIndex == -1) {
                return "Text contains invalid characters. Allowed characters are A-Z and a-z only.";
            }
            int newIndex = (originalIndex + key1) % 26;
            result.append(alphabet.charAt(newIndex));
        }
        return result.toString();
    }

    public static String decrypt(String text, int key1, String alphabet) {
        text = text.toUpperCase().replaceAll("\\s", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int originalIndex = alphabet.indexOf(c);
            if (originalIndex == -1) {
                return "Text contains invalid characters. Allowed characters are A-Z and a-z only.";
            }
            int newIndex = (originalIndex - key1 + 26) % 26;
            result.append(alphabet.charAt(newIndex));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose operation: 1 for Encrypt, 2 for Decrypt");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (operation != 1 && operation != 2) {
            System.out.println("Invalid operation. Please choose 1 or 2.");
            return;
        }

        System.out.println("Enter the shift key (1-25):");
        int key1 = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (key1 < 1 || key1 > 25) {
            System.out.println("Invalid shift key. The key must be between 1 and 25 inclusive.");
            return;
        }

        System.out.println("Enter the permutation key (at least 7 Latin letters):");
        String key2 = scanner.nextLine();

        String alphabet = generatePermutedAlphabet(key2);
        if (alphabet == null) {
            System.out.println("Invalid permutation key. It must contain only A-Z letters (case insensitive) and be at least 7 characters long.");
            return;
        }

        // Print alphabets
        System.out.println("Original Alphabet: " + formatAlphabet(ORIGINAL_ALPHABET));
        System.out.println("Permuted Alphabet: " + formatAlphabet(alphabet));

        System.out.println("Enter the text:");
        String text = scanner.nextLine();

        String result;
        if (operation == 1) {
            result = encrypt(text, key1, alphabet);
            System.out.println("Encrypted text: " + result);
        } else {
            result = decrypt(text, key1, alphabet);
            System.out.println("Decrypted text: " + result);
        }
    }
}
