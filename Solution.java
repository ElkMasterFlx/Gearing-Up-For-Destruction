package fooBar;

public class Solution {
	public static void main(String[] args) {
		int[] b = new int[] {4, 35, 50, 80, 120};
		TestClass.solution(b);		
	}
	// solution() loops through gearOneSize[0] and gearOneSize[1] proposed values, and implements ratioCheck() method to check if solution is viable
	// 1 <= size of first gear = gearOneSize[0]/gearOneSize[1] <= pegLocations[1] - pegLocations[0] - 1; 
	// The size of the first gear is as a fraction of 2 integers (size of first gear = gearOneSize[0]/gearOneSize[1])
	// gearOneSize[0] value can approach infinity while satisfying 1 <= gearOneSize[0]/gearOneSize[1] <= pegLocation[1] - pegLocation[0] -1
	// The for loop produces values for gearOneSize[0] as large as 214784, 
	// 214784 is the largest value for gearOneSize[0] which cannot cause pegLocationsFactor[] values to exceed the largest value data type int can store (2e31 - 1)
	// This maximizes possibility for solution() to discover a value that produces appropriate gear ratio
	public static void solution(int[] pegLocations) {
		int[] gearOneSize = new int[2];
		for (int i = 1; i < 13; i++) {                                                    
			gearOneSize[0] = i;
			// minimum value for j = gearOneSize[1]
			// i/j <= pegLocations[1] - pegLocations[0] -1
			// j >= i/(peg spacing)
			for (int j = i; j >= 1; j--) {
				gearOneSize[1] = j;
				if (ratioCheck(gearOneSize, pegLocations) == true) {
					System.out.println(gearOneSize[0] + ", " + gearOneSize[1]);
					return;
				}
			}
		}
		System.out.println("-1, -1");
	}
	// RatioCheck() creates subsequent gears and calculates gear ratio when passed gearOneSize[] and pegLocations[]
	// When ratioCheck() produces gear ratio 2:1, ratioCheck() returns true to solution(), solution() returns gearOneSize[] to user
	// Rather than calculate produced gear ratios using quotient of arrays representing numerator and denominator of gear sizes, new variables gearOneSize[] and pegLocations[] are created
	// By storing new variables previousGearSize (gearOneSize[0]/gearOneSize[1] * gearOneSize[1]) and pegLocationsFactor[] (pegLocations[] * gearOneSize[1]) we can now calculate subsequent gear sizes and final gear ratio using only integers
	// gearRatioNumerator and gearRatioDenominator represent the first and last gear size, or numerator and denominator of the final gear ratio
	// gearRatioNumerator and gearRatioDenominator are stored as long integers
	// Rather than performing ratio calculations as a quotient and storing the resulting decimal value which could sacrifice accuracy, and sacrifice the ability to pinpoint ratio of 2:1, gearRatioNumerator == 2 * gearRatioDenominator is assessed to check if gear ratio is 2:1
	// gearRatioNumerator (represents first gear size) and gearRatioDenominator (represents last gear size) are the only values that need to be stored, each intermediate gear size cancels itself out when calculating ratios between all gears
	public static boolean ratioCheck(int[] gearOneSize, int[] pegLocations) {
		//!!!!!!!!!!!!!!!!!!!!!does this need to multiply previous gear size by numerator and denominator of gearOneSize????????????????????????????????
		int[] pegLocationsFactor = pegLocations.clone();
		int previousGearSize = gearOneSize[0];
		int nextGearSize;
		int gearRatioNumerator = 1;
		int gearRatioDenominator = 1;
		boolean check = false;
		for (int i = 0; i < pegLocationsFactor.length; i++) {
			pegLocationsFactor[i] = gearOneSize[1] * pegLocationsFactor[i]; 
		}
		for (int i = 0; i < (pegLocationsFactor.length - 1); i++) {
			nextGearSize = pegLocationsFactor[i + 1] - pegLocationsFactor[i] - previousGearSize;
			if (i == 0) gearRatioNumerator *= previousGearSize; 
			if (i == pegLocationsFactor.length - 2) gearRatioDenominator *= nextGearSize; 
			if (nextGearSize < 1) check = true;
			previousGearSize = nextGearSize;
		}
		if (check == false) return (gearRatioNumerator == gearRatioDenominator * 2);		
		else return false;
	}
}

//As Commander Lambda's personal assistant, you've been assigned the task of configuring the LAMBCHOP doomsday device's axial orientation gears. It should be pretty simple -- just add gears 
//to create the appropriate rotation ratio. But the problem is, due to the layout of the LAMBCHOP and the complicated system of beams and pipes supporting it, the pegs that will support the 
//gears are fixed in place.

//The LAMBCHOP's engineers have given you lists identifying the placement of groups of pegs along various support beams. You need to place a gear on each peg (otherwise the gears will collide 
//with unoccupied pegs). The engineers have plenty of gears in all different sizes stocked up, so you can choose gears of any size, from a radius of 1 on up. Your goal is to build a system 
//where the last gear rotates at twice the rate (in revolutions per minute, or rpm) of the first gear, no matter the direction. Each gear (except the last) touches and turns the gear on the 
//next peg to the right.

//Given a list of distinct positive integers named pegs representing the location of each peg along the support beam, write a function solution(pegs) which, if there is a solution, returns a 
//list of two positive integers a and b representing the numerator and denominator of the first gear's radius in its simplest form in order to achieve the goal above, such that radius = a/b. 
//The ratio a/b should be greater than or equal to 1. Not all support configurations will necessarily be capable of creating the proper rotation ratio, so if the task is impossible, the 
//function solution(pegs) should return the list [-1, -1].

//For example, if the pegs are placed at [4, 30, 50], then the first gear could have a radius of 12, the second gear could have a radius of 14, and the last one a radius of 6. Thus, the last 
//gear would rotate twice as fast as the first one. In this case, pegs would be [4, 30, 50] and solution(pegs) should return [12, 1].

//The list pegs will be given sorted in ascending order and will contain at least 2 and no more than 20 distinct positive integers, all between 1 and 10000 inclusive.

//Input:
//Solution.solution({4, 17, 50})
//Output:
//  -1,-1

//Input:
//Solution.solution({4, 30, 50})
//Output:
//  12,1
