//package com.example.as.waluty;
//
//import android.app.Activity;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Created by as on 2016-12-28.
// */
//public class MyListAdapter extends ArrayAdapter<String>{
//    private final Activity context;
//    private final Integer[] progImages;
//    ArrayList<HashMap<String, String>> contactList5;
//
//
//    public MyListAdapter(Activity context,Integer[] progImages) {
//        super(context, R.layout.list_item);
//        this.context = context;
//        this.progImages = progImages;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater inflater = context.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.list_item,null,true);
//
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
//
//        TextView name = (TextView) rowView.findViewById(R.id.name);
//        TextView email = (TextView) rowView.findViewById(R.id.email);
//        TextView mobile = (TextView) rowView.findViewById(R.id.mobile);
//
//        ////////////////////////////
//        contactList5 = new ArrayList<>();
////        DBHelper mydb = new DBHelper(this);
//        Cursor mCursor = mydb.getLastData();
//        contactList5 = new ArrayList<>();
//        mCursor.moveToFirst();
//        while(!mCursor.isAfterLast()) {
//            //HashMap<String, String> contact2 = new HashMap<>();
//            String currency = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CURRENCY));
//            String code = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CODE));
//            String mid = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_MID));
////            contact2.put("currency", currency);
////            contact2.put("code", code);
////            contact2.put("mid", mid);
////            contactList5.add(contact2);
//            mCursor.moveToNext();
//
//            name.setText(currency);
//            email.setText(code);
//            mobile.setText(mid);
//            imageView.setImageResource(progImages[position]);
//        }
//
////            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList3,
////                    R.layout.list_item, new String[]{"currency", "code", "mid"},
////                    new int[]{R.id.name, R.id.email, R.id.mobile});
////            lv.setAdapter(adapter);
//
//        return rowView;
//    }
//}
