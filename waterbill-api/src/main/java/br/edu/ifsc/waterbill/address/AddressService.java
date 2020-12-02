package br.edu.ifsc.waterbill.address;

/*
@author Guilherme Pereira
 */

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    private static String readAll(Reader rd) throws IOException{
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1){
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = readAll(rd);
            JSONObject cepJson = new JSONObject(jsonText);
            return cepJson;
        }finally{
            is.close();
        }
    }

    public JSONObject createAddress(int cep) throws IOException, JSONException {
        String url = "https://viacep.com.br/ws/"+cep+"/json/";
        JSONObject json = readJsonFromUrl(url);
        return json;
    }

    public void saveAddress(Address address){
        addressRepository.save(address);
    }

    public void deleteAddress(Address address){ addressRepository.delete(address);}



}





