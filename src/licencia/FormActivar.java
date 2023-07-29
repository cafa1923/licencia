package licencia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormActivar extends JFrame {

	private JPanel contentPane;
	private JTextField txtNewLicencia;
	Licencia licencia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				/*try {
					FormActivar frame = new FormActivar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormActivar() {
		licencia=new Licencia();
		setTitle("Activar Licencia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 84);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		txtNewLicencia = new JTextField();
		txtNewLicencia.setColumns(10);
		
		JButton btnActivar = new JButton("Activar");
		btnActivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNewLicencia.getText()!="") {
					if(licencia.activarLicencia(txtNewLicencia.getText())) {
						JOptionPane.showMessageDialog(null, "Activado", "Activar Licencia", 1);
					}else {
						JOptionPane.showMessageDialog(null, "Licencia Incorrecta", "Activar Licencia", 0);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "El campo clave esta vacio", "Activar Licencia", 0);
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtNewLicencia, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnActivar))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNewLicencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnActivar)))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
