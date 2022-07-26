package hangingmangame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.*;
import java.awt.Font;
import java.awt.FontMetrics;

public class GameScreen extends Canvas{
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	private static final double playerx = WIDTH - WIDTH / 2;
	private static final double playery = HEIGHT - HEIGHT * 0.75;
	private static int wordProgress = 0; //controls which parts of hidden word user is on
	private static int progress = 0;
	private static List<Shape> shapes = new ArrayList<Shape>();
	private static String tries = ""; //contains tried characters
	private static String word = ""; //the secret word to guess
	
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter secret word");
		word = scan.nextLine();
		JFrame frame = new JFrame("Hang Man");
		Canvas canvas = new GameScreen();
		canvas.setSize(WIDTH, HEIGHT);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		
		while(wordProgress < word.length() && progress < 6)
		{
	
			if(word.charAt(wordProgress) == ' ')
			{
				wordProgress++;
				continue;
			}
			System.out.println("enter letter");
			char letter = scan.next().charAt(0);
			tries += letter;
			if(Character.toLowerCase(letter) != Character.toLowerCase(word.charAt(wordProgress)))
			{
				progress++;
				System.out.println("Guessed wrong");
			}
			else
			{
				wordProgress++;
			}
			canvas.repaint();
			
			
		}
		scan.close();
		
	}
	
	/**
	 * Paints the main interface.
	 * @param g the graphical context.
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		drawHanger(g2);
		drawBody(g2);
		Font font = new Font("Serif", Font.PLAIN, 12);
		g2.setFont(font);
		g2.drawString("Tries: " + tries + " ", 0, HEIGHT - 40);
		if(wordProgress > 0)
		{
			g2.drawString("Hidden Word: " + word.substring(0, wordProgress), 0, 10);
		}
		else
		{
			g2.drawString("Hidden Word: ", 0, 10);
		}
		if(wordProgress == word.length())
		{
			g2.setColor(Color.GREEN);
			g2.drawString("You Won!", WIDTH - 100, 10);
		}
		else if(progress >= 6)
		{
			g2.setColor(Color.RED);
			g2.drawString("You Lost", WIDTH - 100, 10);
		}
		
		//System.out.println(word.substring(0, wordProgress + 1));
		
	}
	
	/**
	 * Draws the gallow.
	 * @param g2 graphical context.
	 */
	public void drawHanger(Graphics2D g2)
	{
		Line2D.Double leg1 = new Line2D.Double(playerx - 70, playery + 200, playerx - 60, playery + 230);
		Line2D.Double leg2 = new Line2D.Double(playerx - 70, playery + 200, playerx - 80, playery + 230);
		Line2D.Double foundation = new Line2D.Double(playerx - 70, playery + 200, playerx - 70, playery - 40);
		Line2D.Double overHead = new Line2D.Double(playerx - 70, playery - 40, playerx + 15, playery - 40);
		Line2D.Double rope = new Line2D.Double(playerx + 15, playery - 40, playerx + 15, playery);
		g2.draw(rope);
		g2.draw(leg1);
		g2.draw(leg2);
		g2.draw(foundation);
		g2.draw(overHead);
		
	}
	
	/**
	 * Draws the body bit by bit, adding components of the hang man based on
	 * number of failed tries.
	 * @param g2 graphical context.
	 */
	public void drawBody(Graphics2D g2)
	{
		double headx = playerx;
		double heady = playery;
		
		switch(progress)
		{
			case 1:
				shapes.add(new Ellipse2D.Double(headx, heady, 30, 30));
				break;
			case 2:
				shapes.add(new Line2D.Double(headx + 15, heady + 30, headx + 15, heady + 150));
				break;
			case 3:
				shapes.add(new Line2D.Double(headx + 15, heady + 60, headx + 32, heady + 70));
				break;
			case 4:
				shapes.add(new Line2D.Double(headx + 15, heady + 60, headx - 2, heady + 70));
				break;
			case 5:
				shapes.add(new Line2D.Double(headx + 15, heady + 150, headx + 35, heady + 170));
				break;
			case 6:
				shapes.add(new Line2D.Double(headx + 15, heady + 150, headx - 5, heady + 170));
				break;
		}
		
		for(int index = 0; index < shapes.size(); index++)
		{
			g2.draw(shapes.get(index));
		}
	}
}
