/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 03/06/2019
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.util.*;
public class GeracaoDeCodigo {

    public int contadorVariaveis;
    public int contadorRotulos;
    public int contadorTemporarios;
    public BufferedWriter arquivoAsm;

    public static final byte Asm_Nenhuma_classe = 0; // Nenhuma classe = 0
    public static final byte Asm_Constante_classe = 1;// classe constante = 1
    public static final byte Asm_Variavel_classe = 2;// classe variavel = 2
    
    public static final byte Asm_Nenhum_tipo = 0; // Nenhuma tipo = 0
    public static final byte Asm_Inteiro_tipo = 1;// Tipo inteiro = 1
    public static final byte Asm_Caracter_tipo = 2;// Tipo caracter = 2
    public static final byte Asm_Logico_tipo = 3;// Tipo logico = 3

    GeracaoDeCodigo(String nomeArquivoAsm) {
        try {
            contadorVariaveis = 0x4000;
            contadorRotulos = 0;
            contadorTemporarios = 0;
            this.arquivoAsm = new BufferedWriter(new FileWriter(nomeArquivoAsm));
        }catch(Exception e){
            System.out.println("Erro ao criar arquivo asm"); //arquivoAsm.append(linha + "\n"); arquivoAsm.close();
        }
    }

    public void inicioPrograma() {
        try {
            arquivoAsm.append("sseg SEGMENT STACK ;início seg. pilha" + "\n");
            arquivoAsm.append("byte 4000h DUP(?) ;dimensiona pilha" + "\n");
            arquivoAsm.append("sseg ENDS ;fim seg. pilha" + "\n");
            arquivoAsm.append("dseg SEGMENT PUBLIC ;início seg. dados" + "\n");
            arquivoAsm.append("byte 4000h DUP(?) ;temporários" + "\n");
        }catch(Exception E){
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public void inicioCodigo() {
        try {
            arquivoAsm.append("dseg ENDS ;fim seg. dados" + "\n");
            arquivoAsm.append("cseg SEGMENT PUBLIC ;início seg. código" + "\n");
            arquivoAsm.append("ASSUME CS:cseg, DS:dseg" + "\n");
            arquivoAsm.append("strt: ;início do programa" + "\n");
            arquivoAsm.append("mov ax, dseg" + "\n");
            arquivoAsm.append("mov ds, ax" + "\n");
        }catch(Exception E){
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public void fimDoAsm() {
        try {
            arquivoAsm.append("mov ah,4Ch" + "\n");
            arquivoAsm.append("int 21h" + "\n");
            arquivoAsm.append("cseg ENDS ;fim seg. código" + "\n");
            arquivoAsm.append("END strt ;fim programa" + "\n");
            arquivoAsm.close();
        }catch(Exception E){
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public void adicionarInteiro(Simbolo simbolo,String valor) {
        try {
            if(valor == null) {
                arquivoAsm.append("sword ?" + "; inteiro declarado na posicao: " + contadorVariaveis + " Simbolo: " + simbolo.lexema + "\n");
                simbolo.endereco = contadorVariaveis;
                contadorVariaveis+= 2;
            }else{
                arquivoAsm.append("sword " + Integer.parseInt(valor) + "; inteiro declarado na posicao: " + contadorVariaveis + " Simbolo:"+ simbolo.lexema +"\n");
                simbolo.endereco = contadorVariaveis;
                contadorVariaveis+= 2;
            }   
        } catch (Exception e) {
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public void adicionarCaracter(Simbolo simbolo, String valor) {
        try {
            if(valor == null) {
                arquivoAsm.append("byte ?" + "; caracter sem valor declarado na posicao: " + contadorVariaveis + " Simbolo: " + simbolo.lexema + "\n");
                simbolo.endereco = contadorVariaveis;
                contadorVariaveis+= 1;
            }else{
                arquivoAsm.append("byte " + valor + "; caracter declarado na posicao: " + contadorVariaveis + " Simbolo:"+ simbolo.lexema +"\n");
                simbolo.endereco = contadorVariaveis;
                contadorVariaveis+= 1;
            }
        } catch (Exception e) {
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public void adicionarVetor(Simbolo simbolo) {
        try {    
            if(simbolo.tipo == Asm_Caracter_tipo) {
                arquivoAsm.append("byte " + simbolo.tamanho + " DUP(?)" + "; caracter vetor declarado na posicao: " + contadorVariaveis + " Simbolo: " + simbolo.lexema + "\n");
                simbolo.endereco = contadorVariaveis;
                contadorVariaveis+= simbolo.tamanho;
            }else{
                arquivoAsm.append("sword " + simbolo.tamanho + " DUP(?)" + "; inteiro vetor declarado na posicao: " + contadorVariaveis + " Simbolo: " + simbolo.lexema + "\n");
                simbolo.endereco = contadorVariaveis;
                contadorVariaveis+= simbolo.tamanho * 2;
            }
        } catch (Exception e) {
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public void declararString(String lexema) {
        try{
            arquivoAsm.append("dseg SEGMENT PUBLIC ; declarando string");
            arquivoAsm.append("byte "+ "\"" + lexema + "\" ; string" + "\n");
            arquivoAsm.append("dseg ENDS ; fim da string");
        }catch(Exception e) {
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }
    public void escreverComandos(String comando) {
        try{
            arquivoAsm.append(comando + "\n");
        }catch(Exception e){
            System.out.println("Erro ao escrever no arquivo asm.");
        }
    }

    public int novoTemp(int i){
        int novoTemp = contadorTemporarios;
        contadorTemporarios += i;
        return novoTemp;
    }

    public void resetTemp() {
        contadorTemporarios = 0;
    }

}