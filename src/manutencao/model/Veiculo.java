package manutencao.model;

import java.io.Serializable;
import java.util.ArrayList;

//Implementa interface Serializable para poder gravar o objeto no arquivo
public class Veiculo implements Serializable {

    //Atributos
    private final String renavam;
    private final String placa;
    private final String classe;
    private final String marca;
    private final String modelo;
    private int quilometragem;
    ArrayList<Dano> danos;
    ArrayList<Manutencao> manutencao;

    //Construtor
    public Veiculo(String renavam, String placa, String classe, String marca, String modelo, int quilometragem) {
        this.renavam = renavam;
        this.placa = placa;
        this.classe = classe;
        this.marca = marca;
        this.modelo = modelo;
        this.quilometragem = quilometragem;
        manutencao = new ArrayList<>();
    }

    //Métodos Getters, Setters de cada atributo
    public ArrayList<Dano> getDanos() {
        return danos;
    }

    public void setDanos(ArrayList<Dano> danos) {
        this.danos = danos;
    }
    
    public ArrayList<Manutencao> getManutencao() {
        return manutencao;
    }

    public void setManutencao(ArrayList<Manutencao> manutencao) {
        this.manutencao = manutencao;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getRenavam() {
        return renavam;
    }

    public String getPlaca() {
        return placa;
    }

    public String getClasse() {
        return classe;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    

}
