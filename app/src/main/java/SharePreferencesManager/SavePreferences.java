package SharePreferencesManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.MainActivity;
import com.example.malthshop.R;

import Login.LoginActivity;
import Login.RegisterActivity;
import ModelHome.Customer;

public class SavePreferences {
    static final String PREF_CUSTOMER_NAME = "USER_NAME_CUSTOMER";
    static final String PREF_CUSTOMER_PASS = "PASSWORD_CUSTOMER";

    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, String username, String password){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_CUSTOMER_NAME, username);
        editor.putString(PREF_CUSTOMER_PASS, password);
        editor.commit();
    }

    public static Customer getUser(Context context){
        Customer customer = new Customer();
        customer.setUsername(getSharedPreferences(context).getString(PREF_CUSTOMER_NAME,""));
        customer.setPassword(getSharedPreferences(context).getString(PREF_CUSTOMER_PASS,""));
        return customer;
    }

    public static void exchangeActivity(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
        ((ActivityShowProduct) context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
    public static void clear(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear().commit();
    }
}
