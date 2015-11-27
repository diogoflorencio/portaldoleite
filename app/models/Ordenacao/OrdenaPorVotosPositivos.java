package models.Ordenacao;

import models.Dica;

import java.util.Collections;
import java.util.List;

/**
 * Created by pablovfds on 25/11/15.
 */
public class OrdenaPorVotosPositivos implements OrdenaDicas {
    @Override
    public int ordenaListaDicas(Dica o1, Dica o2) {
        if (o1.getConcordancias() > o2.getConcordancias()){
            return -1;
        }else if (o1.getConcordancias() < o2.getConcordancias()){
            return 1;
        }
        return 0;
    }
}
