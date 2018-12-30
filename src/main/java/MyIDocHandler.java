import com.sap.conn.idoc.IDocDocumentList;
import com.sap.conn.idoc.IDocXMLProcessor;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.idoc.jco.JCoIDocHandler;
import com.sap.conn.jco.server.JCoServerContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

class MyIDocHandler implements JCoIDocHandler {
    public void handleRequest(JCoServerContext serverCtx, IDocDocumentList idocList) {

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            IDocXMLProcessor xmlProcessor =
                    JCoIDoc.getIDocFactory().getIDocXMLProcessor();
            fos = new FileOutputStream(serverCtx.getTID() + "_idoc.xml");
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            xmlProcessor.render(idocList, osw,
                    IDocXMLProcessor.RENDER_WITH_TABS_AND_CRLF);
            osw.flush();
        } catch (Throwable thr) {
            thr.printStackTrace();
        } finally {
            try {
                if (osw != null)
                    osw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}