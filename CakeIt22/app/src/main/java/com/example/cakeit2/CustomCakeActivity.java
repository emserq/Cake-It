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

public class CustomCakeActivity extends AppCompatActivity {

    // Hidden textviews as variable
    private TextView selectedSize, selectedshape,selectedlayers, selectedstructure, selectedsponge, selectedfilling, selectedfrosting,
            selectedtoppings, selectedcandles, selectedmessage;

    private EditText candles, message;
    private RadioGroup size, shape, layers, structure, sponge, filling, frosting, toppings;
    private RadioButton size6, size10, size12,
                        circle, square, rectangle, star, heart, oval,
                        layer2, layer4, layer6, layer8, layer10, layer12,
                        pyramid, straight,
                        spongeVanilla, spongeChocolate, spongeUbe, spongeCoffee, spongeOrange, spongeBanana, spongeDarkChocolate,
                        spongeStrawberry, spongeCheese, spongeBlueberry, spongeMilkChocolate, spongePandan,
                        fillingVanilla, fillingChocolate, fillingUbe, fillingCoffee, fillingOrange, fillingBanana, fillingDarkChocolate,
                        fillingStrawberry, fillingCheese, fillingBlueberry, fillingMilkChocolate, fillingPandan,
                        frostingVanilla, frostingChocolate, frostingUbe, frostingCoffee, frostingOrange, frostingBanana, frostingDarkChocolate,
                        frostingStrawberry, frostingCheese, frostingBlueberry, frostingMilkChocolate, frostingPandan,
                        sprinkles, assortedfruits, powderedsugar, powderedmilk, cookiesncream, berrynnuts, chocolatecurls,flowericing;

    ImageView back;
    Button proceed;

    int Psize, Players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cake);
        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.mintyellow));
        }

        proceed = findViewById(R.id.btn_proceed);
        back = findViewById(R.id.btn_back);

        // Hidden textviews as variable
        selectedSize = findViewById(R.id.selected_size);
        selectedshape = findViewById(R.id.selected_shape);
        selectedlayers = findViewById(R.id.selected_layers);
        selectedstructure = findViewById(R.id.selected_structure);
        selectedsponge = findViewById(R.id.selected_sponge);
        selectedfilling = findViewById(R.id.selected_filling);
        selectedfrosting = findViewById(R.id.selected_frosting);
        selectedtoppings =findViewById(R.id.selected_toppings);
        selectedcandles = findViewById(R.id.selected_candles);
        selectedmessage = findViewById(R.id.selected_message);

        // Radio Group
        size = findViewById(R.id.rgSize);
        shape = findViewById(R.id.rgShape);
        layers = findViewById(R.id.rgLayers);
        structure = findViewById(R.id.rgStructure);
        sponge = findViewById(R.id.rgSpongeFlavor);
        filling = findViewById(R.id.rgFillingFlavor);
        frosting = findViewById(R.id.rgFrostingFlavor);
        toppings = findViewById(R.id.rgToppings);

        // Size Radio Button
        size6 = findViewById(R.id.rbSize6);
        size10 = findViewById(R.id.rbSize10);
        size12 = findViewById(R.id.rbSize12);

        // Shape Radio Button
        circle = findViewById(R.id.circle);
        square = findViewById(R.id.square);
        rectangle = findViewById(R.id.rectangle);
        star = findViewById(R.id.star);
        heart = findViewById(R.id.heart);
        oval = findViewById(R.id.oval);

        // Layer Radio Button
        layer2 = findViewById(R.id.rb2);
        layer4 = findViewById(R.id.rb4);
        layer6 = findViewById(R.id.rb6);
        layer8 = findViewById(R.id.rb8);
        layer10 = findViewById(R.id.rb10);
        layer12 = findViewById(R.id.rb12);

        // Structure Radio Button
        pyramid = findViewById(R.id.rbPyramid);
        straight = findViewById(R.id.rbStraight);

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
        message = findViewById(R.id.etMessageonCake);


        //-------------------------- FUNCTIONS ----------------------------//

        size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbSize6:
                        String selected_size6 = size6.getText().toString();
                        selectedSize.setText(selected_size6);
                        Psize = 400;
                        break;
                    case R.id.rbSize10:
                        String selected_size10 = size10.getText().toString();
                        selectedSize.setText(selected_size10);
                        Psize = 500;
                        break;
                    case R.id.rbSize12:
                        String selected_size12 = size12.getText().toString();
                        selectedSize.setText(selected_size12);
                        Psize = 600;
                        break;
                }
            }
        });

        shape.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.circle:
                        String selected_circle = circle.getText().toString();
                        selectedshape.setText(selected_circle);
                        break;
                    case R.id.square:
                        String selected_square = square.getText().toString();
                        selectedshape.setText(selected_square);
                        break;
                    case R.id.rectangle:
                        String selected_rectangle = rectangle.getText().toString();
                        selectedshape.setText(selected_rectangle);
                        break;
                    case R.id.star:
                        String selected_star = star.getText().toString();
                        selectedshape.setText(selected_star);
                        break;
                    case R.id.heart:
                        String selected_heart = heart.getText().toString();
                        selectedshape.setText(selected_heart);
                        break;
                    case R.id.oval:
                        String selected_oval = oval.getText().toString();
                        selectedshape.setText(selected_oval);
                        break;
                }

            }
        });

        layers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rb2:
                        String selected_layer2 = layer2.getText().toString();
                        selectedlayers.setText(selected_layer2);
                        Players = 0;
                        break;
                    case R.id.rb4:
                        String selected_layer4 = layer4.getText().toString();
                        selectedlayers.setText(selected_layer4);
                        Players = 100;
                        break;
                    case R.id.rb6:
                        String selected_layer6 = layer6.getText().toString();
                        selectedlayers.setText(selected_layer6);
                        Players = 300;
                        break;
                    case R.id.rb8:
                        String selected_layer8 = layer8.getText().toString();
                        selectedlayers.setText(selected_layer8);
                        Players = 500;
                        break;
                    case R.id.rb10:
                        String selected_layer10 = layer10.getText().toString();
                        selectedlayers.setText(selected_layer10);
                        Players = 700;
                        break;
                    case R.id.rb12:
                        String selected_layer12 = layer12.getText().toString();
                        selectedlayers.setText(selected_layer12);
                        Players = 1000;
                        break;
                }
            }
        });

        structure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbPyramid:
                        String selected_Pyramid = pyramid.getText().toString();
                        selectedstructure.setText(selected_Pyramid);
                        break;
                    case R.id.rbStraight:
                        String selected_Straight = straight.getText().toString();
                        selectedstructure.setText(selected_Straight);
                        break;
                }

            }
        });

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
                        break;
                    case R.id.rbAssortedFruits:
                        String top1 = assortedfruits.getText().toString();
                        selectedtoppings.setText(top1);
                        break;
                    case R.id.rbPowderedSugar:
                        String top2 = powderedsugar.getText().toString();
                        selectedtoppings.setText(top2);
                        break;
                    case R.id.rbPowderedMilk:
                        String top3 = powderedmilk.getText().toString();
                        selectedtoppings.setText(top3);
                        break;
                    case R.id.rbCookiesnCream:
                        String top4 = cookiesncream.getText().toString();
                        selectedtoppings.setText(top4);
                        break;
                    case R.id.rbBerrynNuts:
                        String top5 = berrynnuts.getText().toString();
                        selectedtoppings.setText(top5);
                        break;
                    case R.id.rbChocolateCurls:
                        String top6 = chocolatecurls.getText().toString();
                        selectedtoppings.setText(top6);
                        break;
                    case R.id.rbFlowerIcing:
                        String top7 = flowericing.getText().toString();
                        selectedtoppings.setText(top7);
                        break;
                }

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Sum of the Psize and Players
                int total = Psize + Players;
                String stotal = String.valueOf(total);

                Intent intent = new Intent(CustomCakeActivity.this,ConfirmationCakeActivity.class);
                intent.putExtra("selectedSize", selectedSize.getText().toString());
                intent.putExtra("selectedShape", selectedshape.getText().toString());
                intent.putExtra("selectedLayers", selectedlayers.getText().toString());
                intent.putExtra("selectedStructure", selectedstructure.getText().toString());
                intent.putExtra("selectedSponge", selectedsponge.getText().toString());
                intent.putExtra("selectedFilling", selectedfilling.getText().toString());
                intent.putExtra("selectedFrosting", selectedfrosting.getText().toString());
                intent.putExtra("selectedToppings", selectedtoppings.getText().toString());
                intent.putExtra("selectedCandles", candles.getText().toString());
                intent.putExtra("selectedMessage", message.getText().toString());
                intent.putExtra("total", stotal);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomCakeActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

    }
}