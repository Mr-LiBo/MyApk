package com.myApk.logic.contactsLogic;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by admin on 2015/11/11.
 */
public class localContactsUtils {
    private static Context context;
    private static localContactsUtils utils = null;
    private static String[] simSelectionArgs;

    public static localContactsUtils getInstance(Context con) {
        context = con;
        if (utils == null) {
            utils = new localContactsUtils();
        }
        return utils;
    }


    /**
     * 获取本地联系人数量
     * @return
     */
    public int getLocadContactsCount()
    {
        int result =-1;
        Uri contactsUri=ContactsContract.Contacts.CONTENT_URI;
//        String[] proj1=new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER, ContactsContract.Contacts.LOOKUP_KEY};
        Cursor curContacts=context.getContentResolver().query(contactsUri,null, null, null, null);
        while(curContacts.moveToNext()){
            result = curContacts.getCount();
        }
        return result;
    }

    public String getAllPhoneNumbers(String lookUp_Key){
        String allPhoneNo="";

        // Phone info are stored in the ContactsContract.Data table
        Uri phoneUri=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] proj2={ContactsContract.CommonDataKinds.Phone.NUMBER};
        // using lookUp key to search the phone numbers
        String selection=ContactsContract.Data.LOOKUP_KEY+"=?";

        Cursor cur= context.getContentResolver().query(phoneUri,proj2,selection, new String[]{lookUp_Key}, null);
        while(cur.moveToNext()){
            allPhoneNo+=cur.getString(0)+" ";
        }

        return allPhoneNo;
    }

    public List<HashMap<String, String>> fillMaps()
    {
        List<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
        Cursor cur = null;
        try {
            cur =context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "sort_key_alt asc");
            if (cur.moveToFirst())
            {
                do {
                    String phoneNumber = "";
                    String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String displayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    int numberCount = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (numberCount > 0)
                    {
                        Cursor phones =context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,null,"null");
                        if (phones.moveToFirst()) {
                            int numberColumn = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            do {
                                phoneNumber += phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + ",";
                            } while (phones.moveToNext());
                        }
                    }
                    HashMap<String, String> i = new HashMap<String, String>();
                    i.put("name", displayName);
                    i.put("key", phoneNumber);
                    items.add(i);
                } while (cur.moveToNext());
            } else {
                HashMap<String, String> i = new HashMap<String, String>();
                i.put("name", "Your Phone");
                i.put("key", "Have No Contacts.");
                items.add(i);
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return items;
    }
}
