import javax.swing.JFrame;

public class SnakeFrame extends JFrame {
	SnakeFrame(){
		this.add(new SnakePanel());
		this.setTitle("All snakes are snakes on a plain to a flat eather");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}
}
