/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TikTakToeRetro;
/*Για κάθε άδεια θέση, δοκιμάζει προσωρινά την κίνηση και στη συνέχεια χρησιμοποιεί τη 
συνάρτηση minimax για να προσομοιώσει όλες τις πιθανές μελλοντικές κινήσεις τόσο του 
υπολογιστή όσο και του παίκτη. Ο αλγόριθμος υπολογίζει ένα σκορ για κάθε πιθανή εξέλιξη 
(νίκη = +10, ήττα = –10, ισοπαλία = 0) και επιλέγει την κίνηση που μεγιστοποιεί το τελικό 
αποτέλεσμα για τον υπολογιστή*/

public class MinimaxStrategy implements MoveStrategy {
 
    @Override
    public int[] move(Board board, char pc, char usr){
        char[][] g = board.getGrid();
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(g[i][j]==' '){
                    g[i][j] = pc;
                    int score = minimax(g, false, pc, usr);
                    g[i][j] = ' ';
                    
                    if(score > bestScore){
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }
    private int minimax(char[][] g, boolean isMax, char pc, char usr){
        if(checkWin(g, pc)){
            return 10;
        }
        if(checkWin(g, usr)){
            return -10;
        }
        if(isFull(g)){
            return 0;
        }
        
        int best;
        if(isMax){
            best = Integer.MIN_VALUE;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(g[i][j]==' '){
                        g[i][j]=pc;
                        best = Math.max(best, minimax(g, false, pc, usr));
                        g[i][j]=' ';
                    }
                }
            }
        }else{
            best = Integer.MAX_VALUE;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(g[i][j]==' '){
                        g[i][j]=usr;
                        best = Math.min(best, minimax(g, true,pc, usr));
                        g[i][j]=' ';
                    }
                }
            }
        }
        return best;
        
    }
    
    private boolean isFull(char[][] g){
        for(char[] r : g){
            for(char c : r){
                if(c==' '){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean checkWin(char[][] g, char c){
        for(int i=0;i<3;i++){
            if(g[i][0]==c && g[i][1]==c && g[i][2]==c) return true;
            if(g[0][i]==c && g[1][i]==c && g[2][i]==c) return true;
        }
        return (g[0][0]==c && g[1][1]==c && g[2][2]==c)|| (g[0][2]==c && g[1][1]==c && g[2][0]==c);
    }
}
