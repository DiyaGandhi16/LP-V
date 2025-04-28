import java.util.*;
import java.util.stream.IntStream;

public class ParallelReduction {

    public static void minReduction(int[] arr) {
        int min = IntStream.of(arr).parallel().min().orElse(Integer.MAX_VALUE);
        System.out.println("Minimum value: " + min);
    }

    public static void maxReduction(int[] arr) {
        int max = IntStream.of(arr).parallel().max().orElse(Integer.MIN_VALUE);
        System.out.println("Maximum value: " + max);
    }

    public static void sumReduction(int[] arr) {
        int sum = IntStream.of(arr).parallel().sum();
        System.out.println("Sum: " + sum);
    }

    public static void averageReduction(int[] arr) {
        if (arr.length == 0) {
            System.out.println("Average: Cannot calculate (array size too small)");
            return;
        }

        double avg = IntStream.of(arr).parallel().average().orElse(Double.NaN);
        System.out.println("Average: " + avg);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of elements: ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.err.println("Error: Array size must be positive");
            return;
        }

        int[] arr = new int[n];
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        minReduction(arr);
        maxReduction(arr);
        sumReduction(arr);
        averageReduction(arr);

        scanner.close();
    }
}
