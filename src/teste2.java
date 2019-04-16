import java.io.*;
//teste do analisador lexico
public class teste2{
    public static void main(String[]args){
        try{
            System.setProperty("file.encoding", "UTF-8");
            TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
<<<<<<< HEAD
            BufferedReader code = new BufferedReader(new FileReader("exemplo2.l"));
=======
            BufferedReader code = new BufferedReader(new FileReader("exemplo1.l"));
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
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