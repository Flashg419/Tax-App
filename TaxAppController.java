
package taxapp;

import java.lang.Math;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.text.DecimalFormat;
import java.math.BigDecimal;

/**
 * Created on 14 Nov 18
 * @author Mark Glover
 */
public class TaxAppController implements Initializable
{
    final double deductibles[] = {12000.00, 24000.00};
    final double[][] standardRates = new double[][]
    {
        {9525, 19050, .1},
        {38700, 77400, .12},
        {82500, 165000, .22},
        {157500, 315000, .24},
        {200000, 400000, .32},
        {500000, 600000, .35}
    };
    
    double totalWages = 0.00;
    double fedTaxWH = 0.00;
    int filingStatus = 0;
    double remBal = 0.00;
    double standardRate = 0.0;
    double deductible = 0.00;
    double taxableIncome = 0.00;
    double totalTaxOwed = 0.00;
    DecimalFormat pf = new DecimalFormat("#.00");
    
    //check value vaiables
    boolean validIncome = false;
    boolean validTaxWH = false;
    
    @FXML 
    private Button button;
    
    @FXML
    private Label refundLabel;
    
    @FXML
    private Label oweLabel;
    
    @FXML 
    private Label owe;
    
    @FXML
    private Label refund;
    
    @FXML
    public ComboBox statusCombobox;
    
    @FXML 
    public TextField income;
    
    @FXML
    public TextField fedTaxWithheld;
  
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {        
        filingStatus = statusCombobox.getSelectionModel().getSelectedIndex();
        totalWages = Double.parseDouble(income.getText());
        for(int i = 0; i < 6; ++i)
        {
            if(standardRates[i][filingStatus] >= totalWages)
            {
                standardRate = standardRates[i][2];
                i = 6;
            }
        }
        deductible = deductibles[filingStatus];
        taxableIncome = totalWages - deductible;
        if(taxableIncome < 0)
            taxableIncome = 0;
        totalTaxOwed = taxableIncome * standardRate;
        fedTaxWH = (Double.parseDouble(fedTaxWithheld.getText()) * 12);
        remBal = totalTaxOwed - fedTaxWH;
        
        if(remBal <= 0.0)
        {
            remBal = Math.abs(remBal);
            oweLabel.setVisible(false);
            owe.setVisible(false);
            refund.setText("$" + (pf.format(remBal)));
            refundLabel.setVisible(true);
            refund.setVisible(true);
        }
        else
        {
           refundLabel.setVisible(false);
           refund.setVisible(false);
           owe.setText("$" + pf.format(remBal));
           oweLabel.setVisible(true);
           owe.setVisible(true);
        }
        
        System.out.print("Total Wages: " + totalWages + "\ndeductible:" + deductible + "\nTax Withheld: " + fedTaxWH + "\nRemaining balance: " + remBal + 
                "\nTotal tax owed: " + totalTaxOwed + "\nStandard rate: " + standardRate); //used for testing
    }
    
    @FXML                                                 
    private void handleIncomeAction(KeyEvent event)     
    {   
        validIncome = true; //initiate valid income test
        for (int i=0; i< income.getText().length(); ++i)
            if (!Character.isDigit(income.getText().charAt(i)))
                if (income.getText().charAt(i) != '.')               
                {
                    validIncome = false;
                    button.setDisable(true);
                }
        if (income.getText().isEmpty())
        {
            validIncome = false;
            button.setDisable(true);
        }
        checkInputs();
    }
    
    @FXML
    private void handleFedTaxAction(KeyEvent event)
    {
        validTaxWH = true; //initialize valid TaxWH test
        for (int i=0; i < fedTaxWithheld.getText().length(); ++i)
            if (!Character.isDigit(fedTaxWithheld.getText().charAt(i)))
                if (fedTaxWithheld.getText().charAt(i) != '.')
                {
                    validTaxWH = false;
                    button.setDisable(true);
                }
        if ((fedTaxWithheld.getText().isEmpty()))
        {
            validTaxWH = false;
            button.setDisable(true);
        }
        checkInputs();
    }
    
    private void checkInputs()
    {
        System.out.println("checkInputs called\n" + validIncome + validTaxWH);
        if(validIncome == true && validTaxWH == true)
            button.setDisable(false);
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       refundLabel.setVisible(false);
       oweLabel.setVisible(false);
       statusCombobox.setValue("Single");
    }    
    
}
