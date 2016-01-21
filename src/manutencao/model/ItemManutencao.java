package manutencao.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.UUID;

//Implementa interface Serializable para poder gravar o objeto no arquivo
public class ItemManutencao implements Serializable {

    String id = UUID.randomUUID().toString();
    private String nome;
    private double valor;
    
    /*   
    *   Cria atributo cell para ter controle 
    *   da formatação do atributo "valor"
    *   através do método Getter 
    *   na TableView de Manutenção.
    */
    private String cellValor;
    
    //Construtor
    public ItemManutencao(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    //Método Getter da cell
    public String getCellValor() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        return "R$ " + nf.format(valor);
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getID() {
        return id;
    }
}
