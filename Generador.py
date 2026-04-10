import random

def grafo_bc_linea(n, tam_A, extra_aristas=0):
    if tam_A <= 0 or tam_A >= n:
        raise ValueError("tam_A debe estar entre 1 y n-1")
    
    A = list(range(tam_A))
    B = list(range(tam_A, n))
    aristas = set()
    
    for i in range(1, n):
        if i in A:
            v = random.choice(B)
        else:
            v = random.choice(A)
        aristas.add((i, v))
    
    max_aristas = len(A) * len(B)
    objetivo = min(n - 1 + extra_aristas, max_aristas)
    
    while len(aristas) < objetivo:
        u = random.choice(A)
        v = random.choice(B)
        aristas.add((u, v))
    
    return f"{n} {len(aristas)} " + " ".join(f"{u} {v}" for u, v in aristas)


# Caso imposible de balancear a 0:
# N impar de grafos todos con el mismo diferencial impar
C = 50
N = 99  # impar!

with open("casoImbalanceado.txt", "w") as f:
    f.write(f"{C}\n")
    for _ in range(C):
        f.write(f"{N}\n")
        for _ in range(N):
            # todos con diferencial 3 (2 en A, 5 en B)
            linea = grafo_bc_linea(7, 2, 0)
            f.write(linea + "\n")

print("Caso imbalanceado generado ✅")