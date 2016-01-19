# soapjava
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlError;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlValidator;
import com.eviware.soapui.settings.WsdlSettings;


public class SOAPUI_Test {
	WsdlProject project;
	WsdlRequest req;
	WsdlInterface wsdl;
	public SOAPUI_Test(){
		// TODO Auto-generated constructor stub
		try { 
		SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_TYPE_EXAMPLE_VALUE, true ); 
	     SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_SKIP_COMMENTS, true ); 
	     SoapUI.saveSettings();
	     project = new WsdlProject();
		 project.setName("SoapProject");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String createSoapUiProject(File wsdlPath,String opname) throws Exception {
		 WsdlInterface[] wsdls = WsdlImporter.importWsdl(project,wsdlPath.toString());
	     wsdl = wsdls[0];
	     WsdlOperation op1=wsdl.getOperationByName(opname);
	     req = op1.addNewRequest("Req_all");
	     req.setRequestContent(op1.createRequest(true));
	     //System.out.println(req.getRequestContent());
	     return req.getRequestContent();
	     
	}
	public WsdlRequest getReq() {
		return req;
	}
	public void setReq(WsdlRequest req) {
		this.req = req;
	}
	public List<XmlError> validateRequest() {
		List<XmlError> l1 = new ArrayList<XmlError>();
		WsdlValidator val = new WsdlValidator(wsdl.getWsdlContext());
		val.validateXml(req.getRequestContent(),l1);
		System.out.println("jjapanm"+l1);
		return l1;
	}
	public int generateTestCases() {
		List<XmlError> l1=this.validateRequest();
		if(l1.size()>0) {
			return -1;
		}
		
		return 0;
	}
	public static void main(String[] args) throws Exception {
		SOAPUI_Test obj = new SOAPUI_Test();
		obj.createSoapUiProject(new File("F:/Documents and Settings\\Compter\\Desktop\\mb study\\dp study\\Calculator.wsdl"),"Subtract");
	}
	

}
