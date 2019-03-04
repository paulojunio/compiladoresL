import java.io.*;

public class AnalisadorLexico{

    public BufferedReader code;


    public AnalisadorLexico(BufferedReader bufferedReader){
        this.code = bufferedReader;
    }

    public void read(){
        try{
            int intcode = this.code.read();
            while (intcode != -1){
                System.out.println( " " + (char)intcode);
                intcode = this.code.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String formando_lexema(char c){

    }
    
}
