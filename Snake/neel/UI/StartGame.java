package UI;


import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;

public class StartGame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGame window = new StartGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("SnakeAI");
		frame.setResizable(false);//every time it will redraw fixed drag
		SnakePanel panel = new SnakePanel();
		new Thread(panel).start();//Start the thread
		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(100, 100, 200, 200);//frame size
		 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}



