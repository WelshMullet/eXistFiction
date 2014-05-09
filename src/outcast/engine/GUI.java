package outcast.engine;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.Canvas;

import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.Resource;
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
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DropMode;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class GUI {

	private JFrame frame;
	private ExistManager manager = null;
	private Engine engine = null;
	private JTextField input;
	private JLabel image;
	private DefaultListModel output = new DefaultListModel();
	private DefaultListModel inventory = new DefaultListModel();


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
				output.addElement(engine.parseInput(input.getText()));
				input.setText("");
				update();
			}
		});
		
		input = new JTextField();
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					output.addElement(engine.parseInput(input.getText()));
					if(output.getSize() > 10){
						output.remove(0);
					}
					input.setText("");
					update();
				}
			}
		});
		input.setColumns(10);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JList outputList = new JList(output);
		outputList.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(image, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(input, GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEnter))
						.addComponent(outputList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
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
							.addComponent(outputList, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEnter)))
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList inventoryList = new JList(inventory);
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnQuit = new JButton("Quit to desktop");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		panel_1.add(btnQuit, "2, 2, 3, 1");
		
		JButton btnSave = new JButton("Save Game");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.save();
			}
		});
		panel_1.add(btnSave, "2, 4, 3, 1");
		
		JButton btnLoad = new JButton("Load Game");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.load();
			}
		});
		panel_1.add(btnLoad, "2, 6, 3, 1");
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String help = engine.getHelp();
				if(help == null){
					JOptionPane.showMessageDialog(frame,"No help found");
				}
				else{
					JOptionPane.showMessageDialog(frame,help);
				}

			}
		});
		panel_1.add(btnHelp, "2, 8, 3, 1");
		
		JButton btnNew = new JButton("New Game");
		panel_1.add(btnNew, "2, 10, 3, 1");
		frame.getContentPane().setLayout(groupLayout);
		
		NodeList list =engine.getLoc().getElementsByTagName("description");
		String description = list.item(0).getFirstChild().getNodeValue();
		output.addElement(description);
	}
	
	public void update(){
		
		//Background
		
		BufferedImage wPic = null;
		NodeList list =engine.getLoc().getElementsByTagName("background");
		String background = list.item(0).getFirstChild().getNodeValue();
		
        URL url;
		try {
			url = new URL("http://localhost:8080/exist/rest/locations/images/" + background);
			wPic = ImageIO.read(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		int w = image.getWidth();
		int h = image.getHeight();
		int a = wPic.getWidth();
		int b = wPic.getHeight();
		double x = 1.0;
		double y = 1.0;
		
		x = ((double)w/a);
		y = ((double)h/b);
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(x,y);
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(wPic, after);
		
		image.setIcon(new ImageIcon(after) );
		
		
		//Main text
		/*list =engine.getLoc().getElementsByTagName("description");
		String description = list.item(0).getFirstChild().getNodeValue();
		output.append(description + "\n");*/
		
		//Inventory list
		
		/*int i = 0;
		String[] inv = engine.getInventory();
		inventory.clear();
		while(i < inv.length){
			inventory.add(i, inv[i]);
		}*/
		
		
		
	}
}
