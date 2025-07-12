package com.practice.TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class PlayerAndAiEasy {
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

        return count==board.length;
    }

    public static boolean colTraverse(char[][] board,int col,char symbol){
        int count=0;
        for(int i=0;i<board.length;++i){
            if(board[i][col]==symbol){
                count++;
            }
        }
        return count==board.length;
    }

    public static boolean leftDiagonalTraverse(char[][] board,char symbol){
        int count=0;
        for(int i=0;i<board.length;++i){
            if(board[i][i]==symbol){
                count++;
            }
        }
        return count==board.length;
    }

    public static boolean rightDiagonalTraverse(char[][] board,char symbol){
        int count=0;
        for(int i=0;i<board.length;++i){
            if(board[i][board.length-1-i]==symbol){
                count++;
            }
        }
        return count==board.length;
    }

    public static boolean findWinning(char[][] board,int row,int column,char symbol){
        return (rowTraverse(board,row,symbol) || colTraverse(board,column,symbol) || leftDiagonalTraverse(board,symbol) || rightDiagonalTraverse(board,symbol));
    }

    public static void firstPlayer(int[] position,char[][] board,char symbol,Scanner input){
        boolean isNotValid=true;
        int row=-1,column=-1;

        while(isNotValid){
            System.out.println("Player enter your index");

            System.out.println("Enter row:");
            position[0]=input.nextInt();
            row=position[0];

            System.out.println("Enter column:");
            position[1]=input.nextInt();
            column=position[1];

            if(row>=0 && row<board.length && column>=0 && column<board.length){
                //then it's valid index
                if(board[row][column]==' '){
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
    }

    public static void artificalPlayer(int[] position,char[][] board,char symbol){
        Random random=new Random(); //for random numbers
        boolean isNotValid=true;
        int row=-1,column=-1;
        System.out.println("Bot Turn");
        while(isNotValid){
            position[0]=random.nextInt(board.length);
            row=position[0];
            position[1]=random.nextInt(board.length);
            column=position[1];
                if(board[row][column]==' '){
                    isNotValid=false;
                }
        }

        System.out.println("Enter row:");
        System.out.println(row);
        System.out.println("Enter column:");
        System.out.println(column);
    }
    public static void playGame(char[][] board,char symbol,Scanner input){
        int boardPiece=0;
        int[] position=new int[2];
        int row=-1,column=-1;
        boolean isGameRunning=true,playerTurn=true;

        while(isGameRunning){
            //printing board
            printBoard(board);

            if(playerTurn){
                System.out.println("---------------------------------------------");
                firstPlayer(position,board,symbol,input);
                playerTurn=false;
            }
            else{
                System.out.println("---------------------------------------------");
                artificalPlayer(position,board,symbol);
                playerTurn=true;
            }

            //updating the position
            row=position[0];
            column=position[1];

            //make the change
            board[row][column]=symbol;
            boardPiece++;

            //check winning
            boolean isWin=findWinning(board,row,column,symbol);

            if(isWin){
                System.out.println("*************************");
                printBoard(board);
                System.out.println("*************************");
                if(!playerTurn){
                    System.out.println("Player won the game :) ");
                }
                else{
                    System.out.println("Bot won the game :( ");
                }
                isGameRunning=false;
            }
            if(boardPiece==9){
                printBoard(board);
                System.out.println("Draw the match :)");
                System.out.println("Well played");
                isGameRunning=false;
            }

            symbol=(symbol=='X')?'O':'X';

        }


    }
    public static void main(String[] args) {
        int row=3,column=3;
        char[][] board=new char[row][column];//Board
        boolean isNotValidSymbol=true;
        Scanner input=new Scanner(System.in);
        char symbol ='F';

        for(int i=0;i<row;++i){
            for(int j=0;j<column;++j){
                board[i][j]=' ';
            }
        }
        while(isNotValidSymbol){
            System.out.println("Enter the player symbol ( X | O ) :");
            symbol=input.next().toUpperCase().charAt(0);
            if(symbol=='X' || symbol=='O'){
                isNotValidSymbol=false;
            }
            else{
                System.out.println("InValid Symbol !");
            }
        }

        playGame(board,symbol,input);

    }
}
