package manutencao.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manutencao.model.Dano;
import manutencao.model.Veiculo;
import manutencao.model.ManipuladorDeObjetos;
import manutencao.view.Alerta;

//Implementa interface obrigatória Initializable do javaFX para controllers
public class DanosAddController implements Initializable {


    @FXML
    private TextField tfLocal;

    @FXML
    private TextField tfDesc;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdicionar;

    @FXML
    private ChoiceBox<String> cbPrioridade;
    
    //Refletindo os dados
    private Veiculo v;
    private PrimaryStageController primaryController;

    //Sobrescrevendo o método initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Valores Iniciais
        cbPrioridade.getSelectionModel().select(0);

    }

    @FXML
    public void sair() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void add() {

        if (tfLocal.getText().isEmpty()) {

            //Alerta: O campo "Local" deve ser preenchido
            Alerta.atencao("O campo \"Local\" é obrigatório.");

        } else {

            String prioridade = cbPrioridade.getValue();
            String local = tfLocal.getText();
            String descricao = tfDesc.getText();

            ArrayList<Dano> listDanos = v.getDanos();

            if (v.getDanos() != null) {

                listDanos.add(new Dano(prioridade, local, descricao));
                this.v.setDanos(listDanos);
                ManipuladorDeObjetos.write(v);

            } else {

                listDanos = new ArrayList<>();
                listDanos.add(new Dano(prioridade, local, descricao));
                this.v.setDanos(listDanos);
                ManipuladorDeObjetos.write(v);

            }

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
            primaryController.bindData(v);
        }

    }

    //Refletindo os dados
    public void setVeiculos(Veiculo veiculos) {
        this.v = veiculos;
    }
    
    public void setPrimaryController(PrimaryStageController controller) {
        this.primaryController = controller;
    }
}
