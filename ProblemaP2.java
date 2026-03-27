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
        // Calculamos la suma total
        int S = 0;
        for (int d : diferenciales) S += d;

        // Si S es 0, el diferencial es 0 directamente
        if (S == 0) return 0;

        // Calculamos el GCD de todos los diferenciales
        int g = diferenciales[0];
        for (int d : diferenciales) g = gcd(g, d);

        // Dividimos todo por el GCD
        S = S / g;
        int[] reducidos = new int[diferenciales.length];
        for (int i = 0; i < diferenciales.length; i++) {
            reducidos[i] = diferenciales[i] / g;
        }
        
        // DP booleano sobre los valores reducidos
        boolean[] dp = new boolean[S + 1];
        // Caso Base: suma 0 siempre es posible (por el conjunto vacio)
        dp[0] = true;
        
        // Para cada diferencial, actualizamos el DP
        for (int d : diferenciales) {
            boolean[] nuevo = dp.clone(); // copia para no reusar el mismo diferencial
            for (int j = d; j <= S; j++) {
                nuevo[j] = dp[j] || dp[j - d];
            }
            // Actualizamos el DP con el nuevo estado
            dp = nuevo;
        }
        
        // Buscamos el j mas cercano a S/2
        int mejor = 0;
        for (int j = S / 2; j >= 0; j--) {
            // Si encontramos un j que es posible, lo guardamos como mejor y termina el ciclo
            if (dp[j]) {
                mejor = j;
                break;
            }
        }
        
        // La diferencia minima es la diferencia entre el total y dos veces el mejor j encontrado
        return Math.abs(2 * mejor - S);
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

    

