package com.example.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    String price = "";
    int basePrice = 5, priceWhipped = 1, priceChocolate = 2;
    int priceNumber = 0;
    boolean isWhipped = false;
    boolean isChoco = false;
    long orderno=0;

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.isWhipped:
                if (checked) {
                    isWhipped = true;
                    price = "$" + (calculatePrice(quantity));
                    displayPrice(price);
                } else {
                    isWhipped = false;
                    price = "$" + (calculatePrice(quantity));
                    displayPrice(price);
                }
                break;
            case R.id.isChocolate:
                if (checked) {
                    isChoco = true;
                    price = "$" + (calculatePrice(quantity));
                    displayPrice(price);
                } else {
                    isChoco = false;
                    price = "$" + (calculatePrice(quantity));
                    displayPrice(price);
                }
                break;
        }
    }

    public int calculatePrice(int q) {
        int priceOne = basePrice;
        if (isWhipped) priceOne += +priceWhipped;
        if (isChoco) priceOne += priceChocolate;
        priceNumber = q * priceOne;
        return priceNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name;
        EditText nameInput=(EditText)findViewById(R.id.name_edit_text);
        name=nameInput.getEditableText().toString();
//        displayPrice("Name: "+name+"\nIsWhipped?" +isWhipped+ "\nIsChoco?"+isChoco+"\n"+price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:helloaymaanxyz@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Coffee Order no"+orderno);
        intent.putExtra(Intent.EXTRA_TEXT, "Name: "+name+"\nIsWhipped?" +isWhipped+ "\nIsChoco?"+isChoco+"\n"+price);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(String s1) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);
        if (price.equals("Invalid")) {
            orderSummaryTextView.setTextColor(Color.RED);
            orderSummaryTextView.setText(s1);
        } else {
            orderSummaryTextView.setTextColor(Color.BLACK);
            orderSummaryTextView.setText(s1);
        }
    }

    public void increment(View view) {
        quantity += 1;
        price = "$" + (calculatePrice(quantity));
        display(quantity);
        displayPrice(price);
    }

    public void decrement(View view) {
        quantity -= 1;
        price = "$" + (calculatePrice(quantity));
        if (quantity < 0) {
            Toast.makeText(this, "Cannot have less than 0 coffees!", Toast.LENGTH_SHORT).show();
            price = "Invalid";
            quantity = 0;
        }
        display(quantity);
        displayPrice(price);
    }

}
