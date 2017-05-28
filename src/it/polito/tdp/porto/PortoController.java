package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	model.caricaInformazioni();
    	model.creaGrafo();
    	Author autoreSelezionato = boxPrimo.getValue();
    	String result =  model.getCoautori(autoreSelezionato);
    	txtResult.setText(result);
    	boxSecondo.getItems().addAll(model.getNonCoautori());
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	Author autorePartenza = boxPrimo.getValue();
    	Author autoreArrivo = boxSecondo.getValue();
    	model.calcolaSequenza(autorePartenza, autoreArrivo);
    	String sequenza = model.getSequenzaArticoli();
    	txtResult.setText(sequenza);

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model m) {
		this.model= m;
		boxPrimo.getItems().addAll(model.getAutori().values());
		
	}
}
