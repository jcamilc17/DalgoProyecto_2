import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class ProblemaP2 {

    static int[] bfsBipartition(int v, List<List<Integer>> adj) {
        // Arreglo de colores para cada vertice
        int[] color = new int[v];
        // Inicializamos todos los colores en -1 (sin color)
        Arrays.fill(color, -1);
        
        // Contadores para cada conjunto de colores
        int a = 0, b = 0;
        
        // BFS clásico
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        color[0] = 0;
        
        while (!queue.isEmpty()) {
            // Sacamos el nodo de la cola
            int current = queue.poll();
            
            // Contamos el color del nodo actual
            // Si el color es 0, incrementamos el contador del conjunto A
            if (color[current] == 0) a++;
            // Si el color es 1, incrementamos el contador del conjunto B
            else b++;
            
            // Recorremos los vecinos del nodo actual
            for (int vecino : adj.get(current)) {
                // Si el vecino no ha sido coloreado, lo coloreamos con el color opuesto al actual
                if (color[vecino] == -1) {
                    color[vecino] = 1 - color[current]; // color alternado
                    // Agregamos al vecino a la cola
                    queue.add(vecino);
                }
            }
        }
        // Retornamos los contadores de cada conjunto
        return new int[]{a, b};
    }

    static int gcd(int a, int b) {
        // Algoritmo de Euclides para calcular MCD
        while (b != 0) {
            // Guardamos el valor de b en una variable temporal
            int t = b;
            // Actualizamos b con el residuo de a dividido por b
            b = a % b;
            // Actualizamos a con el valor de t (el valor anterior de b)
            a = t;
        }
        // Retornamos el valor de a, que es el MCD de ambos numeros
        return a;
    }

    static int minDiferencial(int[] diferenciales) {
        // Suma total de los diferenciales
        int S = 0;
        // Calculamos la suma total de los diferenciales
        for (int d : diferenciales) S += d;
        // Si la suma es 0, no hay diferencia, retornamos 0
        if (S == 0) return 0;

        // GCD reduction
        int g = diferenciales[0];
        // Calculamos el MCD de todos los diferenciales para reducir el problema
        for (int d : diferenciales) g = gcd(g, d);
        // Reducimos S y los diferenciales dividiendolos por el MCD
        S = S / g;

        // Frecuencias de cada diferencial reducido
        int[] freq = new int[1001];
        // Contamos la frecuencia de cada diferencial reducido
        for (int d : diferenciales) freq[d / g]++;

        // DP booleano solo hasta S/2
        int target = S / 2;
        // dp[j] indica si es posible formar la suma j con los diferenciales disponibles
        boolean[] dp = new boolean[target + 1];
        // Inicializamos dp[0] como verdadero, ya que siempre es posible formar la suma 0 sin usar ningun diferencial
        dp[0] = true;

        // Bounded knapsack con triple for
        for (int v = 1; v <= 1000; v++) {
            // Si el diferencial v no esta presente, continuamos al siguiente valor
            int maxVeces = freq[v];
            // Si no hay diferenciales de valor v, no procesamos este valor
            if (maxVeces == 0) continue;
            for (int j = target; j >= v; j--) {
                for (int k = 1; k <= maxVeces && k * v <= j; k++) {
                    if (dp[j - k * v]) {
                        // Si es posible formar la suma j-k*v, entonces es posible formar la suma j usando k diferenciales de valor v
                        dp[j] = true;
                        break; // booleano, con uno que llegue basta
                    }
                }
            }
            if (dp[target]) break; // early stopping
        }

        // Buscamos el j mas cercano a S/2
        int mejor = 0;
        for (int j = target; j >= 0; j--) {
            // Si dp[j] es verdadero, significa que es posible formar la suma j con los diferenciales disponibles
            if (dp[j]) {
                mejor = j;
                // Encontramos el j mas cercano a S/2 que se puede formar, lo guardamos en mejor y salimos del ciclo
                break;
            }
        }
        return Math.abs(2 * mejor - S) * g;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int C = sc.nextInt();
        
        while (C-- > 0) {
            int N = sc.nextInt();
            int[] diferenciales = new int[N];
            
            for (int k = 0; k < N; k++) {
                int v = sc.nextInt();
                int e = sc.nextInt();
                
                List<List<Integer>> adj = new ArrayList<>();
                for (int i = 0; i < v; i++) adj.add(new ArrayList<>());
                
                for (int i = 0; i < e; i++) {
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    adj.get(x).add(y);
                    adj.get(y).add(x);
                }
                
                int[] grupos = bfsBipartition(v, adj);
                diferenciales[k] = Math.abs(grupos[0] - grupos[1]);
            }
            System.out.println(minDiferencial(diferenciales));
        }
    }
}

    

