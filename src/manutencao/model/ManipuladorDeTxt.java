package manutencao.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import manutencao.view.Alerta;
import org.joda.time.DateTime;

public class ManipuladorDeTxt {

    public static String getDiasRevisao() {

        try {

            File txtDias = new File("src/manutencao/files/intervaloDias.txt");
            FileReader fr = new FileReader(txtDias);
            BufferedReader bf = new BufferedReader(fr);
            return bf.readLine();

        } catch (FileNotFoundException ex) {
            Alerta.erro(ex);
        } catch (IOException ex) {
            Alerta.erro(ex);
        }
        return "";
    }

    public static void setDiasRevisao(int diasRevisao) {

        try {
            File txt = new File("src/manutencao/files/intervaloDias.txt");
            FileWriter fw = new FileWriter(txt);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(diasRevisao));
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Alerta.erro(ex);
        }

    }

    public static int getIntervaloRevisao() {

        try {

            File txtDias = new File("src/manutencao/files/intervaloRevisao.txt");
            FileReader fr = new FileReader(txtDias);
            BufferedReader bf = new BufferedReader(fr);
            return Integer.parseInt(bf.readLine());

        } catch (FileNotFoundException ex) {
            Alerta.erro(ex);
        } catch (IOException ex) {
            Alerta.erro(ex);
        }

        return 2;
    }

    public static void setIntervaloRevisao(int intervaloRevisao) {

            try {
                File txt = new File("src/manutencao/files/intervaloRevisao.txt");
                FileWriter fw = new FileWriter(txt);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(Integer.toString(intervaloRevisao));
                bw.flush();
                bw.close();
            } catch (IOException ex) {
                Alerta.erro(ex);
            }

    }

    public static String getQuilometrosRevisao() {

        try {

            File txtKm = new File("src/manutencao/files/intervaloQuilometros.txt");
            FileReader fr = new FileReader(txtKm);
            BufferedReader bf = new BufferedReader(fr);
            return bf.readLine();

        } catch (FileNotFoundException ex) {
            Alerta.erro(ex);
        } catch (IOException ex) {
            Alerta.erro(ex);
        }
        return "";

    }

    public static void setQuilometrosRevisao(int quilometrosRevisao) {

        try {
            File txt = new File("src/manutencao/files/intervaloQuilometros.txt");
            FileWriter fw = new FileWriter(txt);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(quilometrosRevisao));
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Alerta.erro(ex);
        }

    }

    public static String getProximaRevisao(Veiculo v) {

        //Carrega as Manutencoes do veiculo
        ArrayList<Manutencao> manutencoes = v.getManutencao();

        //Se houver manutencoes
        if (!manutencoes.isEmpty()) {

            //Se o intervalo de revisão for por dias
            if (ManipuladorDeTxt.getIntervaloRevisao() == 1) {

                try {

                    //Cria uma data
                    Date data = new Date();

                    data = manutencoes.get(manutencoes.size() - 1).getData();

                    //Define o formato
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                    //Formata e Calcula os dias para a próxima Revisão
                    data = df.parse(df.format(data));

                    DateTime jodaTime = new DateTime(data);
                    DateTime nDias = jodaTime.plusDays(Integer.parseInt(ManipuladorDeTxt.getDiasRevisao()));
                    Date proxRevisao = nDias.toDate();

                    //Converte a Date em String
                    String dataString = df.format(proxRevisao);

                    if (jodaTime.isEqualNow()) {
                    }

                    //Retorna a data em String
                    return dataString;

                } catch (ParseException ex) {
                    Alerta.erro(ex);
                }

            } else { //Senão (por Quilometros)

                int quilometragem = manutencoes.get(manutencoes.size() - 1).getQuilometragem();
                return Integer.toString(quilometragem + Integer.parseInt(ManipuladorDeTxt.getQuilometrosRevisao())) + " Km";

            }

        }
        return "";
    }

    public static DateTime getProximaRevisaoDateTime(Veiculo v) {

        //Carrega as Manutencoes do veiculo
        ArrayList<Manutencao> manutencoes = v.getManutencao();

        //Se houver manutencoes
        if (!manutencoes.isEmpty()) {

            //Se o intervalo de revisão for por dias
            if (ManipuladorDeTxt.getIntervaloRevisao() == 1) {

                try {

                    //Cria uma data
                    Date data = new Date();

                    data = manutencoes.get(manutencoes.size() - 1).getData();

                    //Define o formato
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                    //Formata e Calcula os dias para a próxima Revisão
                    data = df.parse(df.format(data));

                    DateTime jodaTime = new DateTime(data);
                    DateTime nDias = jodaTime.plusDays(Integer.parseInt(ManipuladorDeTxt.getDiasRevisao()));
                    Date proxRevisao = nDias.toDate();

                    //Converte a Date em String
                    String dataString = df.format(proxRevisao);

                    //Retorna a data em String
                    return nDias;

                } catch (ParseException ex) {
                    Alerta.erro(ex);
                }
            }
        }
        return new DateTime();
    }
    
    public static int getProximaRevisaoInteger(Veiculo v) {

        //Carrega as Manutencoes do veiculo
        ArrayList<Manutencao> manutencoes = v.getManutencao();

        //Se houver manutencoes
        if (!manutencoes.isEmpty()) {

            //Se o intervalo de revisão for por dias
            if (ManipuladorDeTxt.getIntervaloRevisao() == 0) {

                int quilometragem = manutencoes.get(manutencoes.size() - 1).getQuilometragem();
                return quilometragem + Integer.parseInt(ManipuladorDeTxt.getQuilometrosRevisao());
            }
        }
        return 0;
    }

    public static Date getUltimaRevisaoData(Veiculo v) {
        ArrayList<Manutencao> manutencoes = v.getManutencao();
        if (!manutencoes.isEmpty()) {
            return manutencoes.get(manutencoes.size() - 1).getData();
        } else {
            return new Date();
        }

    }

    public static int getUltimaRevisaoKm(Veiculo v) {
        ArrayList<Manutencao> manutencoes = v.getManutencao();
        if (!manutencoes.isEmpty()) {
            return manutencoes.get(manutencoes.size() - 1).getQuilometragem();
        } else {
            return 0;
        }

    }

    public static String getUltimaRevisao(Veiculo v) {
        ArrayList<Manutencao> manutencoes = v.getManutencao();

        //Se houver manutencoes
        if (!manutencoes.isEmpty()) {

            //Se o intervalo de revisão for por dias
            if (ManipuladorDeTxt.getIntervaloRevisao() == 1) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date data = manutencoes.get(manutencoes.size() - 1).getData();
                return df.format(data);

            } else { //Senão (por Quilometros)

                return Integer.toString(manutencoes.get(manutencoes.size() - 1).getQuilometragem()) + " Km";

            }

        }

        return "";
    }

}
