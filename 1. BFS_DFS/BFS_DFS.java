// Include necessary headers
#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <omp.h> // OpenMP library

using namespace std;

// Define a simple graph class
class Graph {
    int V; // Number of vertices
    vector<vector<int>> adj; // Adjacency list

public:
    Graph(int V) {
        this->V = V;
        adj.resize(V);
    }

    // Function to add an undirected edge
    void addEdge(int u, int v) {
        adj[u].push_back(v);
        adj[v].push_back(u);
    }

    // Parallel BFS function
    void parallelBFS(int start) {
        vector<bool> visited(V, false);
        queue<int> q;

        visited[start] = true;
        q.push(start);

        cout << "Parallel BFS starting from node " << start << ": ";

        while (!q.empty()) {
            int node = q.front();
            q.pop();
            cout << node << " ";

            // Parallelize visiting neighbors
            #pragma omp parallel for
            for (int i = 0; i < adj[node].size(); i++) {
                int neighbor = adj[node][i];
                if (!visited[neighbor]) {
                    #pragma omp critical // Ensure only one thread updates at a time
                    {
                        if (!visited[neighbor]) { // Check again inside critical
                            visited[neighbor] = true;
                            q.push(neighbor);
                        }
                    }
                }
            }
        }
        cout << endl;
    }

    // Parallel DFS function
    void parallelDFS(int start) {
        vector<bool> visited(V, false);
        stack<int> s;

        s.push(start);

        cout << "Parallel DFS starting from node " << start << ": ";

        while (!s.empty()) {
            int node = s.top();
            s.pop();

            if (!visited[node]) {
                visited[node] = true;
                cout << node << " ";

                // Parallelize visiting neighbors
                #pragma omp parallel for
                for (int i = 0; i < adj[node].size(); i++) {
                    int neighbor = adj[node][i];
                    if (!visited[neighbor]) {
                        #pragma omp critical // Ensure only one thread pushes at a time
                        s.push(neighbor);
                    }
                }
            }
        }
        cout << endl;
    }
};

// Main function
int main() {
    Graph g(6); // Create a graph with 6 nodes (0 to 5)

    // Add edges
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 5);

    // Perform Parallel BFS and DFS
    g.parallelBFS(0);
    g.parallelDFS(0);

    return 0;
}
