

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import java.util.HashMap;
import java.util.List;

import java.io.File;



public class Main {
    public static void main(String[] args) throws DocumentException {
        File dir = new File("C:\\esx_mml"); // Folder w którym znajdują się pliki esx

        File [] files = dir.listFiles(); // pliki esx
        SAXReader reader = new SAXReader();

        HashMap<String, Integer> attributes_amount = new HashMap<String, Integer>();
        System.out.println("liczba plików do przetworzenia: "+ files.length);
        for(File file : files) {
            if(file.isFile() && file.getName().endsWith(".esx")) {
                Document document = reader.read(file);
                List<Node> distinct_attr_nodes = document.selectNodes("//Infix-Term","@absolutepatternMMLId",true);
                if (distinct_attr_nodes.size()>0){
                    for (int i = 0, size = distinct_attr_nodes.size(); i < size; i++) {
                        Element element = (Element) distinct_attr_nodes.get(i);
                        String attr_value = element.attributeValue("absolutepatternMMLId");
                        String search_xpath = "count(//Infix-Term[@absolutepatternMMLId='"+ attr_value +"'])";
                        Integer nodes_amount = document.numberValueOf(search_xpath).intValue();

                        if (attributes_amount.containsKey(attr_value)) {
                            Integer amount = attributes_amount.get(attr_value);
                            attributes_amount.put(attr_value, amount + nodes_amount);
                        }
                        else {
                            attributes_amount.put(attr_value, nodes_amount);
                        }
                    }
                }
            }
        }

        System.out.println(attributes_amount);
    }
}
