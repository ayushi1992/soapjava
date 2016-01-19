# soapjava


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

class m100Generator {
	 public  String prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        //System.out.println(out.toString());
        return out.toString();
 	}
	 public void initialCleaning(File inputFile,File cleanInput) throws Exception {
		 Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(cleanInput));
		 transformer.transform(new StreamSource(inputFile), new StreamResult(new File("file1.xml")));
	 }
	 public void finalCleaning(Document xml) {
		 
	 }
	public void saveFile(Document doc) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("file.xml"));
		transformer.transform(source, result);

		System.out.println("File saved!");
	}
	public void generateM100(Document inputDoc, String tdd) throws Exception {
		BufferedReader br =  new BufferedReader(new FileReader(tdd));
		String line = "";
		String cvsSplitBy = ",";
		int attr=0;
		Document outputDoc = null;
		while ((line = br.readLine()) != null) {
			attr++;
			String[] country = line.split(cvsSplitBy);
			
			if(country.length > 5) {
				String in = country[5];
				Pattern p = Pattern.compile("\\<(.*?)[\\>\\s]");
				Matcher m = p.matcher(in);
				String xpath= new String("/envelope/body/operationName");
				while(m.find()) {
					xpath = xpath + "/"+m.group(1);
				}
				System.out.println(xpath);
				XPath xPath =  XPathFactory.newInstance().newXPath();
				NodeList nodeList = (NodeList) xPath.compile(xpath).evaluate(inputDoc, XPathConstants.NODESET);
				System.out.println(nodeList.getLength());
				if(nodeList.getLength()==1)
					if(nodeList.item(0).getChildNodes().getLength()<=1) //deals with length 0 or 1 (i.e., ?)
						if((nodeList.item(0).getTextContent().length())<=1)
							nodeList.item(0).setTextContent("attr"+attr);
						else nodeList.item(0).setTextContent(nodeList.item(0).getTextContent()+";attr"+attr);
					else
						System.out.println("path contains multiple nodes"); //else path is wrong ; path contains multiple nodes
				else //else multiple path exist or path doesnot exist ; multiple : ideally not possible; no path : error
					System.out.println("either mulitple path exist or path doesnt exist"); 
			}
		}
		prettyPrint(inputDoc);
		System.out.println("outputDoc\n");
		prettyPrint(outputDoc);
		//saveFile(inputDoc);
	}
	
}
public class m100GeneratorTest {
	public static void main(String[] args) throws Exception {
		m100Generator obj = new m100Generator();
		File inputFile = new File("input.xml");
		File cleanInput = new File("cleaner.xsl");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document inputDoc=dBuilder.parse(inputFile); 
		obj.generateM100(inputDoc,"tdd.csv");
		File inputFile1 = new File("file.xml");
		obj.initialCleaning(inputFile1,cleanInput);
		
	}
}
