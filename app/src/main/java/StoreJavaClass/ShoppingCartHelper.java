package StoreJavaClass;

import android.content.res.Resources;

import com.example.aviga_000.bookstoreandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by User on 07/01/2016.
 */
public class ShoppingCartHelper {
    //public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    //private static List<SupplierBook> catalog;
    private static ArrayList<SupplierBook> cart ;
    private long id;

    //constructure
    public ShoppingCartHelper (long idUser){
        id = idUser;
        cart = new ArrayList<SupplierBook>();
    };

    //getter
    public long getId ()
    {
        return id;
    }

    public ArrayList<SupplierBook> getSupplierBookListCart ()
    {
        return cart;
    }

    public static List<SupplierBook> getCatalog(Resources res){
//        if(catalog == null) {
//            catalog = new Vector<Product>();
//            catalog.add(new Product("Dead or Alive", res
//                    .getDrawable(R.drawable.deadoralive),
//                    "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
//            catalog.add(new Product("Switch", res
//                    .getDrawable(R.drawable.switchbook),
//                    "Switch by Chip Heath and Dan Heath", 24.99));
//            catalog.add(new Product("Watchmen", res
//                    .getDrawable(R.drawable.watchmen),
//                    "Watchmen by Alan Moore and Dave Gibbons", 14.99));
//        }
//
//        return catalog;
        return null;
    }

    public  static void addBookToShoppingCart (SupplierBook sp)
    {
        if (sp == null)
            return;
        cart.add(sp);
    }

    public  static void deleteBookToShoppingCart (ArrayList<SupplierBook> spl)
    {
        if (cart.isEmpty() || spl.isEmpty())
            return;
        else
            cart.removeAll(spl); //delete from cart the chosen book that in spl list
    }

}
