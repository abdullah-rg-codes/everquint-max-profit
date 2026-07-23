import java.util.*;

public class MaxProfit {

    // Memo: key = timeRemaining, value = maxEarnings
    private static Map<Integer, Integer> memo = new HashMap<>();

    // Step 1: Find max earnings (your exact pattern) 
    public static int solve(int timeRemaining) {

        // Base case: no building fits in remaining time
        if (timeRemaining < 4) return 0;

        // Cache hit: already solved this subproblem
        if (memo.containsKey(timeRemaining)) {
            return memo.get(timeRemaining);
        }

        int maxEarnings = 0;

        // Try Theatre (5 units to build)
        if (timeRemaining >= 5) {
            int profit = (1500 * (timeRemaining - 5))   // earns for remaining time after built
                       + solve(timeRemaining - 5);       // best from leftover time
            maxEarnings = Math.max(maxEarnings, profit);
        }

        // Try Pub (4 units to build)
        if (timeRemaining >= 4) {
            int profit = (1000 * (timeRemaining - 4))
                       + solve(timeRemaining - 4);
            maxEarnings = Math.max(maxEarnings, profit);
        }

        // Try Commercial Park (10 units to build)
        if (timeRemaining >= 10) {
            int profit = (2000 * (timeRemaining - 10))
                       + solve(timeRemaining - 10);
            maxEarnings = Math.max(maxEarnings, profit);
        }

        // Cache and return
        memo.put(timeRemaining, maxEarnings);
        return maxEarnings;
    }

    // Step 2: Find ALL solutions that match max earnings
    //
    //  Same countdown logic at each step try every building,
    //  only follow paths where: earnedSoFar + solve(timeRemaining) == target
    //  This uses the ALREADY FILLED memo to prune dead branches.
    //
    public static void findSolutions(
            int timeRemaining,
            int target,
            int earnedSoFar,
            int theatres,
            int pubs,
            int parks,
            List<int[]> results,
            Set<String> seen) {

        //  Prune: best possible from here can't hit target
        if (earnedSoFar + solve(timeRemaining) < target) return;

        boolean builtSomething = false;

        // Try Theatre
        if (timeRemaining >= 5) {
            builtSomething = true;
            int gain = 1500 * (timeRemaining - 5);
            findSolutions(
                timeRemaining - 5,   // countdown after building
                target,
                earnedSoFar + gain,
                theatres + 1,        // one more theatre
                pubs,
                parks,
                results, seen
            );
        }

        // Try Pub
        if (timeRemaining >= 4) {
            builtSomething = true;
            int gain = 1000 * (timeRemaining - 4);
            findSolutions(
                timeRemaining - 4,
                target,
                earnedSoFar + gain,
                theatres,
                pubs + 1,            // one more pub
                parks,
                results, seen
            );
        }

        // Try Commercial Park
        if (timeRemaining >= 10) {
            builtSomething = true;
            int gain = 2000 * (timeRemaining - 10);
            findSolutions(
                timeRemaining - 10,
                target,
                earnedSoFar + gain,
                theatres,
                pubs,
                parks + 1,           // one more park
                results, seen
            );
        }

        // Leaf node: nothing more fits 
        // Check if this path's total earnings hit the target
        if (!builtSomething && earnedSoFar == target) {
            String key = theatres + "-" + pubs + "-" + parks;
            if (seen.add(key)) {
                results.add(new int[]{theatres, pubs, parks});
            }
        }
    }

    // Step 3: Print result
    public static void printResult(int n) {
        // reset memo for each test case        
        memo.clear();                          
        // fill memo, get answer
        int maxEarnings = solve(n);            

        List<int[]>  results = new ArrayList<>();
        Set<String>  seen    = new HashSet<>();

        // trace all optimal paths
        findSolutions(                         
            n, maxEarnings, 0,
            0, 0, 0,
            results, seen
        );

        System.out.println("Input   : Time Unit = " + n);
        System.out.println("Earnings: $" + maxEarnings);
        System.out.println("Solutions:");
        for (int i = 0; i < results.size(); i++) {
            int[] r = results.get(i);
            System.out.printf("  %d. T: %d  P: %d  C: %d%n",
                i + 1, r[0], r[1], r[2]);
        }
        System.out.println();
    }

    // Test cases
    public static void main(String[] args) {
        printResult(7);    
        printResult(8);    
        printResult(13);
    }
}
