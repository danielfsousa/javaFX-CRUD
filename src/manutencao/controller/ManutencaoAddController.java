package manutencao.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import manutencao.model.ItemManutencao;
import manutencao.model.ManipuladorDeObjetos;
import manutencao.model.Manutencao;
import manutencao.model.Veiculo;
import manutencao.view.Alerta;

//Implementa interface obrigatória Initializable do javaFX para controllers
public class ManutencaoAddController implements Initializable {

    @FXML
    private TableView tableItens;

    @FXML
    private TextField tfItem;

    @FXML
    private TextField tfValor;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label lblData;

    @FXML
    private Label lblKm;

    @FXML
    private Label lblVTotal;

    @FXML
    private ChoiceBox<String> cbTipo;

    //Refletindo os dados
    private Veiculo v;
    private Manutencao m;
    private PrimaryStageController primaryController;

    private ArrayList<ItemManutencao> itens;
    private ObservableList<ItemManutencao> observableItens;
    private double valorTotal;

    //Sobrescrevendo o método initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Cria um objeto Manutencao
        m = new Manutencao();
        //Cria uma ArrayList de Itens
        itens = new ArrayList<>();
        //Cria uma observableList para a TableView
        observableItens = FXCollections.observableArrayList(itens);
        tableItens.setItems(observableItens);

        //Formatar tfValor para que receba apenas números decimais
        DecimalFormat format = new DecimalFormat("#,0");
        tfValor.setTextFormatter(new TextFormatter<>(c -> {

            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));

        //Desabilita botão
        btnExcluir.disableProperty().bind(tableItens.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void salvar() {

        //Se houver itens
        if (!itens.isEmpty()) {

            //Atribui valores ao objeto Manutencao
            m.setData(new Date());
            m.setQuilometragem(v.getQuilometragem());
            m.setTipo(cbTipo.getValue());

            //Adiciona a ArrayList de itens ao objeto Manutencao
            m.setItens(itens);

            //Cria uma nova ArrayList
            ArrayList<Manutencao> manutencoes = new ArrayList<>();

            //Carrega a ArrayList de Manutecoes do Veiculo selecionado
            if (v.getManutencao() != null) {
                manutencoes = v.getManutencao();
            }

            //Adiciona a nova Manutencao à lista
            manutencoes.add(this.m);

            //Adiciona a ArrayList de Manutencao no Veiculo selecionado
            v.setManutencao(manutencoes);

            //Grava no Arquivo veiculos.ser
            ManipuladorDeObjetos.write(v);

            //Faz o binding na Janela Principal
            primaryController.bindData(v);

            //Fecha a Janela
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        } else {
            Alerta.atencao("Você não adicionou nenhum item.");
        }

    }

    @FXML
    private void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void add() {

        if (!tfItem.getText().isEmpty() && !tfValor.getText().isEmpty()) {
            //Transforma as virgulas em pontos para usar como double
            NumberFormat nf = NumberFormat.getNumberInstance();
            double valor = 0;
            try {
                valor = nf.parse(tfValor.getText()).doubleValue();
            } catch (ParseException ex) {
                Alerta.erro(ex);
            }

            //Cria um novo item
            ItemManutencao item = new ItemManutencao(tfItem.getText(), valor);

            //Adiciona o item à ArrayList de itens
            itens.add(item);

            //Adiciona o Item à TableView
            observableItens.add(item);

            //Soma o valor total
            valorTotal += valor;
            lblVTotal.setText("R$ " + nf.format(valorTotal));
        } else {
            Alerta.atencao("Os campos \"Nome do item\" e \"Valor do item\" são obrigatórios.");
        }

    }

    @FXML
    private void excluir() {

        if (!tableItens.getSelectionModel().isEmpty()) {
            //Carrega o item selecionado
            ItemManutencao item = (ItemManutencao) tableItens.getSelectionModel().getSelectedItem();

            //Remove o item selecionado da ArrayList de itens
            itens.remove(item);

            //Remove o item selecionado da TableView
            observableItens.remove(item);

            //Subtrai o valor total
            valorTotal -= item.getValor();
            NumberFormat nf = NumberFormat.getNumberInstance();
            lblVTotal.setText("R$ " + nf.format(valorTotal));
        } else {
            Alerta.atencao("Você não selecionou nenhum item.");
        }

    }

    //Refletindo dados
    public void setVeiculos(Veiculo veiculos) {
        this.v = veiculos;
    }

    public void setPrimaryController(PrimaryStageController controller) {
        this.primaryController = controller;
    }

    //Valores iniciais
    public void setVeloresIniciais() {

        lblKm.setText(Integer.toString(v.getQuilometragem()) + " Km");
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        lblData.setText(formato.format(data));
        cbTipo.getSelectionModel().select(0);
    }

}
