import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<String> rows = new ArrayList<>();

        String current;
        while (!(current = sc.nextLine()).isEmpty()) {
            rows.add(current);
        }

        int[] firstInfected = Arrays.stream(rows.get(rows.size() - 1).split("\\s+|,| ,"))
                .mapToInt(Integer::parseInt).toArray();
        rows.remove(rows.size() - 1);

        char[][] world = new char[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            world[i] = rows.get(i).toCharArray();
        }

        System.out.println(Arrays.toString(codenavirus(world, firstInfected)));

    }

    public static int[] codenavirus(char[][] world, int[] firstInfected) {
        Deque<int[]> allInfectedCoordinates = new ArrayDeque<>();
        int[][] status = new int[world.length][];
        int countDays = 0;

        for (int i = 0; i < world.length; i++) {
            status[i] = new int[world[i].length];
            for (int j = 0; j < world[i].length; j++) {
                world[i][j] = world[i][j] == '#' ? 'H' : '.';
                status[i][j] = world[i][j] == 'H' ? 0 : -1;
            }
        }

        if (isValidPosition(world, firstInfected[0], firstInfected[1])){
            countDays++;

            world[firstInfected[0]][firstInfected[1]] = 'I';
            status[firstInfected[0]][firstInfected[1]]++;

            allInfectedCoordinates.add(firstInfected);

            countDays = dayCounter(world, status, countDays, allInfectedCoordinates);
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

    private static int dayCounter(char[][] world, int[][] status, int countDays, Deque<int[]> allInfectedCoordinates) {
        int newDay = countDays + 1;
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
                if (isValidPosition(world, coordinates[0], coordinates[1] + 1)
                        || isValidPosition(world, coordinates[0] - 1, coordinates[1])
                        || isValidPosition(world, coordinates[0], coordinates[1] - 1)
                        || isValidPosition(world, coordinates[0] + 1, coordinates[1])) {
                    if (toInfect(world, coordinates[0], coordinates[1] + 1)) {
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        allInfectedCoordinates.offer(new int[]{coordinates[0], coordinates[1] + 1});
                        status[coordinates[0]][coordinates[1] + 1]++;
                    } else if (toInfect(world, coordinates[0] - 1, coordinates[1])) {
                        allInfectedCoordinates.offer(new int[]{coordinates[0] - 1, coordinates[1]});
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        status[coordinates[0] - 1][coordinates[1]]++;
                    } else if (toInfect(world, coordinates[0], coordinates[1] - 1)) {
                        allInfectedCoordinates.offer(new int[]{coordinates[0], coordinates[1] - 1});
                        allInfectedCoordinates.offer(new int[] {coordinates[0], coordinates[1]});
                        status[coordinates[0]][coordinates[1] - 1]++;
                    } else if (toInfect(world, coordinates[0] + 1, coordinates[1])) {
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

        if (check == 0){
            return newDay;
        }else {
            return dayCounter(world, status, newDay, allInfectedCoordinates);
        }

    }

    private static boolean toInfect(char[][] world, int row, int col) {
        if (isValidPosition(world, row, col)) {
            world[row][col] = 'I';
            return true;
        }
        return false;
    }

    private static void printMatrix(char[][] world) {
        for (char[] row : world) {
            for (char el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isValidPosition(char[][] world, int toInfectRow, int toInfectCol) {
        return toInfectRow >= 0
                && toInfectRow < world.length
                && toInfectCol >= 0
                && toInfectCol < world[toInfectRow].length
                && world[toInfectRow][toInfectCol] == 'H';
    }


}

/*
Passed tests: 0 of 5 Failed tests: #1 Input: world: [["#","#","#"], ["#","#","#"], ["#","#","#"]] firstInfected: [1, 1] Output: [1, 3, 6, 0] Expected Output: [7, 3, 6, 0] #2 Input: world: [["#","#","."], [".","#","#"], ["#",".","#"]] firstInfected: [1, 1] Output: [1, 3, 2, 1] Expected Output: [5, 3, 2, 1] #3 Input: world: [["#",".","."], [".","#","."], ["#",".","#"]] firstInfected: [1, 1] Output: [1, 1, 0, 3] Expected Output: [2, 1, 0, 3] #4 Input: world: [["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"], ["#","#","#","#","#","#","#"]] firstInfected: [1, 1] Output: [1, 13, 50, 0] Expected Output: [15, 3, 60, 0] #5 Input: world: [["#","#","#","#",".",".",".","#","."], ["#","#","#","#","#","#","#",".","#"], [".",".",".",".",".",".",".","#","#"], [".",".","#",".",".","#",".",".","."], [".",".",".",".","#","#",".","#","#"], ["#",".",".",".",".",".","#","#","#"], ["#",".",".",".","#","#","#","#","."], ["#",".",".",".",".","#",".",".","."], [".",".","#","#","#",".","#","#","."]] firstInfected: [3, 5] Output: [1, 2, 1, 34] Expected Output: [4, 2, 1, 34]
 */
