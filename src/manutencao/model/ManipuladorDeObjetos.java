package manutencao.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import manutencao.view.Alerta;

public class ManipuladorDeObjetos {

    //Carrega o arquivo veiculos.ser
    public static ArrayList<Veiculo> load() {

        //Cria uma lista
        ArrayList<Veiculo> veiculosSer = new ArrayList<>();
        
/*
* 
*  O CODIGO COMENTADO CARREGA ALGUNS VEICULOS PARA DEMONSTRAÇÃO
*
*/

//        ArrayList<Veiculo> veiculosList = new ArrayList<>();
//        
//        veiculosList.add(new Veiculo("0000000", "ABC1234", "SUV", "FIAT", "Freemont", 3000));
//        veiculosList.add(new Veiculo("1111111", "ZXC7410", "SUV", "FIAT", "Freemont", 1500));
//        veiculosList.add(new Veiculo("2222222", "LOL1590", "Esportivo", "Volkswagen", "Jetta", 20456));
//        veiculosList.add(new Veiculo("3333333", "HUE5555", "Híbrido", "Chevrolet", "Volt", 4940));
//        veiculosList.add(new Veiculo("4444444", "XYZ8520", "Elétrico", "Tesla", "Model S", 13804));
//        veiculosList.add(new Veiculo("5555555", "PQP0069", "SUV", "BMW", "X1", 10051));
//        veiculosList.add(new Veiculo("6666666", "PAZ4567", "Minivan", "Ford", "Transit Connect", 9712));
//        Veiculo vei = new Veiculo("7777777", "DOL4777", "Conversível", "Mercedes-Benz", "SL65 AMG", 401);
//        ArrayList<Manutencao> manutencaoList = new ArrayList<>();
//        ArrayList<ItemManutencao> itemList = new ArrayList<>();
//        itemList.add(new ItemManutencao("teste", 50));
//        Manutencao man = new Manutencao();
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        String data = "17/11/2015";
//        try {
//            man.setData(df.parse(data));
//        } catch (ParseException ex) {
//        }
//        man.setQuilometragem(400);
//        man.setTipo("Preventiva");
//        man.setItens(itemList);
//        manutencaoList.add(man);
//        vei.setManutencao(manutencaoList);
//        veiculosList.add(vei);
//        try {
//
//            File arq = new File("src/manutencao/files/veiculos.ser");
//            FileOutputStream fo = new FileOutputStream(arq);
//            ObjectOutputStream oo = new ObjectOutputStream(fo);
//
//            oo.writeObject(veiculosList);
//            oo.flush();
//            oo.close();
//
//        } catch (FileNotFoundException ex) {
//            System.out.println("ERRO: " + ex.getMessage());
//        } catch (IOException ex) {
//            System.out.println("ERRO: " + ex.getMessage());
//        }

        //Carrega os objetos do arquivo veiculos.ser
        try {

            File arq = new File("src/manutencao/files/veiculos.ser");
            FileInputStream fo = new FileInputStream(arq);
            ObjectInputStream oo = new ObjectInputStream(fo);

                try {
                    
                    veiculosSer = (ArrayList) oo.readObject();
                    oo.close();
                    
                } catch (ClassNotFoundException ex) {
                    Alerta.erro(ex);
                }
            

        } catch (IOException ex) {
            Alerta.erro(ex);
        }

        return veiculosSer;
    }
    
    //Grava no arquivo veiculos.ser
    public static void write(Veiculo newVeiculo) {

        //Cria uma nova Lista
        ArrayList<Veiculo> veiculosSer = new ArrayList<>();
        ArrayList<Veiculo> veiculosSerNew = new ArrayList<>();

        //Carrega os objetos do arquivo veiculos.ser
        try {

            File arq = new File("src/manutencao/files/veiculos.ser");
            FileInputStream fo = new FileInputStream(arq);
            ObjectInputStream oo = new ObjectInputStream(fo);

                try {
                    
                    veiculosSer = (ArrayList) oo.readObject();
                    oo.close();
                    
                } catch (ClassNotFoundException ex) {
                    Alerta.erro(ex);
                }

        } catch (IOException ex) {
            Alerta.erro(ex);
        }
        
        //Remove o antigo objeto da lista
        for (Veiculo v : veiculosSer) {
            
            if (v.getRenavam().equals(newVeiculo.getRenavam())) {
                
                veiculosSerNew.add(newVeiculo);
                continue;
                
            }
            
            veiculosSerNew.add(v);
        }
        
        //Grava os objetos da lista no arquivo veiculos.ser
        try {

            File arq = new File("src/manutencao/files/veiculos.ser");
            FileOutputStream fo = new FileOutputStream(arq);
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            oo.writeObject(veiculosSerNew);
            oo.flush();
            oo.close();

        } catch (FileNotFoundException ex) {
            Alerta.erro(ex);
        } catch (IOException ex) {
            Alerta.erro(ex);
        }

    }

}
