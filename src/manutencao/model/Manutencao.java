package manutencao.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Manutencao implements Serializable {

    private String tipo;
    private Date data;
    private int quilometragem;
    private ArrayList<ItemManutencao> itens;
    
    /*   
    *   Cria atributos cell para ter controle da formatação 
    *   dos atributos "itens", "valor", "data" e "quilometragem"
    *   através dos métodos Getters 
    *   na TableView de Manutenção.
    */
    private String cellItens;
    private String cellValor;
    private String cellData;
    private String cellKm;

    //Métodos Getters das cells
    public String getCellValor() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        double vTotal = 0;
        
        for (ItemManutencao i : itens) {
            vTotal += i.getValor();
        }
        
        return "R$ " + nf.format(vTotal);
    }

    public String getCellData() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(data);
    }
    
    public String getCellKm() {
        return Integer.toString(quilometragem) + " Km";
    }

    public String getCellItens() {
        return Integer.toString(itens.size());
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<ItemManutencao> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemManutencao> itens) {
        this.itens = itens;
    }

}
