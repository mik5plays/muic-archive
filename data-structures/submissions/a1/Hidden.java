public class Hidden {
    public static boolean isHidden(String s, String t) {
        String wordSoFar = "";
        int scan = 0;
        for (int i = 0; i < s.length();i++) {
            if (s.charAt(i) == t.charAt(scan)) {
                wordSoFar = wordSoFar.concat(String.valueOf(s.charAt(i)));
                scan++;
            }
            if (wordSoFar.equals(t)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Boolean.toString(isHidden("welcometothehotelcalifornia","melon")));
        System.out.println(Boolean.toString(isHidden("welcometothehotelcalifornia","space")));
        System.out.println(Boolean.toString(isHidden("TQ89MnQU3IC7t6","MUIC")));
        System.out.println(Boolean.toString(isHidden("VhHTdipc07","htc")));
        System.out.println(Boolean.toString(isHidden("VhHTdipc07","hTc")));
    }
}
