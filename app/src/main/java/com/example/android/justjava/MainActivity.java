package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.R.attr.order;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamcheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhippedCream = whippedCreamcheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        Boolean hasChocolate = chocolateCheckbox.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name_text = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream,hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name_text);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for" + name_text);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }




    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num1) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num1);
    }

    /**
     * This method displays the given price on the screen.
     */
    public void increment(View view) {
        if (quantity==100) {
            return;
        }
        else{

            quantity = quantity + 1;
            displayQuantity(quantity);
        }


    }

    public void decrement(View view) {

        if (quantity==1) {
            return;
        }
        else{
            quantity=quantity-1;
            displayQuantity(quantity);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate) {

        int baseprice=5;
        if (hasWhippedCream){

             baseprice+=1;
        }
        if (hasChocolate){
            baseprice+=2;
        }
        int price;
        price=baseprice*quantity;
        return price;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameText) {

        String priceMessage = getString(R.string.order_summary_name,nameText);
        priceMessage += "\n"+getString(R.string.Flavour1_question) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.Flavour2_question) + addChocolate;
        priceMessage += "\n" + getString(R.string.quantity_text) + quantity;
        priceMessage += "\n" + getString(R.string.Total_Text) + price;
        priceMessage += "\n" + getString(R.string.Thank_You_Text);
        return priceMessage;
    }

}