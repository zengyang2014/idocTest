import com.sap.conn.idoc.jco.JCoIDocHandler;
import com.sap.conn.idoc.jco.JCoIDocHandlerFactory;
import com.sap.conn.idoc.jco.JCoIDocServerContext;

public class MyIDocHandlerFactory implements JCoIDocHandlerFactory {
    private JCoIDocHandler handler = new MyIDocHandler();

    public JCoIDocHandler getIDocHandler(JCoIDocServerContext serverCtx) {
        return handler;
    }
}