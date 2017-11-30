package com.myApk.ui.main.item3.tabHostGridViewItem;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.myApk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/10/21.
 */
public class JYActivity extends Activity implements  View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jy);
        findViewById(R.id.btn_go_system_content_edit).setOnClickListener(this);
        findViewById(R.id.btn_go_system_content_insert).setOnClickListener(this);
        findViewById(R.id.btn_go_system_content_select).setOnClickListener(this);

    }

    private void goSystemEdit(String temp) {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts/"
                + temp + "?caller_is_syncadapter=true");
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setData(uri);
        i.putExtra("finishActivityOnSaveCompleted", true);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(i, 0);
        for (int j = 0; j < list.size(); j++)
        {
            String packageName = list.get(j).activityInfo.packageName;
            if ("com.android.contacts".equalsIgnoreCase(packageName))
            {
                i.setPackage(packageName);
                break;
            }
        }
        startActivity(i);
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
            content.photo_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
            content.send_to_voicemail = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.SEND_TO_VOICEMAIL));
            content.contact_status = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));
            content.contact_status_label = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_LABEL));

//           cursor.getString(cursor.getColumnIndex((ContactsContract.AUTHORITY_URI));

            // 联系人的id
            content._id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // 获取联系人的name
            content.display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            content.name_raw_contact_id = cursor.getString(cursor.getColumnIndex((ContactsContract.Contacts.NAME_RAW_CONTACT_ID)));
            content.sort_key_alt = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.SORT_KEY_ALTERNATIVE));


            lists.add(content);
        }
        cursor.close();
        return temp;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_go_system_content_edit:
                String temp = getContents();
                goSystemEdit(temp);
            break;
            case R.id.btn_go_system_content_insert:
                goSystemInsert();
                break;
            case R.id.btn_go_system_content_select:
                getContents();
                break;
        }
    }

    private void goSystemInsert() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.dir/person");
        intent.setType("vnd.android.cursor.dir/contact");
        intent.setType("vnd.android.cursor.dir/raw_contact");
//        // 添加姓名
//        intent.putExtra(Contacts.Intents.Insert.NAME,"王梨");
//        // 添加手机
//        intent.putExtra(Contacts.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
//        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "18956894589");
//        intent.putExtra(ContactsContract.Intents.Insert.IM_PROTOCOL, ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ);
//        intent.putExtra(ContactsContract.Intents.Insert.IM_PROTOCOL,"444255655");
        startActivity(intent);
    }
}
