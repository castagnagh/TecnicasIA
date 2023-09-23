package missionarioecanibal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import busca.Antecessor;
import busca.BuscaBidirecional;
import busca.BuscaIterativo;
import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.Estado;
import busca.Nodo;

/**
 * Solução para o problema dos missionários e canibais.
 *
 * @author (Malcus Otavio Quinoto Imhof & Daniel Dalcastagne - 5. Semestre Matutino - BCC)
 * @version (02/04/2004)
 */
public class MissionarioCanibal implements Estado, Antecessor {

    public String getDescricao() {
        return
            "Tres missionários e três canibais estão à beira de um rio e dispõem de\n" +
            "um barco com capacidade para apenas duas pessoas. O problema é determinar\n" +
            "as tripulações de uma série de travessias de maneira que todo o grupo passe\n" +
            "para o outro lado do rio, respeitada a condição de que em momento algum os\n" +
            "canibais sejam mais numerosos do que os missionários em uma das margens do rio.\n\n" +
            "Implementação de Malcus Otavio Quinoto Imhof & Daniel Dalcastagne - 5. Semestre Matutino - BCC\n\n";
    }

    final int Me, Md, Ce, Cd; // Missionários na Esquerda, Missionários na Direita, Canibais na Esquerda, Canibais na Direita
    final char barco;
    final String op;

    /** Recebe a quantidade de missionários e canibais em cada margem */
    public MissionarioCanibal(int Me, int Ce, int Md, int Cd, char b, String o) {
        this.Me = Me;
        this.Ce = Ce;
        this.Md = Md;
        this.Cd = Cd;
        this.barco = b;
        this.op = o;
    }

    public boolean ehMeta() {
        return Me == 0 && Ce == 0;
    }

    public int custo() {
        return 1;
    }

    /** Lista de sucessores */
    public List<Estado> sucessores() {
        List<Estado> suc = new LinkedList<Estado>(); // Lista de sucessores
        // Levar um missionário
        levar1m(suc);
        // Levar dois missionários
        levar2m(suc);
        // Levar 1 missionário e 1 canibal
        levar1m1c(suc);
        // Levar 1 canibal
        levar1c(suc);
        // Levar 2 canibais
        levar2c(suc);

        // Retornar a lista de Sucessores
        return suc;
    }

    /** Lista de antecessores, para busca bidirecional */
    public List<Estado> antecessores() {
        return sucessores();
    }

    /** Verifica se o sucessor gerado é válido */
    public boolean ehValido() {
        if ((Me < Ce && Me > 0) || (Md < Cd && Md > 0)) {
            return false;
        }
        return true;
    }

    /** Movimenta um missionário de uma margem a outra */
    public void levar1m(List<Estado> suc) {
        // Se o barco estiver do lado esquerdo e houver pelo menos 1 missionário nesse lado
        if (barco == 'e' && Me > 0) {
            // Gera um sucessor
            MissionarioCanibal novo = new MissionarioCanibal(Me - 1, Ce, Md + 1, Cd, 'd', "Levar 1 missionário para margem direita");
            // Verifica se o sucessor gerado é válido
            if (novo.ehValido())
                // Se for válido, adiciona na lista de sucessores
                suc.add(novo);
        } else {
            // Se o barco estiver do lado direto e Houver pelo menos 1 missionário neste lado
            if (barco == 'd' && Md > 0) {
                // Gerar o sucessor
                MissionarioCanibal novo = new MissionarioCanibal(Me + 1, Ce, Md - 1, Cd, 'e', "Levar 1 missionário para margem esquerda");
                // Verificar se ele é válido
                if (novo.ehValido())
                    // Adicionar na lista de sucessores
                    suc.add(novo);
            }
        }
    }

    /** Movimenta 2 missionários de uma margem a outra */
    public void levar2m(List<Estado> suc) {
        // Se o barco estiver do lado esquerdo e houver pelo menos 2 missionários deste lado
        if (barco == 'e' && Me > 1) {
            // Gerar o sucessor
            MissionarioCanibal novo = new MissionarioCanibal(Me - 2, Ce, Md + 2, Cd, 'd', "Levar 2 missionários para margem direita");
            // Verificar se ele é válido
            if (novo.ehValido())
                // Adicionar na lista de sucessores
                suc.add(novo);
        } else {
            // Se o barco estiver do lado direito e houver dois missionários deste lado
            if (barco == 'd' && Md > 1) {
                // Gerar sucessor
                MissionarioCanibal novo = new MissionarioCanibal(Me + 2, Ce, Md - 2, Cd, 'e', "Levar 2 missionários para margem esquerda");
                // Verificar se ele é válido
                if (novo.ehValido())
                    // Adicionar na lista de sucessores
                    suc.add(novo);
            }
        }
    }

    /** Movimentar 1 missionário e 1 canibal */
    public void levar1m1c(List<Estado> suc) {
        // Se o barco estiver do lado esquerdo e houver pelo menos um missionário e um canibal deste lado
        if (barco == 'e' && Me > 0 && Ce > 0) {
            // Gerar sucessor
            MissionarioCanibal novo = new MissionarioCanibal(Me - 1, Ce - 1, Md + 1, Cd + 1, 'd', "Levar 1 missionário e 1 canibal para margem direita");
            // Verificar se ele é válido
            if (novo.ehValido())
                // Adicionar na lista de sucessores
                suc.add(novo);
        } else {
            // Se o barco estiver do lado direito e houver pelo menos um missionário e um canibal deste lado
            if (barco == 'd' && Md > 0 && Cd > 0) {
                // Gerar Sucessor
                MissionarioCanibal novo = new MissionarioCanibal(Me + 1, Ce + 1, Md - 1, Cd - 1, 'e', "Levar 1 missionário e 1 canibal para margem esquerda");
                // Verificar se ele é válido
                if (novo.ehValido())
                    // Adicionar na lista de sucessores
                    suc.add(novo);
            }
        }
    }

    /** Movimentar um canibal de uma margem a outra */
    public void levar1c(List<Estado> suc) {
        // Se o barco estiver do lado esquero e houver pelo menos um canibal deste lado
        if (barco == 'e' && Ce > 0) {
            // Gerar sucessor
            MissionarioCanibal novo = new MissionarioCanibal(Me, Ce - 1, Md, Cd + 1, 'd', "Levar 1 canibal para margem direita");
            // Verificar se ele é válido
            if (novo.ehValido())
                // Adicionar na lista
                suc.add(novo);
        } else {
            // Se o barco estiver no lado direito e houver pelo menos 1 canibal deste lado
            if (barco == 'd' && Cd > 0) {
                // Gerar sucessor
                MissionarioCanibal novo = new MissionarioCanibal(Me, Ce + 1, Md, Cd - 1, 'e', "Levar 1 canibal para margem esquerda");
                // Verificar se ele é válido
                if (novo.ehValido())
                    // Adicionar na lista de sucessores
                    suc.add(novo);
            }
        }
    }

    /** Levar 2 canibais de uma margem a outra */
    public void levar2c(List<Estado> suc) {
        // Se o barco estiver do lado esquerdo e houver pelo menos dois canibais deste lado
        if (barco == 'e' && Ce > 1) {
            // Gerar sucessor
            MissionarioCanibal novo = new MissionarioCanibal(Me, Ce - 2, Md, Cd + 2, 'd', "Levar 2 canibais para margem direita");
            // Verificar se é válido
            if (novo.ehValido())
                // Adicionar na lista de sucessores
                suc.add(novo);
        } else {
            // Se o barco estiver do lado direto e houver pelo menos 2 canibais deste lado
            if (barco == 'd' && Cd > 1) {
                // Gerar sucessor
                MissionarioCanibal novo = new MissionarioCanibal(Me, Ce + 2, Md, Cd - 2, 'e', "Levar 2 canibais para margem esquerda");
                // Verificar se ele é válido
                if (novo.ehValido())
                    // Adicionar na lista
                    suc.add(novo);
            }
        }
    }

    public String toString() {
        String esq = "M".repeat(Me) + "C".repeat(Ce);
        String dir = "M".repeat(Md) + "C".repeat(Cd);
        if (barco == 'e') {
            esq += 'B';
        } else {
            dir += 'B';
        }
        return esq + "|" + dir + " ( " + op + " )" + "\n";
    }

    public static void main(String[] a) throws IOException {
        MissionarioCanibal inicial = new MissionarioCanibal(3, 3, 0, 0, 'e', "");
        MissionarioCanibal finalu = new MissionarioCanibal(0, 0, 3, 3, 'd', "");

        String str;
        BufferedReader teclado;
        teclado = new BufferedReader(new InputStreamReader(System.in));

        Nodo n = null;

        System.out.print("Digite sua opção de busca { Digite S para finalizar }\n");
        System.out.print("\t1  -  Largura\n");
        System.out.print("\t2  -  Profundidade\n");
        System.out.print("\t3  -  Pronfundidade Iterativo\n");
        System.out.print("\t4  -  Bidirecional\n");
        System.out.print("Opção: ");
        str = teclado.readLine().toUpperCase();
        while (!str.equals("S")) {
            if (str.equals("1")) {
                System.out.println("Busca em Largura");
                n = new BuscaLargura().busca(inicial);
            } else {
                if (str.equals("2")) {
                    System.out.println("Busca em Profundidade");
                    n = new BuscaProfundidade(20).busca(inicial);
                } else {
                    if (str.equals("3")) {
                        System.out.println("Busca em Profundidade Iterativo");
                        n = new BuscaIterativo().busca(inicial);
                    } else if (str.equals("4")) {
                        System.out.println("Busca Bidirecional");
                        n = new BuscaBidirecional().busca(inicial, finalu);
                    }
                }
            }
            if (str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4"))
                if (n == null) {
                    System.out.println("Sem Solução!");
                } else {
                    System.out.println("Solução:\n" + n.montaCaminho() + "\n\n");
                }
            System.out.print("Digite sua opção de busca { Digite S para finalizar }\n");
            System.out.print("\t1  -  Largura\n");
            System.out.print("\t2  -  Profundidade\n");
            System.out.print("\t3  -  Pronfundidade Iterativo\n");
            System.out.print("\t4  -  Bidirecional\n");
            System.out.print("Opção: ");
            str = teclado.readLine().toUpperCase();
        }
    }

    /**
     * Verifica se um estado é igual a outro já inserido na lista de sucessores (usado para poda)
     */
    public boolean equals(Object o) {
        try {
            MissionarioCanibal e = (MissionarioCanibal) o;
            return Me == e.Me && Ce == e.Ce && Md == e.Md && Cd == e.Cd && barco == e.barco;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retorna o hashCode desse estado (usado para poda, conjunto de fechados)
     */
    public int hashCode() {
        return (Me + "," + Ce + "," + Md + "," + Cd + barco).hashCode();
    }

}
