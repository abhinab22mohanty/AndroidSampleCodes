package org.intracode.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText lblName,lblPhone, lblemail, lblAddress;

    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lblName = (EditText) findViewById(R.id.lblName);
        lblPhone = (EditText) findViewById(R.id.lblPhone);
        lblemail = (EditText) findViewById(R.id.lblemail);
        lblAddress = (EditText) findViewById(R.id.lblAddress);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        contactListView = (ListView) findViewById(R.id.listView);


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("store");
        tabSpec.setContent(R.id.tabStorage);
        tabSpec.setIndicator("Store");
        tabHost.addTab(tabSpec);





            final Button addbtn = (Button) findViewById(R.id.btnContact);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(lblName.getText().toString(), lblPhone.getText().toString(), lblemail.getText().toString(), lblAddress.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(),"Contact has been added",Toast.LENGTH_SHORT).show();
            }
        });

        lblName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addbtn.setEnabled(!lblName.getText().toString().trim().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }

    private void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);

    }


    private void addContact(String name, String phone, String email, String address){
        Contacts.add(new Contact(name, phone, email, address));

    }


    private class ContactListAdapter extends ArrayAdapter<Contact>{
        public ContactListAdapter(){
            super(MainActivity.this, R.layout.listview_item,Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);



            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.get_name());
            TextView phone = (TextView) view.findViewById(R.id.contactPhone);
            phone.setText(currentContact.get_phone());
            TextView email = (TextView) view.findViewById(R.id.contactEmail);
            email.setText(currentContact.get_email());
            TextView address = (TextView) view.findViewById(R.id.contactAddress);
            address.setText(currentContact.get_address());

            return view;
        }
    }
}
