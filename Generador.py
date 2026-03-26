import random

def grafo_bc_linea(n, tam_A, extra_aristas=0):
    """
    n: total de nodos
    tam_A: tamaño del conjunto A (el resto va a B)
    """

    if tam_A <= 0 or tam_A >= n:
        raise ValueError("tam_A debe estar entre 1 y n-1")

    A = list(range(tam_A))
    B = list(range(tam_A, n))

    aristas = set()

    # 🔹 Asegurar conexidad
    for i in range(1, n):
        if i in A:
            v = random.choice(B)
        else:
            v = random.choice(A)
        aristas.add((i, v))

    # 🔹 Máximo posible (para evitar loop infinito)
    max_aristas = len(A) * len(B)

    objetivo = min(n - 1 + extra_aristas, max_aristas)

    # 🔹 Aristas extra
    while len(aristas) < objetivo:
        u = random.choice(A)
        v = random.choice(B)
        aristas.add((u, v))

    return f"{n} {len(aristas)} " + " ".join(f"{u} {v}" for u, v in aristas)


# 🔹 Ejemplo: 1000 nodos, 300 en A y 700 en B
n = 1000
tam_A = 657
extra = 2500

with open("grafo.txt", "w") as f:
    f.write(grafo_bc_linea(n, tam_A, extra))

print("Grafo NO balanceado generado ✅")