import java.io.*;
//teste do analisador lexico
public class teste2{
    public static void main(String[]args){
        try{
            System.setProperty("file.encoding", "UTF-8");
            TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
            BufferedReader code = new BufferedReader(new FileReader("exemplo22.l"));
            AnalisadorLexico a = new AnalisadorLexico(code,tabelaSimbolos);
            AnalisadorSintatico b = new AnalisadorSintatico(code);
            b.S();
            //while(a.maquinaDeEstados() != null) {
            //    Simbolo simbolo = a.maquinaDeEstados();
            //    System.out.println(" " + simbolo.token);
            //}
            //System.out.println("Fim de arquivo");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Deu ruim");
        }
    }
}