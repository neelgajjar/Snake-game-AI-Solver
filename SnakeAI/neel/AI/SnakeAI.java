package AI;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import Mode.Node;
import Mode.Snake;



public class SnakeAI {
	/**
	 * Make it work
	 * Direct Manhattan distance, Note: Because the Node class overrides the equals method, s.remove in the Snake class's move method 
	 * deletes 2 nodes when it deletes an end node,
	 * Causes the snake body duplication place will not be drawn, demonstrated the snake body breaks, may need to repair 
	 * or delete equals method
	 * @param s
	 * @param fn
	 * @param dir
	 * @return
	 */
	public int play(Snake s,Node f){
		if(f.getX()>s.getFirst().getX()&&s.getDir()!=4){
			return 6;
		}
		if(f.getX()<s.getFirst().getX()&&s.getDir()!=6){
			return 4;
		}
		if(f.getX()==s.getFirst().getX()){//the x axis equal
			if(f.getY()>s.getFirst().getY()&&s.getDir()!=8)return 2;
			else if(f.getY()>s.getFirst().getY()&&s.getDir()==8){
				if(s.canMove(4))return 4;
				else if(s.canMove(6))return 6;
				else return -1;
			}
			else if(f.getY()<s.getFirst().getY()&&s.getDir()!=2)return 8;
			else if(f.getY()<s.getFirst().getY()&&s.getDir()==2){
				if(s.canMove(4))return 4;
				else if(s.canMove(6))return 6;
				else return -1;
			}
		}
		
		if(f.getY()>s.getFirst().getY()&&s.getDir()!=2){
			return 8;
		} 
		if(f.getY()==s.getFirst().getY()){
			if(f.getX()>s.getFirst().getX()&&s.getDir()!=4)return 6;
			else if(f.getX()>s.getFirst().getX()&&s.getDir()==4){
				if(s.canMove(8))return 8;
				else if(s.canMove(2))return 2;
				else return -1;
			}
			else if(f.getX()<s.getFirst().getX()&&s.getDir()!=6)return 4;
			else if(f.getX()<s.getFirst().getX()&&s.getDir()==6){
				if(s.canMove(8))return 8;
				else if(s.canMove(2))return 2;
				else return -1;
			}
		}
		if(f.getY()<s.getFirst().getY()&&s.getDir()!=8){
			return 2;
		}
		return -1;
	}
	/**
	 * Make it right
	 * BFS, but when the snake goes to finish it, it turns out that it's GG's to be around
	 * @param s snake
	 * @param f target, where the target may not be food so separate parameters
	 * @return  can return to the path to the first step, can not return -1;
	 */
	
	public int play1(Snake s,Node f) {
		Queue<Node> q = new LinkedList<Node>();
		Set<String> vis = new HashSet<String>();// Record the visited node
		Map<String, String> path = new HashMap<String, String>();
		//Record the path of the visit, and later use the A * algorithm to add the father node in Node, this can be removed
		Stack<String> stack = new Stack<String>();//Snake to eat the path
		
		q.add(s.getFirst());
		while (!q.isEmpty()) {
			Node n = q.remove();
			if (n.getX() == f.getX() && n.getY() == f.getY()) {
			//If found the food, began to parse the path, because it is added from the back, so use the stack back
				String state = f.toString();
				while (state != null &&!state.equals(s.getFirst().toString())) {
					stack.push(state);
					state = path.get(state);
				}
				
				String []str;
				//sometimes can lead to food and head next to the stack is empty
				if(stack.isEmpty()){
					str = state.split("-");
				}else  str = stack.peek().split("-");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				if (x > s.getFirst().getX() && y == s.getFirst().getY()) {
					return 6;
				}
				if (x < s.getFirst().getX() && y == s.getFirst().getY()) {
					return 4;
				}
				if (x == s.getFirst().getX() && y > s.getFirst().getY()) {
					return 2;
				}
				if (x == s.getFirst().getX() && y < s.getFirst().getY()) {
					return 8;
				}
			}
			Node up = new Node(n.getX(), n.getY() - Snake.size);
			Node right = new Node(n.getX() + Snake.size, n.getY());
			Node down = new Node(n.getX(), n.getY() + Snake.size);
			Node left = new Node(n.getX() - Snake.size, n.getY());
			if (!s.getMap().contains(up.toString()) && !vis.contains(up.toString()) 
					&& up.getX() <= Snake.map_size&& up.getX() >= 10 
					&& up.getY() <= Snake.map_size && up.getY() >= 10) {
				q.add(up);
				vis.add(up.toString());
				path.put(up.toString(),n.toString());
			}
			if (!s.getMap().contains(right.toString()) && !vis.contains(right.toString())
					&& right.getX() <= Snake.map_size&& right.getX() >= 10 
					&& right.getY() <= Snake.map_size && right.getY() >= 10) {
				q.add(right);
				vis.add(right.toString());
				path.put(right.toString(),n.toString());
			}
			if (!s.getMap().contains(down.toString()) && !vis.contains(down.toString()) 
					&& down.getX() <= Snake.map_size&& down.getX() >= 10 
					&& down.getY() <= Snake.map_size && down.getY() >= 10) {
				q.add(down);
				vis.add(down.toString());
				path.put(down.toString(),n.toString());
			}
			if (!s.getMap().contains(left.toString()) && !vis.contains(left.toString()) 
					&& left.getX() <= Snake.map_size&& left.getX() >= 10 
					&& left.getY() <= Snake.map_size && left.getY() >= 10) {
				q.add(left);
				vis.add(left.toString());
				path.put(left.toString(),n.toString());
			}
		}
		return -1;
	}
	/**
	 *Make it right + // make it fast is a little harder
	 * 
	 * If you can not eat food on the tail, you can eat on the first to send a virtual snake to eat,
	 * if you can eat after the food can also go tail, or the first tail, until after eating food is also safe , Go eat.
	 * If you can not eat food can not follow the tail on the random walk, this should be a small probability event
	 * @param s snake
	 * @param f target
	 * @return direction
	 */

	public int play2(Snake snake,Node f){
		Snake virSnake =new Snake(snake.getFirst(),snake.getLast(),snake.getFood(),snake.getTail());
		virSnake.setS((ArrayList<Node>) snake.getS().clone());
		virSnake.setMap((HashSet<String>) snake.getMap().clone());

		//Serpent to eat food in the direction
		int realGoTofoodDir=play1(snake,f);
		//If you eat food
		if(realGoTofoodDir!=-1){
			 //send virtual snake to eat
			while(!virSnake.getFirst().toString().equals(f.toString())){
				virSnake.move(play1(virSnake, f));
			}
			 //Virtual snake to the tail to go in the direction
				int goToDailDir=Asearch(virSnake,virSnake.getTail());
				 //If the virtual snake can eat the tail, really snake to eat
				if(goToDailDir!=-1)return realGoTofoodDir;
				else {
					snake.c++;
					/**
					 * If you can not go to their own tail after eating, just follow the tail run
					 * There may be infinite running with the tail trail, mainly because I use the tail to track the tail,
					 *  the tail should be walking the most distance
					 * The farthest distance can use A * algorithm, which is bfs weighted greedy
					 */
					if(snake.c<100)return Asearch(snake,snake.getTail());
					else {
//						System.out.println("ok");
						return realGoTofoodDir;
					}
				}
		}else{// If you can not eat food
			 //Serpent to the tail to the direction
			int realGoToDailDir=Asearch(snake,snake.getTail());
			if(realGoToDailDir==-1){
				 //  If you can not eat food can not go to the tail on a random walk
				realGoToDailDir=randomDir();
				int i=0;
				while(!snake.canMove(realGoToDailDir)){
					//There may be a cycle of death, can not go on all sides
					realGoToDailDir=randomDir();
					i++;
					if(i>300)return -1;//anti-death cycle, only GG
				}
				return realGoToDailDir;
			}
			return realGoToDailDir;
		}
	}
	/**
	 * A Find the furthest path
	 * @param s
	 * @param f
	 * @return
	 */
	public int Asearch(Snake s,Node f){
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closeList = new ArrayList<Node>();
		Stack<Node> stack = new Stack<Node>();//Snake to eat the path
		openList.add(s.getFirst());// Place the start node in the open list;
		s.getFirst().setH(dis(s.getFirst(),f));

		while(!openList.isEmpty()){
			Node now=null;
			int max=-1;
			for(Node n:openList){//We find the F value (the description farthest from the target), if the same we choose behind the list is the latest addition.
				if(n.getF()>=max){
					max=n.getF();
					now=n;
				}
			}
			// Remove the current node from the open list and add it to the closed list
			openList.remove(now);
			closeList.add(now);
			//Neighbor in four directions
			Node up = new Node(now.getX(), now.getY() - Snake.size);
			Node right = new Node(now.getX() + Snake.size, now.getY());
			Node down = new Node(now.getX(), now.getY() + Snake.size);
			Node left = new Node(now.getX() - Snake.size, now.getY());
			ArrayList<Node> temp = new ArrayList<Node>(4);
			temp.add(up);
			temp.add(right);
			temp.add(down);
			temp.add(left);
			for (Node n : temp){
				// If the neighboring node is not accessible or the neighboring node is already in the closed list, then no action is taken and the next node continues to be examined;
				if (s.getMap().contains(n.toString()) || closeList.contains(n)
						|| n.getX() > Snake.map_size|| n.getX() < 10 
						|| n.getY() > Snake.map_size || n.getY() < 10)
					continue;
				
				// If the neighbor is not in the open list, add the node to the open list,
				//  and the adjacent node's parent node as the current node, while saving the adjacent node G and H value, F value calculation I wrote directly in the Node class
				if(!openList.contains(n)){
//					System.out.println("ok");
					n.setFather(now);
					n.setG(now.getG()+10);
					n.setH(dis(n,f));
					openList.add(n);
					// When the destination node is added to the open list as the node to be checked, the path is found, and the loop is terminated and the direction is returned.
					if (n.equals(f)) {
						
						//Go forward from the target node, .... lying groove there is a pit, node can not use f, because f and find the same node coordinates but f did not record father
						Node node = openList.get(openList.size() - 1);
						while(node!=null&&!node.equals(s.getFirst())){
							stack.push(node);
							node=node.getFather();
						}
						int x = stack.peek().getX();
						int y = stack.peek().getY();
						if (x > s.getFirst().getX() && y == s.getFirst().getY()) {
							return 6;
						}
						if (x < s.getFirst().getX() && y == s.getFirst().getY()) {
							return 4;
						}
						if (x == s.getFirst().getX() && y > s.getFirst().getY()) {
							return 2;
						}
						if (x == s.getFirst().getX() && y < s.getFirst().getY()) {
							return 8;
						}
					}
				}
				// If the neighbor is in the open list,
				// // judge whether the value of G that reaches the neighboring node via the current node is greater than or less than the value of G that is stored earlier than the current node (if the value of G is greater than or smaller than the value of G), set the parent node of the adjacent node as Current node, and reset the G and F values ​​of the adjacent node.
				if (openList.contains(n)) {
					if (n.getG() > (now.getG() + 10)) {
						n.setFather(now);
						n.setG(now.getG() + 10);
					}
				}
			}
		}
		// When the open list is empty, indicating that there is no new node to add, and there is no end node in the tested node, the path can not be found. At this moment, the loop returns -1 too.
		return -1;
	}
	/**
	 * Calculate Manhattan distance
	 * @param src
	 * @param des
	 * @return
	 */
	public int dis(Node src,Node des){
		return Math.abs(src.getX()-des.getX())+Math.abs(src.getY()-des.getY());
	}
	/**
	 * Random production direction
	 * @return
	 */
	public int randomDir(){
		int dir=(int)Math.random()*4;
		if(dir==0)return 8;
		else if(dir==1)return 6;
		else if(dir==2)return 2;
		else return 4;
	}
}
