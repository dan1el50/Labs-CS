import java.util.Scanner;

public class Task2 {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Generate permuted alphabet from keyword
    private static String generatePermutedAlphabet(String keyword) {
        keyword = keyword.toUpperCase();

        // Validate keyword
        if (!keyword.matches("[A-Z]+") || keyword.length() < 7) {
            return null;
        }

        StringBuilder permuted = new StringBuilder();
        boolean[] used = new boolean[26];

        // Add unique letters from keyword
        for (char c : keyword.toCharArray()) {
            int index = c - 'A';
            if (!used[index]) {
                permuted.append(c);
                used[index] = true;
            }
        }

        // Add remaining letters
        for (char c = 'A'; c <= 'Z'; c++) {
            int index = c - 'A';
            if (!used[index]) {
                permuted.append(c);
            }
        }

        return permuted.toString();
    }

    // Encrypt text
    public static String encrypt(String text, int shift, String permutedAlphabet) {
        text = text.toUpperCase().replace(" ", "");
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            int pos = permutedAlphabet.indexOf(c);
            int newPos = (pos + shift) % 26;
            result.append(permutedAlphabet.charAt(newPos));
        }

        return result.toString();
    }

    // Decrypt text
    public static String decrypt(String text, int shift, String permutedAlphabet) {
        text = text.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            int pos = permutedAlphabet.indexOf(c);
            int newPos = (pos - shift + 26) % 26;
            result.append(permutedAlphabet.charAt(newPos));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get operation
        System.out.println("Choose: 1 for encryption, 2 for decryption:");
        int operation = sc.nextInt();
        sc.nextLine();

        if (operation != 1 && operation != 2) {
            System.out.println("Invalid operation!");
            return;
        }

        // Get shift key
        System.out.println("Enter shift key (1-25):");
        int shift = sc.nextInt();
        sc.nextLine();

        if (shift < 1 || shift > 25) {
            System.out.println("Key must be between 1 and 25!");
            return;
        }

        // Get permutation keyword
        System.out.println("Enter keyword (minimum 7 letters, only A-Z):");
        String keyword = sc.nextLine();

        String permutedAlphabet = generatePermutedAlphabet(keyword);
        if (permutedAlphabet == null) {
            System.out.println("Invalid keyword! Must be at least 7 letters, only A-Z.");
            return;
        }

        // Display alphabets
        System.out.println("\nOriginal alphabet:  " + ALPHABET);
        System.out.println("Permuted alphabet:  " + permutedAlphabet);

        // Get text
        System.out.println("\nEnter text:");
        String text = sc.nextLine();

        // Perform operation
        String result;
        if (operation == 1) {
            result = encrypt(text, shift, permutedAlphabet);
            System.out.println("\nEncrypted: " + result);
        } else {
            result = decrypt(text, shift, permutedAlphabet);
            System.out.println("\nDecrypted: " + result);
        }
    }
}
