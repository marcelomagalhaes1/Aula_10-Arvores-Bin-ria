import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class No {
    int valor;
    No esquerdo, direito;

    No(int valor) {
        this.valor = valor;
        this.esquerdo = null;
        this.direito = null;
    }
}

class ArvoreBinaria {
    No raiz;

    ArvoreBinaria() {
        this.raiz = null;
    }

    void inserir(int valor) {
        raiz = inserirRecursivo(raiz, valor);
    }

    private No inserirRecursivo(No atual, int valor) {
        if (atual == null) {
            return new No(valor);
        }
        if (valor < atual.valor) {
            atual.esquerdo = inserirRecursivo(atual.esquerdo, valor);
        } else if (valor > atual.valor) {
            atual.direito = inserirRecursivo(atual.direito, valor);
        }
        return atual;
    }

    void balancearDSW() {
        criarEspinhaDorsal();
        balancearArvore();
    }

    private void criarEspinhaDorsal() {
        No temp = raiz;
        No pai = null;
        while (temp != null) {
            if (temp.esquerdo != null) {
                temp = rotacaoDireita(temp);
                if (pai == null) {
                    raiz = temp;
                } else {
                    pai.direito = temp;
                }
            } else {
                pai = temp;
                temp = temp.direito;
            }
        }
    }

    private No rotacaoDireita(No no) {
        No esquerdo = no.esquerdo;
        no.esquerdo = esquerdo.direito;
        esquerdo.direito = no;
        return esquerdo;
    }

    private void balancearArvore() {
        int n = contarNos(raiz);
        int m = maiorPotenciaMenorOuIgual(n + 1) - 1;
        realizarRotacoes(n - m);
        while (m > 1) {
            m /= 2;
            realizarRotacoes(m);
        }
    }

    private int contarNos(No no) {
        if (no == null) return 0;
        return 1 + contarNos(no.esquerdo) + contarNos(no.direito);
    }

    private int maiorPotenciaMenorOuIgual(int n) {
        int x = 1;
        while (x <= n) {
            x *= 2;
        }
        return x / 2;
    }

    private void realizarRotacoes(int quantidade) {
        No temp = raiz;
        for (int i = 0; i < quantidade; i++) {
            if (temp.direito != null) {
                temp = rotacaoEsquerda(temp);
                temp = temp.direito;
            }
        }
    }

    private No rotacaoEsquerda(No no) {
        No direito = no.direito;
        no.direito = direito.esquerdo;
        direito.esquerdo = no;
        return direito;
    }

    private int log2(int n) {
        int log = 0;
        while (n > 1) {
            n /= 2;
            log++;
        }
        return log;
    }

    void imprimirEmOrdem() {
        imprimirEmOrdemRecursivo(raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRecursivo(No no) {
        if (no != null) {
            imprimirEmOrdemRecursivo(no.esquerdo);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRecursivo(no.direito);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();
        Random random = new Random();

        List<Integer> numeros = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            int numero = random.nextInt(101);
            numeros.add(numero);
            arvore.inserir(numero);
        }

        System.out.println("Números escolhidos inicialmente: " + numeros);

        arvore.balancearDSW();

        System.out.print("Árvore balanceada: ");
        arvore.imprimirEmOrdem();

        numeros.clear();
        for (int i = 0; i < 20; i++) {
            int numero = random.nextInt(101);
            numeros.add(numero);
            arvore.inserir(numero);
        }

        System.out.println("Novos números aleátorios: " + numeros);

        arvore.balancearDSW();

        System.out.print("Árvore balanceada após novas inserções: ");
        arvore.imprimirEmOrdem();
    }
}
