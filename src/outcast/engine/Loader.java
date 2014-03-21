package outcast.engine;

import java.awt.*;

import javax.swing.*;
class JavaSwing1{
	private static JFrame jfrm = null;
public void display(){
//create frame 
jfrm=new JFrame("Loader");

//set size
jfrm.setSize(200,100);

//when closed?
jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//set the layout
jfrm.setLayout(new FlowLayout());

//set visible
jfrm.setVisible(true);

JLabel jlab=new JLabel("Loading Engine");

//add to frame`s pane
jfrm.add(jlab);

}

public void hide(){
	jfrm.setVisible(false);
}

 public static void main(String j[]){
//creating thread
SwingUtilities.invokeLater(new Runnable(){
public void run(){
JavaSwing1 obj=new JavaSwing1();
obj.display();
ExistManager manager = ExistManager.getInstance();
if (manager.start() == 0){
	//ERROR HERE, HANDLE DB NOT STARTING
	GUI gui = new GUI();
	obj.hide();
}



}

});

}
}
