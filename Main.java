

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import java.util.HashMap;
import java.util.List;

import java.io.File;



public class Main {
    public static void main(String[] args) throws DocumentException {
        File dir = new File("C:\\esx_mml"); // Folder w którym znajdują się pliki esx
        HashMap<String, HashMap> elements = new HashMap<>();
        elements.put("Infix-Term", new HashMap<String, Integer>());
        elements.put("Relation-Formula", new HashMap<String, Integer>());
        elements.put("Circumfix-Term", new HashMap<String, Integer>());

        File [] files = dir.listFiles(); // pliki esx
        SAXReader reader = new SAXReader();

        for(File file : files) {
            String name = file.getName();
            if(file.isFile() && name.endsWith(".esx") && name.startsWith("a") ) {
                Document document = reader.read(file);
                for (String el : elements.keySet()) {

                    HashMap<String, Integer> attributes_amount = elements.get(el);
                    List<Node> distinct_attr_nodes = document.selectNodes("//"+el,"@absolutepatternMMLId",true);
                    if (distinct_attr_nodes.size()>0){
                        for (int i = 0, size = distinct_attr_nodes.size(); i < size; i++) {
                            Element element = (Element) distinct_attr_nodes.get(i);
                            String attr_value = element.attributeValue("absolutepatternMMLId");
                            String search_xpath = "count(//"+el+"[@absolutepatternMMLId='"+ attr_value +"'])";
                            Integer nodes_amount = document.numberValueOf(search_xpath).intValue();

                            if (attributes_amount.containsKey(attr_value)) {
                                Integer amount = attributes_amount.get(attr_value);
                                attributes_amount.put(attr_value, amount + nodes_amount);
                            }
                            else {
                                attributes_amount.put(attr_value, nodes_amount);
                            }

                        }
                        elements.put(el, attributes_amount);
                    }


                }
                printHash(elements);
            }
        }

    }

    public static void printHash(HashMap<String, HashMap> elements) {
        for (String el : elements.keySet()) {
            System.out.println(el);
            System.out.println(elements.get(el));
        }
        System.out.println("\n\n");
    }

}
