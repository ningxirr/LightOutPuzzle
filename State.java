//Nasreeya 6213128
//Pojanut 6213205
//Palakorn 6213206
//Pakkapond 6213207

import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.Objects;

public class State {
    private String BitString = "";
    private int Value;
    private int NumberRow;
    private boolean[][] Light;
    private int TRow, TCol;
    public State(String Input){
        BitString = Input;
        NumberRow = (int)(sqrt(BitString.length()));       
        Light = new boolean[NumberRow][NumberRow];
        try{
            int count = 0;
            Value = Integer.parseInt(BitString,2);
            for(int i=0; i<NumberRow; i++){
                for(int j=0; j<NumberRow; j++){
                    Light[i][j] = BitString.charAt(count)=='1';
                    count++;
                }
            }
        }
        catch(Exception e){
            int count = 0;
            System.out.println("all bits are not equal 1 or 0 will be set to 0");
            for(int i=0; i<NumberRow; i++){
                for(int j=0; j<NumberRow; j++){
                    if(BitString.charAt(count)=='1'){
                        Light[i][j] = true;
                    }
                    else{Light[i][j] = false;}
                    count++;
                }
            }
            BitString = "";
            for(int i=0; i<NumberRow; i++){ 
                for(int j=0; j<NumberRow; j++){  
                    BitString = BitString.concat(String.valueOf(Light[i][j]?1:0));
                }
            }
            Value = Integer.parseInt(BitString,2);
        }
    }
    public State(boolean[][] L){
        Light = L;
        NumberRow = L.length;
        for(int i=0; i<NumberRow; i++){
            for(int j=0; j<NumberRow; j++){
                BitString = BitString.concat(String.valueOf(L[i][j]?1:0));
            }           
        }
        Value = Integer.parseInt(BitString,2);
    }
    public State(int v){Value = v;}
    public void printState(){
        System.out.printf("Bit string = %s",BitString);
        System.out.printf(", Decimal ID = %d\n",Value);      
        System.out.print("      |");
        for(int i=0; i<NumberRow; i++){System.out.printf(" col %d |",i);}
        System.out.println();
        for(int i=0; i<NumberRow; i++){
            System.out.printf("row %d |", i);
            for(int j=0; j<NumberRow; j++){System.out.printf("   %d   |",Light[i][j]?1:0);}
            System.out.println();
        }   
    }
    public State toggle(int row,int col){
        boolean[][] TLight = new boolean[NumberRow][NumberRow];
        for (int i = 0; i < NumberRow; i++) TLight[i] = Arrays.copyOf(Light[i], Light[i].length);
        State S;
        TLight[row][col] = !TLight[row][col];
        if(row+1<NumberRow){TLight[row+1][col] = !TLight[row+1][col];}
        if(row-1>=0){TLight[row-1][col] = !TLight[row-1][col];}
        if(col+1<NumberRow){TLight[row][col+1] = !TLight[row][col+1];}
        if(col-1>=0){TLight[row][col-1] = !TLight[row][col-1];}
        S = new State(TLight);
        S.setmove(row, col);
        return S;
    }
    public void setmove(int row, int col){TRow = row; TCol = col;}
    public void printmove(){
        if(Light[TRow][TCol]){
            System.out.printf("turn on row %d, col %d\n",TRow,TCol);
        }
        else{
            System.out.printf("turn off row %d, col %d\n",TRow,TCol);
        }
        
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.Value);
        return hash;
    }
    public boolean equals(Object o){
	State other = (State)o;
        return this.Value == other.Value;
    }
}

