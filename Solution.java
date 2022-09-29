import java.lang.Math;
public class Solution {	
	/* Configures the LAMBCHOP doomsday device's axial orientation gears to produce gear ratio of 2
	 * '.solution()' method returns the radius of the devices primary gear in array 'primaryGearSize[]' from an array of peg locations 'pegLocations[]'
	 * If configuration is not feasible, '.solution()' returns array {-1, -1}
	 * Driving equations:
	 *		n = number of peg locations
	 *		g[i] = gear radius at index i
	 *		p[i] = location of peg at index i = pegLocations[i]
	 *		g[0] / g[n - 1] = 2
	 *		g[i] + g[i + 1] = p[i + 1] - p[i], for index [0] through [n - 1]
	 * Resulting equation implemented in '.solution()' method:
	 *		g[0] = 2 (fP(n - 1)) / (1 + 2 * Math.pow(-1, n)) = numeratorPrimaryGear/denominatorPrimaryGear
	 *		Recursive equation 'fP(i)', 'd' is recursive difference:
	 *			Initial condition: fP(i) = p[i] - p[i - 1], i--, fP(i) = d - (p[i] - p[i - 1]), Halting Condition: fP(0) = d - 0
	 *			executed via '.fPRecursion()' method 
	 * '.solution()' method stores numerator and denominator of radius of 'g[0]' separately in variables 'numeratorPrimaryGear' & 'denominatorPrimaryGear'
	 */
	public static int[] solution(int[] pegLocations) {
		int numeratorPrimaryGear = 2 * fPRecursion(pegLocations, pegLocations.length);
		int denominatorPrimaryGear = 1 + 2 * (int)Math.pow(-1, pegLocations.length);
		// If denominatorPrimaryGear is -1, invert numeratorPrimaryGear & denominatorPrimaryGear
		if (denominatorPrimaryGear == -1) {
			numeratorPrimaryGear *= -1;
			denominatorPrimaryGear *= -1;
		}
		// If numeratorPrimaryGear & denominatorPrimaryGear are both factors of 3, reduce each by factor of 3
		if (numeratorPrimaryGear % 3 == 0 && denominatorPrimaryGear % 3 == 0) {
			numeratorPrimaryGear /= 3;
			denominatorPrimaryGear /= 3;
		}
		// Checks if all gear sizes are feasible. Gears should have radius greater than one, and less than distance between pegs minus one
		double nextGear = numeratorPrimaryGear/denominatorPrimaryGear;
		boolean isFeasible = true;
		for (int i = 0; i < pegLocations.length - 1; i++) {
			if (nextGear < 1 || pegLocations[i+1] - pegLocations[i] -1 < nextGear) {
				isFeasible = false;
			}	
			nextGear = pegLocations[i + 1] - pegLocations[i] - nextGear;
		}
		// If numeratorPrimaryGear & denominatorPrimaryGear are feasible, return values in array primaryGearSize. If not feasible, return {-1, -1}
		int[] primaryGearSize = {numeratorPrimaryGear, denominatorPrimaryGear};
		if (isFeasible) {
			return primaryGearSize;
		}
		int[] fail = {-1, -1};
		return fail;		
	}
	// Recursive calculation for fP(i)
	static int fPRecursion(int[] pegLocations, int i) {
		i--;
		if (i == 0) 
			return 0;
		return (pegLocations[i] - pegLocations[i-1] - fPRecursion(pegLocations, i));
	}
}