package com.company.server;

import com.company.model.Book;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by krzysztofgrys on 3/28/17.
 */

class Library {
    private static HashMap<Integer, Book> books = new HashMap<Integer, Book>();
    private static HashMap<Integer, String> owner = new HashMap<Integer, String>();
    private static Library instance = null;
    private static int id = 0;

    static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    static synchronized int putPrivate(Book book, String o) {
        books.put(id, book);
        owner.put(id, o);
        id++;

        return id - 1;
    }

    static synchronized int putPublic(Book book) {
        books.put(id, book);
        id++;

        return id - 1;
    }

    static synchronized Book get(String user, int id) {
        if (!books.containsKey(id)) {
            return null;
        }
        if (!booksHasOwner(id)) {
            return books.get(id);
        }
        if (checkOwner(id, user)) {
            return books.get(id);
        }

        return null;
    }


    static synchronized boolean delete(String user, int id) {
        if (!books.containsKey(id)) {
            return false;
        }
        if (!booksHasOwner(id)) {
            books.remove(id);
            return true;
        }
        if (checkOwner(id, user)) {
            books.remove(id);
            return true;
        }

        return false;
    }

    private static boolean booksHasOwner(int id) {
        return books.containsKey(id);
    }

    private static boolean checkOwner(int id, String user) {
        return books.containsKey(id) && owner.containsKey(id) && owner.get(id) == user;
    }
}

public class BookshelfServer implements IHandler{
    public int putPrivate(Book book, String ownerName) {
        return Library.putPrivate(book, ownerName);
    }

    public int putPublic(Book book) {
        return Library.putPublic(book);

    }

    public Book get(String user, int id) {
        return Library.get(user, id);
    }

    public boolean delete(String user, int id) {
        return Library.delete(user,id);
    }

    public DomainObject toDomainObject(String s) {
        return new DomainObject(s);
    }

    public static void main(String[] args) throws XmlRpcException, IOException {
        Library.getInstance();
        WebServer server = new WebServer(10000);
        XmlRpcStreamServer rpcServer = server.getXmlRpcServer();
        PropertyHandlerMapping handlers = new PropertyHandlerMapping();
        handlers.addHandler(IHandler.class.getName(), BookshelfServer.class);
        handlers.addHandler("babkazpiasku", BookshelfServer.class);
        rpcServer.setHandlerMapping(handlers);

        System.out.println("Startujemy serwer!");
        server.start();
    }
}
