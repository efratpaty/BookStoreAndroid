package model.datasource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;


import com.example.aviga_000.bookstoreandroid.BooksPoolActivity;
import com.example.aviga_000.bookstoreandroid.CustomerActivity;
import com.example.aviga_000.bookstoreandroid.EntranceActivity;
import com.example.aviga_000.bookstoreandroid.ManagerComplaintsActivity;
import com.example.aviga_000.bookstoreandroid.SupplierBooksActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.BookSearch;
import StoreJavaClass.Buyer;
import StoreJavaClass.Complains;
import StoreJavaClass.Manager;
import StoreJavaClass.Order;
import StoreJavaClass.ShoppingCartHelper;
import StoreJavaClass.Subject;
import StoreJavaClass.Supplier;
import StoreJavaClass.SupplierBook;

import model.backend.PoolFunctions;

/**
 * Created by aviga_000 on 03/01/2016.
 */
public class StoreMySql implements PoolFunctions {

    public static final String web_url = "http://halevis.vlab.jct.ac.il/";

    public final PoolFunctions list;

    public StoreMySql(Context context) {
        list = new model.datasource.StoreList(context);
    }

    private static String GET(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        }
        else {
            return "";
        }
    }

    private static String POST(String url, Map<String,Object> params) throws IOException {

        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        else return "";
    }

    @Override
    public void addBuyer(final Buyer buyer) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("buyerId",buyer.getId());
                    _params.put("firstName", buyer.getFirstName());
                    _params.put("lastName", buyer.getLastName());
                    _params.put("password", buyer.getPassword());
                    _params.put("phoneNumber", buyer.getPhoneNumber());
                    _params.put("eMail", buyer.geteMail());
                    _params.put("city", buyer.getCity());
                    _params.put("street", buyer.getStreet());
                    _params.put("buildingNumber", buyer.getBuildingNumber());
                    _params.put("houseNumber", buyer.getHouseNumber());
                    _params.put("zipCode", buyer.getZipCode());
                    try {
                        POST(web_url + "addBuyer.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setBuyers() {

        try {
            new AsyncTask<Void, Void, ArrayList<Buyer>>() {

                @Override
                protected ArrayList<Buyer> doInBackground(Void... voids) {
                    try {
                        Buyer tempBuyer;
                        buyers.clear();
                        JSONArray buyersList = new JSONObject(GET(web_url + "getBuyersList.php")).getJSONArray("buyers");
                        for (int i = 0; i < buyersList.length(); i++) {
                            tempBuyer = new Buyer();
                            tempBuyer.setId(buyersList.getJSONObject(i).getLong("buyerId"));
                            tempBuyer.setFirstName(buyersList.getJSONObject(i).getString("firstName"));
                            tempBuyer.setLastName(buyersList.getJSONObject(i).getString("lastName"));
                            tempBuyer.setPassword(buyersList.getJSONObject(i).getString("password"));
                            tempBuyer.setPhoneNumber(buyersList.getJSONObject(i).getLong("phoneNumber"));
                            tempBuyer.seteMail(buyersList.getJSONObject(i).getString("eMail"));
                            tempBuyer.setCity(buyersList.getJSONObject(i).getString("city"));
                            tempBuyer.setStreet(buyersList.getJSONObject(i).getString("street"));
                            tempBuyer.setBuildingNumber(buyersList.getJSONObject(i).getInt("buildingNumber"));
                            tempBuyer.setHouseNumber(buyersList.getJSONObject(i).getString("houseNumber"));
                            tempBuyer.setZipCode(buyersList.getJSONObject(i).getInt("zipCode"));

                            buyers.add(tempBuyer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<Buyer> myBuyersList) {
                }
            };
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBuyer(final Buyer buyer) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("buyerId",buyer.getId());
                    _params.put("firstName", buyer.getFirstName());
                    _params.put("lastName", buyer.getLastName());
                    _params.put("password", buyer.getPassword());
                    _params.put("phoneNumber", buyer.getPhoneNumber());
                    _params.put("eMail", buyer.geteMail());
                    _params.put("city", buyer.getCity());
                    _params.put("street", buyer.getStreet());
                    _params.put("buildingNumber", buyer.getBuildingNumber());
                    _params.put("houseNumber", buyer.getHouseNumber());
                    _params.put("zipCode", buyer.getZipCode());
                    try {
                        POST(web_url + "updateBuyer.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBuyer(final Buyer buyer) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    GET(web_url + "deleteBuyer.php" + "?id=" + buyer.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();
    }


    public ArrayList<Buyer> buyerListForSignIn(final String userName, final String password, final Activity activity)throws Exception{

        try {
            new AsyncTask<Void, Void, ArrayList<Buyer>>() {

                @Override
                protected ArrayList<Buyer> doInBackground(Void... voids) {

                    try {
                        Buyer tempBuyer;
                        buyers.clear();
                        JSONArray buyersList = new JSONObject(GET(web_url + "getBuyersList.php")).getJSONArray("buyers");
                        for (int i = 0; i < buyersList.length(); i++) {
                            tempBuyer = new Buyer();
                            tempBuyer.setId(buyersList.getJSONObject(i).getLong("buyerId"));
                            tempBuyer.setFirstName(buyersList.getJSONObject(i).getString("firstName"));
                            tempBuyer.setLastName(buyersList.getJSONObject(i).getString("lastName"));
                            tempBuyer.setPassword(buyersList.getJSONObject(i).getString("password"));
                            tempBuyer.setPhoneNumber(buyersList.getJSONObject(i).getLong("phoneNumber"));
                            tempBuyer.seteMail(buyersList.getJSONObject(i).getString("eMail"));
                            tempBuyer.setCity(buyersList.getJSONObject(i).getString("city"));
                            tempBuyer.setStreet(buyersList.getJSONObject(i).getString("street"));
                            tempBuyer.setBuildingNumber(buyersList.getJSONObject(i).getInt("buildingNumber"));
                            tempBuyer.setHouseNumber(buyersList.getJSONObject(i).getString("houseNumber"));
                            tempBuyer.setZipCode(buyersList.getJSONObject(i).getInt("zipCode"));

                            buyers.add(tempBuyer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(ArrayList<Buyer> myBuyersList) {
                    Intent intent = null;

                //if user was not found, search buyer list for username and password1 if found make intent buyer activity
                    ArrayList<Buyer> _buyers = buyers;
                    if (buyers != null) {
                        for (Buyer b : _buyers) {
                            if (b.geteMail().equals(userName) && b.getPassword().equals(password)) {
                                intent = new Intent(activity, BooksPoolActivity.class);
                                intent.putExtra("user_id", b.getId());
                                intent.putExtra("user", 1);
                                activity.startActivity(intent);

                            }
                        }
                    }
                            //if user was not found, check if username and password belong to manger, if so make intent manager activity
                            if (manager.geteMail().equals(userName) && manager.getPassword().equals(password)) {
                                intent = new Intent(activity, ManagerComplaintsActivity.class);
                                intent.putExtra("user_id", manager.getId());
                                intent.putExtra("user",3);
                                activity.startActivity(intent);
                            }

                            else
                            {//if user not found open dialog to let user know the login failed
                                new AlertDialog.Builder(activity)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("ERROR")
                                        .setMessage("incorrect username or password1")
                                        .setPositiveButton("OK", null)
                                        .setIcon(android.R.drawable.stat_notify_error)
                                        .show();
                            }
                        }
            }.execute();



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buyers;
    }


    @Override
    public ArrayList<Buyer> buyerList()throws Exception{

        try {
            new AsyncTask<Void, Void, ArrayList<Buyer>>() {

                @Override
                protected ArrayList<Buyer> doInBackground(Void... voids) {

                    try {
                        Buyer tempBuyer;
                        buyers.clear();
                        JSONArray buyersList = new JSONObject(GET(web_url + "getBuyersList.php")).getJSONArray("buyers");
                        for (int i = 0; i < buyersList.length(); i++) {
                            tempBuyer = new Buyer();
                            tempBuyer.setId(buyersList.getJSONObject(i).getLong("buyerId"));
                            tempBuyer.setFirstName(buyersList.getJSONObject(i).getString("firstName"));
                            tempBuyer.setLastName(buyersList.getJSONObject(i).getString("lastName"));
                            tempBuyer.setPassword(buyersList.getJSONObject(i).getString("password"));
                            tempBuyer.setPhoneNumber(buyersList.getJSONObject(i).getLong("phoneNumber"));
                            tempBuyer.seteMail(buyersList.getJSONObject(i).getString("eMail"));
                            tempBuyer.setCity(buyersList.getJSONObject(i).getString("city"));
                            tempBuyer.setStreet(buyersList.getJSONObject(i).getString("street"));
                            tempBuyer.setBuildingNumber(buyersList.getJSONObject(i).getInt("buildingNumber"));
                            tempBuyer.setHouseNumber(buyersList.getJSONObject(i).getString("houseNumber"));
                            tempBuyer.setZipCode(buyersList.getJSONObject(i).getInt("zipCode"));

                            buyers.add(tempBuyer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(ArrayList<Buyer> myBuyersList) {

                }
            }.execute().get();



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buyers;
    }


    @Override
    public void addBook(final Book book) throws IOException {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("bookId",book.getBookId());
                    _params.put("bookName", book.getBookName());
                    _params.put("author", book.getAuthor());
                    _params.put("publicationDate", book.getPublicationDate());
                    _params.put("publishers", book.getPublishers());
                    _params.put("recommendedPrice", book.getRecommendedPrice());
                    _params.put("summary", book.getSummary());
                    _params.put("imageBook", book.getUrl());
                    try {
                        POST(web_url + "addBook.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteBook(final Book book) {

        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("bookId",book.getBookId());
                    _params.put("bookName", book.getBookName());
                    _params.put("author", book.getAuthor());
                    _params.put("publicationDate", book.getPublicationDate());
                    _params.put("publishers", book.getPublishers());
                    _params.put("recommendedPrice", book.getRecommendedPrice());
                    _params.put("summary", book.getSummary());
                    _params.put("url", book.getUrl());
                    try {
                        POST(web_url + "deleteBook.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public ArrayList<SupplierBook> searchBooks(String bookName, String author, Subject subject, float price, BookCondition condition) {
        return null;
    }

    @Override
    public ArrayList<Book> bookList() {

        try {
            new AsyncTask<Void, Void, ArrayList<Book>>() {

                @Override
                protected ArrayList<Book> doInBackground(Void... voids) {
                    try {
                        Book tempBook;
                        books.clear();
                        Book.count = new AtomicInteger(1000);
                        JSONArray booksList = new JSONObject(GET(web_url + "getBooksList.php")).getJSONArray("books");
                        for (int i = 0; i < booksList.length(); i++) {
                            JSONObject childJSONObject = booksList.getJSONObject(i);

                            int bookId =  childJSONObject.getInt("bookId");
                            String bookName = childJSONObject.getString("bookName");
                            String author = childJSONObject.getString("author");
                            int publicationDate =  childJSONObject.getInt("publicationDate");
                            String publishers = childJSONObject.getString("publishers");
                            float recPrice =Float.valueOf(String.valueOf(childJSONObject.getDouble("recommendedPrice")));
                            String summary = childJSONObject.getString("summary");
                            //Subject subject = Subject.valueOf(childJSONObject.getString("subject"));
                            String url = childJSONObject.getString("imageBook");

                            tempBook = new Book(bookName,author,publicationDate,publishers,recPrice,summary,Subject.ALL_SUBJECTS,url);
                            /*tempBook.setBookId(bookId);
                            tempBook.setBookName(bookName);
                            tempBook.setAuthor(author);
                            tempBook.setPublicationDate(publicationDate);
                            tempBook.setPublishers(publishers);
                            tempBook.setRecommendedPrice(recPrice);
                            tempBook.setSummary(summary);
                            tempBook.setSubject(Subject.ALL_SUBJECTS);
                            tempBook.setUrl(url);
*/
                            /*JSONArray supplierList = booksList.getJSONObject(i).getJSONArray("supplierList");
                            ArrayList<Supplier> tempSupplierList = new ArrayList<Supplier>();
                            Supplier tempSupplier = null;
                            for (int j = 0; j < supplierList.length(); j++) {
                                tempSupplier.setId(supplierList.getJSONObject(i).getLong("id"));
                                tempSupplier.setFirstName(supplierList.getJSONObject(i).getString("firstName"));
                                tempSupplier.setLastName(supplierList.getJSONObject(i).getString("lastName"));
                                tempSupplier.setPassword(supplierList.getJSONObject(i).getString("password"));
                                tempSupplier.setPhoneNumber(supplierList.getJSONObject(i).getLong("phoneNumber"));
                                tempSupplier.seteMail(supplierList.getJSONObject(i).getString("eMail"));
                                tempSupplier.setCity(supplierList.getJSONObject(i).getString("city"));
                                tempSupplier.setStreet(supplierList.getJSONObject(i).getString("street"));
                                tempSupplier.setBuildingNumber(supplierList.getJSONObject(i).getInt("buildingNumber"));
                                tempSupplier.setHouseNumber(supplierList.getJSONObject(i).getString("houseNumber"));
                                tempSupplier.setZipCode(supplierList.getJSONObject(i).getInt("zipCode"));

                                tempSupplierList.add(tempSupplier);
                            }
                            tempBook.setSupplierList(tempSupplierList);*/
                            books.add(tempBook);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<Book> myBooksList) {
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void addSupplier(final Supplier supplier) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("supplierId",supplier.getId());
                    _params.put("firstName", supplier.getFirstName());
                    _params.put("lastName", supplier.getLastName());
                    _params.put("password", supplier.getPassword());
                    _params.put("phoneNumber", supplier.getPhoneNumber());
                    _params.put("eMail", supplier.geteMail());
                    _params.put("city", supplier.getCity());
                    _params.put("street", supplier.getStreet());
                    _params.put("buildingNumber", supplier.getBuildingNumber());
                    _params.put("houseNumber", supplier.getHouseNumber());
                    _params.put("zip", supplier.getZipCode());
                    try {
                        POST(web_url + "addSupplier.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSupplier(final Supplier supplier) {

        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("supplierId",supplier.getId());
                    _params.put("password", supplier.getPassword());
                    _params.put("firstName", supplier.getFirstName());
                    _params.put("lastName", supplier.getLastName());
                    _params.put("phoneNumber", supplier.getPhoneNumber());
                    _params.put("eMail", supplier.geteMail());
                    _params.put("city", supplier.getCity());
                    _params.put("street", supplier.getStreet());
                    _params.put("buildingNumber", supplier.getBuildingNumber());
                    _params.put("houseNumber", supplier.getHouseNumber());
                    _params.put("zip", supplier.getZipCode());
                    try {
                        POST(web_url + "updateSupplier.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteSupplier(final Supplier supplier) {

        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("supplierId",supplier.getId());
                    _params.put("firstName", supplier.getFirstName());
                    _params.put("lastName", supplier.getLastName());
                    _params.put("password", supplier.getPassword());
                    _params.put("phoneNumber", supplier.getPhoneNumber());
                    _params.put("eMail", supplier.geteMail());
                    _params.put("city", supplier.getCity());
                    _params.put("street", supplier.getStreet());
                    _params.put("buildingNumber", supplier.getBuildingNumber());
                    _params.put("houseNumber", supplier.getHouseNumber());
                    _params.put("zip", supplier.getZipCode());
                    try {
                        POST(web_url + "deleteSupplier.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public Supplier searchSupplier(final String password) {

        return list.searchSupplier(password);

    }

    @Override
    public ArrayList<SupplierBook> supplierBookList(long supplierID) {

        return list.supplierBookList(supplierID);
    }

    @Override
    public ArrayList<Supplier> supplierList() {

        try {
            new AsyncTask<Void, Void, ArrayList<Supplier>>() {

                @Override
                protected ArrayList<Supplier> doInBackground(Void... voids) {
                    try {
                        Supplier tempSupplier;
                        suppliers.clear();
                        JSONArray suppliersList = new JSONObject(GET(web_url + "getSupplierList.php")).getJSONArray("supplier");
                        for (int i = 0; i < suppliersList.length(); i++) {
                            tempSupplier = new Supplier();
                            tempSupplier.setId(suppliersList.getJSONObject(i).getLong("supplierId"));
                            tempSupplier.setFirstName(suppliersList.getJSONObject(i).getString("firstName"));
                            tempSupplier.setLastName(suppliersList.getJSONObject(i).getString("lastName"));
                            tempSupplier.setPassword(suppliersList.getJSONObject(i).getString("password"));
                            tempSupplier.setPhoneNumber(suppliersList.getJSONObject(i).getLong("phoneNumber"));
                            tempSupplier.seteMail(suppliersList.getJSONObject(i).getString("eMail"));
                            tempSupplier.setCity(suppliersList.getJSONObject(i).getString("city"));
                            tempSupplier.setStreet(suppliersList.getJSONObject(i).getString("street"));
                            tempSupplier.setBuildingNumber(suppliersList.getJSONObject(i).getInt("buildingNumber"));
                            tempSupplier.setHouseNumber(suppliersList.getJSONObject(i).getString("houseNumber"));
                            tempSupplier.setZipCode(suppliersList.getJSONObject(i).getInt("zip"));

                            suppliers.add(tempSupplier);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<Supplier> mySuppliersList) {
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return suppliers;
    }

    public ArrayList<Supplier> supplierListForSignIn(final String userName, final String password, final Activity activity) {

        try {
            new AsyncTask<Void, Void, ArrayList<Supplier>>() {

                @Override
                protected ArrayList<Supplier> doInBackground(Void... voids) {
                    try {
                        Supplier tempSupplier;
                        suppliers.clear();
                        JSONArray suppliersList = new JSONObject(GET(web_url + "getSupplierList.php")).getJSONArray("supplier");
                        for (int i = 0; i < suppliersList.length(); i++) {
                            tempSupplier = new Supplier();
                            tempSupplier.setId(suppliersList.getJSONObject(i).getLong("supplierId"));
                            tempSupplier.setFirstName(suppliersList.getJSONObject(i).getString("firstName"));
                            tempSupplier.setLastName(suppliersList.getJSONObject(i).getString("lastName"));
                            tempSupplier.setPassword(suppliersList.getJSONObject(i).getString("password"));
                            tempSupplier.setPhoneNumber(suppliersList.getJSONObject(i).getLong("phoneNumber"));
                            tempSupplier.seteMail(suppliersList.getJSONObject(i).getString("eMail"));
                            tempSupplier.setCity(suppliersList.getJSONObject(i).getString("city"));
                            tempSupplier.setStreet(suppliersList.getJSONObject(i).getString("street"));
                            tempSupplier.setBuildingNumber(suppliersList.getJSONObject(i).getInt("buildingNumber"));
                            tempSupplier.setHouseNumber(suppliersList.getJSONObject(i).getString("houseNumber"));
                            tempSupplier.setZipCode(suppliersList.getJSONObject(i).getInt("zip"));

                            suppliers.add(tempSupplier);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<Supplier> mySuppliersList) {
                    //search suppliers list for username and password if found make intent supplier activity

                    if (suppliers!= null)
                    {
                        Intent intent = null;

                        for (Supplier s :suppliers) {
                            String e = s.geteMail();
                            String p = s.getPassword();
                            if (e.equals(userName) && p.equals(password)) {
                                intent = new Intent(activity, SupplierBooksActivity.class);
                                intent.putExtra("user_id", s.getId());
                                intent.putExtra("user", 2);
                                activity.startActivity(intent);

                            }
                        }
                    }
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return suppliers;
    }
    @Override
    public void addBookSupplier(final SupplierBook supplierBook) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("supplierBook_id",supplierBook.getId());
                    _params.put("book_id", supplierBook.getBookId());
                    _params.put("supplier_id", supplierBook.getSupplierId());
                    _params.put("copies", supplierBook.getCopies());
                    _params.put("price", supplierBook.getPrice());
                    _params.put("bookCondition", supplierBook.getBookCondition());
                    try {
                        POST(web_url + "addSupplierBook.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void updateBookSupplier(final SupplierBook supplierBook) {

        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("supplierBook_id",supplierBook.getId());
                    _params.put("book_id", supplierBook.getBookId());
                    _params.put("supplier_id", supplierBook.getSupplierId());
                    _params.put("copies", supplierBook.getCopies());
                    _params.put("price", supplierBook.getPrice());
                    _params.put("bookCondition", supplierBook.getBookCondition());
                    try {
                        POST(web_url + "updateSupplierBook.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteBookSupplier(final SupplierBook supplierBook) {

        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("supplierBook_id",supplierBook.getId());
                    _params.put("book_id", supplierBook.getBookId());
                    _params.put("supplier_id", supplierBook.getSupplierId());
                    _params.put("copies", supplierBook.getCopies());
                    _params.put("price", supplierBook.getPrice());
                    _params.put("bookCondition", supplierBook.getBookCondition());
                    try {
                        POST(web_url + "deleteSupplierBook.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public ArrayList<SupplierBook> supplierBookList() {

        try {
            new AsyncTask<Void, Void, ArrayList<SupplierBook>>() {

                @Override
                protected ArrayList<SupplierBook> doInBackground(Void... voids) {
                    try {
                        SupplierBook tempSupplierBook;
                        supplierBooks.clear();
                        SupplierBook.count = new AtomicInteger(2000);//variable for auto generating id
                        JSONArray supplierBooksList = new JSONObject(GET(web_url + "getSupplierBookList.php")).getJSONArray("supplierBook");
                        for (int i = 0; i < supplierBooksList.length(); i++) {
                            tempSupplierBook = new SupplierBook();
                            tempSupplierBook.setId(supplierBooksList.getJSONObject(i).getInt("supplierBook_id"));
                            tempSupplierBook.setBookId(supplierBooksList.getJSONObject(i).getInt("book_id"));
                            tempSupplierBook.setSupplierId(supplierBooksList.getJSONObject(i).getLong("supplier_id"));
                            tempSupplierBook.setCopies(supplierBooksList.getJSONObject(i).getInt("copies"));
                            tempSupplierBook.setPrice(supplierBooksList.getJSONObject(i).getDouble("price"));

                            tempSupplierBook.setBookCondition(BookCondition.valueOf(supplierBooksList.getJSONObject(i).getString("bookCondition")));

                            supplierBooks.add(tempSupplierBook);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<SupplierBook> mySupplierBooksList) {
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return supplierBooks;
    }

    @Override
    public void addOrder(final Order order) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("orderId",order.getOrderId());
                    _params.put("book_id", order.getBookId());
                    _params.put("supplier_id", order.getSupplierId());
                    _params.put("customer_id", order.getCustomerId());
                    _params.put("creditNumber", order.getCreditNumber());
                    _params.put("cvs", order.getCvs());
                    _params.put("cardHolderName", order.getCardHolderName());
                    _params.put("cardHolderId", order.getCardHolderId());
                    _params.put("paymentSum", order.getPaymentSum());
                    _params.put("orderDate", order.getOrderDate());
                    try {
                        POST(web_url + "addOrder.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteOrder(final Order order) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("orderId",order.getOrderId());
                    _params.put("book_id", order.getBookId());
                    _params.put("supplier_id", order.getSupplierId());
                    _params.put("customer_id", order.getCustomerId());
                    _params.put("creditNumber", order.getCreditNumber());
                    _params.put("cvs", order.getCvs());
                    _params.put("cardHolderName", order.getCardHolderName());
                    _params.put("cardHolderId", order.getCardHolderId());
                    _params.put("paymentSum", order.getPaymentSum());
                    _params.put("orderDate", order.getOrderDate());
                    try {
                        POST(web_url + "addOrder.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public ArrayList<Order> customerOrderListByDate(Date from, Date to, Long customerId) {
        return list.customerOrderListByDate(from, to, customerId);
    }

    @Override
    public ArrayList<Order> SupplierOrderListByDate(Date from, Date to, Long supplierId) {
        return list.SupplierOrderListByDate(from, to, supplierId);
    }

    @Override
    public ArrayList<Order> orderListByDate(Date from, Date to) {
        return list.orderListByDate(from, to);
    }

    @Override
    public ArrayList<Order> orderList() {
        try {
            new AsyncTask<Void, Void, ArrayList<Order>>() {

                @Override
                protected ArrayList<Order> doInBackground(Void... voids) {
                    try {
                        Order tempOrder;
                        JSONArray ordersList = new JSONObject(GET(web_url + "getOrderList.php")).getJSONArray("orders");
                        for (int i = 0; i < ordersList.length(); i++) {
                            tempOrder = new Order();

                            long tempSupplierId = 0L;
                            ArrayList<Long> tempSupplierIdList = new ArrayList<Long>();
                            JSONArray supplierIdList = ordersList.getJSONObject(i).getJSONArray("supplier_id");
                            for (int j = 0; j < ordersList.length(); j++)
                            {
                                tempSupplierId = supplierIdList.getJSONObject(j).getLong("supplier_id");
                                tempSupplierIdList.add(tempSupplierId);
                            }
                            tempOrder.setSupplierId(tempSupplierIdList);

                            int tempId = 0;
                            ArrayList<Integer> tempIdList = new ArrayList<Integer>();
                            JSONArray bookIdList = ordersList.getJSONObject(i).getJSONArray("book_id");
                            for (int j = 0; j < ordersList.length(); j++) {
                                tempId = bookIdList.getJSONObject(j).getInt("book_id");
                                tempIdList.add(tempId);
                            }
                            tempOrder.setBookId(tempIdList);

                            tempOrder.setCustomerId(ordersList.getJSONObject(i).getLong("customer_id"));
                            tempOrder.setCreditNumber(ordersList.getJSONObject(i).getLong("creditNumber"));
                            tempOrder.setCardHolderId(ordersList.getJSONObject(i).getLong("cardHolderId"));
                            tempOrder.setCardHolderName(ordersList.getJSONObject(i).getString("cardHolderName"));
                            tempOrder.setCvs(ordersList.getJSONObject(i).getInt("cvs"));

                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = ordersList.getJSONObject(i).getString("orderDate");
                            try {
                                tempOrder.setOrderDate(formatter.parse(dateInString));
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tempOrder.setPaymentSum(ordersList.getJSONObject(i).getDouble("paymentSum"));

                            orders.add(tempOrder);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<Order> myOrdersList) {
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public ArrayList<Order> SupplierorderList(long supplierId) {
        return list.SupplierorderList (supplierId);
    }

    @Override
    public ArrayList<Order> BuyerOrderList(long buyerId) {
        return list.BuyerOrderList(buyerId);
    }

    @Override
    public double expenses(Date from, Date to) {
        return list.expenses(from, to);
    }

    @Override
    public double profit(Date from, Date to) {
        return list.profit(from, to);
    }

    @Override
    public void addBookSearch(final BookSearch bookSearching) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("bookSearch_id",bookSearching.getBookSearchId());
                    _params.put("dateOfRequest", bookSearching.getDateOfRequest());
                    _params.put("book_id", bookSearching.getBookId());
                    _params.put("customer_id", bookSearching.getCustomerId());
                    _params.put("customerEmail", bookSearching.getCustomerEmail());
                    _params.put("bookCondition", bookSearching.getBookCondition());
                    _params.put("maxPrice", bookSearching.getMaxPrice());
                    _params.put("sendMessage", bookSearching.isSendMessage());
                    try {
                        POST(web_url + "addBookSearch.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookSearch(final BookSearch bookSearching) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("bookSearch_id",bookSearching.getBookSearchId());
                    _params.put("dateOfRequest", bookSearching.getDateOfRequest());
                    _params.put("book_id", bookSearching.getBookId());
                    _params.put("customer_id", bookSearching.getCustomerId());
                    _params.put("customerEmail", bookSearching.getCustomerEmail());
                    _params.put("bookCondition", bookSearching.getBookCondition());
                    _params.put("maxPrice", bookSearching.getMaxPrice());
                    _params.put("sendMessage", bookSearching.isSendMessage());
                    try {
                        POST(web_url + "updateBookSearch.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookSearch(final BookSearch bookSearching) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("bookSearch_id",bookSearching.getBookSearchId());
                    _params.put("dateOfRequest", bookSearching.getDateOfRequest());
                    _params.put("book_id", bookSearching.getBookId());
                    _params.put("customer_id", bookSearching.getCustomerId());
                    _params.put("customerEmail", bookSearching.getCustomerEmail());
                    _params.put("bookCondition", bookSearching.getBookCondition());
                    _params.put("maxPrice", bookSearching.getMaxPrice());
                    _params.put("sendMessage", bookSearching.isSendMessage());
                    try {
                        POST(web_url + "deleteBookSearch.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<BookSearch> bookSearchList() {
        try {
            new AsyncTask<Void, Void, ArrayList<BookSearch>>() {

                @Override
                protected ArrayList<BookSearch> doInBackground(Void... voids) {
                    try {
                        BookSearch tempRequest;
                        JSONArray requestsList = new JSONObject(GET(web_url + "getSearchBookList.php")).getJSONArray("bookSearches");
                        for (int i = 0; i < requestsList.length(); i++) {
                            tempRequest = new BookSearch();
                            tempRequest.setCustomerId(requestsList.getJSONObject(i).getLong("customer_id"));
                            tempRequest.setBookId(requestsList.getJSONObject(i).getInt("book_id"));
                            tempRequest.setCustomerEmail(requestsList.getJSONObject(i).getString("customerEmail"));
                            tempRequest.setBookCondition(BookCondition.valueOf(requestsList.getJSONObject(i).getString("bookCondition")));
                            tempRequest.setMaxPrice(requestsList.getJSONObject(i).getLong("maxPrice"));
                            int tempSendMessage = requestsList.getJSONObject(i).getInt("sendMessage");
                            if (tempSendMessage == 0)
                                tempRequest.setSendMessage(false);
                            else
                                tempRequest.setSendMessage(true);

                            bookSearchings.add(tempRequest);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<BookSearch> myRequestsList) {
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bookSearchings;    }

    @Override
    public ArrayList<BookSearch> authorBookRequestList(String name, String author) {
        return list.authorBookRequestList(name, author);
    }

    @Override
    public void reminder() {
        list.reminder();
    }

    @Override
    public void addComplains(final Complains complains) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("complaint_id",complains.getComplaintId());
                    _params.put("complainant_id", complains.getComplainantId());
                    _params.put("defendant_id", complains.getDefendantId());
                    _params.put("complaint", complains.getComplaint());
                    try {
                        POST(web_url + "addComplaint.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void updateComplains(final Complains complains) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("complaint_id",complains.getComplaintId());
                    _params.put("complainant_id", complains.getComplainantId());
                    _params.put("defendant_id", complains.getDefendantId());
                    _params.put("complaint", complains.getComplaint());
                    try {
                        POST(web_url + "updateComplaint.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteComplains(final Complains complains) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("complaint_id",complains.getComplaintId());
                    _params.put("complainant_id", complains.getComplainantId());
                    _params.put("defendant_id", complains.getDefendantId());
                    _params.put("complaint", complains.getComplaint());
                    try {
                        POST(web_url + "deleteComplaint.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    @Override
    public ArrayList<Complains> complainsList() {
        try {
            new AsyncTask<Void, Void, ArrayList<Complains>>() {

                @Override
                protected ArrayList<Complains> doInBackground(Void... voids) {
                    try {
                        Complains tempComplaint;
                        JSONArray complaintsList = new JSONObject(GET(web_url + "getComplaintsList.php")).getJSONArray("complaints");
                        for (int i = 0; i < complaintsList.length(); i++) {
                            tempComplaint = new Complains() ;
                            tempComplaint.setComplaintId(complaintsList.getJSONObject(i).getInt("complaint_id"));
                            tempComplaint.setComplainantId(complaintsList.getJSONObject(i).getLong("complainant_id"));
                            tempComplaint.setDefendantId(complaintsList.getJSONObject(i).getLong("defendant_id"));
                            tempComplaint.setComplaint(complaintsList.getJSONObject(i).getString("complaint"));

                            complainses.add(tempComplaint);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(ArrayList<Complains> myComplaintsList) {
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return complainses;
    }

    @Override
    public ArrayList<Complains> complainsUserList(long userID) {
        return list.complainsUserList(userID);
    }

    @Override
    public ArrayList<Complains> complainsAboutUserList(long userID) {
        return list.complainsAboutUserList(userID);
    }

    @Override
    public ArrayList<Complains> complainantDefendant(long complainantID, long defendantID) {
        return list.complainantDefendant(complainantID, defendantID);
    }

    @Override
    public void updateManager(final Manager manager) {
        try{
            new AsyncTask< Void,Void,Void>() {
                @SafeVarargs
                @Override
                protected final Void doInBackground(Void... params) {
                    Map<String,Object> _params = new LinkedHashMap<>();
                    _params.put("buyerId",manager.getId());
                    _params.put("firstName", manager.getFirstName());
                    _params.put("lastName", manager.getLastName());
                    _params.put("password", manager.getPassword());
                    _params.put("phoneNumber", manager.getPhoneNumber());
                    _params.put("eMail", manager.geteMail());
                    _params.put("city", manager.getCity());
                    _params.put("street", manager.getStreet());
                    _params.put("buildingNumber", manager.getBuildingNumber());
                    _params.put("houseNumber", manager.getHouseNumber());
                    _params.put("zipCode", manager.getZipCode());
                    try {
                        POST(web_url + "updateManager.php", _params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void SendEmail(List<String> email, String subject, String text) {
        list.SendEmail(email, subject, text);
    }

    @Override
    public ArrayList<Long> customersId() {
        return list.customersId();
    }

    @Override
    public void exitProgram(Activity activity) {
        list.exitProgram(activity);
    }

    @Override
    public ArrayList<ShoppingCartHelper> getCart() {
        return null;
    }

    @Override
    public ArrayList<SupplierBook> findSuplierBookById(long userId) {
        return list.findSuplierBookById(userId);
    }

    @Override
    public void addCartItem(long userId, SupplierBook sb) {

    }

    @Override
    public boolean deleteCartItem(long userId, SupplierBook sb) {
        return false;
    }
}
