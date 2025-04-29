import java.util.*;
import java.util.stream.IntStream;

public class ParallelReduction
{
	public static void minReduction(int[] arr)
	{
		int min = IntStream.of(arr).parallel().min().orElse(Integer.MAX_VALUE);
		System.out.println("Minimum value is : " + min);
	}

	public static void maxReduction(int[] arr)
	{
		int max = IntStream.of(arr).parallel().max().orElse(Integer.MIN_VALUE);
		System.out.println(max);
	}

	public static void sumReduction(int[] arr)
	{
		int sum = IntStream.of(arr).parallel().sum();
		System.out.println(sum);
	}

	public static void avgReduction(int[] arr)
	{
		if(arr.length <= 0)
		{
			System.out.println("error");
		}
		else 
		{
			double avg = IntStream.of(arr).parallel().average().orElse(Double.NaN);
			System.out.println(avg);
		}
	}


	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter length :");
		int n = sc.nextInt();

		if( n <= 0)
		{
			System.out.println("No length");
			return;
		}
		int[] arr = new int[n];
			
			for ( int i = 0; i < n; i++)
			{
				System.out.println("Element :");
				arr[i] = sc.nextInt();
			}
		
		minReduction(arr);
		maxReduction(arr);
		sumReduction(arr);
		avgReduction(arr);

		sc.close();
	}
}
