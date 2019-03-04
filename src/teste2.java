import java.io.*;
//teste do analisador lexico
public class teste2{
    public static void main(String[]args){
        try{
            BufferedReader code = new BufferedReader(new FileReader("testBuffer.txt"));
            AnalisadorLexico a = new AnalisadorLexico(code);
            a.read();
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}