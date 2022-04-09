import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.logging.Logger;

public class XMLaJSON extends DefaultHandler {
    private static final String CLASS_NAME = XMLaJSON.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    private SAXParser parser = null;
    private SAXParserFactory spf;

    private  double totalSales;
    private boolean inRecordSales;
    private boolean inSales;
    private boolean inId;
    private boolean inFirstName;
    private boolean inState;
    private boolean inDepartament;

    private String curreElement;
    private JsonArray array;
    private JsonObject jsonObject;

    public XMLaJSON(){
        super();
        spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);


    }

    public static void main(String[] args) {
        if (args.length==0){
            LOG.severe("No file to process. Usage is:" + "\n java XMLaJSON <filename>");
            return;
        }
        File
    }
}
