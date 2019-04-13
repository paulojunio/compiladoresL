import java.io.*;
//teste do analisador lexico
public class teste2{
    public static void main(String[]args){
        try{
            System.setProperty("file.encoding", "UTF-8");
            TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
            BufferedReader code = new BufferedReader(new FileReader("exemplo2.l"));
            AnalisadorLexico a = new AnalisadorLexico(code,tabelaSimbolos);
            while(a.maquinaDeEstados() != null) {
                //analisando coisas
            }
            //System.out.println("Fim de arquivo");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}