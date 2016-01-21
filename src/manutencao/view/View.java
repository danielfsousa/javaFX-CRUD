package manutencao.view;

import manutencao.controller.ManutencaoConfigController;
import manutencao.controller.PrimaryStageController;
import manutencao.model.Veiculo;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manutencao.controller.DanosAddController;
import manutencao.controller.ManutencaoAddController;

public class View extends Application {

    //Tela inicial
    @Override
    public void start(Stage primaryStage) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/PrimaryStage.fxml"));
            Parent root = (Parent) loader.load();

            PrimaryStageController primary = (PrimaryStageController) loader.getController();
            primary.setPrimaryController(primary);

            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStage.setTitle("Manutenção");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.setMinWidth(1255);
            primaryStage.setMinHeight(550);
            primaryStage.show();

        } catch (IOException ex) {
            Alerta.erro(ex);
        }
    }

    //Configurações da Manutenção
    public void Mconfig(Veiculo v, PrimaryStageController primaryController) throws IOException {
        Stage Pconfig = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/ManutencaoConfig.fxml"));
        Parent root2 = loader.load();
        ManutencaoConfigController controller = (ManutencaoConfigController) loader.getController();
        controller.setPrimaryController(primaryController);
        controller.setVeiculo(v);

        Scene scene2 = new Scene(root2, 394, 182);
        Pconfig.setTitle("Configurar Manutenção");
        Pconfig.setScene(scene2);
        Pconfig.setResizable(false);
        Pconfig.show();

    }

    //Adicionar Manutenção
    public void Madd(Veiculo v, PrimaryStageController primaryController) throws IOException {
        Stage Padd = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/ManutencaoAdd.fxml"));
        Parent root4 = (Parent) loader.load();
        ManutencaoAddController controller = (ManutencaoAddController) loader.getController();
        controller.setVeiculos(v);
        controller.setPrimaryController(primaryController);
        controller.setVeloresIniciais();
        
        Scene scene4 = new Scene(root4, 572, 372);
        Padd.setTitle("Adicionar Manutenção");
        Padd.setScene(scene4);
        Padd.setResizable(false);
        Padd.show();
    }
    
    //Adicionar Dano
    public void Dadd(Veiculo v, PrimaryStageController primaryController) throws IOException {
        Stage Dadd = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/DanosAdd.fxml"));
        
        Parent root3 = loader.load();
        DanosAddController controller = (DanosAddController) loader.getController();
        controller.setVeiculos(v);
        controller.setPrimaryController(primaryController);
        
        Scene scene3 = new Scene(root3, 330, 218);
        Dadd.setTitle("Adicionar Dano");
        Dadd.setScene(scene3);
        Dadd.setResizable(false);
        Dadd.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}
