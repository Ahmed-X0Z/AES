import java.math.BigInteger;
import java.util.Scanner;

public class App {
    private static final String[] SBox = {
            "63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76",
            "ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0",
            "b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15",
            "04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75",
            "09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84",
            "53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf",
            "d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8",
            "51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2",
            "cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73",
            "60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db",
            "e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79",
            "e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08",
            "ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a",
            "70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e",
            "e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df",
            "8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"
    };

    private static final String[] InvSBox = {
            "52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb",
            "7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb",
            "54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e",
            "08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25",
            "72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92",
            "6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84",
            "90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06",
            "d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b",
            "3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73",
            "96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e",
            "47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b",
            "fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4",
            "1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f",
            "60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef",
            "a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61",
            "17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"
    };

    private static final String[] invMixColumnsMatrix = {
            "0e", "0b", "0d", "09",
            "09", "0e", "0b", "0d",
            "0d", "09", "0e", "0b",
            "0b", "0d", "09", "0e"
    };

    private static final String[] Rcon = {
            "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36"
    };

    public static String padHex(String hex) {
        while (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static String XOR_HexStrings(String hex1, String hex2) {
        BigInteger num1 = new BigInteger(hex1, 16);
        BigInteger num2 = new BigInteger(hex2, 16);

        BigInteger xorResult = num1.xor(num2);

        return padHex(xorResult.toString(16).toUpperCase());
    }

    public static String textToHex(String text) {
        StringBuilder hex = new StringBuilder();
        for (char c : text.toCharArray()) {
            hex.append(String.format("%02x", (int) c));
        }
        return hex.toString().toUpperCase();
    }

    public static String hexToText(String hex) {

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String hexPair = hex.substring(i, i + 2);
            int decimalValue = Integer.parseInt(hexPair, 16);
            text.append((char) decimalValue);
        }
        return text.toString();
    }

    public static String binaryToHex(String binary) {
        if (!binary.matches("[01]+")) {
            throw new IllegalArgumentException("Input string must contain only '0' or '1'.");
        }
        BigInteger decimalValue = new BigInteger(binary, 2);
        String hexString = decimalValue.toString(16).toUpperCase();

        return hexString;
    }

    public static String hexToBinary(String hex) {
        if (!hex.matches("[0-9A-Fa-f]+")) {
            throw new IllegalArgumentException("Input string must contain only hexadecimal characters.");
        }

        StringBuilder binary = new StringBuilder();
        for (char hexChar : hex.toCharArray()) {
            int decimalValue = Integer.parseInt(String.valueOf(hexChar), 16);
            String binaryValue = Integer.toBinaryString(decimalValue);

            while (binaryValue.length() < 4) {
                binaryValue = "0" + binaryValue;
            }

            binary.append(binaryValue);
        }

        return binary.toString();
    }

    public static String[] KeyExpansion(String[][] initialKey) {
        String[][] w = new String[11][4];

        for (int j = 0; j < 4; j++) {
            w[0][j] = initialKey[0][j] + initialKey[1][j] + initialKey[2][j] + initialKey[3][j];
        }

        for (int i = 1; i < 11; i++) {
            w[i][0] = XOR_HexStrings(g_Function(w[i - 1][3], i - 1), w[i - 1][0]);
            for (int j = 1; j < 4; j++) {
                w[i][j] = XOR_HexStrings(w[i][j - 1], w[i - 1][j]);
            }
        }

        String[] keys = new String[11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 4; j++) {
                keys[i] = "";
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 4; j++) {
                keys[i] = keys[i] + w[i][j];
            }
        }
        System.out.println("-----------------------------------------");
        System.out.println("Expanded Key:");
        for (int i = 0; i < 11; i++) {
            System.out.println("Round " + i + ": " + keys[i]);
        }
        System.out.println("-----------------------------------------");
        return keys;
    }

    public static String g_Function(String w, int round) {
        String[] B = new String[4];
        int startIndex = 0;

        for (int i = 0; i < 4; i++) {
            B[i] = w.substring(startIndex, startIndex + 2);
            startIndex += 2;
        }

        String temp = B[0];
        for (int i = 0; i < 3; i++) {
            B[i] = B[i + 1];
        }
        B[3] = temp;

        for (int i = 0; i < 4; i++) {
            int hexVal = Integer.parseInt(B[i], 16);
            B[i] = SBox[hexVal];
        }

        B[0] = padHex(XOR_HexStrings(B[0], Rcon[round]));

        StringBuilder transformedWord = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            transformedWord.append(B[i]);
        }

        return transformedWord.toString();
    }

    // Encryption

    public static String[] Encryption(String[] plainText_Parts, String[] keys, int numOfParts) {

        String[] EncryptedText_Parts = new String[numOfParts];

        for (int i = 0; i < numOfParts; i++) {

            // XOR with round key
            EncryptedText_Parts[i] = XOR_HexStrings(plainText_Parts[i], keys[0]);
            while (EncryptedText_Parts[i].length() != 32) {
                EncryptedText_Parts[i] = "0" + EncryptedText_Parts[i];
            }

            for (int j = 1; j < 11; j++) {

                // Substitution (S-BOX)
                String currentPart = EncryptedText_Parts[i];
                EncryptedText_Parts[i] = "";

                for (int m = 0; m < 16; m++) {
                    int start = m * 2;
                    int end = (m * 2) + 2;
                    int hexVal = Integer.parseInt(currentPart.substring(start, end), 16);
                    EncryptedText_Parts[i] = EncryptedText_Parts[i] + SBox[hexVal].toUpperCase();
                }

                // Shift Row
                String[][] currentMatrix = new String[4][4];
                int count = 0;
                for (int l = 0; l < 4; l++) {
                    for (int n = 0; n < 4; n++) {
                        currentMatrix[n][l] = EncryptedText_Parts[i].substring(count, count + 2);
                        count += 2;
                    }
                }

                for (int k = 1; k < 4; k++) {

                    for (int l = 0; l < k; l++) {
                        String c = currentMatrix[k][0];
                        for (int m = 0; m < 3; m++) {
                            currentMatrix[k][m] = currentMatrix[k][m + 1];
                        }
                        currentMatrix[k][3] = c;
                    }
                }

                EncryptedText_Parts[i] = "";
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        EncryptedText_Parts[i] = EncryptedText_Parts[i] + currentMatrix[l][k];
                    }

                }

                // Mix Cols
                if (j != 10) {
                    currentMatrix = MixColumns(currentMatrix);
                }

                EncryptedText_Parts[i] = "";
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        EncryptedText_Parts[i] = EncryptedText_Parts[i] + currentMatrix[l][k];
                    }

                }

                // XOR with round key
                EncryptedText_Parts[i] = XOR_HexStrings(EncryptedText_Parts[i], keys[j]);
            }

        }

        return EncryptedText_Parts;
    }

    // Mix Cols
    public static String multiplyBy02(String hex) {
        int num = Integer.parseInt(hex, 16);
        int result = num << 1;
        if ((num & 0x80) != 0) {
            result ^= 0x1B;
        }
        return String.format("%02X", result & 0xFF);
    }

    public static String multiplyBy03(String hex) {
        return XOR_HexStrings(multiplyBy02(hex), hex);
    }

    public static String[][] MixColumns(String[][] state) {
        String[][] newState = new String[4][4];

        for (int c = 0; c < 4; c++) { // For each column
            newState[0][c] = padHex(
                    XOR_HexStrings(
                            multiplyBy02(state[0][c]),
                            XOR_HexStrings(multiplyBy03(state[1][c]), XOR_HexStrings(state[2][c], state[3][c]))));
            newState[1][c] = padHex(
                    XOR_HexStrings(
                            multiplyBy02(state[1][c]),
                            XOR_HexStrings(multiplyBy03(state[2][c]), XOR_HexStrings(state[0][c], state[3][c]))));
            newState[2][c] = padHex(
                    XOR_HexStrings(
                            multiplyBy02(state[2][c]),
                            XOR_HexStrings(multiplyBy03(state[3][c]), XOR_HexStrings(state[0][c], state[1][c]))));
            newState[3][c] = padHex(
                    XOR_HexStrings(
                            multiplyBy02(state[3][c]),
                            XOR_HexStrings(multiplyBy03(state[0][c]), XOR_HexStrings(state[1][c], state[2][c]))));
        }

        return newState;
    }

    // Decryption
    public static String[] Decryption(String[] EncryptedText_Parts, String[] keys, int numOfParts) {

        String[][] currentMatrix = new String[4][4];
        String[] DecryptedText_Parts = new String[numOfParts];
        for (int i = 0; i < numOfParts; i++) {
            DecryptedText_Parts[i] = EncryptedText_Parts[i];
            String currentPart = XOR_HexStrings(DecryptedText_Parts[i], keys[10]);

            for (int j = 9; j >= 0; j--) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        currentMatrix[l][k] = currentPart.substring(count, count + 2);
                        count += 2;
                    }
                }
                currentMatrix = InvShiftRow(currentMatrix);
                currentPart = "";
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        currentPart += currentMatrix[l][k];
                    }
                }

                currentPart = InvSubBytes(currentPart);

                currentPart = XOR_HexStrings(currentPart, keys[j]);

                if (j != 0) {
                    count = 0;
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            currentMatrix[l][k] = currentPart.substring(count, count + 2);
                            count += 2;
                        }
                    }

                    currentMatrix = InverseMixColumns(currentMatrix);
                    currentPart = "";
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            currentPart += currentMatrix[l][k];
                        }
                    }
                }

                DecryptedText_Parts[i] = currentPart;
            }
        }

        return DecryptedText_Parts;
    }

    public static String[][] InvShiftRow(String[][] matrix) {
        for (int k = 1; k < 4; k++) {
            for (int l = 0; l < k; l++) {
                String c = matrix[k][3];
                for (int m = 3; m >= 1; m--) {
                    matrix[k][m] = matrix[k][m - 1];
                }
                matrix[k][0] = c;
            }
        }

        return matrix;
    }

    public static String InvSubBytes(String Encrypted) {
        String currentPart = Encrypted;
        Encrypted = "";

        for (int m = 0; m < 16; m++) {
            int start = m * 2;
            int end = (m * 2) + 2;
            int hexVal = Integer.parseInt(currentPart.substring(start, end), 16);
            Encrypted = Encrypted + InvSBox[hexVal].toUpperCase();
        }

        return Encrypted;
    }

    // Inverse Mix Columns
    // Polynomial representation for GF(2^8) in AES
    private static final int POLYNOMIAL = 0x11B;

    // Multiply in GF(2^8)
    public static String multiplyGF(String hex1, String hex2) {
        int num1 = Integer.parseInt(hex1, 16);
        int num2 = Integer.parseInt(hex2, 16);

        int product = 0;

        for (int i = 0; i < 8; i++) {
            if ((num2 & 1) != 0) {
                product ^= num1;
            }

            boolean carry = (num1 & 0x80) != 0;
            num1 <<= 1;

            if (carry) {
                num1 ^= POLYNOMIAL;
            }

            num2 >>= 1;
        }

        return Integer.toHexString(product);
    }

    public static String xorString(String hex1, String hex2) {
        int num1 = Integer.parseInt(hex1, 16);
        int num2 = Integer.parseInt(hex2, 16);

        return Integer.toHexString(num1 ^ num2);
    }

    public static String[][] InverseMixColumns(String[][] state) {
        String[][] result = new String[4][4];
        String[][] matrix = {
                { "0e", "0b", "0d", "09" },
                { "09", "0e", "0b", "0d" },
                { "0d", "09", "0e", "0b" },
                { "0b", "0d", "09", "0e" }
        };

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                result[row][col] = "00"; // Initialize to zero

                for (int i = 0; i < 4; i++) {
                    String a = matrix[row][i];
                    String b = state[i][col];
                    String product = padHex(multiplyGF(a, b));
                    result[row][col] = padHex(xorString(result[row][col], product).toUpperCase());
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a key (128-bit binary, 16-char text, or 16-char hex): ");
        String key = input.nextLine();

        if (key.length() == 128 && key.matches("[01]+")) {
            key = binaryToHex(key);
        } else if (key.length() == 16) {
            key = textToHex(key);
        } else if (key.matches("[0-9a-fA-F]+") && key.length() == 32) {
            key = key.toUpperCase();
        } else {
            throw new IllegalArgumentException("Key must be 128-bit binary, 16-char text, or 32-char hex.");
        }

        String[][] keyHex = new String[4][4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keyHex[j][i] = key.substring(count, count + 2);
                count += 2;
            }
        }

        System.out.println("Initial Key:");
        for (int i = 0; i < 4; i++) {
            System.out.println(keyHex[i][0] + " " + keyHex[i][1] + " " + keyHex[i][2] + " " + keyHex[i][3]);
        }

        String[] keys = KeyExpansion(keyHex);

        // Plain Text

        System.out.println("Enter the plain text (binary, text, hex): ");
        String plainText = input.nextLine();
        int numOfParts = 0;
        int plainText_Length = 0;
        if (plainText.matches("[01]+")) {

            plainText = binaryToHex(plainText);
            while (plainText.length() % 32 != 0) {
                plainText += "2D";
            }
            plainText_Length = plainText.length();
            numOfParts = (plainText_Length / 32);

            System.out.println("Plain text in hex: " + plainText);
            System.out.println("Number of parts: " + numOfParts);

        } else if (plainText.matches("[0-9a-fA-F]+")) {
            while (plainText.length() % 32 != 0) {
                plainText += "2D";
            }
            plainText = plainText.toUpperCase();
            plainText_Length = plainText.length();
            numOfParts = (plainText_Length / 32);

            System.out.println("Plain text in hex: " + plainText);
            System.out.println("Number of parts: " + numOfParts);

        } else {
            plainText = textToHex(plainText);
            while (plainText.length() % 32 != 0) {
                plainText += "2D";
            }
            plainText = plainText.toUpperCase();
            plainText_Length = plainText.length();
            numOfParts = (plainText_Length / 32);

            System.out.println("Plain text in hex: " + plainText);
            System.out.println("Number of parts: " + numOfParts);
        }

        String[] plainText_Parts = new String[numOfParts];

        for (int i = 0; i < numOfParts; i++) {
            int start = i * 32;
            int end = Math.min((i + 1) * 32, plainText_Length);
            plainText_Parts[i] = plainText.substring(start, end);
        }

        String[] EncryptedText_Parts_Hex = Encryption(plainText_Parts, keys, numOfParts);
        System.out.println("-----------------------------------------");
        System.out.println("Cipher text hex: ");

        for (int i = 0; i < numOfParts; i++) {
            System.out.println(EncryptedText_Parts_Hex[i]);
        }

        String EncryptedText_Parts_Ascii = "";
        System.out.println("Cipher text ascii: ");
        for (int i = 0; i < numOfParts; i++) {
            EncryptedText_Parts_Ascii += hexToText(EncryptedText_Parts_Hex[i]);
        }
        System.out.println(EncryptedText_Parts_Ascii);

        String[] EncryptedText_Parts_Binary = new String[numOfParts];
        System.out.println("Cipher text binary: ");
        for (int i = 0; i < numOfParts; i++) {
            EncryptedText_Parts_Binary[i] = hexToBinary(EncryptedText_Parts_Hex[i]);
            System.out.println(EncryptedText_Parts_Binary[i]);
        }

        // Decryption
        String[] DecryptedText_Parts_Hex = new String[numOfParts];
        DecryptedText_Parts_Hex = Decryption(EncryptedText_Parts_Hex, keys, numOfParts);
        System.out.println("-----------------------------------------");
        System.out.println("Decrypted text hex: ");

        for (int i = 0; i < numOfParts; i++) {
            System.out.println(DecryptedText_Parts_Hex[i]);
        }

        String DecryptedText_Parts_Ascii = "";
        System.out.println("Decrypted text ascii: ");
        for (int i = 0; i < numOfParts; i++) {
            DecryptedText_Parts_Ascii += hexToText(DecryptedText_Parts_Hex[i]);
        }

        String temp = "";

        for (int i = 0; i < DecryptedText_Parts_Ascii.length(); i++) {

            if (DecryptedText_Parts_Ascii.charAt(i) != '-') {
                temp += DecryptedText_Parts_Ascii.charAt(i);
            }
        }
        DecryptedText_Parts_Ascii = temp;

        System.out.println(DecryptedText_Parts_Ascii);

        String[] DecryptedText_Parts_Binary = new String[numOfParts];
        System.out.println("Decrypted text binary: ");
        for (int i = 0; i < numOfParts; i++) {
            DecryptedText_Parts_Binary[i] = hexToBinary(DecryptedText_Parts_Hex[i]);
            System.out.println(DecryptedText_Parts_Binary[i]);
        }

        input.close();
    }

}