package org.intracode.contactmanager;

public class Contact {

    private String _name, _phone, _email, _address;

    public Contact(String name, String phone, String email, String address){

        _name = name;
        _address = address;
        _phone = phone;
        _email = email;

    }

    public String get_name() {
        return _name;
    }

    public String get_phone() {
        return _phone;
    }

    public String get_email() {
        return _email;
    }

    public String get_address() {
        return _address;
    }
}
