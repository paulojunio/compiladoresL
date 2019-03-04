import java.io.*;
//Classe que le um arquivo e pega digito por digito para formar o lexema
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
                if (!(controle.EDelimitador((char)intcode))){
                    char_formato((char)intcode);
                }
                intcode = this.code.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void char_formato(char c){
        if (controle.ELetra(c)){
            System.out.println("É letra");
        }else if(controle.EDigito(c)){

        }
    } 
}
