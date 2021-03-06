import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

class Pilot{

    static int x = 0;
    static int y = 0;
    static int direction = 0;
    static char[] directions = {'N','E','S','W'};
    static char loaded = ' ';

    static void rotate(int mod){
        //if mod -1 then rotate left if 1 then rotate right.
        //Rotate requires method to do this without going outside bounds of array
        if((direction + 1 > directions.length-1) && mod > 0){
            //Sets direction to 0 if value too low
            direction = 0;
        }else if((direction - 1 < 0) && mod < 0){
            //Sets direction to 
            direction = 3;
        }
        else{
            //If rotation within array then safely move pointer up/down
            direction+=mod;
        }
        //System.out.println("Now facing: " + directions[direction]);
    }

    //Resets all variables after a file has been run
    static void reset(){
        x = 0;
        y = 0;
        direction = 0;
        loaded = ' ';
    }

    //Mode is -1 for - and 1 for + (saves having 2 seperate methods for forward and back)
    static void move(int mod){
        //Switches based on current direction and moves forward or back depending on mod
        switch(direction){
            case(0):
                y+=mod;
                break;
            case(1):
                x+=mod;
                break;
            case(2):
                y-=mod;
                break;
            case(3):
                x-=mod;
                break;
        }
    }

    //After reading a cardinal direction it reads the next value to determine direction and then calculates based on that value
    static void go(int mod){
        //For executing cardinal instructions, similar to move but doesn't rely on direction.
        switch(loaded){
            case('N'):
                y+=mod;
                break;
            case('E'):
                x+=mod;
                break;
            case('S'):
                y-=mod;
                break;
            case('W'):
                x-=mod;
                break;
        }
    }

    static void run(String filename){
        try{
            //Opens file within an input stream and processes it for reading 1 character at a time
            Reader r = new InputStreamReader(
                new FileInputStream(
                    new File(filename)
                )
            );

            //Variable for storing current value
            int val;
            //Reads characters until end of file (-1)
            while((val = r.read()) != -1){
                //Casts current read character to char
                char buf = (char)val;

                //System.out.println(buf);

                /*Checks if character matches one from instruction set. R,L,N,E,S,W are all key instructions and will be loaded into a buffer.
                You can use a boolean to set if instruction is cardinal however i want to be able to set no instruction in a space (' ') to cover
                +- instructions without direction as they may not be intentional.
                */
                switch(buf){
                    case('R'):
                        loaded = 'R';
                        rotate(1);
                        break;
                    case('L'):
                        loaded = 'L';
                        rotate(-1);
                        break;
                    case('+'):
                        if(loaded == 'R' || loaded == 'L'){
                            move(1);
                        }else if(loaded == 'N' || loaded == 'E' || loaded == 'S' || loaded == 'W')   {
                            go(1);
                        }else{
                            System.out.println("No valid function loaded");
                        }
                        break;
                    case('-'):
                        if(loaded == 'R' || loaded == 'L'){
                            move(-1);
                        }else if(loaded == 'N' || loaded == 'E' || loaded == 'S' || loaded == 'W'){
                            go(-1);
                        }else{
                            System.out.println("No valid function loaded");
                        }
                        break;   
                    case(' '):
                        loaded = ' ';
                        break;
                    //Go into another switch statement to prevent 4 if else statements based on direction.
                    case('N'):
                        loaded = 'N';
                        break;
                    case('E'):
                        loaded = 'E';
                        break;
                    case('S'):
                        loaded = 'S';
                        break;
                    case('W'):
                        loaded = 'W';
                        break;
                    default:
                        System.out.println("Not a valid character. Please check input");
                        break;
                }
                //System.out.println("("+x+","+y+")");
            }

            System.out.println("Final coordinates: " + "("+x+","+y+")" + "\n");

            r.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found \n");

        }catch(IOException e){
            System.out.println("Problem with access of file\n");
        }

    }

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Please type in path to the route file without quotes\n");
            String s = scanner.nextLine();
            System.out.println();
            run(s);
            reset();
        }        
    }
}