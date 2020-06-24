import java.util.*;

public class Main {

    public static char[][] world;
    public static int[][] status;
    public static Deque<int[]> allInfectedCoordinates = new ArrayDeque<>();
    public static int[] firstInfected = new int[2];
    public static int countDays = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<char[]> rows = new ArrayList<>();
        String current;

        while (!(current = sc.nextLine()).isEmpty()) {
            rows.add(current.toCharArray());
        }

        char[] infectedPosition = rows.get(rows.size() - 1);
        rows.remove(rows.size() - 1);

        for (int i = 0; i < firstInfected.length; i++) {
            firstInfected[i] = Integer.parseInt(String.valueOf(infectedPosition[i]));
        }

        world = new char[rows.size()][rows.get(0).length];
        status = new int[rows.size()][rows.get(0).length];

        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                world[i][j] = rows.get(i)[j] == '#' ? 'H' : '.';
                status[i][j] = world[i][j] == 'H' ? 0 : -1;
            }
        }

        printMatrix(world);
        System.out.println(Arrays.toString(codenavirus(world, firstInfected)));

    }

    public static int[] codenavirus(char[][] world, int[] firstInfected) {
        if (isValidPosition(firstInfected[0], firstInfected[1])){
            countDays++;
            world[firstInfected[0]][firstInfected[1]] = 'I';
            status[firstInfected[0]][firstInfected[1]]++;
            allInfectedCoordinates.add(firstInfected);

            printMatrix(world);

            dayCounter();
        }

        int infectedAtEnd = 0;
        int recoveredAtEnd = 0;
        int uninfectedAtEnd = 0;

        for (char[] chars : world) {
            for (char aChar : chars) {
                if (aChar == 'H') {
                    uninfectedAtEnd++;
                } else if (aChar == 'I') {
                    infectedAtEnd++;
                } else if (aChar == 'R') {
                    recoveredAtEnd++;
                }
            }
        }

        return new int[]{countDays, infectedAtEnd, recoveredAtEnd, uninfectedAtEnd};
    }

    private static void dayCounter() {
        countDays++;
        Deque<int[]> toTraverse = new ArrayDeque<>();

        allInfectedCoordinates.removeIf(el -> world[el[0]][el[1]] == 'R');

        allInfectedCoordinates.forEach(toTraverse::offer);
        allInfectedCoordinates.clear();

        int check = toTraverse.size();

        while (!toTraverse.isEmpty()) {
            int[] coordinates = toTraverse.poll();
            status[coordinates[0]][coordinates[1]]++;

            if (status[coordinates[0]][coordinates[1]] > 3) {
                world[coordinates[0]][coordinates[1]] = 'R';
                check--;
            } else {
                if (isValidPosition(coordinates[0], coordinates[1] + 1)
                                || isValidPosition(coordinates[0] - 1, coordinates[1])
                                || isValidPosition(coordinates[0], coordinates[1] - 1)
                                || isValidPosition(coordinates[0] + 1, coordinates[1])) {
                    if (toInfect(coordinates[0], coordinates[1] + 1)) {
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        allInfectedCoordinates.offer(new int[]{coordinates[0], coordinates[1] + 1});
                        status[coordinates[0]][coordinates[1] + 1]++;
                    } else if (toInfect(coordinates[0] - 1, coordinates[1])) {
                        allInfectedCoordinates.offer(new int[]{coordinates[0] - 1, coordinates[1]});
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        status[coordinates[0] - 1][coordinates[1]]++;
                    } else if (toInfect(coordinates[0], coordinates[1] - 1)) {
                        allInfectedCoordinates.offer(new int[]{coordinates[0], coordinates[1] - 1});
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        status[coordinates[0]][coordinates[1] - 1]++;
                    } else if (toInfect(coordinates[0] + 1, coordinates[1])) {
                        allInfectedCoordinates.offer(new int[]{coordinates[0] + 1, coordinates[1]});
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        status[coordinates[0] + 1][coordinates[1]]++;
                    }

                }else {
                    allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                    check--;
                }
            }
        }

        printMatrix(world);

        if (check == 0){
            return;
        }


        dayCounter();
    }

    private static boolean toInfect(int row, int col) {
        if (isValidPosition(row, col)) {
            world[row][col] = 'I';
            return true;
        }
        return false;
    }

    private static void printMatrix(char[][] world) {
        for (char[] chars : world) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isValidPosition(int toInfectRow, int toInfectCol) {
        return toInfectRow >= 0
                && toInfectRow < world.length
                && toInfectCol >= 0
                && toInfectCol < world[toInfectRow].length
                && world[toInfectRow][toInfectCol] == 'H';
    }


}
