import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener{

	JButton button = new JButton("Restart");
	Random random;
	int xApple;
	int yApple;
	int score = 0;
	final int UNIT = 25;
	char direction = 'R';
	final int xSnake[] = new int[100];
	final int ySnake[] = new int[100];
	int bodyParts = 3;
	boolean running = false;
	Timer timer;
	static final int DELAY = 175;

	SnakePanel(){
		xSnake[0]=75;
		xSnake[1]=50;
		xSnake[2]=25;
		ySnake[0] = ySnake[1] = ySnake[2] = 25;
		random  = new Random ();
		this.setFocusable(true);
		this.add(button);
		button.setVisible(false);
		this.setBackground(Color.black);
		this.addKeyListener(new MyKeyAdapter());
		StartGame();
	}

	public void StartGame() {
		running = true;
		newApple();
		timer = new Timer(DELAY,this);
		timer.start();
	}

	public void newApple() {
		xApple = random.nextInt(1,19)*UNIT;
		yApple = random.nextInt(1,19)*UNIT;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if(running) {
			//grid
			/*
			for(int x = 0; x <= 450; x+=UNIT) {
				g.drawLine(0, x, 475, x);
				g.drawLine(x, 0, x, 475);
			}
			*/
			//apple
			g.setColor(Color.red);
			g.fillOval(xApple, yApple, UNIT, UNIT);

			//snake
			for(int i = 0; i < bodyParts; i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(xSnake[i], ySnake[i], UNIT, UNIT);
				}
				else {
					g.setColor(new Color(45,180,0));
					g.fillRect(xSnake[i], ySnake[i], UNIT, UNIT);
				}
			}
			g.setColor(Color.PINK);
			g.setFont( new Font("Courier",Font.BOLD, 25));
			g.drawString("Score: " + score, 340, 50);
		}
		else {
			gameOver(g);
		}

	}

	public void move() {

		for(int i = bodyParts-1; i>0;i--) {

			xSnake[i]=xSnake[i-1];
			ySnake[i]=ySnake[i-1];
		}
		switch(direction) {
		case 'R':
			xSnake[0] += UNIT;
			break;
		case 'L':
			xSnake[0] -= UNIT;
			break;
		case 'U':
			ySnake[0] -= UNIT;
			break;
		case 'D':
			ySnake[0] += UNIT;
			break;
		}

	}

	public void appleEaten() {
		if((xSnake[0] == xApple) && (ySnake[0] == yApple)) {
			bodyParts++;
			score++;
			newApple();
		}
	}

	public void gameOver(Graphics g) {
		
		button.setVisible(true);
		button.setBounds(180,275,100,25);
		button.setBackground(Color.black);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SnakeFrame();
			}
		});
		g.setColor(Color.ORANGE);
		g.setFont( new Font("Ink Free",Font.BOLD, 65));
		g.drawString("Game Over!", 75, 200);
		
		g.setColor(Color.PINK);
		g.setFont( new Font("Courier",Font.BOLD, 40));
		g.drawString("Score: " + score, 150, 250);
	}
	
	public void checkCollision() {
		//top border
		if(ySnake[0] < 0 ) {
			running = false;
		}
		//bottom border
		if(ySnake[0] > 450 ) {
			running = false;
		}
		//right border
		if(xSnake[0] > 450 ) {
			running = false;
		}
		//left border
		if(xSnake[0] < 0 ) {
			running = false;
		}
		//head touches body
		for(int i=1;i<bodyParts;i++) {
			if((xSnake[0] == xSnake[i]) && (ySnake[0] == ySnake[i])) {
				running = false;
			}
		}
		if(!running)
			timer.stop();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			appleEaten();
			move();
			checkCollision();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if(direction != 'U') {
					direction = 'D';
				}
				break;

			}
		}
	}
}
