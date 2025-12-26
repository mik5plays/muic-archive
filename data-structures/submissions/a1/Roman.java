public class Roman {
    // Helper function to simplify code
    public static int roman(char r) {
        return switch (r) {
            default -> 0;
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
        };
    }
    public static int romanToInt(String romanNum) {
        int result = 0;
        int index = 0;
        while (index < romanNum.length()) {
            if (index == romanNum.length() - 1) {
                result += roman(romanNum.charAt(index));
                break;
            } else {
                if (roman(romanNum.charAt(index)) < roman(romanNum.charAt(index+1)) ) {
                    result += roman(romanNum.charAt(index+1)) - roman(romanNum.charAt(index));
                    index += 2;
                } else {
                    result += roman(romanNum.charAt(index));
                    index++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(romanToInt("I"));
        System.out.println(romanToInt("V"));
        System.out.println(romanToInt("VII"));
        System.out.println(romanToInt("MCMLIV"));
        System.out.println(romanToInt("MCMXC"));
    }
}
