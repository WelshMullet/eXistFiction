package outcast.engine;

import javax.swing.JFrame;

import java.awt.Canvas;

import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.XMLDBException;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.DropMode;
import javax.swing.JTextArea;

public class GUI {

	private JFrame frame;
	private ExistManager manager = null;
	private Engine engine = null;
	private JTextField input;
	private JLabel image;
	private JTextArea output;


	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		frame.setVisible(true);
		update();
	}
	


	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		manager = ExistManager.getInstance();
		engine = Engine.getInstance();
		
		frame = new JFrame();
		frame.setMaximumSize(new Dimension(1280, 720));
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		image = new JLabel();
		
		JButton btnEnter = new JButton("enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.append(engine.parseInput(input.getText()));
				input.setText("");
			}
		});
		
		input = new JTextField();
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					String text =input.getText();
					output.append(engine.parseInput(text));
					input.setText("");
				}
			}
		});
		input.setColumns(10);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		output = new JTextArea();
		output.setWrapStyleWord(true);
		output.setEditable(false);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(output, GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
						.addComponent(image, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(input, GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEnter)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(image, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(output, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEnter)))
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList inventoryList = new JList();
		inventoryList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryList.setVisibleRowCount(20);
		inventoryList.setModel(new AbstractListModel() {
			String[] values = new String[] {""};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		inventoryList.setForeground(Color.WHITE);
		inventoryList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		inventoryList.setBackground(Color.BLACK);
		panel.add(inventoryList);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("37px"),
				ColumnSpec.decode("53px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("57px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		panel_1.add(btnQuit, "2, 2, 3, 1");
		
		JButton btnSave = new JButton("Save");
		panel_1.add(btnSave, "2, 4, 3, 1");
		
		JButton btnLoad = new JButton("Load");
		panel_1.add(btnLoad, "2, 6, 3, 1");
		
		JButton btnHelp = new JButton("Help");
		panel_1.add(btnHelp, "2, 8, 3, 1");
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void update(){
		
		//Background
		/*
		BufferedImage wPic = null;
		NodeList list =engine.getLoc().getElementsByTagName("background");
		String background = list.item(0).getFirstChild().getNodeValue();
		
		try {
			wPic = (BufferedImage) manager.getResource("db/locations/images", background ).getContent();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image.setIcon(new ImageIcon(wPic) );
		*/
		
		//Main text
		NodeList list =engine.getLoc().getElementsByTagName("description");
		String description = list.item(0).getFirstChild().getNodeValue();
		output.append(description + "\n");
		
		
		
		
	}
}
