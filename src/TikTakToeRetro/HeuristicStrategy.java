/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TikTakToeRetro;
/*Για κάθε άδειο κελί, υπολογίζει ένα σκορ ανάλογα με το πόσο καλή θεωρείται η θέση: 
δίνει περισσότερους πόντους στο κέντρο, λιγότερους στις γωνίες και ελάχιστους στα υπόλοιπα κελιά. 
Στη συνέχεια ελέγχει αν η κίνηση οδηγεί σε άμεση νίκη (πολύ υψηλό σκορ) ή αν αποτρέπει πιθανή νίκη 
του αντιπάλου (επίσης υψηλό σκορ). Αφού βαθμολογήσει όλες τις διαθέσιμες θέσεις, επιλέγει εκείνη με 
το μεγαλύτερο σκορ*/

public class HeuristicStrategy implements MoveStrategy {
 @Override
 public int[] move(Board board, char pc, char usr){
     char[][] g = board.getGrid();
     int bestScore = Integer.MIN_VALUE;
     int[] bestMove = {-1,-1};
     
     for(int i=0;i<3;i++){
         for(int j=0;j<3;++j){
             if(g[i][j]==' '){
                 int score = evaluateMove(g, i, j, pc, usr);
                 if(score > bestScore){
                     bestScore = score;
                 }
                 bestMove = new int[]{i, j};
             }
         }
     }
         return bestMove;
        
     } 
private int evaluateMove(char[][] g, int r, int c, char pc, char usr){
   int score = 0;
   if(r==1 && c==1){
       score +=3;
   }
   if((r==0||r==2) && (c==0||c==2)){
       score +=2;
   }
   
   g[r][c]=pc;
   if(checkWin(g, pc)){
       score +=100;
   }
   g[r][c]=' ';
   
   g[r][c]=usr;
   if(checkWin(g, usr)){
       score +=90;
   }
   g[r][c]=' ';
   return score;
}

 private boolean checkWin(char[][] g, char c){
        for(int i=0;i<3;i++){
            if(g[i][0]==c && g[i][1]==c && g[i][2]==c) return true;
            if(g[0][i]==c && g[1][i]==c && g[2][i]==c) return true;
        }
        return (g[0][0]==c && g[1][1]==c && g[2][2]==c)|| (g[0][2]==c && g[1][1]==c && g[2][0]==c);
    }
}