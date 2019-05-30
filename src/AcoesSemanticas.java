/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 17/04/2019
*/
import java.io.*;
public class AcoesSemanticas{

    public AcoesSemanticas () {

    }

    public void verificarID(Simbolo simbolo, byte classe) {
        if(classe == 0) {
            if(simbolo.classe == simbolo.Nenhuma_classe) {
                System.out.println("identificador nao declarado " + '[' + simbolo.lexema + "].");
			}
        }else if(classe == 1) {
			if(simbolo.classe != simbolo.Nenhuma_classe) {
				System.out.println("identificador ja declarado " + '[' + simbolo.lexema + "].");
			}else{
                simbolo.classe = simbolo.Constante_classe;
                System.out.println("Constante alocada: " + '[' + simbolo.lexema + "] " + " Tipo classe: " + simbolo.classe);
			}
        }else{
            if(simbolo.classe != simbolo.Nenhuma_classe) {
				System.out.println("identificador ja declarado " + '[' + simbolo.lexema + "].");
			}else{
                simbolo.classe = simbolo.Variavel_classe;
                System.out.println("Variavel alocada: " + '[' + simbolo.lexema + "] " + " Tipo classe: " + simbolo.classe);
			}
        }
    }

    public void verificarVetorTamanho(Simbolo simbolo, byte tipoDoIdentificador) {
        //System.out.println(Integer.parseInt(simbolo.lexema)*2 + " , " + tipoDoIdentificador);
        if(tipoDoIdentificador == 1) {
            if(Integer.parseInt(simbolo.lexema) * 2 > 4000 ) {
                System.out.println("tamanho do vetor excede o maximo permitido.");
            }
        }else if(tipoDoIdentificador == 2) {
            if(Integer.parseInt(simbolo.lexema)  > 4000 ) {
                System.out.println("tamanho do vetor excede o maximo permitido.");
            }
        }
    }

    public void alocandoTiposEmIds(Simbolo idDeclarado, byte tipoDoId) {
        idDeclarado.tipo = tipoDoId;
    }
}