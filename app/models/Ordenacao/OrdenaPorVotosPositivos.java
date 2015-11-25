package models.Ordenacao;

import models.Dica;

import java.util.Collections;
import java.util.List;

/**
 * Created by pablovfds on 25/11/15.
 */
public class OrdenaPorVotosPositivos implements OrdenaDicas {
    @Override
    public List<Dica> ordenaListaDicas(List<Dica> dicaList) {
        Collections.sort(dicaList);
        return dicaList;
    }
}
