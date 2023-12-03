//header file
import java.io.*;
import java.util.Arrays;
public class Pro3_samireez {
	
	//global buffered reader for the entire project
	public static BufferedReader jIn = new BufferedReader(new InputStreamReader(System.in));
	public static Graph graph = new Graph();
	
	//main function body
	public static void main(String[] args) throws NumberFormatException, IOException{
				
		//menu options using a while loop and a series of switch-breaks
		while(true) {
			//shows menu options
			displayMenu();
			String choice = jIn.readLine().toUpperCase();
			
			switch (choice) {
			
			//creates the graph
			case "G":
				System.out.println("");
				createGraph(graph);
				break;
			
			//edits cities
			case "C":
				System.out.println("");
				if(graph != null) {
					editCities(graph);
				} else {
					System.out.println("ERROR: No graph has been loaded!\n");
					System.out.print("\n");
				}
				break;
			
				//edits arcs
			case "A":
				
				System.out.println("");
				if(graph != null) {
					editArcs(graph);
				} else {
					System.out.println("ERROR: No graph has been loaded!");
					System.out.print("\n");
				}
				break;
			
				//displays current graph
			case "D":
				System.out.println("");
				if(graph != null) {
					graph.print();
				} else {
					System.out.println("ERROR: No graph has been loaded!");
					System.out.print("\n");
				}
				break;
			
			//rests graph
			case "R":
				System.out.println("");
				graph = null;
				System.out.print("\n");
				break;
			
			//salesman;s path
			case "P":
				System.out.println("");
				if(graph != null && graph.getN() > 0) {
					tryPath(graph);
				} else {
					System.out.println("ERROR: No graph has been loaded!");
					System.out.print("\n");
				}
				break;
			
			//quits the program
			case "Q":
				System.out.println("");
				System.out.print("\n");
				System.out.print("Ciao!");
				System.exit(0);
			
			default:
	
				System.out.print("\nERROR: Invalid menu choice!\n");
			}			
		}			
	}
	
	//menu options
	public static void displayMenu() {

		System.out.println(" " + " " + " " + "JAVA TRAVELING SALESMAN PROBLEM V1");
		System.out.println("G - Create graph");
		System.out.println("C - Edit cities");
		System.out.println("A - Edit arcs");
		System.out.println("D - Display graph info");
		System.out.println("R - Reset graph");
		System.out.println("P - Enter salesman's path");
		System.out.println("Q - Quit");
		System.out.println();
		System.out.printf("Enter choice: ");
	}
	
	//creates the graph using methods from the Node and Graph classes
	public static boolean createGraph(Graph G) throws IOException{
		
		int numCities = getNumCities();
		System.out.println();
		
		if(numCities == 0) {
			return false; 
		} else {
			G.init(numCities);
			
			boolean arcMessagePrinted = false;
			
			for(int i = 0; i < numCities; i++) {
				System.out.printf("City " + (i + 1) + ":");
				System.out.println();
				System.out.printf("   Name: ");
				String name = jIn.readLine();
				
				Node newNode = new Node(name, 0.0, 0.0);
				if(graph.existsNode(newNode)) {
					System.out.println("Node already exists!");
		            System.out.println("");
					i--;
					continue;
				}
				
				double latitude = getDouble("   latitude: ", -90, 90);
				double longitude = getDouble("   longitude: ", -180, 180);
				System.out.println();
				
				newNode.setLat(latitude);
				newNode.setLon(longitude);
				if(graph.existsNode(newNode)) {
					System.out.println("Node already exists!");
		            System.out.println("");
	
					i--;
					continue;
				}
				
				Node city = new Node(name, latitude, longitude);
				
				
				graph.addNode(city);
				
				if(!arcMessagePrinted) {
					System.out.println("Now add arcs: ");
					System.out.println("");
					arcMessagePrinted = true;
				}
			}
			
			addArcs(graph);
		}

		return true;
	}
	
	//edits cities using methods from the Node and Graph classes
	public static void editCities(Graph graph) throws IOException{
        graph.printNodes();
        boolean editingDone = false;
        
		while (true) {  //keep prompting the user until they enter 0 to quit
	        System.out.print("Enter city to edit (0 to quit): ");
	        String input = jIn.readLine();  // Use readLine() to get the input as a String

	        if (input.equals("0")) {
	            return;  //exit the method if 0 is entered
	        }

	        try {
	            int cityNum = Integer.parseInt(input);  //try to parse the input as an integer
	            if (cityNum < 1 || cityNum > graph.getN()) {
	                System.out.println("ERROR: Input must be an integer in [1, " + graph.getN() + "]!");
	                continue;  //if the input is out of range, print an error message and prompt again
	            }

	            cityNum -= 1;  //adjust cityNum to be 0-indexed for the getNode method
	            Node city = graph.getNode(cityNum);
	            city.userEdit();
	            editingDone = true;
	            break;

	        } catch (NumberFormatException e) {
	            System.out.println("ERROR: Invalid input!");  //if the input is not a valid integer, print an error message
	        }
	    }
	}
	
	//adds arcs using methods from the Node and Graph classes
	public static void addArcs(Graph graph) throws IOException{
		

		graph.printNodes();

		while(true) {
			System.out.println("ARC LIST\n");

			int startingCity = getInteger("Enter first city index (0 to quit): ", 0, graph.getN()) - 1;
			if (startingCity == -1) {
				return;
			}
			
			int endingCity = getInteger("Enter second city index (0 to quit): ", 0, graph.getN()) -1;
			if (endingCity == -1) {
				return;
			}
			
			if (startingCity == endingCity) {
				System.out.println("ERROR: A city cannot be linked to itself!");
			} else {
				boolean arcAdded = graph.addArc(startingCity,  endingCity);
				if(arcAdded) {
					System.out.printf("Arc %d-%d added!\n", (startingCity + 1) , (endingCity + 1));
				} else {
					System.out.println("ERROR: Arc already exists!");
                    System.out.println("");

				}
			}
		}
		
	}
	
	//to edit the arcs
	public static void editArcs(Graph graph) throws IOException {
	    
	    graph.printArcs();
	    System.out.println("\nEDIT ARCS");
	    System.out.println("A - Add arc");
	    System.out.println("R - Remove arc");
	    System.out.println("Q - Quit");

	    while (true) {
	        System.out.print("Enter choice: ");
	        String choice = jIn.readLine().toUpperCase();

	        switch (choice) {
	            case "A":
	            	graph.printNodes();
	                while (true) {  //nested loop to keep prompting for city indices
	                    int startingCity = getInteger("Enter first city index (0 to quit): ", 0, graph.getN()) - 1;
	                    if (startingCity == -1) {
	                    	graph.printArcs();
	                        break;  //exit nested loop if 0 is entered
	                    }

	                    int endingCity = getInteger("Enter second city index (0 to quit): ", 0, graph.getN()) - 1;
	                    if (endingCity == -1) {
	                        break;  //exit nested loop if 0 is entered
	                    }

	                    if (startingCity == endingCity) {
	                        System.out.println("ERROR: A city cannot be linked to itself!");
                            System.out.println("");

	                    } else {
	                        boolean arcAdded = graph.addArc(startingCity, endingCity);
	                        if (arcAdded) {
	                            System.out.printf("Arc %d-%d added!\n", (startingCity + 1), (endingCity + 1));
	                            System.out.println("");
	                        } else {
	                            System.out.println("ERROR: Arc already exists!");
	                            System.out.println("");
	                        }
	                    }
	                }
	                break;

	            case "R":
	            	graph.printArcs();
	            	System.out.println("\nEDIT ARCS");
	        	    System.out.println("A - Add arc");
	        	    System.out.println("R - Remove arc");
	        	    System.out.println("Q - Quit");
	                int arcToRemove = getInteger("Enter arc to remove (0 to quit): ", 0, graph.getM()) - 1;
	                if (arcToRemove == -1) {
	                    break;
	                }

	                graph.removeArc(arcToRemove);
	                System.out.printf("Arc %d removed!", arcToRemove + 1);
	                graph.printArcs();
	                break;

	            case "Q":
	                return;

	            default:
	                System.out.println("ERROR: Invalid menu choice!");
	                System.out.println("");
	        }
	    }
	}
	
	

	
	//salesman's path using methods from the Node and Graph classes
	public static void tryPath(Graph graph) throws IOException {
	    System.out.printf("Enter the %d cities in the route in order: \n", graph.getN());
	    int[] path = new int[graph.getN() + 1];
	    Arrays.fill(path, -1);

	    for (int i = 0; i < graph.getN() + 1; i++) {
	        while (true) {
	            System.out.print("City " + (i + 1) + ": ");
	            try {
	                String input = jIn.readLine();
	                int cityIndex = Integer.parseInt(input) - 1;

	                if (cityIndex >= 0 && cityIndex < graph.getN()) {
	                	if(i == graph.getN() && cityIndex != path[0]){
	                    	System.out.println("EEROR: Start and end cities must be the same!");
	                    }else if (contains(path, cityIndex, i)) {
	                        System.out.println("ERROR: City " + (cityIndex + 1) + " is already visited!");
	                    } else {
	                        path[i] = cityIndex;
	                        break;
	                    }
	                } else {
	                    System.out.printf("\nERROR: Input must be an integer in [1, %d]!\n", graph.getN());
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("\nERROR: Invalid input. Please enter a valid integer.");
	            }
	        }
	    }
	    

	    if (graph.checkPath(path)) {
	        double cost = graph.pathCost(path);
	        System.out.println("\nThe total distance traveled is " + String.format("%.2f", cost) + " km.");
            System.out.println("");

	    } else {
	        System.out.println("\nERROR: Not all cities are visited!");
            System.out.println("");

	    }
	}

	
	private static boolean contains(int[] path, int cityIndex, int currentPosition) {
		for (int i = 1; i < currentPosition; i++) {
	        if (path[i] == cityIndex) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	//getDouble method to handle user input (from Project-2)
	public static double getDouble(String prompt, double LB, double UB) throws IOException{
			
			double value = 0;
			boolean valid;
			
			do {
				try {
					valid = true;
					System.out.print(prompt);
					String input = jIn.readLine();
					value = Double.parseDouble(input);
					
					if (value >= LB && value <= UB) {
						break;
					} else {
		                throw new NumberFormatException();
		            }
				}
				catch (NumberFormatException e) {
					valid = false;
					System.out.printf("ERROR: Input must be a real number in [%.1f, %.1f]!", LB, UB);
                    System.out.println("");
				}
			} while (!valid);
			
			return value;	
		}
	
	//getInteger method to handle user input (from Project-2)
	public static int getInteger(String prompt, int LB, int UB) throws IOException{
			
			int value = 0;
			boolean valid;
			
			do {
				try {
					valid = true;
					System.out.print(prompt);
					String input = jIn.readLine();	
					value = Integer.parseInt(input);
					
					if (value >= LB && value <= UB) {
						break;				
					} else {
		                throw new NumberFormatException();
		            }
				}catch (NumberFormatException e){
					valid = false;
					System.out.printf("ERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
					System.out.println("");
				}
			} while (!valid);
			
			return value;
	
		}
	
	//method to get # cities
	public static int getNumCities() throws IOException {
		int value = 0;
		boolean valid;
		
		do{
			try {
				valid = true;
				System.out.print("Enter the number of cities (0 to quit): ");
				String input = jIn.readLine();
				value = Integer.parseInt(input);
				
				if(value < 0) {
					throw new NumberFormatException();
				}
			}
			catch (NumberFormatException e) {
				valid = false;
				System.out.println("ERROR: Input must be an integer in [0, infinity]!");
				System.out.println("");
			
			}
		} while (!valid);
		
		return value;
	
	}

} 

//fix path (option P)
//fix formatting
