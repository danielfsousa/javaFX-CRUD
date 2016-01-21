package manutencao.controller;

import manutencao.model.Dano;
import manutencao.model.Veiculo;
import manutencao.model.ManipuladorDeObjetos;
import manutencao.view.View;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import manutencao.model.ManipuladorDeTxt;
import manutencao.model.Manutencao;
import manutencao.view.Alerta;
import org.joda.time.DateTime;

//Implementa interface obrigatória Initializable do javaFX para controllers
public class PrimaryStageController implements Initializable {

    @FXML
    private TableView<Veiculo> tableVeiculos;
    @FXML
    private TableView<Manutencao> tableManutencoes;
    @FXML
    private TableView<Dano> tableDanos;
    @FXML
    private Button btnAddManutencao;
    @FXML
    private Button btnAddDanos;
    @FXML
    private Button btnExcluirDanos;
    @FXML
    private Button btnClearSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label txtQuilometragem;
    @FXML
    private Label txtUltimaRevisao;
    @FXML
    private Label txtProximaRevisao;
    @FXML
    private Label txtKm;

    private final View javaFX = new View();
    private Veiculo v;
    private PrimaryStageController primaryController;

    //Sobrescrevendo o método initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Carrega ArrayList de Veiculos e tranforma em uma ObservableList / FilteredList
        ArrayList<Veiculo> veiculosListArray = ManipuladorDeObjetos.load();
        ObservableList<Veiculo> veiculosList = FXCollections.observableArrayList(veiculosListArray);
        FilteredList<Veiculo> filteredData = new FilteredList<>(veiculosList, p -> true);
        tableVeiculos.setItems(FXCollections.observableArrayList(veiculosList));

        // Adiciona um listener para saber quando um novo item é selecionado na tabela
        tableVeiculos.getSelectionModel().selectedItemProperty().addListener((event, oldValue, newValue) -> {
            getVeiculos(newValue);
            unbindData(oldValue);
            bindData(newValue);
        });

        // Filtragem de Veiculos (Pesqusia)
        // 1. Cria um listener
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Veiculo -> {
                // Se a caixa de texto estiver vazia, mostre todos os veículos.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compara todas as colunas de todos os veículos. Para isso transforma todo o texto em letras minúsculas.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Veiculo.getRenavam().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtra por renavam.
                } else if (Veiculo.getPlaca().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtra por placa.
                } else if (Veiculo.getClasse().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtra por classe.
                } else if (Veiculo.getMarca().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtra por marca.
                } else if (Veiculo.getModelo().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtra por modelo.
                }
                return false; // Não achou resultados.
            });
        });

        // 2. Insere a FilteredList em uma SortedList. 
        SortedList<Veiculo> sortedData = new SortedList<>(filteredData);

        // 3. Faz um bind da SortedList com a TableView(tableVeiculos).
        sortedData.comparatorProperty().bind(tableVeiculos.comparatorProperty());

        // 4. Adiciona os dados ordenados(sorted) e filtrados(filtered) na tabela.
        tableVeiculos.setItems(sortedData);

        //Desabilitar Botões
        btnAddDanos.disableProperty().bind(tableVeiculos.getSelectionModel().selectedItemProperty().isNull());
        btnAddManutencao.disableProperty().bind(tableVeiculos.getSelectionModel().selectedItemProperty().isNull());
        btnExcluirDanos.disableProperty().bind(tableDanos.getSelectionModel().selectedItemProperty().isNull());
        btnClearSearch.disableProperty().bind(txtSearch.textProperty().isEmpty());

    }

    //Limpa a busca
    @FXML
    public void limparBusca() {
        txtSearch.clear();
        txtSearch.requestFocus();
    }

    //Exclui Dano
    @FXML
    public void excluirDano() {
        ArrayList<Dano> listDanos = v.getDanos();
        listDanos.remove(tableDanos.getSelectionModel().getSelectedItem());
        v.setDanos(listDanos);
        ManipuladorDeObjetos.write(v);
        bindData(v);
    }

    //Faz o binding dos Labels, Danos e Manutencoes com as propriedades de Veiculos
    public void bindData(Veiculo veiculo) {
        if (veiculo != null) {

            //Se o dia da próxima revisão for anterior aos dia de hoje -> colorir o texto de vermelho
            DateTime jodaTime = ManipuladorDeTxt.getProximaRevisaoDateTime(veiculo);
            if (ManipuladorDeTxt.getIntervaloRevisao() == 1) {
                if (jodaTime.isBeforeNow()) {
                    txtProximaRevisao.getStyleClass().add("texto-red");
                } else {
                    txtProximaRevisao.getStyleClass().remove("texto-red");
                }
            }

            //Se a quilometragem de revisao for menor ou igual a quilometragem atual -> colorir o texto de vermelho
            if (ManipuladorDeTxt.getIntervaloRevisao() == 0) {
                if (ManipuladorDeTxt.getProximaRevisaoInteger(veiculo) <= veiculo.getQuilometragem()) {
                    txtProximaRevisao.getStyleClass().add("texto-red");
                } else {
                    txtProximaRevisao.getStyleClass().remove("texto-red");
                }
            }

            txtQuilometragem.setText(Integer.toString(veiculo.getQuilometragem()) + " Km");
            txtUltimaRevisao.setText(ManipuladorDeTxt.getUltimaRevisao(veiculo));
            txtProximaRevisao.setText(ManipuladorDeTxt.getProximaRevisao(veiculo));

            if (veiculo.getDanos() != null) {
                tableDanos.setItems(FXCollections.observableArrayList(veiculo.getDanos()));
            }
            if (veiculo.getManutencao() != null) {
                tableManutencoes.setItems(FXCollections.observableArrayList(veiculo.getManutencao()));
            }
        }
    }

    //Faz o unbinding dos Labels, Danos e Manutencoes
    private void unbindData(Veiculo veiculos) {
        if (veiculos != null) {
            txtQuilometragem.setText(null);
            txtUltimaRevisao.setText(null);
            txtProximaRevisao.setText(null);
            tableDanos.setItems(null);
            tableManutencoes.setItems(null);
        }
    }

    //Refletindo dados
    private void getVeiculos(Veiculo veiculos) {
        this.v = veiculos;
    }

    public void setPrimaryController(PrimaryStageController controller) {
        this.primaryController = controller;
    }

    /*
    *   Carrega as janelas da classe "Manutenção" e 
    *   passa os objetos Veiculo e Controller para
    *   as outras janelas terem acesso à eles.
    */
    public void Mconfig() {
        try {
            javaFX.Mconfig(v, primaryController);
        } catch (IOException ex) {
            Alerta.erro(ex);
        }

    }

    public void Madd() {
        try {
            javaFX.Madd(v, primaryController);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Alerta.erro(ex);
        }
    }

    public void Dadd() {
        try {
            javaFX.Dadd(v, primaryController);
        } catch (IOException ex) {
            Alerta.erro(ex);
        }
    }
}
