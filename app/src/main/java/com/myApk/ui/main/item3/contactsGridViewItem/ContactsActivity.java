package com.myApk.ui.main.item3.contactsGridViewItem;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.logic.contactsLogic.localContactsUtils;
import com.myApk.ui.main.item3.tabHostGridViewItem.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2015/11/11.
 */
public class ContactsActivity extends Activity
{
    private Context context;
    int num = 0;
    private TextView tv_local_contacts_count;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        context = this;
        initView();
        num = localContactsUtils.getInstance(context).getLocadContactsCount();
        tv_local_contacts_count.setText(num+"");
        List<HashMap<String, String>> items = localContactsUtils.getInstance(context).fillMaps();
        for (int i =0;i < items.size();i++){
            Log.i("----->",items.get(i).get("name"));
        }
        Toast.makeText(context,items.get(1).get("name")+"",Toast.LENGTH_LONG).show();
    }

    private void initView()
    {
        tv_local_contacts_count = (TextView) findViewById(R.id.tv_local_contacts_count);
    }

    public void get()
    {
        Uri contactsUri=ContactsContract.Contacts.CONTENT_URI;
        String[] proj1=new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.LOOKUP_KEY};
        Cursor curContacts=getContentResolver().query(contactsUri,null, null, null, null);

        ArrayList<String> contactsList=new ArrayList<String>();
        String allPhoneNo="";
        if(curContacts.getCount()>0){

//            while(curContacts.moveToNext()){
//                // get all the phone numbers if exist
//                if(curContacts.getInt(1)>0){
//                    allPhoneNo=getAllPhoneNumbers(curContacts.getString(2));
//                }
//                contactsList.add(curContacts.getString(0)+" , "+allPhoneNo);
//                allPhoneNo="";
//            }
        }
       num = curContacts.getCount();
    }


    public String getAllPhoneNumbers(String lookUp_Key){
        String allPhoneNo="";

        // Phone info are stored in the ContactsContract.Data table
        Uri phoneUri=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] proj2={ContactsContract.CommonDataKinds.Phone.NUMBER};
        // using lookUp key to search the phone numbers
        String selection=ContactsContract.Data.LOOKUP_KEY+"=?";

        Cursor cur=getContentResolver().query(phoneUri,proj2,selection, new String[]{lookUp_Key}, null);
        while(cur.moveToNext()){
            allPhoneNo+=cur.getString(0)+" ";
        }

        return allPhoneNo;
    }



    private String getContents() {
        String temp= null;
        ContentResolver resolver = getContentResolver();
        // 查询联系人
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = resolver.query(contactsUri, null, null, null, null);

        List<Content> lists = new ArrayList<Content>();

        while (cursor.moveToNext()) {
            Content content = new Content();
        content.sort_key = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.SORT_KEY_PRIMARY));
//            content.link = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LI));
//        content.photo_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
//        content.send_to_voicemail = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.SEND_TO_VOICEMAIL));
//        content.contact_status = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));
//        content.contact_status_label = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_LABEL));
//
////           cursor.getString(cursor.getColumnIndex((ContactsContract.AUTHORITY_URI));
//
//        // 联系人的id
//        content._id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//        // 获取联系人的name
//        content.display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        content.name_raw_contact_id = cursor.getString(cursor.getColumnIndex((ContactsContract.Contacts.NAME_RAW_CONTACT_ID)));
        content.sort_key_alt = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.SORT_KEY_ALTERNATIVE));
        lists.add(content);
    }
        cursor.close();
        return temp;
    }

}
