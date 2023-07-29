package licencia;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Color;

import java.awt.Insets;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;



public class FormGeneradorLicencia extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtClave;
	private JPanel panelActivacion;
	Licencia licencia;
	private final String USUARIO = "CAFA1923C@GMAIL.COM";
	private final String PASSWORD = "Dome201013*";
	

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public FormGeneradorLicencia() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 174);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(50, 50, 50, 20));
		
		menuBar.setBackground(new Color(240, 240, 240));
		menuBar.setToolTipText("Ingrese su clave y usuario");
		setJMenuBar(menuBar);
		
		JLabel lblNewLabel = new JLabel(" Usuario:  ");
		menuBar.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setText("cafa1923c@gmail.com");
		lblNewLabel.setLabelFor(txtUsuario);
		menuBar.add(txtUsuario);
		txtUsuario.setColumns(5);
		
		JLabel lblNewLabel_1 = new JLabel("    Password:  ");
		menuBar.add(lblNewLabel_1);
		
		
		txtClave = new JPasswordField();
		//txtClave.setText("Dome201013*");
		menuBar.add(txtClave);
		txtClave.setColumns(5);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txtUsuario.getText().trim().toUpperCase();
				char[] arrayC = txtClave.getPassword();
				String password = new String(arrayC);
				//System.out.println(password);
				if(USUARIO.equals(usuario)) {
					if(PASSWORD.equals( password)) {
						panelActivacion.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Clave Incorrecta", "Generar Clave", 0);
						
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Usuario Incorrecto", "Generar Clave", 0);
					
				}
				
			}
		});
		btnLogin.setSize(20, 10);
		menuBar.add(btnLogin);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		panelActivacion = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panelActivacion, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panelActivacion, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		JLabel lblNewLabel_2 = new JLabel("Activar Hasta: ");
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		JLabel lblNewLabel_3 = new JLabel("Programa: ");
		
		JComboBox comboPrograma = new JComboBox();
		comboPrograma.setModel(new DefaultComboBoxModel(new String[] {"Resumen Bg", "Buscar Faltantes Envio Correos", "Reimpresiones", "Malla Reprocesos", "Empaca Archivos", "Compara Directorios", "Contar Paginas PDF", "Invertir data Pycca", "Listado IMG Cheques BG", "Envio Correos BG"}));
		
		JButton btnGenerarClave = new JButton("Generar Clave");
		btnGenerarClave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String  fcaducidad = datePicker.getJFormattedTextField().getText()+"T13:54:24.114641600";
				String factual = LocalDateTime.now().toString();
				//LocalDateTime fechaSistema = LocalDateTime.parse(datePicker.getModel().getValue().toString());
				//System.out.println(factual); 
				//System.out.println(fcaducidad);
				//System.out.println(comboPrograma.getSelectedIndex()+"");
				licencia =new Licencia();
				licencia.generarLicencia(factual, fcaducidad,comboPrograma.getSelectedIndex()+"");
				JOptionPane.showMessageDialog(null, "Generado", "Activar Licencia", 1);
			}
		});
		//datePicker.setEnabled(false);
		GroupLayout gl_panelActivacion = new GroupLayout(panelActivacion);
		gl_panelActivacion.setHorizontalGroup(
			gl_panelActivacion.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelActivacion.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelActivacion.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelActivacion.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelActivacion.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addGap(18)
							.addComponent(comboPrograma, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnGenerarClave, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panelActivacion.setVerticalGroup(
			gl_panelActivacion.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelActivacion.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelActivacion.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_2)
						.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelActivacion.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(comboPrograma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGenerarClave))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panelActivacion.setVisible(false);
		panelActivacion.setLayout(gl_panelActivacion);
		//JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
		//JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
		//
				
		
		contentPane.setLayout(gl_contentPane);
		
		
		
		
		
	}
}
