import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.idoc.jco.JCoIDocServer;

public class IDocServerExample {
    public static void main(String[] a) {
        try {
            // see examples of configuration files MYSERVER.jcoServer and  BCE.jcoDestination provided in the installation directory.
            CustomDestinationDataProvider.setData("NPL");
            JCoIDocServer server = JCoIDoc.getServer("MYSERVER");
            server.setIDocHandlerFactory(new MyIDocHandlerFactory());
            server.setTIDHandler(new MyTidHandler());

            MyThrowableListener listener = new MyThrowableListener();
            server.addServerErrorListener(listener);
            server.addServerExceptionListener(listener);
            server.setConnectionCount(1);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
