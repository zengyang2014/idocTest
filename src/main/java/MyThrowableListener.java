import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import com.sap.conn.jco.server.JCoServerExceptionListener;

class MyThrowableListener implements JCoServerErrorListener, JCoServerExceptionListener {
    @Override
    public void serverErrorOccurred(JCoServer jCoServer, String s, JCoServerContextInfo jCoServerContextInfo, Error error) {
        System.out.println(">>> Error occured on " + jCoServer.getProgramID() + " connection " + jCoServerContextInfo.getConnectionID());
        error.printStackTrace();
    }

    @Override
    public void serverExceptionOccurred(JCoServer jCoServer, String s, JCoServerContextInfo jCoServerContextInfo, Exception error) {
        System.out.println(">>> Error occured on " + jCoServer.getProgramID() + " connection " + jCoServerContextInfo.getConnectionID());
        error.printStackTrace();
    }
}