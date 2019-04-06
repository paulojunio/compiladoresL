import java.io.*;
//Classe que le um arquivo e pega digito por digito para formar o lexema
public class AnalisadorLexico{

    public BufferedReader codigo;
    public boolean errorCompila;
    public boolean devolve;
    public String lexema;
    public char ultimaLetra;





    public AnalisadorLexico(BufferedReader bufferedReader){
        this.codigo = bufferedReader;
    }

    
    public void read(){
        try{
            int intcode = this.codigo.read();
            while (intcode != -1){
                System.out.println( " " + (char)intcode);
                if (!(controle.EDelimitador((char)intcode))){
                    char_formato((char)intcode);
                }
                intcode = this.codigo.read();
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

    public void maquinaDeEstados() {
        int estadoInicial = 0;
        int estado = estadoInicial;
        int estadoFinal = 15;

        while(estadoInicial != estadoFinal) {
            if(estado == 0) {
                estado = estado0();
            }else if(estado == 1) {
                estado = estado1();
            }else if(estado == 2) {
                estado = estado2();
            }else if(estado == 3) {
                estado = estado3();
            }else if(estado == 4) {
                estado = estado4();
            }else if(estado == 5) {
                estado = estado5();
            }else if(estado == 6) {
                estado = estado6();
            }else if(estado == 7) {
                estado = estado7();
            }else if(estado == 8) {
                estado = estado8();
            }else if(estado == 9) {
                estado = estado9();
            }else if(estado == 10) {
                estado = estado10();
            }else if(estado == 11) {
                estado = estado11();
            }else if(estado == 12) {
                estado = estado12();
            }else if(estado == 13) {
                estado = estado13();
            }else if(estado == 14) {
                estado = estado14();
            }else if(estado == 15) {
                //final
            }
        }
    }

    public int estado0(){
        lexema = "";
        char caracter = lerCaracter();

        if(controle.ELetra(caracter)) {
            lexema += caracter;
            
        }
        return -1;
    }

    public char lerCaracter() {
        try {
            if(devolve == true) {
                devolve = false; //Ultimo caracter
            }else{
                return (char) codigo.read(); //Novo caracter
            }
        }catch(Exception e){
            System.out.println("Error ao acessar arquivo");
            System.out.println(e);
        }
        return ultimaLetra;
    }
}
