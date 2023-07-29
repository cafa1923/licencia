package licencia;

public class Login {
	ConfigXml xmlConfig;
	public Login() {
		xmlConfig = new ConfigXml();
		xmlConfig.initConfigXml("100000");
		xmlConfig.cargarConfig();
	}
	public int ValidarUsuario(String usuario,String password) {
		System.out.println("usuario: "+xmlConfig.getUserAdmin());
		System.out.println("clave: "+xmlConfig.getPassAdmin());
		int codUser =2;
		if(xmlConfig.getUserAdmin().contentEquals(usuario) && xmlConfig.getPassAdmin().contentEquals(password)) {
			codUser=0;
		}
		if(xmlConfig.getUserUser().contentEquals(usuario) && xmlConfig.getPassUser().contentEquals(password)) {
			codUser=1;
		}
		return codUser;
		
	}
	public void cambiarUsuario(int tipoUser,String usuario,String password) {
		if(tipoUser ==0) {
			xmlConfig.setUserAdmin(usuario);
			xmlConfig.setPassAdmin(password);
		}
		if(tipoUser == 1) {
			xmlConfig.setUserUser(usuario);
			xmlConfig.setPassUser(password);
		}
		xmlConfig.guardarConfiguracion();
	}

}
