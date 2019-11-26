package backtracking;

public class Labyrinth {
  public char [][] lab = {{'*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*'},
                          {' ',' ','*',' ','*','*','*','*','*','*','*','*','*','*','*','*','*','*','K','*'},
                          {'*',' ',' ',' ','*','*','*','*','*','*','*','*','*','*','*','*','*','*',' ','*'},
                          {'*',' ','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*',' ','*'},
                          {'*',' ',' ',' ',' ',' ',' ',' ','*',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','*'},
                          {'*',' ','*','*','*','*','*','*','*',' ','*','*','*','*','*','*','*','*',' ','*'},
                          {'*',' ','*','*',' ','*','*','*','*',' ','*','*','*','*','*','*','*','*',' ','*'},
                          {'*',' ',' ',' ',' ','*','*','*','*',' ','*','*',' ',' ',' ',' ','*','*',' ','*'},
                          {'*',' ','*','*','*','*','*','*','*',' ','*','*',' ','*','*',' ','*','*',' ','*'},
                          {'*',' ','*','*','*','*',' ',' ',' ',' ','*','*','*','*','*',' ','*','*',' ','*'},
                          {'*',' ','*','*','*','*',' ','*',' ',' ','*','*','*','*','*',' ','*','*',' ','*'},
                          {'*',' ','*','*','*','*',' ','*',' ',' ',' ',' ',' ',' ',' ',' ','*','*',' ','*'},
                          {'*',' ','*','*','*','*',' ','*',' ','*','*','*','*','*','*','*',' ',' ',' ','*'},
                          {'*',' ',' ',' ',' ','*',' ','*',' ','*','*','*','*','*',' ','*','*','*',' ','*'},
                          {'*','*','*','*',' ','*',' ','*',' ',' ',' ',' ',' ','*',' ',' ',' ',' ',' ','*'},
                          {'*',' ',' ',' ',' ','*',' ','*','*','*','*','*',' ','*','*','*','*','*',' ','*'},
                          {'*','*',' ','*',' ',' ',' ','*','*','*','*','*',' ','*','*','*','*','*',' ','*'},
                          {'*','*',' ','*','*','*','*','*','*','*','*','*',' ','*','*','*','*','*',' ','*'},
                          {'*',' ',' ',' ',' ',' ','*','*','*','*','*','*',' ',' ',' ',' ',' ',' ',' ','*'},
                          {'*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*' ,'*','*'},
                          };
  public Labyrinth() {
  }

  public void ausgeben(){
    for (int i = 0; i<lab.length;i++){
      for(int j = 0; j<lab[i].length;j++){
        System.out.print(lab[i][j]);
      }
      System.out.println("");
    }
  }
  public boolean rechtsFrei(int posX, int posY){
    try {

    if(lab[posX+1][posY] != '*' && lab[posX+1][posY] != 'M')
      return true;
    else
      return false;
    }
    catch (Exception ex) {
      return false;
    }
  }
  public boolean linksFrei(int posX, int posY){
    try {
      if(lab[posX-1][posY] != '*' && lab[posX-1][posY] != 'M')
      return true;
    else
      return false;
    }
    catch (Exception ex) {
      return false;
    }

  }
  public boolean untenFrei(int posX, int posY){
    try {
      if(lab[posX][posY+1] != '*' && lab[posX][posY+1] != 'M')
      return true;
    else
      return false;
    }
    catch (Exception ex) {
      return false;
    }

  }
  public boolean obenFrei(int posX, int posY){
    try {
      if(lab[posX][posY-1] != '*' && lab[posX][posY-1] != 'M')
      return true;
    else
      return false;
    }
    catch (Exception ex) {
      return false;
    }
  }

  public boolean gefunden(int posX, int posY){
   try {
      if(lab[posX][posY] == 'K')
      return true;
    else
      return false;
   }
    catch (Exception ex) {
      return false;
    }
   }

  public void setzteMaus(int posX, int posY, char c){
    lab[posX][posY] = c;
  }
}