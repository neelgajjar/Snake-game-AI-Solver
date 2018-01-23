package Mode;

import java.util.ArrayList;
import java.util.HashSet;


public class Snake {
	/**
	 * Counter anti-cycle
	 */
	public int c=0;
	/**
	 * Serpent size
	 */
	public final static int size =10;
	/**
	 * Map size
	 */
	public final static int map_size=150;
	/**
	 * Snake head
	 */
	private Node first;
	/**
	 *The tail of a snake is actually the last node to go on a tail
	 */
	private Node tail;
	/**
	 * Tail tail
	 */
	private Node last;
	/**
	 * Snake data structure
	 */
	private ArrayList<Node> s=new ArrayList<Node>();
	/**
	 * There are snake nodes on the map, Snake String store
	 */
	private HashSet<String> map=new HashSet<String>();

	/**
	 * Direction
	 */
	private int dir;// 8 6 2 4  
	/**
	 *  Food
	 */
	private Node food;
	
	public Snake(){

	}
	public Snake(Node first,Node last,Node food,Node tail){
		this.first=first;
		this.last=last;
		this.food=food;
		this.tail=tail;
	}
	/* *
	 * Add n to s
	 * @param n
	 */
	private void add_Node(Node n){
		s.add(0, n);

		first=s.get(0);
		//If the added node is not food, remove the tail
		
		if(!n.toString().equals(food.toString())){
			tail=last;//record the tail
			s.remove(last);
			last=s.get(s.size()-1);
		}else{//If yes, random food,
			food=RandomFood();
		}
	}
	/**
	 * Move
	 */
	public void move() {
		if(dir==8){
			Node n=new Node(first.getX(),first.getY()-10);
			add_Node(n);
		}
		if(dir==6){
			Node n=new Node(first.getX()+10,first.getY());
			add_Node(n);
		}
		if(dir==2){
			Node n=new Node(first.getX(),first.getY()+10);
			add_Node(n);
		}
		if(dir==4){
			Node n=new Node(first.getX()-10,first.getY());
			add_Node(n);
		}
		updataMap(s);
	}
	/**
	 * You can set the direction of the move
	 * @param dir
	 */
	public void move(int dir){
		this.dir=dir;
		move();
	}
	/**
	 * Determine the direction of dir can go
	 * @param snake
	 * @param dir
	 * @return
	 */
	public boolean canMove(int dir){
		if(dir==8){
			int X=first.getX();
			int Y=first.getY()-10;
			if(Y<10||map.contains(X+"-"+Y)){
				return false;
			}else return true;
		}
		if(dir==6){
			int X=first.getX()+10;
			int Y=first.getY();
			if(X>Snake.map_size||map.contains(X+"-"+Y)){
				return false;
			}else return true;
		}
		if(dir==2){
			int X=first.getX();
			int Y=first.getY()+10;
			if(Y>Snake.map_size||map.contains(X+"-"+Y)){
				return false;
			}else return true;
		}
		if(dir==4){
			int X=first.getX()-10;
			int Y=first.getY();
			if(X<10||map.contains(X+"-"+Y)){
				return false;
			}else return true;
		}
		return false;
	}
	/**
	 * String switch Node
	 * @param s
	 * @return
	 */
	public Node StringToNode(String s){
		String []str=s.split("-");
		int x = Integer.parseInt(str[0]);
		int y = Integer.parseInt(str[1]);
		return new Node(x,y);
	}
	/**
	 * Node to String, suddenly remembered that you can directly write a toString. .
	 * @param n
	 * @return
	 */
//	public String NodeToString(Node n){
//		return n.getX()+"-"+n.getY();
//	}
	/**
	 * Updated the location visited on the map	
	 * @param s
	 */
	public void updataMap(ArrayList<Node> s){
		map.clear();//first remove the old station site
		for(Node n:s){
			map.add(n.toString());
		}
	}
	/**
	 * Random appearance of food
	 */
	public Node RandomFood() {
		c=0;
		while(true){
			int x = 0,y;
			 x = Snake.size*(int) (Math.random() * Snake.map_size/Snake.size)+10;
			 y = Snake.size*(int) (Math.random() * Snake.map_size/Snake.size)+10;
			Node n=new Node(x,y);
			// Food can not appear in the snake body, s.contains actually can not check Node,
			// Suddenly think of is not Node class did not override the equals method. .
			if(!s.contains(n)){
				return n;
			}
		}
	}
	

	/**
	 * 
	 * @return snake long
	 */
	public int getLen() {
		return s.size();
	}
	/**
	 * @return the last node of the tail lsat
	 */
	public Node getTail() {
		return tail;
	}
	
	public void setTail(Node tail) {
		this.tail = tail;
	}
	
	public HashSet<String> getMap() {
		return map;
	}
	public Node getFirst() {
		return first;
	}

	public Node getLast() {
		return last;
	}

	public ArrayList<Node> getS() {
		return s;
	}

	public void setFirst(Node first) {
		this.first = first;
	}
	public void setLast(Node last) {
		this.last = last;
	}
	public void setS(ArrayList<Node> s) {
		this.s = s;
	}
	public void setMap(HashSet<String> map) {
		this.map = map;
	}
	public void setFood(Node food) {
		this.food = food;
	}
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public Node getFood() {
		return food;
	}
}
