import java.util.Scanner;
public class S26871 {
    public static boolean Checking(int counter)
    {
        if ( counter % 2 == 0 ) return true;
        else return false;
    }
    public static void main(String[] args) {
        Board NextStep = new Board();
        Scanner input = new Scanner(System.in);
        Piece[] P1 = Players.Player1.getPieces();
        Piece[] P2 = Players.Player2.getPieces();


        int newX,newY,counter=0;
        NextStep.BoardWriteFigures();
        while (P1[8].getProperId()==1 && P2[8].getProperId()==1 ) {
            System.out.println("Input coordinates ");
            System.out.println("Symbol");
            newX= input.nextInt();
            System.out.println("Number");
            newY = input.nextInt();
            if(Checking(counter)) {
                for (Piece i : P1) {
                    if (newX == i.getX() && newY == i.getY()) {
                        i.Move(P2, i);
                        P1[8].Check(P2);
                    }
                }
                for (Piece i : P2)
                {
                    // System.out.println("P2:"+i.getProperId());
                }
            }
            else
            {
                for (Piece i : P2)
                {
                    if (newX== i.getX() && newY== i.getY())
                    {
                        i.Move(P1,i);
                        P2[8].Check(P1);
                    }

                }

                for (Piece i : P1)
                {
                    //System.out.println("P1:"+i.getProperId());
                }
            }
            counter++;
            NextStep.BoardWriteFigures();
        }
    }
}
public class Board {
    String[] abc = {"a","b","c","d","e","f","g","h",};
    // public int Convert(String a)
    // {
//
    //         switch (a)
    //         {
    //             case "a":return 1;
    //             case "b":return 2;
    //             case "c":return 3;
    //             case "d":return 4;
    //             case "e":return 5;
    //             case "f":return 6;
    //             case "g":return 7;
    //             case "h":return 8;
    //             default:return 10;
    //         }
    // }
    public static String checkOfColors(String black, String white,String clear,String rowColor)
    {
        rowColor =( (rowColor == black) ? white : black);
        return rowColor;
    }
    public void BoardWriteFigures()
    {
        for (String a :abc)
        {
            System.out.print(a+"  ");
        }
        System.out.println();
        Piece[] P1 = Players.Player1.getPieces();
        Piece[] P2 = Players.Player2.getPieces();
        boolean Found = false;
        String black = "\u001B[40m";
        String white = "\u001B[47m";
        String clear = "\u001B[0m";
        String rowColor = black;
        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                for (int f = 0; f < P1.length; f++) {
                    if (P1[f].getY() == y && P1[f].getX() == x && P1[f].getProperId()==1) {
                        if (x>4) System.out.print(rowColor+" ");
                        System.out.print(rowColor+P1[f].getView()+clear);
                        if (x<5) System.out.print(rowColor+" ");
                        rowColor=checkOfColors(black,white,clear,rowColor);
                        Found = true;
                        break;
                    }
                }
                for (int f = 0; f < P2.length; f++) {
                    if (P2[f].getY() == y && P2[f].getX() == x && P2[f].getProperId()==1) {
                        if (x>4) System.out.print(rowColor+" ");
                        System.out.print(rowColor+P2[f].getView()+clear);
                        if (x<5) System.out.print(rowColor+" ");
                        rowColor=checkOfColors(black,white,clear,rowColor);
                        Found = true;
                        break;
                    }
                }
                if (!Found){
                    System.out.print(rowColor+"   "+clear);
                    rowColor=checkOfColors(black,white,clear,rowColor);
                }
                Found=false;
            }
            rowColor=checkOfColors(black,white,clear,rowColor);
            System.out.println();
        }
    }
    public  void BoardWrite()
    {
        int numRows = 20;
        int numCols = 20;
        String black = "\u001B[40m";
        String white = "\u001B[47m";
        String clear = "\u001B[0m";

        String rowColor = black;

        for (int i = 1; i < numRows-11; i++) {
            for (int j = 1; j < numCols-11; j++) {
                System.out.print(rowColor + "   " + clear);
                rowColor=checkOfColors(black,white,clear,rowColor);
            }
            rowColor=checkOfColors(black,white,clear,rowColor);
            System.out.println();
        }
    }
}
public abstract class Piece {
    private int x;
    private int y;
    private char view;
    private int ProperId;
    private int Id;

    public void setProperId(int properId) {
        ProperId = properId;
    }

    public int getProperId() {
        return ProperId;
    }

    public Piece(int x, int y, char view, int ProperId,int id)
    {
        this.x=x;
        this.y=y;
        this.view=view;
        this.ProperId=ProperId;
        this.Id=id;
    }
    public char getView() {
        return view;
    }
    public void setView(char view) {
        this.view = view;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return this.y;
    }
    public void setKilled()
    {
        setProperId(0);
    }
    public abstract boolean canDoIt(int newX, int newY, Piece[] pieces);
    public abstract void Move(Piece[] pieces, Piece foot);
    public boolean hasThePieceForOne(Piece piece, int newX, int newY)
    {

        if (piece.getX() == newX && piece.getY() == newY) return true;
        else return false;
    }

    public int getId() {
        return Id;
    }

    public int GetBackPieceOn(Piece[] pieces, int newX, int newY)
    {        int not_work = 10;
        for (int i=0 ; i<pieces.length;i++) {
            if (pieces[i].getX() == newX && pieces[i].getY()==newY) return i;
        }
        return not_work;
    }
    public boolean hasThePiece(Piece[] pieces, int newX, int newY)
    {
        for (Piece piece : pieces) {
            if (piece.getX() == newX && piece.getY() == newY) return true;
        }
        return false;
    }
    public boolean hasThePieceOnTheRoadZero(Piece[] pieces, int newX, int newY)
    {
        int xl=getX(),yl=getY();
        do
        {
            if (newX>getX() && newY==getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                xl++;
            }
            else if(newX<getX() && newY==getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                xl--;
            }
            else if(newX==getX() && newY<getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                yl--;
            }
            else if(newX==getX() && newY>getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                yl++;
            }
        }
        while (xl!=newX && yl!=newY);
        return false;
    }
    public boolean hasThePieceOnTheRoad(Piece[] pieces, int newX, int newY)
    {
        int xl=getX()+1,yl=getY()+1;
        do
        {
            if (newX>getX() && newY>getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                xl++;
                yl++;
                System.out.println(xl+" "+yl);
            }
            else if(newX<getX() && newY<getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                xl--;
                yl--;
                System.out.println(xl+" "+yl);
            }
            else if(newX>getX() && newY<getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                xl++;
                yl--;
                System.out.println(xl+" "+yl);
            }
            else if(newX<getX() && newY>getY())
            {
                if (hasThePiece(pieces,xl,yl) && ProperId!=0) return true;
                xl--;
                yl++;
                System.out.println(xl+" "+yl);
            }
        }
        while (xl!=newX && yl!=newY);

        return false;
    }
    public boolean Check(Piece[] pieces) {
        int K=0;
        for (int i=0 ; i<pieces.length;i++)
        {
            if (pieces[i].getId()==1)
            {
                K=i;
            }
        }
        int X = pieces[K].getX();
        int Y = pieces[K].getY();
        for (Piece i : pieces)
        {
            if (i.getId()==6) {if (i.canDoIt(X,Y,pieces)) return true;}
            else if (i.getId()==2) {if (i.canDoIt(X,Y,pieces)) return true;}
            else if (i.getId()==3) {if (i.canDoIt(X,Y,pieces)) return true;}
            else if (i.getId()==4) {if (i.canDoIt(X,Y,pieces)) return true;}
            else if (i.getId()==5) {if (i.canDoIt(X,Y,pieces)) return true;}
        }
        return false;
    }
    //public void Mate(Piece[] pieces)
    //{
    //    for (Piece i : pieces)
    //    {

    //    }
    //}
}
public enum Players {
    Player1(new Piece[] {
            new Foot(2, 1, '♙',1,2),
            new Foot(2, 2, '♙',1,2),
            new Foot(2, 3, '♙',1,2),
            new Foot(2, 4, '♙',1,2),
            new Foot(2, 5, '♙',1,2),
            new Foot(2, 6, '♙',1,2),
            new Foot(2, 7, '♙',1,2),
            new Foot(2, 8, '♙',1,2),
            new King(1,5,'♔',1,1),
            new Bishop(1,3,'♗',1,3),
            new Bishop(1,6,'♗',1,3),
            new Rook(1,1,'♖',1,4),
            new Rook(1,8,'♖',1,4),
            new Knight(1,2,'♘',1,5),
            new Knight(1,7,'♘',1,5),
            new Queen(1,4,'♕',1,6)
//

    }
    ),
    Player2(new Piece[]
            {
                    new Foot(7, 1, '♟',1,2),
                    new Foot(7, 2, '♟',1,2),
                    new Foot(7, 3, '♟',1,2),
                    new Foot(7, 4, '♟',1,2),
                    new Foot(7, 5, '♟',1,2),
                    new Foot(7, 6, '♟',1,2),
                    new Foot(7, 7, '♟',1,2),
                    new Foot(7, 8, '♟',1,2),
                    new King(8,4,'♚',1,1),
                    new Bishop(8,3,'♝',1,3),
                    new Bishop(8,6,'♝',1,3),
                    new Rook(8,8,'♜',1,4),
                    new Rook(8,1,'♜',1,4),
                    new Knight(8,2,'♞',1,5),
                    new Knight(8,7,'♞',1,5),
                    new Queen(8,5,'♛',1,6)

            }
    );

    private Piece[] pieces;

    Players(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Piece[] getPieces() {
        return pieces;
    }
}
import java.util.Scanner;

public class Bishop extends Piece {
    public Bishop(int x, int y,char view,int ProperId,int id)
    {
        super(x,y,view,ProperId,id);
    }
    public boolean canDoIt(int newX, int newY, Piece[] pieces) {
        if (newX>8 || newX<1 || newY>8 || newY<1 ) return false;
        if (newX>getX() && newY>getY()  && !hasThePieceOnTheRoad(pieces,newX,newY)||newX<getX() && newY<getY()&& !hasThePieceOnTheRoad(pieces,newX,newY)||newX>getX() && newY<getY() && !hasThePieceOnTheRoad(pieces,newX,newY) ||newX<getX() && newY>getY() && !hasThePieceOnTheRoad(pieces,newX,newY))return true;
        else return false;
    }
    public void Move(Piece[] pieces, Piece foot) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input coordinates \n");
        int newX = input.nextInt();
        int newY = input.nextInt();
        while (!canDoIt(newX,newY,pieces))
        {
            System.out.println("Wrong bitch\n");
            newX = input.nextInt();
            newY = input.nextInt();
        }
        for (Piece i : pieces)
        {
            if (hasThePieceForOne(i,newX,newY))
            {
                i.setProperId(0);
            }
        }
        setX(newX);
        setY(newY);
    }
}
import java.util.Scanner;
public class Foot extends Piece {
    public void respondP1(int answer, Piece[] pieces, int newX, int newY)//For X==8
    {
        switch (answer)
        {
            case 1 : pieces[GetBackPieceOn(pieces,newX,newY)] = new Queen(newX,newY,'♕',1,6);
            case 2: pieces[GetBackPieceOn(pieces,newX,newY)] = new Bishop(newX,newY,'♗',1,3);
            case 3: pieces[GetBackPieceOn(pieces,newX,newY)] = new Rook(newX,newY,'♖',1,4);
            case 4: pieces[GetBackPieceOn(pieces,newX,newY)] = new Knight(newX,newY,'♘',1,5);
        }
    }
    public void respondP2(int answer, Piece[] pieces, int newX, int newY)//For X==8
    {
        switch (answer)
        {
            case 1 : pieces[GetBackPieceOn(pieces,newX,newY)] = new Queen(newX,newY,'♛',1,6);
            case 2: pieces[GetBackPieceOn(pieces,newX,newY)] = new Bishop(newX,newY,'♝',1,3);
            case 3: pieces[GetBackPieceOn(pieces,newX,newY)] = new Rook(newX,newY,'♜',1,4);
            case 4: pieces[GetBackPieceOn(pieces,newX,newY)] = new Knight(newX,newY,'♞',1,5);
        }
    }
    private int counterOfMoves=0;
    public int id = 1;

    public int getCounterOfMoves() {
        return counterOfMoves;
    }

    public Foot(int x, int y,char view,int ProperId,int id)
    {
        super(x,y,view,ProperId, id);

    }

    public void Move(Piece[] pieces, Piece foot)
    {
        Board NextStep = new Board();
        Scanner input = new Scanner(System.in);
        System.out.println("Input coordinates \n");
        int newX = input.nextInt();
        int newY = input.nextInt();
        while (!canDoIt(newX,newY,pieces))
        {
            System.out.println("Wrong \n");
            newX = input.nextInt();
            newY = input.nextInt();
        }
        for (Piece i : pieces)
        {
            if (hasThePieceForOne(i,newX,newY))
            {
                i.setProperId(0);
            }
            if (newX==8)
            {

                Piece[] P1 = Players.Player1.getPieces();
                Piece[] P2 = Players.Player2.getPieces();
                if (P1 == pieces)
                {
                    System.out.println("1:Queen 2:Bishop, 3:Rook , 4:Knight");
                    int newFigure = input.nextInt();
                    respondP2(newFigure,pieces,newX,newY);
                }
                else if (P2 == pieces) {
                    System.out.println("1:Queen 2:Bishop, 3:Rook , 4:Knight");
                    int newFigure = input.nextInt();
                    respondP1(newFigure,pieces,newX,newY);
                }
            }
        }System.out.println(GetBackPieceOn(pieces,8,8));
        setX(newX);
        setY(newY);
        counterOfMoves++;
    }

    public boolean canDoIt(int newX, int newY, Piece[] pieces) {
        if (newX>8 || newX<1 || newY>8 || newY<1 ) return false; // fool avoid
        else if (hasThePiece(pieces,newX,newY) && getX()+1 == newX && newY == getY()) return false;//has piece in x+1
        else if (!hasThePiece(pieces,newX,newY) && getX()+1 == newX && newY == getY()||!hasThePiece(pieces,newX,newY) && getX()-1 == newX && newY == getY()) return true;//x+1
        else if (newY==getY() && getCounterOfMoves()<1 && getX()+2 == newX && !hasThePiece(pieces,newX,newY)||newY==getY() && getCounterOfMoves()<1 && getX()-2 == newX && !hasThePiece(pieces,newX,newY))return true;//first step
        else if (newX==getX()+1 && newY==getY()-1 && hasThePiece(pieces,newX,newY) || newX==getX()+1 && newY==getY()+1 && hasThePiece(pieces,newX,newY)|| newX==getX()-1 && newY==getY()-1 && hasThePiece(pieces,newX,newY)|| newX==getX()-1 && newY==getY()+1 && hasThePiece(pieces,newX,newY)) return true; //killing
        else return false;
    }

}
import java.util.Scanner;
        import java.util.Scanner;
public class King extends Piece {
    private int counterOfMoves=0;

    public King(int x, int y, char view, int ProperId,int id) {
        super(x, y, view, ProperId,id);
    }
    public boolean canDoIt(int newX, int newY, Piece[] pieces) {
        if (newX > 8 || newX < 1|| newY > 8 || newY < 1) return false;
        else if (newX == getX() + 1 && getY() == newY || newY == getY() + 1 && getX() == newX || newX == newY && !hasThePieceOnTheRoad(pieces, newX, newY) || newY == getY() - 1 && getX() == newX || newX == getX() - 1 && getY() == newY)
            return true;
        else return false;
    }
    public boolean canDoR(int newX, int newY, Piece[] pieces)
    {
        return hasThePiece(pieces, newX, newY) && pieces[GetBackPieceOn(pieces, newX, newY)].getId() == 4 && !hasThePieceOnTheRoadZero(pieces, newX, newY) && counterOfMoves == 0;
    }
    public void Move(Piece[] pieces, Piece foot) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input coordinates \n");
        int newX = input.nextInt();
        int newY = input.nextInt();
        if (newY==0 && newX==0)
        {
            System.out.println("Input cords R");
            newX = input.nextInt();
            newY = input.nextInt();
            while (!canDoR(newX, newY, pieces)) {
                System.out.println("Wrong R\n");
                newX = input.nextInt();
                newY = input.nextInt();
            }
            pieces[GetBackPieceOn(pieces,newX,newY)].setY(getY());
            pieces[GetBackPieceOn(pieces,newX,newY)].setX(getX());
            setY(newY);
            setX(newX);
        }
        else
        {
            while (!canDoIt(newX, newY, pieces)) {
                System.out.println("Wrong \n");
                newX = input.nextInt();
                newY = input.nextInt();
            }
            for (Piece i : pieces)
            {
                if (hasThePieceForOne(i,newX,newY ) || hasThePieceOnTheRoadZero(pieces,newX,newY))
                {
                    i.setProperId(0);
                }
            }
            if (Check(pieces))
            {
                System.out.println("Check");
            }
            else {System.out.println("..");}
            setX(newX);
            setY(newY);
            counterOfMoves++;
        }
    }
}

public class Knight extends Piece {
    public Knight(int x, int y,char view,int ProperId,int id)
    {
        super(x,y,view,ProperId,id);
    }
    public boolean canDoIt(int newX, int newY, Piece[] pieces) {
        if (newX>8 || newX<1 || newY>8 || newY<1 ) return false;
        else if(getX()+2==newX && (getY()-1== newY || getY()+1== newY ) || getX()-2==newX && (getY()-1== newY || getY()+1== newY )
                || getY()+2==newY  && (getX()-1== newX || getX()+1== newX ) || getY()-2==newY && (getX()-1== newX || getY()+1== newX ))
        {
            return true;
        }
        else return false;
    }
    public void Move(Piece[] pieces, Piece foot) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input coordinates \n");
        int newX = input.nextInt();
        int newY = input.nextInt();
        while (!canDoIt(newX,newY,pieces))
        {
            System.out.println("Wrong \n");
            newX = input.nextInt();
            newY = input.nextInt();
        }
        for (Piece i : pieces)
        {
            if (hasThePieceForOne(i,newX,newY))
            {
                i.setProperId(0);
            }
        }
        setX(newX);
        setY(newY);
    }
}
import java.util.Scanner;
public class Queen  extends Piece {
    Scanner input = new Scanner(System.in);
    public Queen(int x, int y,char view,int ProperId,int id)
    {
        super(x,y,view,ProperId,id);
    }
    public boolean canDoIt(int newX, int newY, Piece[] pieces) {
        if (newX>8 || newX<1 || newY>8 || newY<1 ) return false;
        else if (newX==getX() && newY>0 && !hasThePieceOnTheRoadZero(pieces,newX,newY) || newY==getY() && newX>0 && !hasThePieceOnTheRoadZero(pieces,newX,newY)) return true;
        else if (newX>getX() && newY>getY()  && !hasThePieceOnTheRoad(pieces,newX,newY)||newX<getX() && newY<getY()&& !hasThePieceOnTheRoad(pieces,newX,newY)||newX>getX() && newY<getY() && !hasThePieceOnTheRoad(pieces,newX,newY) ||newX<getX() && newY>getY() && !hasThePieceOnTheRoad(pieces,newX,newY)) return true;
        else return false;
    }
    public void Move(Piece[] pieces, Piece foot) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input coordinates \n");
        int newX = input.nextInt();
        int newY = input.nextInt();
        while (!canDoIt(newX,newY,pieces))
        {
            System.out.println("Wrong \n");
            newX = input.nextInt();
            newY = input.nextInt();
        }
        for (Piece i : pieces)
        {
            if (hasThePieceForOne(i,newX,newY))
            {
                i.setProperId(0);
            }
        }
        setX(newX);
        setY(newY);
    }
}
import java.util.Scanner;

public class Rook extends Piece {
    public Rook(int x, int y,char view,int ProperId,int id)
    {
        super(x,y,view,ProperId,id);
    }
    private int counterOfMoves=0;
    public boolean canDoIt(int newX, int newY, Piece[] pieces) {
        if (newX>8 || newX<1 || newY>8 || newY<1 ) return false;
        else if (newX==getX() && newY>0 && !hasThePieceOnTheRoadZero(pieces,newX,newY) || newY==getY() && newX>0 && !hasThePieceOnTheRoadZero(pieces,newX,newY)) return true;
        else return false;
    }
    public void Move(Piece[] pieces, Piece foot) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input coordinates \n");
        int newX = input.nextInt();
        int newY = input.nextInt();
        while (!canDoIt(newX,newY,pieces))
        {
            System.out.println("Wrong \n");
            newX = input.nextInt();
            newY = input.nextInt();
        }
        for (Piece i : pieces)
        {
            if (hasThePieceForOne(i,newX,newY))
            {
                i.setProperId(0);
            }
        }
        setX(newX);
        setY(newY);
        counterOfMoves++;
    }
}
public interface BinaryPart {
    public void Save(Piece[] P1, Piece[] P2);
    public void Download(Piece[] P1, Piece[] P2);
}

