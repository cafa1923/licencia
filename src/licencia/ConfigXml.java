package licencia;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigXml {
	Encript encriptado;
	private String id="";
	private LocalDateTime fActivacion;
	private LocalDateTime fechaCancelacion;
	private LocalDateTime fechaActualizacion;
	private String nombreApp;
	private String userAdmin;
	private String passAdmin;
	private String userUser;
	private String passUser;
	public ConfigXml() {
		encriptado=new Encript();
	}


	
	
	public Document inicializarDocumento() throws ParserConfigurationException{
        Document documento;
        // Creamos los objectos de creacion de Documentos XML
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor = docFactory.newDocumentBuilder();
        
        documento = constructor.newDocument();
        
        return documento;        
    }
	
	public Document CargarDocumento(String _ruta) throws ParserConfigurationException, SAXException, IOException {
		File archivoXml = new File(_ruta);
		DocumentBuilderFactory  documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();		
		documentBuilderFactory.setNamespaceAware(false);
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
		documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);		
		Document documentoXML  = documentBuilder.parse(archivoXml+"\n");
		documentoXML.getDocumentElement().normalize();
		return documentoXML;
	}
	public void cargarConfig() {
		Document configActual;
		try {
			configActual = CargarDocumento(new File("").getAbsolutePath()+"\\config.xml");
			String idAppConfig =encriptado.decrypt(getTexNodo(configActual,"/Configuracion/Cil/id"));
			setId(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/Cil/id")));
			setFechaActualizacion(LocalDateTime.parse(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/Cil/fu"))));
			setFechaCancelacion(LocalDateTime.parse(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/Cil/fc")) ));
			setfActivacion(LocalDateTime.parse(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/Cil/fa")) ));
			
			setUserAdmin(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/lg/u1")));
			setUserUser(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/lg/u2")));
			setPassAdmin(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/lg/p1")));
			setPassUser(encriptado.decrypt(getTexNodo(configActual,"/Configuracion/lg/p2")));
		
		} catch (ParserConfigurationException e ) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
			e.printStackTrace();
		}

		
	}
	public void initConfigXml(String idApp) {
		File xml =new File(new File("").getAbsolutePath()+"\\config.xml");
		if(!xml.exists()) {
			LocalDateTime fechaSistema = LocalDateTime.now();
			try {
				setfActivacion(LocalDateTime.parse(fechaSistema.toString()));
				setFechaCancelacion(LocalDateTime.parse("1990-06-06T02:15:12.442437400"));
				setFechaActualizacion(LocalDateTime.parse(fechaSistema.toString()));
				setId(idApp);
				setUserAdmin("admin");
				setUserUser("usuario");
				setPassAdmin("admin");
				setPassUser("usuario");
				guardarConfiguracion();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
				e.printStackTrace();
			}
					

			
		}
	}
	public void guardarConfiguracion() {		
		
		try {
			EliminarDocumento(new File("").getAbsolutePath()+"\\config.xml");
			Document nuevaConfig =crearDocumento();
			escribirArchivo(nuevaConfig, new File("").getAbsolutePath()+"\\config.xml");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		public String convertirString(Document documento) throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        
        // Indicamos que queremos que idente el XML con 2 espacios
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        // Creamos el escritor a cadena de texto
        StringWriter writer = new StringWriter();
        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual se almacenara en el objecto writer
        StreamResult result = new StreamResult(writer);
        // Efectuamos la transformacion a lo que indica el objecto resultado, writer apuntara a el resultado
        transformer.transform(source, result);
        // Convertimos el buffer de writer en cadena de texto
        String output = writer.getBuffer().toString();

        return output;
    }
	
		public void escribirArchivo(Document documento, String fileName) throws TransformerConfigurationException, TransformerException, IOException {
	        // Creamos el objecto transformador
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        
	        // Indicamos que queremos que idente el XML con 2 espacios
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

	        // Archivo donde almacenaremos el XML
	        File archivo = new File(fileName);
	        DOMSource source = new DOMSource(documento);
	        // Resultado, el cual almacena en el archivo indicado
	        StreamResult result = new StreamResult(archivo);
	        // Transformamos de ña fuente DOM a el resultado, lo que almacena todo en el archivo
	        transformer.transform(source, result);
	        Path toCreatePath = Paths.get(archivo.toURI());
	        //System.out.println("****"+archivo.toURI());
	        if (Files.exists(toCreatePath)) {
	        	Files.setAttribute(archivo.toPath(), "dos:hidden", true);
	        }
	    }
	   
		public Document crearDocumento() throws ParserConfigurationException {
	        Document documento = this.inicializarDocumento();
	        
	        // Creamos el elemento principal
	        Element configuracion = documento.createElement("Configuracion");
	        documento.appendChild(configuracion);

	        // Creamos el Elemento de licencia
	        Element licencia = documento.createElement("Cil");
	        configuracion.appendChild(licencia);
	        
	        Element login = documento.createElement("lg");
	        configuracion.appendChild(login);
	        

	        //Creamos mas elementos
	        Element nombreApp = documento.createElement("id");
	        Element fechaActivacion = documento.createElement("fa");
	        Element fechaCancelacion = documento.createElement("fc");
	        Element fechaActualizacion = documento.createElement("fu");
	        Element usuarioAdmin = documento.createElement("u1");	
	        Element usuarioUser = documento.createElement("u2");	
	        Element passAdmin = documento.createElement("p1");	
	        Element passUser = documento.createElement("p2");	
	        try {
	        	nombreApp.setTextContent(encriptado.encript(id));
				fechaCancelacion.setTextContent(encriptado.encript(getFechaCancelacion().toString()));
				fechaActivacion.setTextContent(encriptado.encript( getfActivacion().toString()));
				fechaActualizacion.setTextContent(encriptado.encript(getFechaActualizacion().toString()));
				usuarioAdmin.setTextContent(encriptado.encript(getUserAdmin()));
				usuarioUser.setTextContent(encriptado.encript(getUserUser()));
				passAdmin.setTextContent(encriptado.encript(getPassAdmin()));
				passUser.setTextContent(encriptado.encript(getPassUser()));
				
			} catch (DOMException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error:" +e.getMessage(),this.getClass().getName(), 0, null);
				e.printStackTrace();
			}
	        licencia.appendChild(nombreApp);        
	        licencia.appendChild(fechaActivacion);
	        licencia.appendChild(fechaActualizacion);
	        licencia.appendChild(fechaCancelacion);
	        login.appendChild(usuarioAdmin);
	        login.appendChild(usuarioUser);
	        login.appendChild(passAdmin);
	        login.appendChild(passUser);
	        
	       
	        
	        return documento;
	    }

		public String getTexNodo(Document documento ,String rutaNodo) throws XPathExpressionException {
			XPath xPath = XPathFactory.newInstance().newXPath();	
			NodeList nodeList = (NodeList) xPath.compile(rutaNodo).evaluate(documento, XPathConstants.NODESET);
			Node nd =  nodeList.item(0);
			return nd.getTextContent();
			}
		public void EliminarDocumento(String fileName) {
			System.out.println(fileName);
			File xml = new File(fileName);
			if(xml.exists()) {
				xml.delete();
				
			}
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public LocalDateTime getfActivacion() {
			return fActivacion;
		}
		public void setfActivacion(LocalDateTime fActivacion) {
			this.fActivacion = fActivacion;
		}
		public LocalDateTime getFechaCancelacion() {
			return fechaCancelacion;
		}
		public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
			this.fechaCancelacion = fechaCancelacion;
		}
		public LocalDateTime getFechaActualizacion() {
			return fechaActualizacion;
		}
		public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
			this.fechaActualizacion = fechaActualizacion;
		}
		public String getUserAdmin() {
			return userAdmin;
		}
		public void setUserAdmin(String userAdmin) {
			this.userAdmin = userAdmin;
		}
		public String getPassAdmin() {
			return passAdmin;
		}
		public void setPassAdmin(String passAdmin) {
			this.passAdmin = passAdmin;
		}
		public String getUserUser() {
			return userUser;
		}
		public void setUserUser(String userUser) {
			this.userUser = userUser;
		}
		public String getPassUser() {
			return passUser;
		}
		public void setPassUser(String passUser) {
			this.passUser = passUser;
		}
		
		
		public String getNombreApp() {
			return nombreApp;
		}
		public void setNombreApp(String nombreApp) {
			this.nombreApp = nombreApp;
		}
}
