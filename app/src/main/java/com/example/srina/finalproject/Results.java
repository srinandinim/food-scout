package com.example.srina.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Results extends Fragment {

    Activity mainActivity;
    View parentView;

    ArrayList<Integer> minValues = new ArrayList<>();
    ArrayList<Integer> maxValues = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    int mealType;

    int minCalories, maxCalories, minCarbs, maxCarbs, minFat, maxFat, minProtein, maxProtein;
    ImageView imageOne, imageTwo, imageThree, imageFour, imageFive, imageSix;
    TextView textOne, textTwo, textThree, textFour, textFive, textSix;
    CardView cardOne, cardTwo, cardThree, cardFour, cardFive, cardSix;

    View alertView;
    ImageView alertImage;
    TextView alertName, alertLocation, alertServing;
    Button alertBack;
    AlertDialog.Builder favoriteBuilder;
    AlertDialog dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = getActivity();
        parentView = inflater.inflate(R.layout.fragment_results, container, false);

        imageOne = parentView.findViewById(R.id.imageOne);
        imageTwo = parentView.findViewById(R.id.imageTwo);
        imageThree = parentView.findViewById(R.id.imageThree);
        imageFour = parentView.findViewById(R.id.imageFour);
        imageFive = parentView.findViewById(R.id.imageFive);
        imageSix = parentView.findViewById(R.id.imageSix);
        textOne = parentView.findViewById(R.id.titleOne);
        textTwo = parentView.findViewById(R.id.titleTwo);
        textThree = parentView.findViewById(R.id.titleThree);
        textFour = parentView.findViewById(R.id.titleFour);
        textFive = parentView.findViewById(R.id.titleFive);
        textSix = parentView.findViewById(R.id.titleSix);
        cardOne = parentView.findViewById(R.id.cardViewOne);
        cardTwo = parentView.findViewById(R.id.cardViewTwo);
        cardThree = parentView.findViewById(R.id.cardViewThree);
        cardFour = parentView.findViewById(R.id.cardViewFour);
        cardFive = parentView.findViewById(R.id.cardViewFive);
        cardSix = parentView.findViewById(R.id.cardViewSix);

        alertView = getLayoutInflater().inflate(R.layout.dialog_results_info, null);
        alertImage = alertView.findViewById(R.id.alertImage);
        alertName = alertView.findViewById(R.id.alertName);
        alertLocation = alertView.findViewById(R.id.alertLocation);
        alertServing = alertView.findViewById(R.id.alertServing);
        alertBack = alertView.findViewById(R.id.alertBack);
        favoriteBuilder = new AlertDialog.Builder(getContext());
        favoriteBuilder.setView(alertView);
        dialog = favoriteBuilder.create();


        Bundle bundle = getArguments();

        minValues = bundle.getIntegerArrayList("minValues");
        maxValues = bundle.getIntegerArrayList("maxValues");
        description = bundle.getStringArrayList("description");
        mealType = bundle.getInt("mealType");

        minCalories = minValues.get(0);
        minCarbs = minValues.get(1);
        minFat = minValues.get(2);
        minProtein = minValues.get(3);

        maxCalories = maxValues.get(0);
        maxCarbs = maxValues.get(1);
        maxFat = maxValues.get(2);
        maxProtein = maxValues.get(3);

        AsyncThread asyncThread = new AsyncThread();
        asyncThread.execute();

        return parentView;
    }

    public class AsyncThread extends AsyncTask<Void, Void, JSONObject> {

        URL url;
        URLConnection connection;
        InputStream stream;
        BufferedReader reader;
        String readerString;
        JSONObject data;
        int queryValue;
        String queryChoice;

        menuItem itemOne, itemTwo, itemThree, itemFour, itemFive, itemSix;

        protected JSONObject doInBackground(Void... voids) {

            try {
                queryValue = (int) (Math.random() * description.size());
                queryChoice = description.get(queryValue);
                url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/menuItems/search?maxCalories=" + maxCalories + "&maxCarbs=" + maxCarbs + "&maxFat=" + maxFat + "&maxProtein=" + maxProtein + "&minCalories=" + minCalories + "&minCarbs=" + minCarbs + "&minFat=" + minFat + "&minProtein=" + minProtein + "&number=6&offset=0&query=" + queryChoice);
                connection = url.openConnection();
                connection.setRequestProperty("X-Mashape-Key", "IA0yrvadwgmsh3JgoKDBIBFkllrXp18u2tIjsnT53Ioh012tJK");
                connection.setRequestProperty("Accept", "application/json");
                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                readerString = reader.readLine();
                data = new JSONObject(readerString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;

        }

        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            itemOne = createMenuItem(data, 0);
            itemTwo = createMenuItem(data, 1);
            itemThree = createMenuItem(data, 2);
            itemFour = createMenuItem(data, 3);
            itemFive = createMenuItem(data, 4);
            itemSix = createMenuItem(data, 5);

            textOne.setText(itemOne.getTitle());
            textTwo.setText(itemTwo.getTitle());
            textThree.setText(itemThree.getTitle());
            textFour.setText(itemFour.getTitle());
            textFive.setText(itemFive.getTitle());
            textSix.setText(itemSix.getTitle());

            loadImageFromUrl(itemOne.getImageLink(), imageOne);
            loadImageFromUrl(itemTwo.getImageLink(), imageTwo);
            loadImageFromUrl(itemThree.getImageLink(), imageThree);
            loadImageFromUrl(itemFour.getImageLink(), imageFour);
            loadImageFromUrl(itemFive.getImageLink(), imageFive);
            loadImageFromUrl(itemSix.getImageLink(), imageSix);

            cardOne.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cardAlert(itemOne);
                    dialog.show();
                    alertBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            cardTwo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cardAlert(itemTwo);
                    dialog.show();
                    alertBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            cardThree.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cardAlert(itemThree);
                    dialog.show();
                    alertBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            cardFour.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cardAlert(itemFour);
                    dialog.show();
                    alertBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            cardFive.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cardAlert(itemFive);
                    dialog.show();
                    alertBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            cardSix.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cardAlert(itemSix);
                    dialog.show();
                    alertBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });


        }

        public void loadImageFromUrl(String url, final ImageView imageView) {
            Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(imageView, new com.squareup.picasso.Callback() {
                public void onSuccess() {

                }

                public void onError() {
                }
            });
        }

        public menuItem createMenuItem(JSONObject object, int index) {
            int id = 0;
            String title = "", restaurantChain = "", servingSize = "", imageLink = "";
            try {
                id = Integer.parseInt(object.getJSONArray("menuItems").getJSONObject(index).getString("id"));
                title = object.getJSONArray("menuItems").getJSONObject(index).getString("title");
                restaurantChain = object.getJSONArray("menuItems").getJSONObject(index).getString("restaurantChain");
                servingSize = object.getJSONArray("menuItems").getJSONObject(index).getString("readableServingSize");
                imageLink = object.getJSONArray("menuItems").getJSONObject(index).getString("image");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            menuItem newItem = new menuItem(id, title, restaurantChain, servingSize, imageLink);
            return newItem;
        }

        public void cardAlert(menuItem item) {
            loadImageFromUrl(item.getImageLink(), alertImage);
            alertName.setText("Name: \n" + item.getTitle());
            alertLocation.setText("Restaurant Chain: \n" + item.getRestaurantChain());
            if (item.getServingSize().equals(null)) {
                alertServing.setText("Serving Size: N/A");
            } else {
                alertServing.setText("Serving Size: \n" + item.getServingSize());
            }

        }
    }

}
