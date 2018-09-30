package com.example.srina.finalproject;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Favorites extends Fragment {

    private static final String FILE_NAME = "finalProject.txt";
    View parentView;
    TextView calories, carbs, fat, protein, meal, description;
    ListView listView;
    ArrayList<favoriteSet> favArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_favorites, container, false);
        calories = parentView.findViewById(R.id.calories);
        carbs = parentView.findViewById(R.id.carbs);
        fat = parentView.findViewById(R.id.fat);
        protein = parentView.findViewById(R.id.protein);
        meal = parentView.findViewById(R.id.meal);
        description = parentView.findViewById(R.id.description);
        listView = parentView.findViewById(R.id.listView);
        favArray = load(parentView);
        CustomAdapter adapter = new CustomAdapter(getContext(), R.layout.list_layout, favArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                calories.setVisibility(View.VISIBLE);
                carbs.setVisibility(View.VISIBLE);
                fat.setVisibility(View.VISIBLE);
                protein.setVisibility(View.VISIBLE);
                meal.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                calories.setText("Calories: " + favArray.get(i).getMinCalories() + " (Min) " + favArray.get(i).getMaxCalories() + " (Max)");
                carbs.setText("Carbohydrates: " + favArray.get(i).getMinCarbs() + " (Min) " + favArray.get(i).getMaxCarbs() + " (Max)");
                fat.setText("Fat: " + favArray.get(i).getMinFat() + " (Min) " + favArray.get(i).getMaxFat() + " (Max)");
                protein.setText("Protein: " + favArray.get(i).getMinProtein() + " (Min) " + favArray.get(i).getMaxProtein() + " (Max)");
                meal.setText("Meal: " + favArray.get(i).getMealValue());
                description.setText("Description: " + favArray.get(i).getDescription());
            }
        });
        return parentView;

    }

    public ArrayList<favoriteSet> load(View view) {
        ArrayList<favoriteSet> arrayList = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String textString = sb.toString();
            String[] fileArray = textString.split(";");
            Log.d("msg", "FileArray length: " + fileArray.length);
            for (int i = 0; i < fileArray.length; i++) {
                Log.d("msg", "Filearray element: " + fileArray[i]);
                String[] setSplit = fileArray[i].split(",");
                Log.d("msg", "SetSplit length: " + setSplit.length + "");
                if (setSplit.length == 11) {
                    favoriteSet setData = new favoriteSet(setSplit[0], setSplit[1], setSplit[2], setSplit[3], setSplit[4], setSplit[5], setSplit[6], setSplit[7], setSplit[8], setSplit[9], setSplit[10]);
                    arrayList.add(setData);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    public class CustomAdapter extends ArrayAdapter<favoriteSet> {

        Context context;
        List<favoriteSet> list;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        ArrayList<Integer> minValues = new ArrayList<>();
        ArrayList<Integer> maxValues = new ArrayList<>();
        ArrayList<String> descriptionList = new ArrayList<>();

        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<favoriteSet> objects) {
            super(context, resource, objects);
            this.context = context;
            list = objects;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = inflater.inflate(R.layout.list_layout, null);

            TextView savedName = adapterView.findViewById(R.id.savedName);
            Button savedApply = adapterView.findViewById(R.id.savedApply);

            savedName.setText(list.get(position).getName());
            savedApply.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int mealType = list.get(position).getMealNum();
                    int minCalories = list.get(position).getMinCalories();
                    int minCarbs = list.get(position).getMinCarbs();
                    int minFat = list.get(position).getMinFat();
                    int minProtein = list.get(position).getMinProtein();
                    int maxCalories = list.get(position).getMaxCalories();
                    int maxCarbs = list.get(position).getMaxCarbs();
                    int maxFat = list.get(position).getMaxFat();
                    int maxProtein = list.get(position).getMaxProtein();
                    String keyword = list.get(position).getDescription();
                    minValues.add(minCalories);
                    minValues.add(minCarbs);
                    minValues.add(minFat);
                    minValues.add(minProtein);
                    maxValues.add(maxCalories);
                    maxValues.add(maxCarbs);
                    maxValues.add(maxFat);
                    maxValues.add(maxProtein);
                    descriptionList.add(keyword);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle searchBundle = new Bundle();
                    searchBundle.putInt("mealType", mealType);
                    searchBundle.putIntegerArrayList("minValues", minValues);
                    searchBundle.putIntegerArrayList("maxValues", maxValues);
                    searchBundle.putStringArrayList("description", descriptionList);
                    Results results = new Results();
                    results.setArguments(searchBundle);
                    fragmentTransaction.replace(R.id.constraintLayout, results);
                    fragmentTransaction.commit();

                }
            });


            return adapterView;
        }

    }

}
