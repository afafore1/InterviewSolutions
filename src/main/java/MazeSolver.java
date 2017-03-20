import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ayomitunde on 3/18/2017.
 */
public class MazeSolver {
    /*
    Question..........
    Write a program that constructs and displays a maze (having a floor plan always helps when trying to get around)
    with a marked start and finish - or call it your desk and the coffee machine -- and then solves it (and displays the solution).
    The exact size, shape, and type of maze is up to you.

    Solution..........
    Assume we have a maze like this..
        O O O O O X O
        X X O X O O X
        O X O O X X X
        X X X O O X O
        X X X X O O X
        O O O O O O O
        X X O X X X O
        
    The idea here is to use breadth first search to find the shortest path from one source to a location.
    Method to get neighbors is by checking the top, bottom, left and right.
        There are certain constraints to getting neighbors because the maze is represented with an adjacency matrix
            There will be no neighbors to the left or top if we are at the very top i.e. 00, 01, 0....
            There will be no neighbors to the bottom if we are at the very bottom i.e. 60, 61, 6....
            There will be no neighbors to the right if we are at the end.. i.e. 66
    Get the path by reversing. Child to parent until we get to source which will have no parent.

     */

    static int getX(String input)
    {
        return Integer.parseInt(String.valueOf(input.charAt(0)));
    }

    static int getY(String input)
    {
        return Integer.parseInt(String.valueOf(input.charAt(1)));
    }

    static String [][] createMaze() // hard coding this :(
    {
        String[][] maze = new String[7][7];
        /*

         */
        maze[0][0]= maze[0][1] = maze[0][2] = maze[0][3] = maze[0][4] = maze[0][6] = "O"; maze[0][5] = "X";
        maze[1][2] = maze[1][4] = maze[1][5] = "O"; maze[1][0]= maze[1][1] = maze[1][3] = maze[1][6] = "X";
        maze[2][0]= maze[2][2] = maze[2][3] = "O"; maze[2][1] = maze[2][4] = maze[2][5] = maze[2][6] = "X";
        maze[3][3]= maze[3][4] = maze[3][6] = "O"; maze[3][0] = maze[3][1] = maze[3][2] = maze[3][5] = "X";
        maze[4][4]= maze[4][5] = "O"; maze[4][0] = maze[4][1] = maze[4][2] = maze[4][3] = maze[4][6] = "X";
        maze[5][0]= maze[5][1] = maze[5][2] = maze[5][3] = maze[5][4] = maze[5][5] = maze[5][6] = "O";
        maze[6][2]= maze[6][6] = "O"; maze[6][0] = maze[6][1] = maze[6][3] = maze[6][4] = maze[6][5] = "X";
        return maze;
    }

    static LinkedList<String> getNeighbors(String current, String [][] maze)
    {
        LinkedList<String> neighbors = new LinkedList<String>();
        int currentX = getX(current); // lets assume this is index 0
        int currentY = getY(current); // and this is index 0
        int index1 = currentX-1; //-1
        int index2 = currentY + 1; //1
        int index3 = currentY - 1;
        int index4 = currentX + 1;

        if(index1 >= 0)
        {

            if(maze[index1][currentY] == "O") // i.e 01
            {
                neighbors.add(String.valueOf(index1+""+currentX));
            }
            if(index3 >= 0)
            {
                if(maze[currentX][index3] == "O") // i.e 10
                {
                    neighbors.add(String.valueOf(currentX+""+index3));
                }
            }
        }
        if(index4 <= maze.length - 1)
        {
            if(maze[index4][currentY] == "O") // i.e 21
            {
                neighbors.add(String.valueOf(index4+""+currentY));
            }
        }
        if(index2 <= maze.length - 1)
        {
            if(maze[currentX][index2] == "O") // i.e 12
            {
                neighbors.add(String.valueOf(currentX+""+index2));
            }
        }

        return neighbors;
    }

    static String solve(String [][] maze, String startPos, String deskSpace)
    {
        printMaze(maze);
        HashMap<String, String> childParent = new HashMap<String, String>();
        //modified bfs..
        List<String> visited = new LinkedList<String>();
        Queue<String> mazeQueue = new LinkedList<String>();
        mazeQueue.add(startPos); // add the start position to queue
        visited.add(startPos); // mark start position as visited.
        while(!mazeQueue.isEmpty())
        {
            // get current position and check for neighbors
            String current = mazeQueue.remove();
            childParent.put(null, current);
            LinkedList<String> neighbors = getNeighbors(current, maze);
            for(String n : neighbors)
            {
                if(!visited.contains(n))
                {
                    mazeQueue.add(n);
                    visited.add(n);
                    childParent.put(n, current);
                }
            }
        }

        // get path
        StringBuilder sb = new StringBuilder();
        String parent = childParent.get(deskSpace);
        sb.insert(0, "->"+deskSpace);
        while(parent!= null)
        {
            sb.insert(0, "->"+parent);
            parent = childParent.get(parent);
        }
        return sb.toString();
    }

    static void printMaze(String [][] maze)
    {
        for(int i = 0; i < maze.length; i++)
        {
            for(int j = 0; j < maze.length; j++)
            {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String [] args)
    {
        String startPos = "22";
        String deskSpace = "66";
        String [][] maze = createMaze();
        System.out.println(solve(maze, startPos, deskSpace));
    }
}
