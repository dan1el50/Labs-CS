import java.util.*;

public class Task1 {

    static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static final Map<Character, Integer> TO_INDEX = new HashMap<>();
    static final Set<Character> VALID_CHARS = new HashSet<>();

    static {
        for (int i = 0; i < 26; i++) {
            TO_INDEX.put(ALPHABET[i], i); // A->0, ..., Z->25
            VALID_CHARS.add(ALPHABET[i]);
        }
    }

    // Normalize: convert to uppercase and remove spaces; validate only A–Z letters.
    static String normalizeAndValidate(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Invalid value: null text.");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = Character.toUpperCase(input.charAt(i));
            if (input.charAt(i) == ' ') {
                // Remove spaces before encryption/decryption
                continue;
            } else if (VALID_CHARS.contains(ch)) {
                sb.append(ch);
            } else {
                throw new IllegalArgumentException(
                        "Invalid text: only letters A–Z are allowed; remove other characters."
                );
            }
        }

        if (sb.length() == 0) {
            throw new IllegalArgumentException("Invalid text: nothing to process after removing spaces.");
        }

        return sb.toString();
    }

    static int mod(int a, int m) {
        int r = a % m;
        return (r < 0) ? r + m : r;
    }

    static String transform(String normalizedUpperAZ, int k, boolean encrypt) {
        if (k < 1 || k > 25) {
            throw new IllegalArgumentException("Invalid key: enter a value between 1 and 25.");
        }

        char[] result = new char[normalizedUpperAZ.length()];
        for (int i = 0; i < normalizedUpperAZ.length(); i++) {
            char ch = normalizedUpperAZ.charAt(i);
            Integer x = TO_INDEX.get(ch); // index 0..25
            if (x == null) {
                throw new IllegalArgumentException("The text contains disallowed characters.");
            }

            int y = encrypt ? mod(x + k, 26) : mod(x - k, 26);
            result[i] = ALPHABET[y];
        }

        return new String(result);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose operation: C for encryption, D for decryption:");
        String op = sc.nextLine().trim().toUpperCase(Locale.ROOT);
        boolean encrypt = "C".equals(op);
        boolean decrypt = "D".equals(op);
        if (!encrypt && !decrypt) {
            System.out.println("Invalid operation: enter C (encryption) or D (decryption).");
            return;
        }

        System.out.println("Enter the key (between 1 and 25):");
        String kStr = sc.nextLine().trim();
        int k;
        try {
            k = Integer.parseInt(kStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid key: enter an integer between 1 and 25.");
            return;
        }

        if (k < 1 || k > 25) {
            System.out.println("Invalid key: enter a value between 1 and 25.");
            return;
        }

        if (encrypt) {
            System.out.println("Enter the message (only letters will be kept; spaces removed; converted to uppercase):");
        } else {
            System.out.println("Enter the cryptogram (only letters; spaces, if present, removed):");
        }

        String raw = sc.nextLine();
        String normalized;
        try {
            normalized = normalizeAndValidate(raw);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        try {
            String output = transform(normalized, k, encrypt);
            System.out.println(encrypt ? "Cryptogram:" : "Decrypted message:");
            System.out.println(output);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
