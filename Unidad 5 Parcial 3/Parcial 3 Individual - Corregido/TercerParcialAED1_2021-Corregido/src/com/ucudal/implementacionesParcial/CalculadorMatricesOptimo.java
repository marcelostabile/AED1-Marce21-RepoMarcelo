package com.ucudal.implementacionesParcial;

import com.ucudal.tdas.*;

public class CalculadorMatricesOptimo implements ICalculadorMatricesOptimo {

    int[][] W;
    int[][] P;
    int[][] R;

    @Override
    public void encontrarOptimo(int cantElem, int[] FrecExito, int[] FrecNoExito) {
        int i, j, k, kraiz, h;
        int min, PesoSubArboles;


        //wii = bii y pii = wii
        for (i = 0; i < cantElem + 1; i++) {
            W[i][i] = FrecNoExito[i];
            P[i][i] = W[i][i]; // h = 0
        }

        // wi,j = wi,j-1+ aj + bj
        for (i = 0; i < cantElem; i++) {
            for (j = i + 1; j < cantElem + 1; j++) {
                W[i][j] = W[i][j - 1] + FrecExito[j] + FrecNoExito[j];
            }
        }
        // h = 1 pij = wij + pii + pjj

        for (i = 0; i < cantElem; i++) {
            j = i + 1;
            P[i][j] = W[i][j] + P[i][i] + P[j][j];
            R[i][j] = j;
        }

        // h = 2 hasta h = n
        kraiz = 0;
        for (h = 2; h < cantElem + 1; h++) {

            for (i = 0; i < cantElem - h + 1; i++) {
                j = i + h;
                min = Integer.MAX_VALUE;

                for (k = i + 1; k < j + 1; k++) {
                    PesoSubArboles = P[i][k - 1] + P[k][j];
                    if (PesoSubArboles <= min) {
                        min = PesoSubArboles;
                        kraiz = k;
                    }
                }

                P[i][j] = min + W[i][j];
                R[i][j] = kraiz;
            }
        }
    }

    @Override
    public void armarArbolBinario(int i, int j, IElementoAB[] elementos, IArbolBB elArbolBB) {

        IElementoAB<Comparable> unNodo;
        int unaraiz;
        if (i < j) {
            unaraiz = R[i][j];
            elArbolBB.insertar(elementos[unaraiz]);
            armarArbolBinario(i, unaraiz - 1, elementos,elArbolBB);
            armarArbolBinario(unaraiz, j, elementos, elArbolBB);
        }
    }

    public CalculadorMatricesOptimo(int cantElem) {
        crearMatrices(cantElem);
    }

    private void crearMatrices(int cantElem) {
        W = new int[cantElem+1][cantElem+1];
        P = new int[cantElem+1][cantElem+1];
        R = new int[cantElem+1][cantElem+1];
    }

}
