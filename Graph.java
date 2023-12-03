//header file
import java.util.ArrayList;

public class Graph {
	
	//variables to use in this class
	private int n;
	private int m;
	private ArrayList<Node> node;
	private boolean [][] A;
	private double [][] C;
	
	//constructors
	public Graph() {
		this.init(0);
		
	}
	
	public Graph(int n){
		this.init(n);
	}
	
	//setters
	public void setN(int n) {
		
		//setter for # nodes
		this.n = n;
		
	}
	public void setM(int m){
		
		//setter for # arcs
		this.m = m;
	}
	public void setArc(int i, int j, boolean b){
		
		//sets the arc betwn i & j nodes
		A[i][j] = b;
		A[j][i] = b;
		
	}
	public void setCost(int i, int j, double c) {
		
		//sets cost of an arc betwn i & j nodes
		C[i][j] = c;
		C[j][i] = c;
	}	
	
	//getters
	public int getN() {
		
		//getter for # nodes
		return n;	
	}
	public int getM() {
		
		//getter for # arcs
		return m;
	}
	public boolean getArc(int i, int j) {
		
		//if there's an arc betwn i & j nodes
		return A[i][j];
		
	}
	public double getCost(int i, int j) {
		
		//the cost of an arc betwn i & j nodes
		return C[i][j];
	}
	public Node getNode(int i) {
		
		//returns a node using its index
		return node.get(i);
	}
	public void init(int n) {
		this.n = n;
		this.m = 0;
		this.node = new ArrayList<Node>(n);
		this.A = new boolean[n][n];
		this.C = new double[n][n];
	}
	public void reset() {
		
		//resets the graph to default
		node.clear();
		init(n);
		m = 0;
	}
	public boolean existsArc(int i, int j, double cost) {
		
		//checks if there's an arc betwn i & j nodes
		if(i<0 || j<0|| i>=n || j>=n) {
			return false;
		}
		return (A[i][j] && C[i][j]==cost) || (A[j][i] && C[j][i] == cost);
	}
	
	public boolean isArcPresent(int i, int j) {
        // Ensure indices are valid
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return false;
        }
        //checks for the existence of the arc
        return A[i][j] || A[j][i];
    }
	public boolean existsNode(Node t) {
		
		//checks if the name or coordinates exists in the graph
		for(Node existingNode: node) {
			if(existingNode.getName().equals(t.getName()) && existingNode.getLat() == t.getLat() && existingNode.getLon() == t.getLon()) {
				return true;
			}
		}
		
		return false;
	}
	public boolean addArcWithCost(int i, int j, double cost) {
		
		//adds arc betwn i & j nodes; returns true if successful and false if not
		if (existsArc(i, j, cost)) {
			System.out.println("ERROR: Arc already exists!");
            System.out.println("");

			return false;
		}
		if(i != j && !A[j][j]) {
			setArc(i, j, true);
			setCost(i, j, cost);
			m++;
			return true;
	}
		return false;
	}
	
	public boolean addArc(int i, int j) {
		if(i != j && !isArcPresent(i, j)) {
			double cost = Node.distance(node.get(i), node.get(j));
			setArc(i, j, true);
			setCost(i, j, cost);
			m++;
			return true;
		}
		//adding Arc using the i & j nodes and the new function
		return false;
	}
	
	//method to remove an Arc from the list
	public int[] getArcIndices(int k) {
	    int count = 0;
	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            if (A[i][j]) {
	                if (count == k) {
	                    return new int[] { i, j };  //return the matrix indices of the arc
	                }
	                count++;
	            }
	        }
	    }
	    return null;  //return null if the arc index is invalid
	}
	
	public void removeArc(int k) {
	    int[] arcIndices = getArcIndices(k);
	    if (arcIndices != null) {
	        int i = arcIndices[0];
	        int j = arcIndices[1];
	        A[i][j] = false;
	        A[j][i] = false;
	        C[i][j] = 0;
	        C[j][i] = 0;
	        m--;
	    }
	}
	
	//adding a node to the graph
	public boolean addNode(Node t) {
		
		//adds node to the graph; return true if successful and false if not
		if(!node.contains(t)) {
			node.add(t);
			return true;
		}
		return false;
	}
	
	public void print() {
		//prints the nodes and arcs
		printNodes();
		printArcs();
	}
	
	public void printNodes() {
		//prints the Node list
		System.out.println("NODE LIST");
		System.out.printf("%-5s%-20s%-20s%n", "No.", "Name", "Coordinates");
		System.out.println("-----------------------------------------");
		for(int i = 0; i<getN(); i++) {
			Node node = getNode(i);
			String coordinates = String.format("(%.1f,%.1f)", node.getLat(), node.getLon());
	        System.out.printf("%-5d%-20s%-20s%n", i + 1, node.getName(), coordinates);
		}
	}
	public void printArcs() {
		
		//prints the Arc list
		System.out.println("\nARC LIST");
		System.out.printf("%-5s%-15s%-10s\n", "No.", "Cities", "Distance");
		System.out.println("-----------------------------------------");
		int count = 1;
		for(int i = 0; i < n; i++) {
			for(int j = i + 1; j < n; j++) {
				if(A[i][j]) {
					String cities = (i+1) + "-" + (j+1);
					System.out.printf("%-6d%-16s%-10.2f\n", count, cities, C[i][j]);
					count++;
				}
			}
		}	
	}
	
	//checks the path of the Nodes (Error handling)
	public boolean checkPath(int[] P) {
	    if (P[0] < 0 || P[P.length - 1] < 0 || P[0] >= n || P[P.length - 1] >= n) {
	        System.out.print("ERROR: Invalid city number!");
            System.out.println("");

	        return false;
	    }

	    boolean[] visited = new boolean[n];

	    if (P[0] != P[P.length - 1]) {
	        System.out.print("ERROR: Start and end cities must be the same!");
            System.out.println("");

	        return false;
	    }

	    for (int i = 0; i < P.length - 1; i++) {
	        int cityIndex = P[i];

	        if (visited[cityIndex]) {
	            System.out.print("ERROR: Cities cannot be visited more than once!");
                System.out.println("");

	            return false;
	        }
	        visited[cityIndex] = true;
	    }

	    for (int i = 0; i < visited.length; i++) {
	        if (!visited[i]) {
	            System.out.print("ERROR: Not all cities are visited!");
                System.out.println("");

	            return false;
	        }
	    }

	    for (int i = 0; i < P.length - 1; i++) {
	        int city1 = P[i];
	        int city2 = P[i + 1];

	        if (city1 < 0 || city2 < 0 || city1 >= n || city2 >= n) {
	            System.out.print("ERROR: Invalid city number!");
                System.out.println("");

	            return false;
	        }

	        if (!A[city1][city2]) {
	            System.out.print("ERROR: Arc " + (city1 + 1) + "-" + (city2 + 1) + " does not exist!");
                System.out.println("");
	            return false;
	        }
	    }

	    return true;
	}

	
	//calculates the cost of the path
	public double pathCost(int[] P) {
		
		//calculates the total cost of a path
		double totalCost = 0.0;
		for(int i = 0; i < P.length - 1; i++) {
			int city1 = P[i];
			int city2 = P[i+1];
			totalCost += Node.distance(node.get(city1), node.get(city2));
		}
		return totalCost;
	}
	
	
}


