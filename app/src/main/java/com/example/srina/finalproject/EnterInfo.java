package com.example.srina.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class EnterInfo extends Fragment {

    EditText minCalories, maxCalories, minCarbs, maxCarbs, minFat, maxFat, minProtein, maxProtein, info, enterSavedName;
    RadioGroup mealGroup;
    ImageButton favorite;
    Button search, favoriteSavedButton;

    Activity mainActivity;
    View parentView, favoriteView;

    ArrayList<EditText> minUser = new ArrayList<>();
    ArrayList<EditText> maxUser = new ArrayList<>();
    ArrayList<Integer> minValues = new ArrayList<>();
    ArrayList<Integer> maxValues = new ArrayList<>();

    int mealType;
    ArrayList<String> description = new ArrayList<>();
    boolean done = false;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static final String FILE_NAME = "finalProject.txt";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = getActivity();
        parentView = inflater.inflate(R.layout.fragment_enter_info, container, false);

        minCalories = parentView.findViewById(R.id.caloriesMin);
        maxCalories = parentView.findViewById(R.id.caloriesMax);
        minCarbs = parentView.findViewById(R.id.carbsMin);
        maxCarbs = parentView.findViewById(R.id.carbsMax);
        minFat = parentView.findViewById(R.id.fatMin);
        maxFat = parentView.findViewById(R.id.fatMax);
        minProtein = parentView.findViewById(R.id.proteinMin);
        maxProtein = parentView.findViewById(R.id.proteinMax);
        mealGroup = parentView.findViewById(R.id.mealGroup);
        favorite = parentView.findViewById(R.id.saveButton);
        search = parentView.findViewById(R.id.searchButton);
        info = parentView.findViewById(R.id.description);

        favoriteView = getLayoutInflater().inflate(R.layout.dialog_enter_favorite, null);
        enterSavedName = favoriteView.findViewById(R.id.enterSavedName);
        favoriteSavedButton = favoriteView.findViewById(R.id.favoriteSaveButton);

        minUser.add(minCalories);
        minUser.add(minCarbs);
        minUser.add(minFat);
        minUser.add(minProtein);

        maxUser.add(maxCalories);
        maxUser.add(maxCarbs);
        maxUser.add(maxFat);
        maxUser.add(maxProtein);

        minCalories.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        minCarbs.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        minFat.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        minProtein.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        maxCalories.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        maxCarbs.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        maxFat.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        maxProtein.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addMealNumbers();
            }

            public void afterTextChanged(Editable editable) {

            }
        });

        done = false;

        if (mealType == 0) {
            search.setEnabled(false);
        }

        mealGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                search.setEnabled(true);
                if (i == R.id.breakfast)
                    mealType = 1;
                if (i == R.id.main)
                    mealType = 2;
                if (i == R.id.dessert)
                    mealType = 3;
            }
        });

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addMealInfo();
                addMealNumbers();
                Bundle searchBundle = new Bundle();
                searchBundle.putInt("mealType", mealType);
                searchBundle.putIntegerArrayList("minValues", minValues);
                searchBundle.putIntegerArrayList("maxValues", maxValues);
                searchBundle.putStringArrayList("description", description);
                Results results = new Results();
                results.setArguments(searchBundle);
                fragmentTransaction.replace(R.id.constraintLayout, results);
                fragmentTransaction.commit();
            }
        });


        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!(minCalories.equals("") || minCarbs.equals("") || minFat.equals("") || minProtein.equals("") || maxCalories.equals("") || maxCarbs.equals("") || maxProtein.equals("") || maxFat.equals("") || description.equals("") || mealType == 0)) {
                    AlertDialog.Builder favoriteBuilder = new AlertDialog.Builder(getContext());
                    favoriteBuilder.setView(favoriteView);
                    final AlertDialog dialog = favoriteBuilder.create();
                    dialog.show();
                    done = true;
                    favoriteSavedButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                            save(parentView);
                        }
                    });
                }
                if (done == true){
                    favorite.setEnabled(false);
                }

            }
        });

        return parentView;
    }


    public boolean editTextEmpty(EditText editText) {
        if (editText.getText().toString().length() > 0) {
            return false;
        }
        return true;
    }

    public void addMealInfo() {
        description = new ArrayList<>();
        if (editTextEmpty(info) == false) {
            description = new ArrayList<>(Arrays.asList(info.getText().toString().split(",")));
            Log.d("msg", "in true");
        } else if (mealType == 1) {
            description.add("pancake");
            description.add("waffle");
            description.add("muffin");
            description.add("hash brown");
            description.add("bagel");
            description.add("toast");
            description.add("granola");
            description.add("egg");
        } else if (mealType == 2) {
            description.add("burger");
            description.add("salad");
            description.add("pasta");
            description.add("pizza");
            description.add("quesadilla");
            description.add("taco");
            description.add("sub");
            description.add("soup");
        } else if (mealType == 3) {
            description.add("milkshake");
            description.add("ice cream");
            description.add("cake");
            description.add("pie");
            description.add("brownie");
            description.add("sorbet");
            description.add("custard");
            description.add("pudding");
        }
    }

    public void addMealNumbers() {
        maxValues = new ArrayList<>();
        minValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            boolean minEmpty = editTextEmpty(minUser.get(i));
            if (minEmpty == false && Integer.parseInt(minUser.get(i).getText().toString()) > 0) {
                int minimum = Integer.parseInt(minUser.get(i).getText().toString());
                minValues.add(minimum);
            } else {
                minValues.add(0);
            }
            boolean maxEmpty = editTextEmpty(maxUser.get(i));
            if (maxEmpty == false && Integer.parseInt(maxUser.get(i).getText().toString()) > 0) {
                int minimum = Integer.parseInt(maxUser.get(i).getText().toString());
                maxValues.add(minimum);
            } else {
                maxValues.add(5000);
            }
        }
    }

    public void save(View view) {
        String text = enterSavedName.getText().toString() + ",";
        for (int i = 0; i < minValues.size(); i++) {
            text += minValues.get(i) + ",";
        }
        for (int i = 0; i < maxValues.size(); i++) {
            text += maxValues.get(i) + ",";
        }
        text += mealType + "," + info.getText().toString() + ";";
        FileOutputStream fos = null;
        Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT);
        Log.d("msg", "Text in the Saved Method: " + text);
        try {
            fos = getActivity().openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
