import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.*;
import java.util.logging.Logger;

public class JSONaXML {
    static final String CLASS_NAME = JSONaXML.class.getSimpleName();
    static final Logger LOG = Logger.getLogger(CLASS_NAME);

    public static void main(String args[]) {
        if (args.length != 1) {
            LOG.severe("Falta archivo XML como argumento.");
            System.exit(1);
        }
        FileReader fileReader = null;
        BufferedReader in = null;
        try {
            fileReader = new FileReader("sales.json");
            in = new BufferedReader(fileReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        String jsonString = null;
        try {
            PrintWriter output = new PrintWriter("output.xml");

            Sale saleObject = null;
            while ((jsonString = in.readLine()) != null) {
                saleObject = gson.fromJson(jsonString, Sale.class);
                System.out.println(saleObject);

                output.print(saleObject);
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public class Sale {

        int id;
        String first_name;
        String last_name;

        double sales;
        String state;

        String department;

        public Sale(int id, String first_name, String last_name, double sales, String state, String department) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.sales = sales;
            this.state = state;
            this.department = department;
        }

        @Override
        public String toString() {
            return "<sale_record>\n" +
                    "<id>" + id + "</id>\n"+
                    "<firstName>" + first_name + "</firstName>\n"+
                    "<lastName>" + last_name +  "</lastName>\n" +
                    "<sales>" + sales + "</sales>\n" +
                    "<state>" + state + "</state>\n" +
                    "<department>" + department + "</department>\n" +
                    "</sale_record>\n";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return first_name;
        }

        public void setFirstName(String first_name) {
            this.first_name = first_name;
        }

        public String getLastName() {
            return last_name;
        }

        public void setLastName(String last_name) {
            this.last_name = last_name;
        }

        public double getSales() {
            return sales;
        }

        public void setSales(double sales) {
            this.sales = sales;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}