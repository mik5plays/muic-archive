public class Diamond {
    public static void printDiamond(int k) {
        for (int i=1;i<=k;i++) {
            System.out.println("#".repeat(k-i+1)+"*".repeat((2*i)-1)+"#".repeat(k-i+1));
        }
        for (int j=k-1;j>=1;j--) {
            System.out.println("#".repeat(k-j+1)+"*".repeat((2*j)-1)+"#".repeat(k-j+1));
        }

    }
    public static void main(String[] args) {
        printDiamond(2);

    }
}
