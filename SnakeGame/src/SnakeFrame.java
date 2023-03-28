import javax.swing.JFrame;

public class SnakeFrame extends JFrame{
	SnakeFrame(){
		this.add(new SnakePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setBounds(0,0,490,510);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
