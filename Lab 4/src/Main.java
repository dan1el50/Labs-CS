import java.util.Scanner;

public class Main {

    // P permutation table (from DES algorithm)
    private static final int[] P_TABLE = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("DES Algorithm - Task 2.8");
        System.out.println("Calculate R_i for round k, given L_(k-1) and S-box output\n");

        // Choose input method
        System.out.println("Choose input method:");
        System.out.println("1 - Manual input");
        System.out.println("2 - Random generation");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine().trim();
        String lPrev = "";
        String sBoxOutput = "";

        if (choice.equals("1")) {
            // Manual input
            System.out.println("\n--- Manual Input ---");

            // Input L_(k-1) - 32 bits
            System.out.println("Enter L_(k-1) (32 bits, no spaces):");
            lPrev = scanner.nextLine().trim();

            // Validate L_(k-1)
            if (!isValidBinary(lPrev, 32)) {
                System.out.println("Error: L_(k-1) must be exactly 32 binary digits!");
                return;
            }

            // Input S-box output - 32 bits
            System.out.println("\nEnter S-box output (32 bits, no spaces):");
            sBoxOutput = scanner.nextLine().trim();

            // Validate S-box output
            if (!isValidBinary(sBoxOutput, 32)) {
                System.out.println("Error: S-box output must be exactly 32 binary digits!");
                return;
            }
        } else if (choice.equals("2")) {
            // Random generation
            System.out.println("\n--- Random Generation ---");
            lPrev = generateRandomBinary(32);
            sBoxOutput = generateRandomBinary(32);

            System.out.println("Generated L_(k-1): " + formatBinary(lPrev, 4));
            System.out.println("Generated S-box output: " + formatBinary(sBoxOutput, 4));
        } else {
            System.out.println("Invalid choice! Please choose 1 or 2.");
            return;
        }

        System.out.println("CALCULATION STEPS");

        // Step 1: Display inputs
        System.out.println("\nStep 1: Input Data");
        System.out.println("L_(k-1) = " + formatBinary(lPrev, 4));
        System.out.println("S-box output = " + formatBinary(sBoxOutput, 4));

        // Step 2: Apply P permutation
        System.out.println("\nStep 2: Apply P Permutation");
        System.out.println("P permutation table:");
        displayPTable();

        String pOutput = applyPermutation(sBoxOutput, P_TABLE);
        System.out.println("\nAfter P permutation:");
        System.out.println("P(S-box output) = " + formatBinary(pOutput, 4));

        // Step 3: XOR with L_(k-1)
        System.out.println("\nStep 3: XOR with L_(k-1)");
        String ri = xor(lPrev, pOutput);

        System.out.println("L_(k-1)         = " + formatBinary(lPrev, 4));
        System.out.println("P(S-box output) = " + formatBinary(pOutput, 4));
        System.out.println("                  " + "-".repeat(35));
        System.out.println("R_i = L_(k-1) ⊕ P(S-box output)");
        System.out.println("R_i             = " + formatBinary(ri, 4));

        // Final result
        System.out.println("FINAL RESULT");
        System.out.println("R_i = " + formatBinary(ri, 4));
        System.out.println("\nIn hexadecimal:");
        System.out.println("R_i = " + binaryToHex(ri));

        scanner.close();
    }

    /**
     * Apply permutation table to input bits
     */
    private static String applyPermutation(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int pos : table) {
            output.append(input.charAt(pos - 1)); // Tables use 1-based indexing
        }
        return output.toString();
    }

    /**
     * XOR two binary strings
     */
    private static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    /**
     * Format binary string with spaces every n bits
     */
    private static String formatBinary(String binary, int groupSize) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < binary.length(); i++) {
            if (i > 0 && i % groupSize == 0) {
                formatted.append(" ");
            }
            formatted.append(binary.charAt(i));
        }
        return formatted.toString();
    }

    /**
     * Convert binary string to hexadecimal
     */
    private static String binaryToHex(String binary) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 4) {
            String fourBits = binary.substring(i, i + 4);
            int value = Integer.parseInt(fourBits, 2);
            hex.append(Integer.toHexString(value).toUpperCase());
        }
        return hex.toString();
    }

    /**
     * Validate binary string
     */
    private static boolean isValidBinary(String str, int expectedLength) {
        if (str.length() != expectedLength) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    /**
     * Generate random binary string of specified length
     */
    private static String generateRandomBinary(int length) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < length; i++) {
            binary.append(Math.random() < 0.5 ? '0' : '1');
        }
        return binary.toString();
    }

    /**
     * Display P permutation table
     */
    private static void displayPTable() {
        System.out.println("Input bit position -> Output position:");
        for (int i = 0; i < P_TABLE.length; i++) {
            System.out.printf("%2d ", P_TABLE[i]);
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }
    }
}