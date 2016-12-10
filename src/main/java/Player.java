import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Player {

	static int width = 30;
	static int height = 20;
	static int boardSize = width * height;
	static boolean flag = true;
	static Map<Node, List<Node>> neighbors;
	static List<Node> nodes = new ArrayList<Node>();;

	@SuppressWarnings("null")
	public static void main(String args[]) {

		String move;// for output

		neighbors = new HashMap<Node, List<Node>>();
		Scanner in = new Scanner(System.in);

		Node player = null; // current player
		List<Node> enemy = null;
		Map<Node, List<Node>> graph = null;

		Node current = null; // current node position
		List<Node> neighbor; // list of node neighbors

		// adds all notes to a list
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				nodes.add(new Node(null, j, i, false, -1, false));
			}
		}

		// adds all their neighbors
		for (int index = 0; index < boardSize; index++) {

			current = nodes.get(index);
			neighbor = new ArrayList<Node>();

			if (index >= width) {
				neighbor.add(nodes.get(index - width)); // add top neighbor
			}
			if (index <= boardSize - width - 1) {
				neighbor.add(nodes.get(index + width)); // add bottom neighbor
			}
			if (index % width != 0) {
				neighbor.add(nodes.get(index - 1)); // adds left neighbor
			}
			if ((index + 1) % width != 0) {
				neighbor.add(nodes.get(index + 1)); // adds right neighbor
			}

			neighbors.put(current, neighbor);
		}

		// game loop
		while (true) {

			int N = in.nextInt(); // total number of players (2 to 4).
			int P = in.nextInt(); // your player number (0 to 3).

			for (int i = 0; i < N; i++) {

				int X0 = in.nextInt();
				int Y0 = in.nextInt();
				int X1 = in.nextInt();
				int Y1 = in.nextInt();

				if (X0 == -1) {
					removeWall(i);

				} else {
					nodes.get(X0 + Y0 * width).player = i;
					nodes.get(X1 + Y1 * width).player = i;
				}

				if (X0 >= 0 && X0 < 30 && Y0 >= 0 && Y0 < 20) {
					nodes.get(X0 + Y0 * width).wall = true;
					nodes.get(X0 + Y0 * width).value = -1;
				}
				if (X1 >= 0 && X1 < 30 && Y1 >= 0 && Y1 < 20) {
					nodes.get(X1 + Y1 * width).wall = true;
					nodes.get(X1 + Y1 * width).value = -1;
					current = nodes.get(X1 + Y1 * width);
				}
				if (i == P) { // myself
					player = current;
				} else {
					// enemy.add(current);
				}
			}
			// if (flag) {
			// System.out.println(edgeMove(player));
			// } else {
			FloodFillNumGet(player);
			// LongestPath(player);

			// for (int j = 0; j < height; j++) {
			// for (int i = 0; i < width; i++) {
			// if (nodes.get(30 * j + i).value <= 9) {
			// System.err.print(" " + nodes.get(30 * j + i).value + " ");
			// } else {
			// System.err.print(nodes.get(30 * j + i).value + " ");
			// }
			// }
			// System.err.println();
			// }
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					System.err.print(" " + nodes.get(30 * j + i).wall + " ");

				}
				System.err.println();
			}

			Node nextMove = FloodFillLongestPath(player);
			move = nextMoves(player, nextMove);
			System.out.println(move); // output
			// reset Flood Fill
			for (int i = 0; i < boardSize; i++) {
				nodes.get(i).FFCheck = false;
				nodes.get(i).value = -1;
			}

		}

	}
	// System.err.println(player.value);

	// }

	public static void removeWall(int player) {
		for (int i = 0; i < boardSize; i++) {
			if (nodes.get(i).player == player) {
				nodes.get(i).wall = false;
			}
		}

	}

	public static String edgeMove(Node player) {

		while (!(player.x == 0 || player.y == 0 || player.x == 29 || player.y == 19)) {
			System.err.println(player.x + " , " + player.y);
			if (player.x <= 15) {
				if (!nodes.get(player.y * width + player.x - 1).wall) {
					return "LEFT";
				} else {
					break;
				}
			} else if (player.x > 15) {
				if (!nodes.get(player.y * width + player.x + 1).wall) {
					return "RIGHT";
				} else {
					break;
				}
			} else if (player.y >= 10) {
				if (!nodes.get((player.y + 1) * width + player.x).wall) {
					return "DOWN";
				} else {
					break;
				}
			} else if (player.y < 10) {
				if (!nodes.get((player.y - 1) * width + player.x).wall) {
					return "UP";
				} else {
					break;
				}
			}
		}
		flag = false;

		if (player.x <= 10 || player.x > 10) {
			if (player.y > 10) {
				return "UP";
			} else {
				return "DOWN";
			}

		} else if (player.y <= 10 || player.y > 10) {
			if (player.x <= 15) {
				return "RIGHT";
			} else {
				return "LEFT";
			}
		}
		return null;
	}

	public static void LongestPath(Node Player) {
		int steps = 1;
		Node temNode = Player;
		temNode.value = 0;
		Stack<Node> stack = new Stack<Node>();
		stack.add(temNode);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			// System.err.println("node"+node.x+" , "+node.y+"checked");
			for (Node neighbor : neighbors.get(node)) {

				if (!neighbor.wall && !neighbor.FFCheck) {
					neighbor.value = node.value + 1;
					stack.add(neighbor);
					neighbor.FFCheck = true;
				}
			}
			// System.err.println(node.x + " , " + node.y+" , "+node.FFCheck);
			// System.err.println(queue.size());
		}
	}

	static List<Integer> steps = new ArrayList<Integer>();

	public static void FloodFillNumGet(Node Player) {
		Node temNode = Player;
		temNode.value = 0;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(temNode);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			// System.err.println("node"+node.x+" , "+node.y+"checked");
			for (Node neighbor : neighbors.get(node)) {

				if (!neighbor.wall && !neighbor.FFCheck) {
					neighbor.value = node.value + 1;
					queue.add(neighbor);
					neighbor.FFCheck = true;
				}
			}
			// System.err.println(node.x + " , " + node.y+" , "+node.FFCheck);
			// System.err.println(queue.size());
		}
	}

	public static Node FloodFillLongestPath(Node Player) {
		Node node = nodes.get(0);
		for (int i = 1; i < boardSize; i++) {
			if (!nodes.get(i).wall) {
				if (nodes.get(i).value >= node.value) {
					node = nodes.get(i);
				}
			}
		}

		while (node.value != 1) {
			for (Node n : neighbors.get(node)) {
				if (n.value == node.value - 1) {
					node = n;
					System.err.println(node.x + " , " + node.y);
				}
			}
		}
		// System.err.println(node.x+" , "+node.y);
		return node;

	}

	public static String nextMoves(Node player, Node move) {

		if (player.y - 1 == move.y)
			return "UP";
		else if (player.x + 1 == move.x)
			return "RIGHT";
		else if (player.y + 1 == move.y)
			return "DOWN";
		else if (player.x - 1 == move.x)
			return "LEFT";
		return null;
	}
}

class Node<T> {
	private final T data;
	int x;
	int y;
	int value;
	boolean wall;
	boolean FFCheck;
	int player;

	Node(T data, int x, int y, boolean wall, int value, boolean FFC) {
		this.data = data;
		this.x = x;
		this.y = y;
		this.wall = wall;
		this.value = value;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public T getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return this.y + this.x * 1000;
	}

	@Override
	public String toString() {
		return ("Node{" + "data=" + data + "(x:" + x + ", y:" + y + ", v:" + wall + ")");
	}

}