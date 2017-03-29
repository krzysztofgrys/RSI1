package com.company.client;

import core.IHandler;
import model.Book;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.AsyncCallback;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

import java.net.URL;
import java.util.Arrays;

/**
 * Created by krzysztofgrys on 3/28/17.
 */
public class Client {
    public static void main(String[] args) throws Throwable {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://localhost:10000"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(1000);

        XmlRpcClient client = new XmlRpcClient();

        client.setConfig(config);




        long start = System.currentTimeMillis();
//    for(int i=0;i<1000;i++){
//      client.execute("core.IHandler.put", Arrays.asList(50.4));
//      client.execute("core.IHandler.get", Arrays.asList(0));
//      client.execute("core.IHandler.delete", Arrays.asList(0));
//      client.execute("core.IHandler.get", Arrays.asList(0));
//    }
        long elapsedTime = System.currentTimeMillis() - start;
//
//    System.out.println(elapsedTime/1000F);

        // Co dostaliśmy w wyniku? powinno być hello world
//    System.out.println(String.format("Z serwera miało przyjść hello world a przyszło: %s", (String)result));
//
        // sprawdźmy też czy działają inne przestrzenie nazw dla funkcji!
//    Object babkaResult = client.execute("babkazpiasku.addHello", Arrays.asList("universe"));

        // Co dostaliśmy w wyniku? powinno być hello universe
//    System.out.println(String.format("Z serwera miało przyjść hello universe a przyszło: %s", (String)babkaResult));

        // ok, ale tracimy mnóstwo bezpieczeństwa wynikającego z typów. Czy można inaczej?
        // nie pytałbym gdyby nie można było, prawda?

        // fabryka klientów, podajemy prototyp jako argument
        ClientFactory factory = new ClientFactory(client);


        // warunek - musimy mieć wydzielony interfejs, więc utworzyliśmy IHandler
        IHandler handler = (IHandler)factory.newInstance(IHandler.class);

        start = System.currentTimeMillis();

        for(int i=0;i<1000;i++){
            handler.putPublic(new Book("pawlacz","jak rozpetalem "));
            handler.get("pawlacz",i);
        }
        elapsedTime = System.currentTimeMillis() - start;

        System.out.println(elapsedTime/1000F);



    }
}
