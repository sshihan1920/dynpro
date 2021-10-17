import java.io.FileNotFoundException;

public class MakingChange {
	static int calls;
	static int[][] table;
	
//Recursive Make Change Method
	public static boolean recMakeChange(int amount, int[] denominations,int[] counts) {
		boolean rvalue = false;
		++calls;
		
		if (amount == 0) return true;
		else if (amount < 0) return false;
		
		if (table[amount] != null) {
			 for (int k = 0; k < table[amount].length; k++)
			 counts[k] = table[amount][k];
			 return true;
			}
		
		else {
			int[] temp = new int[counts.length];
			int[] best = new int[counts.length];
			best[best.length - 1] = amount + 1;
			
			for (int i = 0; i < denominations.length; ++i) {
				if (recMakeChange(amount - denominations[i], denominations, temp)) {
					 if (temp[temp.length-1] < best[best.length-1]) {
					 temp[i]++;
					 temp[temp.length-1]++;
					 for(int z = 0; z<best.length;z++)
					 best[z] = temp[z];
					 }
					 rvalue = true;
					}
			}
			
			/*
			if (rvalue == true) {
				for (int x = 0; x < best.length; x++) counts[x] = best[x];
				return true;
			}
			*/
			
			if (rvalue) {
				 table[amount] = new int[counts.length];
				 for(int z = 0; z < best.length; z++)
				 table[amount][z] = counts[z] = best[z];
				 return true;
				}
			return false;
		}	
		
		/*
		 	If the amount is zero, then a solution
			has been found and the method returns true. If the amount is less than we have a bad attempt
			and the method should return false.
		 */
	}
	
//Make Change Method
	private static int[] makeChange(int amount, int[] denominations) {
		int[] counts = new int[denominations.length + 1];
		table = new int[amount+1][];
		
		//then make a call on the (yet to be written) recursive method
		/*. After the recursion completes, the counts for the optimal set of change should be in the
			counts array. So, our method then needs to “unpack” the counts into the format we want for 
			output
			*/
		
		recMakeChange(amount, denominations, counts);
		int[] returnArr = new int[counts[counts.length - 1]];
		buildReturn(counts, denominations, returnArr);
		
		return returnArr;
	}

//Assign Return Array Method
	public static void buildReturn(int[] counts, int[] denominations, int[] returnArr) {
		/*Loop through the counts array and place the appropriate values into the return array
		 For
		example, if the denominations array contained {25, 10, 5, 1}, then for the amount of 63 the
		counts array should contain {2, 1, 0, 3, 6}. We can allocate a return array of length 6. Using
		loops we can fill the return array with {25, 25, 10, 1, 1, 1} 
		*/
		int x = 0;
		int y = 0;
		for (int i = 0; i < counts.length - 1; i++) {
			int temp = counts[i];

			while (temp > 0) {
				returnArr[x] = denominations[y];
				temp--;
				x++;
			}
			
			y++;
		}
	}
	
//Main Method	
	public static void main(String [] args){			
		//Default Value
		//int[] denominations = { 100000, 5000, 2000, 1000, 500, 100, 25, 10, 5, 1 };
		int[] denominations = {25, 10, 5, 1};
				int amount = 63; // $0.63, or 63 pennies
		calls = 0;		
		//Input Value		
				if (args.length == 1)
				 amount = Integer.parseInt(args[0]);
						
		int[] change = makeChange(amount, denominations);
		System.out.print("Change for " + amount + " is {");
		for (int i = 0; i< change.length; i++) {
			if (i == change.length - 1) {
				System.out.print(change[i]);
			}
			else System.out.print(change[i] + ", ");
		}
		
		System.out.println("}");
		System.out.println(calls);
	}
}


