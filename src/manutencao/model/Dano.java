package manutencao.model;
 
import java.io.Serializable;

//Implementa interface Serializable para poder gravar o objeto no arquivo
public class Dano implements Serializable {
        
    //Atributos
    private String prioridade;
    private String local;
    private String descricao;

    //Construtor
    public Dano(String prioridade, String local, String descricao) {
        this.prioridade = prioridade;
        this.local = local;
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    


}