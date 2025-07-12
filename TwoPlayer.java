package com.practice.TicTacToe;

import java.util.Scanner;

public class TwoPlayer {

    public static void printBoard(char[][] board){

        for(char[] array:board){
            for (char c : array) {
                System.out.print(c + " | ");
            }
            System.out.println();
        }

    }

    public static boolean rowTraverse(char[][] board,int row,char symbol){

        int count=0;
        for(int i=0;i<board[row].length;++i){
            if(board[row][i]==symbol){
                count++;
            }
        }

        return count==3;
    }

    public static boolean colTraverse(char[][] board,int col,char symbol){
        int count=0;
        for(int i=0;i<board.length;++i){
            if(board[i][col]==symbol){
                count++;
            }
        }
        return count==3;
    }

    public static boolean leftDiagonalTraverse(char[][] board,char symbol){
        int count=0;
        for(int i=0;i<board.length;++i){
            if(board[i][i]==symbol){
                count++;
            }
        }
        return count==3;
    }

    public static boolean rightDiagonalTraverse(char[][] board,char symbol){
        int count=0;
        for(int i=0;i<board.length;++i){
            if(board[i][board.length-1-i]==symbol){
                count++;
            }
        }
        return count==3;
    }

    public static boolean findWinning(char[][] board,int row,int column,char symbol){
        return (rowTraverse(board,row,symbol) || colTraverse(board,column,symbol) || leftDiagonalTraverse(board,symbol) || rightDiagonalTraverse(board,symbol));
    }

    public static void playGame(char[][] board,char symbol,Scanner input){
        //Entered into the game
        int player=1;   //default start with player and that symbol
        boolean isGameRunning=true;
        int row=-1,columns=-1;
        int boardPiece=0;

        while(isGameRunning){
            printBoard(board);

            boolean isNotValid=true;

            while(isNotValid){
                System.out.println("Player "+player+" enter your index");
                System.out.println("Enter row:");
                row=input.nextInt();
                System.out.println("Enter column:");
                columns=input.nextInt();

                if(row>=0 && row<3 && columns>=0 && columns<3){
                    //then it's valid index
                    if(board[row][columns]==' '){
                        isNotValid=false;
                    }
                    else{
                        System.out.println("It's already filled ! ");
                    }
                }
                else{
                    System.out.println("InValid Index ! ");
                }
            }

            //make the change
            board[row][columns]=symbol;
            boardPiece++;

            //check winning
            boolean isWin=findWinning(board,row,columns,symbol);

            if(isWin){
                System.out.println("*************************");
                printBoard(board);
                System.out.println("*************************");
                System.out.println("Player "+player+" has won :)");
                isGameRunning=false;
            }
            if(boardPiece==9){
                printBoard(board);
                System.out.println("Draw the match :)");
                System.out.println("Well played player 1 & 2");
                isGameRunning=false;
            }

            symbol=(symbol=='X')?'O':'X';
            player=(player==1)?2:1;

        }


    }
    public static void main(String[] args) {

        int row=3,column=3;
        char[][] board=new char[row][column];   //Board
        Scanner input=new Scanner(System.in);
        char symbol;

        for(int i=0;i<row;++i){
            for(int j=0;j<column;++j){
                board[i][j]=' ';
            }
        }
        while(true){
            System.out.println("Enter the player symbol ( X | O ) :");
            symbol=input.next().toUpperCase().charAt(0);
            if(symbol=='X' || symbol=='O'){
                break;
            }
            System.out.println("InValid Symbol !");
        }

        playGame(board,symbol,input);


    }
}
