package com.example.volleyball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PersonListAdapter extends ArrayAdapter<Person> {

    private Context mContext;
    private int mResource;
    private ArrayList<Person> mylist;
    private String docid,collectionname;

    public PersonListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects,String docid,String collectionname) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mylist = objects;
        this.docid = docid;
        this.collectionname = collectionname;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String gender = getItem(position).getGender();
        String age = getItem(position).getAge();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView Name = convertView.findViewById(R.id.tvname);
        TextView Age = convertView.findViewById(R.id.tvage);
        TextView Gender = convertView.findViewById(R.id.tvgender);
        Button Remove = convertView.findViewById(R.id.btremovedata);

        Name.setText(name);
        Age.setText(age);
        Gender.setText(gender);

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Click on: "+position,Toast.LENGTH_SHORT).show();
                Removedata rd = new Removedata(mylist,position,docid,collectionname);
                rd.findndelete();
                Person obj = rd.findndelete();
                mylist.remove(obj);
            }
        });

        return convertView;
    }
}
