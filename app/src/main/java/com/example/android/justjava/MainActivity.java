package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent =new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this,"You cannot hac more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
            quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 0) {
            Toast.makeText(this,"You cannot hac less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
            quantity--;
        displayQuantity(quantity);
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;
        if (addWhippedCream) {
            price += 1;
        }else if (addChocolate) {
            price += 2;
        }
        return quantity * price;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name
                + "\nAdd Whipped Cream? " + addWhippedCream
                + "\nAdd Chocolate? " + addChocolate
                + "\nQuantity: " + quantity
                + "\nTotal: $" + price
                + "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
