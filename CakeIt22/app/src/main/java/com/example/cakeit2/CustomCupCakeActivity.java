package com.example.cakeit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CustomCupCakeActivity extends AppCompatActivity {

    // Hidden textviews as variable
    private TextView selectedsponge, selectedfilling, selectedfrosting,
            selectedtoppings, selectedcandles;

    private EditText candles;
    private RadioGroup sponge, filling, frosting, toppings;
    private RadioButton spongeVanilla, spongeChocolate, spongeUbe, spongeCoffee, spongeOrange, spongeBanana, spongeDarkChocolate,
                        spongeStrawberry, spongeCheese, spongeBlueberry, spongeMilkChocolate, spongePandan,
                        fillingVanilla, fillingChocolate, fillingUbe, fillingCoffee, fillingOrange, fillingBanana, fillingDarkChocolate,
                        fillingStrawberry, fillingCheese, fillingBlueberry, fillingMilkChocolate, fillingPandan,
                        frostingVanilla, frostingChocolate, frostingUbe, frostingCoffee, frostingOrange, frostingBanana, frostingDarkChocolate,
                        frostingStrawberry, frostingCheese, frostingBlueberry, frostingMilkChocolate, frostingPandan,
                        sprinkles, assortedfruits, powderedsugar, powderedmilk, cookiesncream, berrynnuts, chocolatecurls,flowericing;

    ImageView back;
    Button proceed;

    int Ptoppings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cup_cake);
        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.mintgreen));
        }

        proceed = findViewById(R.id.btn_proceed);
        back = findViewById(R.id.btn_back);

        // Hidden textviews as variable
        selectedsponge = findViewById(R.id.selected_sponge);
        selectedfilling = findViewById(R.id.selected_filling);
        selectedfrosting = findViewById(R.id.selected_frosting);
        selectedtoppings =findViewById(R.id.selected_toppings);
        selectedcandles = findViewById(R.id.selected_candles);

        // Radio Group
        sponge = findViewById(R.id.rgSpongeFlavor);
        filling = findViewById(R.id.rgFillingFlavor);
        frosting = findViewById(R.id.rgFrostingFlavor);
        toppings = findViewById(R.id.rgToppings);

        // Sponge flavor Radio Button
        spongeVanilla = findViewById(R.id.rbSpongeVanilla);
        spongeChocolate = findViewById(R.id.rbSpongeChocolate);
        spongeUbe = findViewById(R.id.rbSpongeUbe);
        spongeCoffee = findViewById(R.id.rbSpongeCoffee);
        spongeMilkChocolate = findViewById(R.id.rbSpongeMilkChocolate);
        spongePandan = findViewById(R.id.rbSpongePandan);
        spongeOrange = findViewById(R.id.rbSpongeOrange);
        spongeBanana = findViewById(R.id.rbSpongeBanana);
        spongeDarkChocolate = findViewById(R.id.rbSpongeDarkChocolate);
        spongeStrawberry = findViewById(R.id.rbSpongeStrawberry);
        spongeCheese = findViewById(R.id.rbSpongeCheese);
        spongeBlueberry = findViewById(R.id.rbSpongeBlueberry);

        // Filling flavor Radio Button
        fillingVanilla = findViewById(R.id.rbFillingVanilla);
        fillingChocolate = findViewById(R.id.rbFillingChocolate);
        fillingUbe = findViewById(R.id.rbFillingUbe);
        fillingCoffee = findViewById(R.id.rbFillingCoffee);
        fillingMilkChocolate = findViewById(R.id.rbFillingMilkChocolate);
        fillingPandan = findViewById(R.id.rbFillingPandan);
        fillingOrange = findViewById(R.id.rbFillingOrange);
        fillingBanana = findViewById(R.id.rbFillingBanana);
        fillingDarkChocolate = findViewById(R.id.rbFillingDarkChocolate);
        fillingStrawberry = findViewById(R.id.rbFillingStrawberry);
        fillingCheese = findViewById(R.id.rbFillingCheese);
        fillingBlueberry = findViewById(R.id.rbFillingBlueberry);

        // Frosting flavor Radio Button
        frostingVanilla = findViewById(R.id.rbFrostingVanilla);
        frostingChocolate = findViewById(R.id.rbFrostingChocolate);
        frostingUbe = findViewById(R.id.rbFrostingUbe);
        frostingCoffee = findViewById(R.id.rbFrostingCoffee);
        frostingMilkChocolate = findViewById(R.id.rbFrostingMilkChocolate);
        frostingPandan = findViewById(R.id.rbFrostingPandan);
        frostingOrange = findViewById(R.id.rbFrostingOrange);
        frostingBanana = findViewById(R.id.rbFrostingBanana);
        frostingDarkChocolate = findViewById(R.id.rbFrostingDarkChocolate);
        frostingStrawberry = findViewById(R.id.rbFrostingStrawberry);
        frostingCheese = findViewById(R.id.rbFrostingCheese);
        frostingBlueberry = findViewById(R.id.rbFrostingBlueberry);

        // Toppings Radio Button
        sprinkles = findViewById(R.id.rbSprinkles);
        assortedfruits = findViewById(R.id.rbAssortedFruits);
        powderedsugar = findViewById(R.id.rbPowderedSugar);
        powderedmilk = findViewById(R.id.rbPowderedMilk);
        cookiesncream = findViewById(R.id.rbCookiesnCream);
        berrynnuts = findViewById(R.id.rbBerrynNuts);
        chocolatecurls = findViewById(R.id.rbChocolateCurls);
        flowericing = findViewById(R.id.rbFlowerIcing);

        // EditTexts Inputs
        candles =findViewById(R.id.etCandle);


        //-------------------------- FUNCTIONS ----------------------------//

        sponge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbSpongeVanilla:
                        String selected_spongeVanilla = spongeVanilla.getText().toString();
                        selectedsponge.setText(selected_spongeVanilla);
                        break;
                    case R.id.rbSpongeChocolate:
                        String selected_spongeChocolate = spongeChocolate.getText().toString();
                        selectedsponge.setText(selected_spongeChocolate);
                        break;
                    case R.id.rbSpongeUbe:
                        String selected_spongeUbe = spongeUbe.getText().toString();
                        selectedsponge.setText(selected_spongeUbe);
                        break;
                    case R.id.rbSpongeCoffee:
                        String selected_spongeCoffee = spongeCoffee.getText().toString();
                        selectedsponge.setText(selected_spongeCoffee);
                        break;
                    case R.id.rbSpongeMilkChocolate:
                        String selected_spongeMilkChocolate = spongeMilkChocolate.getText().toString();
                        selectedsponge.setText(selected_spongeMilkChocolate);
                        break;
                    case R.id.rbSpongePandan:
                        String selected_spongePandan = spongePandan.getText().toString();
                        selectedsponge.setText(selected_spongePandan);
                        break;
                    case R.id.rbSpongeOrange:
                        String selected_spongeOrange = spongeOrange.getText().toString();
                        selectedsponge.setText(selected_spongeOrange);
                        break;
                    case R.id.rbSpongeBanana:
                        String selected_spongeBanana = spongeBanana.getText().toString();
                        selectedsponge.setText(selected_spongeBanana);
                        break;
                    case R.id.rbSpongeDarkChocolate:
                        String selected_spongeDarkChocolate = spongeDarkChocolate.getText().toString();
                        selectedsponge.setText(selected_spongeDarkChocolate);
                        break;
                    case R.id.rbSpongeStrawberry:
                        String selected_spongeStrawberry = spongeStrawberry.getText().toString();
                        selectedsponge.setText(selected_spongeStrawberry);
                        break;
                    case R.id.rbSpongeCheese:
                        String selected_spongeCheese = spongeCheese.getText().toString();
                        selectedsponge.setText(selected_spongeCheese);
                        break;
                    case R.id.rbSpongeBlueberry:
                        String selected_spongeBlueberry = spongeBlueberry.getText().toString();
                        selectedsponge.setText(selected_spongeBlueberry);
                        break;

                }
            }
        });

        filling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbFillingVanilla:
                        String selected_fillingVanilla = fillingVanilla.getText().toString();
                        selectedfilling.setText(selected_fillingVanilla);
                        break;
                    case R.id.rbFillingChocolate:
                        String selected_fillingChocolate = fillingChocolate.getText().toString();
                        selectedfilling.setText(selected_fillingChocolate);
                        break;
                    case R.id.rbFillingUbe:
                        String selected_fillingUbe = fillingUbe.getText().toString();
                        selectedfilling.setText(selected_fillingUbe);
                        break;
                    case R.id.rbFillingCoffee:
                        String selected_fillingCoffee = fillingCoffee.getText().toString();
                        selectedfilling.setText(selected_fillingCoffee);
                        break;
                    case R.id.rbFillingMilkChocolate:
                        String selected_fillingMilkChocolate = fillingMilkChocolate.getText().toString();
                        selectedfilling.setText(selected_fillingMilkChocolate);
                        break;
                    case R.id.rbFillingPandan:
                        String selected_fillingPandan = fillingPandan.getText().toString();
                        selectedfilling.setText(selected_fillingPandan);
                        break;
                    case R.id.rbFillingOrange:
                        String selected_fillingOrange = fillingOrange.getText().toString();
                        selectedfilling.setText(selected_fillingOrange);
                        break;
                    case R.id.rbFillingBanana:
                        String selected_fillingBanana = fillingBanana.getText().toString();
                        selectedfilling.setText(selected_fillingBanana);
                        break;
                    case R.id.rbFillingDarkChocolate:
                        String selected_fillingDarkChocolate = fillingDarkChocolate.getText().toString();
                        selectedfilling.setText(selected_fillingDarkChocolate);
                        break;
                    case R.id.rbFillingStrawberry:
                        String selected_fillingStrawberry = fillingStrawberry.getText().toString();
                        selectedfilling.setText(selected_fillingStrawberry);
                        break;
                    case R.id.rbFillingCheese:
                        String selected_fillingCheese = fillingCheese.getText().toString();
                        selectedfilling.setText(selected_fillingCheese);
                        break;
                    case R.id.rbFillingBlueberry:
                        String selected_fillingBlueberry = fillingBlueberry.getText().toString();
                        selectedfilling.setText(selected_fillingBlueberry);
                        break;

                }
            }
        });

        frosting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.rbFrostingVanilla:
                        String selected_frostingVanilla = frostingVanilla.getText().toString();
                        selectedfrosting.setText(selected_frostingVanilla);
                        break;
                    case R.id.rbFrostingChocolate:
                        String selected_frostingChocolate = frostingChocolate.getText().toString();
                        selectedfrosting.setText(selected_frostingChocolate);
                        break;
                    case R.id.rbFrostingUbe:
                        String selected_frostingUbe = frostingUbe.getText().toString();
                        selectedfrosting.setText(selected_frostingUbe);
                        break;
                    case R.id.rbFrostingCoffee:
                        String selected_frostingCoffee = frostingCoffee.getText().toString();
                        selectedfrosting.setText(selected_frostingCoffee);
                        break;
                    case R.id.rbFrostingMilkChocolate:
                        String selected_frostingMilkChocolate = frostingMilkChocolate.getText().toString();
                        selectedfrosting.setText(selected_frostingMilkChocolate);
                        break;
                    case R.id.rbFrostingPandan:
                        String selected_frostingPandan = frostingPandan.getText().toString();
                        selectedfrosting.setText(selected_frostingPandan);
                        break;
                    case R.id.rbFrostingOrange:
                        String selected_frostingOrange = frostingOrange.getText().toString();
                        selectedfrosting.setText(selected_frostingOrange);
                        break;
                    case R.id.rbFrostingBanana:
                        String selected_frostingBanana = frostingBanana.getText().toString();
                        selectedfrosting.setText(selected_frostingBanana);
                        break;
                    case R.id.rbFrostingDarkChocolate:
                        String selected_frostingDarkChocolate = frostingDarkChocolate.getText().toString();
                        selectedfrosting.setText(selected_frostingDarkChocolate);
                        break;
                    case R.id.rbFrostingStrawberry:
                        String selected_frostingStrawberry = frostingStrawberry.getText().toString();
                        selectedfrosting.setText(selected_frostingStrawberry);
                        break;
                    case R.id.rbFrostingCheese:
                        String selected_frostingCheese = frostingCheese.getText().toString();
                        selectedfrosting.setText(selected_frostingCheese);
                        break;
                    case R.id.rbFrostingBlueberry:
                        String selected_frostingBlueberry = frostingBlueberry.getText().toString();
                        selectedfrosting.setText(selected_frostingBlueberry);
                        break;

                }
            }
        });

        toppings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbSprinkles:
                        String top = sprinkles.getText().toString();
                        selectedtoppings.setText(top);
                        Ptoppings = 25;
                        break;
                    case R.id.rbAssortedFruits:
                        String top1 = assortedfruits.getText().toString();
                        selectedtoppings.setText(top1);
                        Ptoppings = 35;
                        break;
                    case R.id.rbPowderedSugar:
                        String top2 = powderedsugar.getText().toString();
                        selectedtoppings.setText(top2);
                        Ptoppings = 30;
                        break;
                    case R.id.rbPowderedMilk:
                        String top3 = powderedmilk.getText().toString();
                        selectedtoppings.setText(top3);
                        Ptoppings = 30;
                        break;
                    case R.id.rbCookiesnCream:
                        String top4 = cookiesncream.getText().toString();
                        selectedtoppings.setText(top4);
                        Ptoppings = 40;
                        break;
                    case R.id.rbBerrynNuts:
                        String top5 = berrynnuts.getText().toString();
                        selectedtoppings.setText(top5);
                        Ptoppings = 45;
                        break;
                    case R.id.rbChocolateCurls:
                        String top6 = chocolatecurls.getText().toString();
                        selectedtoppings.setText(top6);
                        Ptoppings = 45;
                        break;
                    case R.id.rbFlowerIcing:
                        String top7 = flowericing.getText().toString();
                        selectedtoppings.setText(top7);
                        Ptoppings = 35;
                        break;
                }

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Total
                String stotal = String.valueOf(Ptoppings);

                Intent intent = new Intent(CustomCupCakeActivity.this,ConfirmationCupCakeActivity.class);
                intent.putExtra("selectedSponge", selectedsponge.getText().toString());
                intent.putExtra("selectedFilling", selectedfilling.getText().toString());
                intent.putExtra("selectedFrosting", selectedfrosting.getText().toString());
                intent.putExtra("selectedToppings", selectedtoppings.getText().toString());
                intent.putExtra("selectedCandles", candles.getText().toString());
                intent.putExtra("total", stotal);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomCupCakeActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

    }
}