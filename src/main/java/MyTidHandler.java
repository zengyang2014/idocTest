import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerTIDHandler;

class MyTidHandler implements JCoServerTIDHandler {
    public boolean checkTID(JCoServerContext serverCtx, String tid) {
        System.out.println("checkTID called for TID=" + tid);
        return true;
    }

    public void confirmTID(JCoServerContext serverCtx, String tid) {
        System.out.println("confirmTID called for TID=" + tid);
    }

    public void commit(JCoServerContext serverCtx, String tid) {
        System.out.println("commit called for TID=" + tid);
    }

    public void rollback(JCoServerContext serverCtx, String tid) {
        System.out.print("rollback called for TID=" + tid);
    }
}