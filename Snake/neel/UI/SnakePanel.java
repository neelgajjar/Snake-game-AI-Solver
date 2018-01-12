package UI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import AI.SnakeAI;
import Mode.Node;
import Mode.Snake;

public class SnakePanel extends JPanel implements Runnable{
	Snake  snake;
	SnakeAI ai;
	
	public SnakePanel(){
		snake = new Snake();
		Node n = new Node(10,10); // the initial position of the snake
		snake.getS().add(n);
		snake.setFirst(n);;
		snake.setLast(n);
		snake.setTail(new Node(0,10)); // last node
		snake.setFood(new Node(80,80)); // the initial position of the food
		ai = new SnakeAI();
		
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.ORANGE);
		g.drawRect(10, 10, Snake.map_size, Snake.map_size); // map range
		
		g.setColor(Color.WHITE);
		paintSnake(g, snake);
		g.setColor(Color.WHITE);
		paintFood(g, snake.getFood());
		int dir=ai.play2(snake,snake.getFood());//Select strategy: play, play1, play2
		if(dir==-1){
//			System.out.println("GG");
		}
		else{
			snake.move(dir);
		}
		
		
	}
	
	public void paintSnake(Graphics g , Snake snake){
		for(Node n: snake.getS()){
			if(n.toString().equals(snake.getFirst().toString())){
				g.setColor(Color.GREEN); //for the snake of convenience, the snake head is green
			}
			if(n.toString().equals(snake.getLast().toString()) && !snake.getFirst().toString().equals(snake.getLast().toString())){
				g.setColor(Color.BLUE); // set tail color to blue
			}
			g.fillRect(n.getX(), n.getY(), snake.size, snake.size);
			g.setColor(Color.WHITE); // body color 
		}
	}
	
	// draw food
	
	public void paintFood(Graphics g, Node food){
		g.setColor(Color.RED);
		g.fillRect(food.getX(), food.getY(), snake.size, snake.size);
	}
	
	public void run(){
		while (true) {
			try {
				Thread.sleep(30);
				this.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
