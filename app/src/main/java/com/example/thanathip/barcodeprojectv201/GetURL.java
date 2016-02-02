package com.example.thanathip.barcodeprojectv201;

/**
 * Created by thanathip on 5/1/2559.
 */
public class GetURL {


//this is method for get update url in server-pug server (192.168.0.99)
    public String UpdateLink(){
        return "http://192.168.0.99/main/tablet/forming/update_database.php";
    }


//this is method for get select list in drawer url in server-pug server(192.168.0.99)
    public String SelectList(){
        return "http://192.168.0.99/main/tablet/forming/select_list.php";
    }
}
