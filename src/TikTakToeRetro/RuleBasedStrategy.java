package TikTakToeRetro;
/* Πρώτα ελέγχει αν υπάρχει κίνηση που της δίνει άμεση νίκη και την παίζει. Αν όχι, 
εξετάζει αν ο αντίπαλος μπορεί να κερδίσει στην επόμενη κίνηση και τον μπλοκάρει. 
Αν δεν ισχύει τίποτα από αυτά, επιλέγει το κέντρο ως την πιο ισχυρή θέση, μετά κάποια 
διαθέσιμη γωνία και τέλος οποιοδήποτε άδειο κελί*/
public class RuleBasedStrategy implements MoveStrategy{
    @Override
    public int[] move(Board board, char pc, char usr){
        char[][] g = board.getGrid();
        
        //1. Trying to move
        int[] winMove = findWinningMove(g, pc);
        if(winMove !=null){
            return winMove;
        }
        
        //2.Block opponent
        int[] blockMove = findWinningMove(g, usr);
        if(blockMove!=null){
            return blockMove;
        }
        
        //3. Center
        if(g[1][1]==' '){
            return new int[]{1,1};
        }
        
        //4. Corners
        int[][] corners = {{0,0},{0,2},{2,0},{2,2}};
        for(int[] c : corners){
            if(g[c[0]][c[1]] == ' '){
                return c;
            }
        }
        //5. anything else
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(g[i][j]==' '){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }
    
    private int[] findWinningMove(char[][] g, char symb){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(g[i][j]==' '){
                    g[i][j]=symb;
                    if(checkWin(g, symb)){
                        g[i][j]=' ';
                        return new int[]{i,j};
                    }
                    g[i][j]=' ';
                }
            }
        }
        return null;
    }
    
    private boolean checkWin(char[][] g, char c){
        for(int i=0;i<3;i++){
            if(g[i][0]==c && g[i][1]==c && g[i][2]==c) return true;
            if(g[0][i]==c && g[1][i]==c && g[2][i]==c) return true;
        }
        return (g[0][0]==c && g[1][1]==c && g[2][2]==c)|| (g[0][2]==c && g[1][1]==c && g[2][0]==c);
    }
}
