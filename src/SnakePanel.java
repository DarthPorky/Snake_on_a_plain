import java.util.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SnakePanel extends JPanel implements ActionListener{
	
	static final int PANEL_WIDTH = 600; // screen width
	static final int PANEL_HEIGHT = 600; // screen height
	static final int UNIT_SIZE = 25; // size of objects (obviously in pixels) that appear on screen
	static final int GAME_UNITS = (PANEL_WIDTH*PANEL_HEIGHT)/UNIT_SIZE; // max allowed
	static final int DELAY = 75; // snake speed, higher equals faster. Slower well you get it
	final int xAxis[] = new int[GAME_UNITS];// holds x-coordinates for snake
	final int yAxis[] = new int[GAME_UNITS];// holds y-coordinates for snake
	int bodyLength = 6; // snake initial body size
	int mushroomsEaten; // self explanatory
	int mushroomX; // x-coordinate for mushroom
	int mushroomY; // y-coordinate for mushroom
	// to add comments later......
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	SnakePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new KeyLogger());
		start();	
	}
	public void start() {
		newMushroom();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			g.setColor(Color.white);
			g.fillOval(mushroomX, mushroomY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0; i < bodyLength; i++) {
				if( i == 0) {
					g.setColor(Color.green);
					g.fillRect(xAxis[i], yAxis[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(Color.red);
					g.fillRect(xAxis[i], yAxis[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.blue);
			
			// G.Nyondo in COD
			FontMetrics metric = getFontMetrics(g.getFont());
			g.drawString("Eaten: " + mushroomsEaten, (PANEL_WIDTH - metric.stringWidth("Eaten: " + mushroomsEaten))/2, g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}
	
	public void move() {
		for(int i = bodyLength; i > 0; i--) {
			xAxis[i] = xAxis[i - 1];
			yAxis[i] = yAxis[i - 1];
		}
		
		switch(direction) {
			case 'U':
				yAxis[0] = yAxis[0] - UNIT_SIZE;
				break;
			case 'D':
				yAxis[0] = yAxis[0] + UNIT_SIZE;
				break;
			case 'L':
				xAxis[0] = xAxis[0] - UNIT_SIZE;
				break;
			case 'R':
				xAxis[0] = xAxis[0] + UNIT_SIZE;
				break;
		}
	}
	
	public void newMushroom() {
		mushroomX = random.nextInt((int)(PANEL_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		mushroomY = random.nextInt((int)(PANEL_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void checkMushroom() {
		if((xAxis[0] == mushroomX) && (yAxis[0] == mushroomY)) {
			bodyLength++;
			mushroomsEaten++;
			newMushroom();
		}
	}
	
	public void collisionDetector() {

		 for(int i = bodyLength; i > 0; i--) {
			 if((xAxis[0] == xAxis[i]) && (yAxis[0] == yAxis[i])) {
				 running = false;
			 } 
		 }
		 

		 if(xAxis[0] < 0) {
			 running = false;
		 }
		 

		 if(xAxis[0] > PANEL_WIDTH) {
			 running = false;
		 }
		 

		if(yAxis[0] < 0) {
			running = false;
		}
				 
		
		if(yAxis[0] > PANEL_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		String gameOver = "Snake Fell off the edge or bit itself";
		g.setColor(Color.blue);
		
		// G.Nyondo in COD
		FontMetrics metric = getFontMetrics(g.getFont());
		g.drawString(gameOver, (PANEL_WIDTH - metric.stringWidth(gameOver))/2, PANEL_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkMushroom();
			collisionDetector();
		}
		repaint();
		
	}
	
	public class KeyLogger extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT: 
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT: 
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP: 
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN: 
					if(direction != 'U') {
						direction = 'D';
					}
					break;
			}
		}
	}

}
