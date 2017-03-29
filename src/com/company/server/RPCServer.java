//package server;
//
//import core.DomainObject;
//import core.IHandler;
//import org.apache.xmlrpc.XmlRpcException;
//import org.apache.xmlrpc.server.PropertyHandlerMapping;
//import org.apache.xmlrpc.server.XmlRpcStreamServer;
//import org.apache.xmlrpc.webserver.WebServer;
//
//import java.io.IOException;
//import java.io.ObjectInput;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
//class CallHistory {
//  private static ArrayList<String> history = new ArrayList<String>();
//  private static CallHistory instance = null;
//  private static HashMap<Double, String> objects = new HashMap<Double, String>();
//
//
//  static synchronized CallHistory getInstance() {
//    if(instance == null) {
//      instance = new CallHistory();
//    }
//    return instance;
//  }
//
//  static synchronized String put(Double number, String s){
//    if(objects.containsKey(number)){
//      System.out.println("403 ");
//      return "403, Forbidden";
//    }
//    if(number != null){
//      objects.put(number, s);
//      System.out.println("dodano "+ number+ " "+ s);
//      return "200, OK";
//    }
//
//    System.out.println("ty ");
//    return "500, Bad Gateway";
//  }
//
//  static synchronized Object get(int index){
//    if(!objects.isEmpty() && objects.get(index) != null) {
//        System.out.println("pobrano " + objects.get(index));
//        return "200, OK";
//    }
//
//    return "404, Not found";
//  }
//
//  static synchronized Object delete(int index){
//    if(!objects.isEmpty() && objects.containsKey(index) != null) {
//        objects.remove(index);
//        System.out.println("usunieto");
//        return "200, OK";
//    }
//
//    return "404, Not Found";
//  }
//
//
//  synchronized void addToHistory(String s) {
//    history.add(s);
//    System.out.println(String.format("Wywołano już RPCServer %d razy", history.size()));
//  }
//
//  private CallHistory() {}
//}
//
//
//
//public class RPCServer implements IHandler {
//
//  public String addHello(String s) {
//    CallHistory.getInstance().addToHistory(s);
//    String result = "hello " + s;
//    System.out.println(String.format("Thread %d: %s", Thread.currentThread().getId(), result));
//    return result;
//  }
//
//  public Object put(Double number, String s ){
//    return CallHistory.put(number, s);
//  }
//
//  public Object get(int index){
//    return CallHistory.get(index);
//  }
//
//  public Object delete(int index){
//    return CallHistory.delete(index);
//  }
//
//
//  // przykład serializacji własnego obiektu
//  public DomainObject toDomainObject(String s) {
//    return new DomainObject(s);
//  }
//
//  // to tylko uproszczony przykład dydaktyczny, w ramach którego ignorujemy błędy komunikacji
//  // dlatego wszystkie możliwe wyjątki propagują się aż tutaj
//  // w rzeczywistym kodzie należy oczywiście wyjątki obsłużyć najszybciej jak tylko ma to sens
//  public static void main(String[] args) throws XmlRpcException, IOException {
//
//    WebServer server = new WebServer(10000);
//    XmlRpcStreamServer rpcServer = server.getXmlRpcServer();
//    PropertyHandlerMapping handlers = new PropertyHandlerMapping();
//    handlers.addHandler(IHandler.class.getName(), RPCServer.class);
//    handlers.addHandler("babkazpiasku", RPCServer.class);
//    rpcServer.setHandlerMapping(handlers);
//
//
//    System.out.println("Startujemy serwer!");
//    server.start();
//  }
//}
