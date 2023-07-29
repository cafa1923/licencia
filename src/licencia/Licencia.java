package licencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Licencia {
	Encript encriptado;
	ConfigXml xmlConfig;
	public Licencia() {
		encriptado=new Encript();
		xmlConfig = new ConfigXml();
		xmlConfig.initConfigXml("100000");
		xmlConfig.cargarConfig();
		
	}
	
	
	public boolean validarLicencia(String idApp) {
		boolean activado = true;
		try {
			//Date fechaSistema = new Date();
			//cargarConfig() ;
			LocalDateTime fechaSistema = LocalDateTime.now();
			//Document configActual =xmlConfig.CargarDocumento(new File("").getAbsolutePath()+"\\config.xml");
			//String idAppConfig =encriptado.decrypt(xmlConfig.getTexNodo(configActual,"/Configuracion/Cil/id"));
			//LocalDateTime fUso =LocalDateTime.parse(encriptado.decrypt(xmlConfig.getTexNodo(configActual,"/Configuracion/Cil/fu"))) ;
			//LocalDateTime fCaducidad =LocalDateTime.parse(encriptado.decrypt(xmlConfig.getTexNodo(configActual,"/Configuracion/Cil/fc")) ) ;
			//LocalDateTime fActivacion =LocalDateTime.parse(encriptado.decrypt(xmlConfig.getTexNodo(configActual,"/Configuracion/Cil/fa")) ) ;
			if(idApp.equals(xmlConfig.getId())) {
				if(fechaSistema.isBefore(xmlConfig.getFechaActualizacion())) {
					activado=false;
					//System.out.println("Fecha del sistema Anterior al uso"+fechaSistema.isAfter(xmlConfig.getFechaActualizacion()));
				}else {
					if(fechaSistema.isAfter(xmlConfig.getFechaCancelacion())) {
						activado=false;
						//System.out.println("Fecha del sistema posterior al caducidad"+fechaSistema.isAfter(fUso));
					}
				}
			}else {
				System.out.println("id no corresponde");
				activado=false;
			}
			if(activado) {
				//xmlConfig.setfActivacion(encriptado.encript(fActivacion.toString()));
				//xmlConfig.setFechaCancelacion(encriptado.encript(fCaducidad.toString()));
				xmlConfig.setFechaActualizacion(LocalDateTime.parse(fechaSistema.toString()));
				//xmlConfig.setNombreApp(encriptado.encript(idAppConfig));
				xmlConfig.guardarConfiguracion();		
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			activado=false;
			System.out.println("error");
			e.printStackTrace();
			
		}
		return activado;
	}
	public boolean activarLicencia(String claveActivacion) {
		boolean activado =false;
				
		try {	//cargarConfig() ;					
				String claveDesencriptada =encriptado.decrypt(claveActivacion);
				String[] datosActivacion =claveDesencriptada.split("\\|");
				String fCaducidad =datosActivacion[1] ;
				String fActivacion =datosActivacion[0] ;
				String idApp =datosActivacion[2] ;
				//Document configActual =xmlConfig.CargarDocumento(new File("").getAbsolutePath()+"\\config.xml");
				//String idAppConfig =encriptado.decrypt(xmlConfig.getTexNodo(configActual,"/Configuracion/Cil/id"));
				System.out.println("id activacion: "+xmlConfig.getId());
				if(xmlConfig.getId().equals(idApp)||xmlConfig.getId().equals("100000")) {	
					System.out.println("entre activar ");
					xmlConfig.setfActivacion(LocalDateTime.parse(fActivacion));
					xmlConfig.setFechaCancelacion(LocalDateTime.parse(fCaducidad));
					xmlConfig.setFechaActualizacion(LocalDateTime.parse(fActivacion));
					xmlConfig.setId(idApp);
					xmlConfig.guardarConfiguracion();	
					activado=true;
					
				}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activado;
		
		
	}
	public void generarLicencia(String fechaActivacion,String fechaCaducidad,String idApp) {
		//System.out.println("generar");
		try {			
				guardarLicencia(encriptado.encript(fechaActivacion+"|"+fechaCaducidad+"|"+idApp),idApp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void guardarLicencia(String licencia,String idApp) {
		String archivoSalida=new File("").getAbsolutePath()+"\\licencia"+ idApp +".cfa";
		try {
			if (Files.exists(Paths.get(archivoSalida))) {
				Files.delete(Paths.get(archivoSalida));
			}
		    Files.write(Paths.get(archivoSalida), licencia.getBytes(), StandardOpenOption.CREATE);
		}catch (IOException e) {
			e.printStackTrace();
		    //exception handling left as an exercise for the reader
		}
		
	}
	public void confirmarXml(String idApp) {
		File xml =new File(new File("").getAbsolutePath()+"\\config.xml");
		if(!xml.exists()) {
			LocalDateTime fechaSistema = LocalDateTime.now();
			try {
				xmlConfig.setfActivacion(LocalDateTime.parse(fechaSistema.toString()));
				xmlConfig.setFechaCancelacion(LocalDateTime.parse("1990-06-06T02:15:12.442437400"));
				xmlConfig.setFechaActualizacion(LocalDateTime.parse(fechaSistema.toString()));
				xmlConfig.setId(idApp);
				xmlConfig.setUserAdmin("kgutierrez");
				xmlConfig.setUserUser("usuario");
				xmlConfig.setPassAdmin("1990");
				xmlConfig.setPassUser("");
				xmlConfig.guardarConfiguracion();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					

			
		}
	}


}
