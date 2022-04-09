import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    private boolean inLastName;
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
        File xmlFile = new File(args[0]);
        XMLaJSON handler = new XMLaJSON();
        handler.process(xmlFile);

        try{
            PrintWriter output =new PrintWriter("output.json");
            JsonArray array = handler.getArray();
            String jsonDoc = array.toString();
            output.print(jsonDoc);
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void process(File file){
        try {

            parser = spf.newSAXParser();
            LOG.info("Parser object is: " + parser);
        }catch (SAXException | ParserConfigurationException e){
            LOG.severe(e.getMessage());
            System.exit(1);
        }
        System.out.println("\nStarting parsing of " + file + "\n");
        try {
            parser.parse(file, this);
        }catch (IOException | SAXException e){
            LOG.severe(e.getMessage());
        }

    }

    public JsonArray getArray() {
        return array;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName){

            case "sale_record":
                inRecordSales = true;
                jsonObject = new JsonObject();
                break;
            case "id":
                inId = true;
                break;
            case "first_name":
                inFirstName = true;
                break;
            case "last_name":
                inLastName = true;
                break;
            case "sales":
                inSales = true;
                break;
            case "state":
                inState = true;
                break;
            case "department":
                inDepartament = true;
                break;
        }
        curreElement = localName;
    }

    @Override
    public void characters(char[] bytes, int start, int length) throws SAXException {
        String data = new String(bytes, start, length);

        switch (curreElement){
            case "id":
                inId = false;
                jsonObject.addProperty(curreElement, Integer.valueOf(data));
                break;
            case "first_name":
                inFirstName = false;
                jsonObject.addProperty(curreElement, data);
                break;
            case "last_name":
                inLastName = false;
                jsonObject.addProperty(curreElement, data);
                break;
            case "sales":
                inSales = false;
                jsonObject.addProperty(curreElement, Double.valueOf(data));
                break;
            case "state":
                inState = false;
                jsonObject.addProperty(curreElement, data);
                break;
            case "department":
                inDepartament = false;
                jsonObject.addProperty(curreElement, data);
                break;
        }

    }

    @Override
    public void startDocument() throws SAXException {
        array = new JsonArray();
    }

    @Override
    public void endDocument() throws SAXException {
        String jsonDoc = array.toString();
        System.out.print(jsonDoc);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("sale_record")){
            array.add(jsonObject);
            inSales = false;

        }
    }
}
