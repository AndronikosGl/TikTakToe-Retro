/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TikTakToeRetro;
public class Board {
    private char[][] grid = new char[3][3];
    public Board(){
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
    }
    public boolean move(int x, int y, char c){
        if(grid[x][y]==' '){
            grid[x][y]=c;
            return true;
        }
        return false;
    }
    
    public char[][] getGrid(){
        return grid;
    }
    
    public boolean isFull(){
        for (char[] row : grid){
            for (char cell : row){
                if (cell == ' ') return false;
            }
        }
        return true;
    }
}
