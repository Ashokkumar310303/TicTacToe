package com.practice.TicTacToe;

import java.util.Arrays;
import java.util.Scanner;

public class PlayerAndAiHard {
    public static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
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

    public static boolean isDraw(char[][] board){
        for(char[] array:board){
            for(char ch:array){
                if(ch==' '){
                    return false;
                }
            }
        }
        return true;
    }

   public static boolean findWinning(char[][] board,char symbol){
        //row checking
       for(int i=0;i<board.length;++i){
           if(board[i][0]==symbol && board[i][1]==symbol && board[i][2]==symbol){
               return true;
           }
       }

       //col checking
       for(int i=0;i<board[0].length;++i){
          if(board[0][i]==symbol && board[1][i]==symbol && board[2][i]==symbol){
              return true;
          }
       }

       //left-diagonal check
       if(board[0][0]==symbol && board[1][1]==symbol && board[2][2]==symbol){
           return true;
       }

       //right-diagonal check
       if(board[0][2]==symbol && board[1][1]==symbol && board[2][0]==symbol){
           return true;
       }

       return false;

   }

    public static boolean isValid(int row,int col,char[][] board){
        return (row>=0 && row<board.length) && (col>=0 && col<board[row].length);
    }

    public static boolean isNotOccupied(int row,int col,char[][] board){
        return board[row][col]==' ';
    }

    public static void playerMove(char[][] board,Scanner input){
        boolean isNotValid=true;
        int row=-1,col=-1;

        while(isNotValid){
            System.out.println("Enter your index [0-2]");
            System.out.println("Enter row:");
            row=input.nextInt();
            System.out.println("Enter column:");
            col=input.nextInt();
            if(isValid(row,col,board)){
                //valid index found
                if(isNotOccupied(row,col,board)){
                    isNotValid=false;
                }
                else{
                    System.out.println("It's already occupied");
                }
            }
            else{
                System.out.println("InValid Index !");
            }
        }

        board[row][col]='X';    //marking the point
    }
    public static int findScore(char[][] board,boolean AiTurn){
        // Ai was try to find the area where it has the max score
        // player was try to find the area where ai score was minimized

        //In real time AI try to win or a make a draw
        //edge cases
        if(isDraw(board)){
            //its draw
            return 0; //no score
        }
        if(findWinning(board,'O')){
            //Check for AI wins
            return 10;
        }
        if(findWinning(board,'X')){
            //check for player wins
            return -10;
        }

        if(AiTurn){
            int bestScore=Integer.MIN_VALUE;
            for(int i=0;i<board.length;++i){
                for(int j=0;j<board[i].length;++j){
                    if(board[i][j]==' '){
                        //free area for player
                        board[i][j]='O';
                        int score=findScore(board,false);
                        bestScore=Math.max(score,bestScore);    //find max score for reason -- placing the piece there it may have a high change of winning
                        //back tracking
                        board[i][j]=' ';
                    }
                }
            }
            return bestScore;

        }else{
            int bestScore=Integer.MAX_VALUE;
            for(int i=0;i<board.length;++i){
                for(int j=0;j<board[i].length;++j){
                    if(board[i][j]==' '){
                        //free area for player
                        board[i][j]='X';
                        int score=findScore(board,true); //placing the piece there will give me chance
                        //so that ai move will be minimized
                        bestScore=Math.min(score,bestScore);
                        //back tracking
                        board[i][j]=' ';
                    }
                }
            }
            return bestScore;
        }
    }

    public static void robotMove(char[][] board){
        int bestMoveScore=Integer.MIN_VALUE;
        int row=-1,col=-1;

        for(int i=0;i<board.length;++i){
            for(int j=0;j<board[i].length;++j){
                if(board[i][j]==' '){
                    //free space area
                    board[i][j]='O';
                    int score=findScore(board,false);
                    if(score>bestMoveScore){
                        bestMoveScore=score;
                        row=i;
                        col=j;
                    }
                    //back track
                    board[i][j]=' ';
                }
            }
        }

        //make a move
        if(row!=-1){
            board[row][col]='O';
        }

    }

    public static void main(String[] args) {
        int rows=3,columns=3;
        //creating the board
        char[][] board=new char[rows][columns];
        Scanner input=new Scanner(System.in);
        boolean isGameOver=false;

        //make all the default char to space
        for(char[] array:board){
            Arrays.fill(array, ' ');    //fill all to space
        }

        System.out.println("Welcome to the Tic Tac Toe Game ! ");
        System.out.println("Player symbol - X ");
        System.out.println("Robot symbol - O ");

        while(!(isGameOver)){

            //player move
            printBoard(board);
            playerMove(board,input);
            if(findWinning(board,'X')){
                System.out.println("----------------------------------------------");
                System.out.println("Player won the game :)");
                printBoard(board);
                System.out.println("----------------------------------------------");
                isGameOver=true;
                continue;
            }
            else if(isDraw(board)){
                System.out.println("----------------------------------------------");
                System.out.println("Draw the match!");
                System.out.println("Well played.........");
                printBoard(board);
                System.out.println("----------------------------------------------");
                isGameOver=true;
                continue;
            }

            printBoard(board);
            robotMove(board);
            if(findWinning(board,'O')){
                System.out.println("----------------------------------------------");
                System.out.println("Robot won the game :)");
                printBoard(board);
                System.out.println("----------------------------------------------");
                isGameOver=true;
            }
        }
    }
}
