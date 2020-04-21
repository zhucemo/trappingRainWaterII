public class Solution {

    static int[][] mapping;
    static int[][] heightMap;
    static int iM;
    static int jM;
    static ArrayList<Point>[] limitLists;


    static public int trapRainWater(int[][] heightMap) {
        Solution.heightMap = heightMap;
        iM = heightMap.length;
        jM = heightMap[0].length;
        int[][] mapping = new int[iM][];
        ArrayList<Point>[] limitLists = new ArrayList[20000];
        Solution.limitLists = limitLists;
        for (int i = 0; i < iM; i++) {
            int[] row = new int[jM];
            for (int j = 0; j < jM; j++) {
                if (i == 0 || j == 0 || i == iM - 1 || j == jM - 1) {
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    ArrayList<Point> limits = limitLists[heightMap[i][j] - 1];
                    if (limits == null) {
                        limits = new ArrayList<>();
                        limitLists[heightMap[i][j] - 1] = limits;
                    }
                    limits.add(point);
                    row[j] = 0;
                } else {
                    row[j] = 1;
                }
            }
            mapping[i] = row;
        }

        Solution.mapping = mapping;
        int yield = 0;
        for (int i = 0; i < limitLists.length; i++) {
            ArrayList<Point> limits = limitLists[i];
            if (limits == null)
                continue;

            for (int lp = 0; lp < limits.size(); lp++) {
                Point point = limits.get(lp);
                yield += stream(point.x - 1, point.y, heightMap[point.x][point.y]);
                yield += stream(point.x + 1, point.y, heightMap[point.x][point.y]);
                yield += stream(point.x, point.y - 1, heightMap[point.x][point.y]);
                yield += stream(point.x, point.y + 1, heightMap[point.x][point.y]);
            }
        }

        return yield;
    }


    static public int stream(int i, int j, int height) {
        if (i < 0 || j < 0 || i >= iM || j >= jM)
            return 0;
        if (mapping[i][j] == 0)
            return 0;
        mapping[i][j] = 0;
        if (height <= heightMap[i][j]) {
            Point point = new Point();
            point.x = i;
            point.y = j;
            ArrayList<Point> limits = limitLists[heightMap[i][j] - 1];
            if (limits == null) {
                limits = new ArrayList<>();
                limitLists[heightMap[i][j] - 1] = limits;
            }
            limits.add(point);
            return 0;
        }
        int water = height - heightMap[i][j];
        water += stream(i - 1, j, height);
        water += stream(i + 1, j, height);
        water += stream(i, j - 1, height);
        water += stream(i, j + 1, height);
        return water;
    }
}

class Point {
    int x;
    int y;
}