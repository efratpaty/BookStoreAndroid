package model.datasource;

import android.content.Context;

import model.backend.PoolFunctions;

/**
 * Created by aviga_000 on 03/01/2016.
 */
public class BackendFactory {
    static PoolFunctions instance = null;

    public static String mode = "mysql";

    public final static PoolFunctions getInstance(Context context)
    {
        if (mode == "lists")
        {
            if (instance == null)
                instance = new model.datasource.StoreList(context);
            return instance;
        }


        if (mode == "mysql")
        {
            if (instance == null)
                instance = new model.datasource.StoreMySql(context);
            return instance;
        }
        else return null;
    }
}
