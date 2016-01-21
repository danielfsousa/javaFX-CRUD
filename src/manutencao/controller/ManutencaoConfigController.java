package manutencao.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manutencao.model.ManipuladorDeTxt;
import manutencao.model.Veiculo;
import manutencao.view.Alerta;

//Implementa interface obrigatória Initializable do javaFX para controllers
public class ManutencaoConfigController implements Initializable {

    @FXML
    private RadioButton rdioDias;

    @FXML
    private RadioButton rdioQuilometros;

    @FXML
    private Label txtDias;

    @FXML
    private Label txtQuilometros;

    @FXML
    private TextField tfDias;

    @FXML
    private TextField tfQuilometros;

    @FXML
    private Button btnCancelar;

    //Refletindo os dados
    private Veiculo v;
    private PrimaryStageController primaryController;

    //Sobrescrevendo o método initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Binds para desabilitar campos
        txtDias.disableProperty().bind(rdioQuilometros.selectedProperty());
        tfDias.disableProperty().bind(rdioQuilometros.selectedProperty());
        txtQuilometros.disableProperty().bind(rdioDias.selectedProperty());
        tfQuilometros.disableProperty().bind(rdioDias.selectedProperty());

        //Valores iniciais
        tfDias.setText(ManipuladorDeTxt.getDiasRevisao());
        tfQuilometros.setText(ManipuladorDeTxt.getQuilometrosRevisao());

        if (ManipuladorDeTxt.getIntervaloRevisao() == 1) {
            rdioDias.setSelected(true);
        }

        if (ManipuladorDeTxt.getIntervaloRevisao() == 0) {
            rdioQuilometros.setSelected(true);
        }

    }

    @FXML
    public void sair() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void salvar() {

        //Carrega o stage atual
        Stage stage = (Stage) btnCancelar.getScene().getWindow();

        if (rdioDias.isSelected()) {

            //Se conter apenas números
            if (tfDias.getText().matches("\\d*")) {

                long dias = Long.parseLong(tfDias.getText());

                //Se o número for maior que 0 e menor que 99999
                if (dias > 0 && dias <= 99999) {

                    ManipuladorDeTxt.setIntervaloRevisao(1);
                    ManipuladorDeTxt.setDiasRevisao(Integer.parseInt(tfDias.getText()));
                    primaryController.bindData(v);
                    stage.close();

                } else {
                    //Alerta: Número de dias
                    Alerta.atencao("Você deve escolher entre 1 a 99999 dias.");
                }

            } else {
                //Alerta: Número de dias
                Alerta.atencao("Você deve escolher entre 1 a 99999 dias.");
            }
        }

        if (rdioQuilometros.isSelected()) {

            //Se conter apenas números
            if (tfQuilometros.getText().matches("\\d*")) {

                long km = Long.parseLong(tfQuilometros.getText());

                //Se o número for maior que 0 e menor que 99999
                if (km > 0 && km <= 99999) {

                    ManipuladorDeTxt.setIntervaloRevisao(0);
                    ManipuladorDeTxt.setQuilometrosRevisao(Integer.parseInt(tfQuilometros.getText()));
                    //Alerta: Alterações salvas
                    primaryController.bindData(v);
                    stage.close();

                } else {
                    //Alerta: Número de quilômetros
                    Alerta.atencao("Você deve escolher entre 1 a 99999999 quilômetros.");
                }

            } else {
                //Alerta: Número de quilômetros
                Alerta.atencao("Você deve escolher entre 1 a 99999999 quilômetros.");
            }

        }

    }

    //Refletindo os dados
    public void setPrimaryController(PrimaryStageController controller) {
        this.primaryController = controller;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.v = veiculo;
    }
}
