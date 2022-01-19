

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import java.util.List;

import java.io.File;

public class Main {
    public static void main(String[] args) throws DocumentException{
        String path_to_xml = "jordan.esx";
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(path_to_xml));
        List<Node> list_of_nodes = document.selectNodes("//Infix-Term","@absolutepatternMMLId",true);
        for (int i = 0, size = list_of_nodes.size(); i < size; i++) {
            Element element = (Element) list_of_nodes.get(i);
            String attr_value = element.attributeValue("absolutepatternMMLId");
            String search_xpath = "count(//Infix-Term[@absolutepatternMMLId='"+ attr_value +"'])";
            Number nodes_amount = document.numberValueOf(search_xpath);
            System.out.println(attr_value + " | " + nodes_amount.intValue());
        }
    }
}
