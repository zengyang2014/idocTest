import com.sap.conn.idoc.IDocDocumentList;
import com.sap.conn.idoc.jco.JCoIDocHandler;
import com.sap.conn.jco.server.JCoServerContext;

class MyIDocHandler implements JCoIDocHandler {
    public void handleRequest(JCoServerContext serverCtx, IDocDocumentList idocList) {
        System.out.println("============== IDOC ID==================");
        System.out.println(serverCtx.getTID());
        System.out.println("============== IDOC ==================");
        System.out.println(idocList);
    }
}