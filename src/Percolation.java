public class Percolation {
    static final int size = 10;
    static final int length = size * size;
    static boolean[] grid = new boolean[length];
	
	public static void main(String[] args) {
		if (args.length == 0) {
            System.err.println("You have to give the number of simulations!");
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);

            long start = System.currentTimeMillis();
            double threshold = Percolation.monteCarlo(n);
            long end = System.currentTimeMillis();

            System.out.println("Percolation theshold after '" + n + "' simulations : " + threshold);
            System.out.println("Execution Time: " + (end - start) + " ms");
        } catch (NumberFormatException e) {
            System.err.println("You have to provide a non null positive integer value for the number of simulations!");
        }

        // // Init tests
        // Percolation.init();
		// Percolation.print();
        // System.out.println("\n");

        // // print tests
        // // Test 1
        // Percolation.init();
        // grid[2 * size + 4] = true;
        // Percolation.print();

        // System.err.println("");

        // // Test 2
        // Percolation.init();
        // for (int i = 0; i < 2; i++) {
        //     for (int j = 0; j < size; j++) {
        //         grid[i * size + j] = true;
        //     }
        // } 
        // for (int j = 0; j < size; j++) {
        //     grid[3 * size + j] = true;
        // }
        // for (int i = 5; i < size; i++) {
        //     for (int j = 0; j < size; j++) {
        //         grid[size + i] = true;
        //         grid[i * size + j] = true;
        //     }
        // } 
        // Percolation.print();

        // // Test of randomShadow
        // int rand_index;
        // for (int i = 0; i < 70; i++) {
        //     rand_index = Percolation.randomShadow();
        //     // System.out.println("Random index: " + rand_index);
        // }
        // Percolation.print();

        // // Test of isNaivePercolation(int n)
        // System.out.println(Percolation.isNaivePercolation(25));

        // // Test of percolation()
        // Double percentage = Percolation.percolation();
        // Percolation.print();
        // System.out.println("Percertange for percolation: " + percentage);

        // // Test of monteCarlo()
        // System.out.println("Percolation theshold: " + Percolation.monteCarlo(20));
	
        // Test of fastFind && fastUnion
        // Percolation.init();
		// Percolation.print();
        // System.out.println("\n");

        // int rand_index;
        // for (int i = 0; i < 5; i++) {
        //     rand_index = Percolation.randomShadow();
        //     Percolation.print();
        //     for (int j = 0; j < length; j++) {
        //         System.out.print(j + " -> " + UnionFind.find(j) + ", ");
        //     }
        //     System.out.println("\n");
        // }
    }
	
	// public static void init() {
    //     for (int i = 0; i < length; i++) {
    //         grid[i] = false;
    //     }
	// }

    public static void init() {
        for (int i = 0; i < length; i++) {
            grid[i] = false;
        }

        UnionFind.init(length+2); 
        // equiv[length] will represent the top element
        // equiv[length+1] will represent the bottom element
	}
	
	public static void print() {
		for (int i = 0; i < length; i++) {
			if (grid[i] == true)
				System.out.print('*');
			else
				System.out.print('-');
			if ((i + 1) % size == 0)
				System.out.println();
		}
	}

    // public static int randomShadow() {
    //     int rand_index;
    //     do { 
    //         rand_index = (int) (Math.random() * length);
    //     } while (grid[rand_index] == true);
        
    //     grid[rand_index] = true;
	// 	return rand_index;
	// }

    //TODO: randomShadow2()

    public static int randomShadow() {
        int rand_index;
        do { 
            rand_index = (int) (Math.random() * length);
        } while (grid[rand_index] == true);
        
        grid[rand_index] = true;
        propagateUnion(rand_index);
		return rand_index;
	}

    public static boolean isNaivePercolation(int n) {
        boolean[] seen = new boolean[length];

        return  detectPath(seen, n, true) && detectPath(seen, n, false);
    }

    private static boolean detectPath(boolean[] seen, int n, boolean up) {
        boolean ispath;
        boolean[] seenUpdated;

        if (up && n < size) { // n is a top cell in the matrix
            return true;
        }

        if (!up && n >= length - size) { // n is a bottow cell in the matrix
            return true;
        }

        if (n % size != 0 && seen[n-1] == false && grid[n-1] == true) { // There is an unseen cell, at the left side of cell n in the matrix
            seenUpdated = seen.clone();
            seenUpdated[n] = true;
            ispath = detectPath(seenUpdated, n-1, up);
            if (ispath) {
                return true;
            }
        }

        if ((n % size != size - 1) && seen[n+1] == false && grid[n+1] == true) { // There is an unseen cell, at the right side of cell n, in the matrix
            seenUpdated = seen.clone();
            seenUpdated[n] = true;
            ispath = detectPath(seenUpdated, n+1, up);
            if (ispath) {
                return true;
            }
        }

        if (n >= size && seen[n-size] == false && grid[n-size] == true) { // There is an unseen cell, on the top of cell n, in the matrix
            seenUpdated = seen.clone();
            seenUpdated[n] = true;
            ispath = detectPath(seenUpdated, n-size, up);
            if (ispath) {
                return true;
            }
        }

        if (n < length - size && seen[n+size] == false && grid[n+size] == true) { // There is an unseen cell, bellow the cell n, in the matrix
            seenUpdated = seen.clone();
            seenUpdated[n] = true;
            ispath = detectPath(seenUpdated, n+size, up);
            if (ispath) {
                return true;
            }
        }

        return false;
    }

    public static boolean isFastPercolation(int n) {
        for (int i = 0; i < size; i++) {
            for (int j = length - size; j < length; j++) {
                if (UnionFind.find(i) == UnionFind.find(j)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isLogPercolation() {
        return UnionFind.find(length) == UnionFind.find(length+1);
    }

    public static boolean isPercolation(int n) {
        // return isNaivePercolation(n);
        // return isFastPercolation(n);
        return isLogPercolation();
    }

    public static double percolation() {
        int rand_index;
        int blackened_cells = 0;
        do { 
            rand_index = randomShadow();
            blackened_cells ++;
        } while (!isPercolation(rand_index));

        return blackened_cells / (double) length;
    }

    public static double monteCarlo(int n) {
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            init();
            sum += percolation();
        }

        return sum / n;
    }

    public static void propagateUnion(int x) {
        if (x < size) { // we are dealing with top cells
            UnionFind.union(x, length);
        }

        if (x >= length - size) { // we are dealing with bottom cells
            UnionFind.union(x, length+1);
        }
        if (x % size != 0 && grid[x-1] == true) { 
            UnionFind.union(x-1, x);
            // UnionFind.union(x, x-1);
        }

        if ((x % size != size - 1) && grid[x+1] == true) { 
            UnionFind.union(x+1, x);
            // UnionFind.union(x, x+1);
        }

        if (x >= size && grid[x-size] == true) { 
            UnionFind.union(x-size, x);
            // UnionFind.union(x, x-size);
        }

        if (x < length - size && grid[x+size] == true) {
            UnionFind.union(x+size, x);
            // UnionFind.union(x, x+size);
        }
    }
}
