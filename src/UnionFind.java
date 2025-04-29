

public class UnionFind {
    static int[] equiv;
    static int[] height;
	
    public static void init(int len) {
        equiv = new int[len];
        height = new int[len];

        for (int i = 0; i < len; i++) {
            equiv[i] = i;
            height[i] = 0;
        }
    }

    public static int naiveFind(int x) {
        return equiv[x];
    }

    public static int fastFind(int x) {
        while (x != equiv[x]) {
            x = equiv[x];
        }
        return x;
    }

    public static int logFind(int x) {
        while (x != equiv[x]) {
        // if (x != equiv[x]) {
            equiv[x] = equiv[equiv[x]];
            x = equiv[x];
            // equiv[x] = logFind(equiv[equiv[x]]);
        }
        
        return x;
        // return equiv[x];
    }

    public static int naiveUnion(int x, int y) {
        int reprX = equiv[x];
        int reprY = equiv[y];
        for (int i = 0; i < equiv.length; i++) {
            if (equiv[i] == reprX) {
                equiv[i] = reprY;
            }
        }

        return reprY;
    }

    public static int fastUnion(int x, int y) {
        int repr = find(y);
        equiv[find(x)] = repr;
        return repr;
    }

    public static int logUnion(int x, int y) {
        int reprX = find(x);
        int reprY = find(y);
        if (height[reprX] < height[reprY]) {
            equiv[reprX] = reprY;
            return reprY;
        } else {
            equiv[reprY] = reprX;
            if (height[reprX] ==  height[reprY]) {
                height[reprX] += 1;
            }
            return reprX;
        }   
    }

    public static int find(int x) {
        // return naiveFind(x);
        // return fastFind(x);
        return logFind(x);
    }

    public static int union(int x, int y) {
        // return naiveUnion(x, y);
        // return fastUnion(x, y);
        return logUnion(x, y);
    }
}
