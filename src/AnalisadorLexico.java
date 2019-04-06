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

    /* Teste
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
    } */

    public void maquinaDeEstados() {
        int estadoInicial = 0;
        int estado = estadoInicial;
        int estadoFinal = 18;

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
                estado = estado15();
            }else if(estado == 16) {
                //final
            }
        }
    }

    public int estado0(){
        lexema = "";
        char caracter = lerCaracter();
        if(controle.ECaracterEspecialEToken(caracter) == true) {
            lexema += caracter;
            if(controle.barra == caracter) {
                return 1;      
            }else if(controle.apostofro == caracter) {
                return 5;
            }else if(controle.aspas == caracter) {
                return 10;
            }else if(controle.menor == caracter) {
                return 12;
            }else if(controle.maior == caracter) {
                return 13;
            }else if (controle.sublinhado == caracter || controle.pontoFinal == caracter) {
                return 14;
            }
            return 16;
        }else if(controle.EDigito(caracter) == true) {
            lexema += caracter;
            if(caracter == '0') {
                return 7;
            }
            return 4;
        }else if(controle.ELetra(caracter) == true ) {
            lexema += caracter;
            return 15;
        }else if(controle.quebraDeLinhas(caracter) == true) {
            if(controle.barraN == caracter || controle.novalinha == caracter) {
                //linha++;
            }
            return 0;
        }else if(controle.fimDeArquivo == caracter){
            //fimDeArquivo = true;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado1() {
        char caracter = lerCaracter();
        if(controle.asterisco == caracter) {
            //lexema += caracter;
            return 2;
        }else if(controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 13;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado2() {
        char caracter = lerCaracter();
        if(controle.asterisco == caracter) {
            return 3;
        }else if(controle.ECaracterValido(caracter) == true) {
            return 2;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado3() {
        char caracter = lerCaracter();
        if(controle.asterisco == caracter){
            return 3;
        }else if(controle.barra == caracter) {
            return 0;
        }else if(controle.ECaracterValido(caracter) == true) {
            return 2;
        }
        //mostrarErro(caracter);
        return 16;
    }
    
    public int estado4() {
        char caracter = lerCaracter();
        if(controle.EDigito(caracter) == true) {
            lexema += caracter;
            return 4;
        }else if(controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado5() {
        char caracter = lerCaracter();
        if(controle.EDigito(caracter) || controle.ELetra(caracter) || controle.ECaracterEspecial(caracter)) {
            lexema += caracter;
            return 6;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado6() {
        char caracter = lerCaracter();
        if(controle.apostofro == caracter) {
            lexema += caracter;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado7() {
        char caracter = lerCaracter();
        if(caracter == 'x' || caracter == 'X') {
            lexema += caracter;
            return 8;
        }
        //mostrarErro(caracter);
        return 16;
    }
    
    public int estado8() {
        char caracter = lerCaracter();
        if(controle.EDigito(caracter) || controle.EHexadecimal(caracter)) {
            lexema += caracter;
            return 9;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado9() {
        char caracter = lerCaracter();
        if(controle.EDigito(caracter) || controle.EHexadecimal(caracter)) {
            lexema += caracter;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado10() {
        char caracter = lerCaracter();
        if(controle.EDigito(caracter) || controle.ELetra(caracter) || controle.ECaracterEspecial(caracter)) {
            if(controle.aspas != caracter && controle.barraN != caracter && controle.novalinha != caracter) {
                lexema += caracter;
                return 11;
            }
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado11() {
        char caracter = lerCaracter();
        if(controle.aspas == caracter) {
            lexema += caracter;
            return 16;
        }else if(controle.EDigito(caracter) || controle.ELetra(caracter) || controle.ECaracterEspecial(caracter)) {
            if(controle.aspas != caracter && controle.barraN != caracter && controle.novalinha != caracter) {
                lexema += caracter;
                return 11;
            }
        }
        //mostrarErro(caracter);
        return 16;
    }
    
    public int estado12() {
        char caracter = lerCaracter();
        if(controle.maior == caracter) {
            lexema += caracter;
            return 16;
        }else if(controle.igual == caracter) {
            lexema += caracter;
            return 16;
        }else if(controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
    }
    
    public int estado13() {
        char caracter = lerCaracter();
        if(controle.igual == caracter) {
            lexema += caracter;
            return 16;
        }else if(controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado14() {
        char caracter = lerCaracter();
        if (controle.sublinhado == caracter || controle.pontoFinal == caracter) {
            lexema += caracter;
            return 14;
        }else if(controle.ELetra(caracter) == true || controle.EDigito(caracter) == true) {
            lexema += caracter;
            return 15;
        }
        //mostrarErro(caracter);
        return 16;
    }

    public int estado15() {
        char caracter = lerCaracter();
        if(controle.ELetra(caracter) == true || controle.EDigito(caracter) == true || controle.sublinhado == caracter || controle.pontoFinal == caracter) {
            lexema += caracter;
            return 15;
        }else if(controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        //mostrarErro(caracter);
        return 16;
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

    public void mostrarErro(char caracter) {
        
        if() {

        }else if(){

        }else if(){

        }
    }
}
