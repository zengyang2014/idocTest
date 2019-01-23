import java.util.HashMap;
import java.util.Properties;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class CustomDestinationDataProvider
{
    static class MyDestinationDataProvider implements DestinationDataProvider
    {
        private DestinationDataEventListener eL;
        private HashMap<String, Properties> secureDBStorage = new HashMap<String, Properties>();

        public Properties getDestinationProperties(String destinationName)
        {
            try
            {
                Properties p = secureDBStorage.get(destinationName);

                if(p!=null)
                {
                    //check if all is correct, for example
                    if(p.isEmpty())
                        throw new DataProviderException(DataProviderException.Reason.INVALID_CONFIGURATION, "destination configuration is incorrect", null);

                    return p;
                }

                return null;
            }
            catch(RuntimeException re)
            {
                throw new DataProviderException(DataProviderException.Reason.INTERNAL_ERROR, re);
            }
        }

        public void setDestinationDataEventListener(DestinationDataEventListener eventListener)
        {
            this.eL = eventListener;
        }

        public boolean supportsEvents()
        {
            return true;
        }

        void changeProperties(String destName, Properties properties)
        {
            synchronized(secureDBStorage)
            {
                if(properties==null)
                {
                    if(secureDBStorage.remove(destName)!=null)
                        eL.deleted(destName);
                }
                else
                {
                    secureDBStorage.put(destName, properties);
                    eL.updated(destName);
                }
            }
        }
    }

    //business logic
    private void executeCalls(String destName)
    {
        JCoDestination dest;
        try
        {
            dest = JCoDestinationManager.getDestination(destName);
            dest.ping();
            System.out.println("Destination " + destName + " works");
        }
        catch(JCoException e)
        {

            e.printStackTrace();
            System.out.println("Execution on destination " + destName+ " failed");
        }

    }

    private static Properties getDestinationPropertiesFromLocal()
    {
        //adapt parameters in order to configure a valid destination
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, System.getenv("JCO_ASHOST"));
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  System.getenv("JCO_SYSNR"));
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, System.getenv("JCO_CLIENT"));
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   System.getenv("JCO_USER"));
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, System.getenv("JCO_PASSWD"));
        connectProperties.setProperty(DestinationDataProvider.JCO_GROUP, System.getenv("JCO_GROUP"));
        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME, System.getenv("JCO_R3NAME"));
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   System.getenv("JCO_LANG"));

        return connectProperties;
    }

    static void setData(String destName)
    {

        MyDestinationDataProvider myProvider = new MyDestinationDataProvider();

        try
        {
            com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);

        }
        catch(IllegalStateException providerAlreadyRegisteredException)
        {
            throw new Error(providerAlreadyRegisteredException);
        }

        CustomDestinationDataProvider test = new CustomDestinationDataProvider();

        //set properties for the destination
        myProvider.changeProperties(destName, getDestinationPropertiesFromLocal());
//        test.executeCalls(destName);

        //now remove the properties
//        myProvider.changeProperties(destName, null);
//        test.executeCalls(destName);
    }

}
