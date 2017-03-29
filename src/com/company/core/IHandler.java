package core;


import model.Book;

public interface IHandler {

  int putPrivate(Book book, String ownerName);
  int putPublic(Book book);
  Object get(String user, int id);
  boolean delete(String user, int id);

}