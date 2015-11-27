package models.Ordenacao;

import models.Dica;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pablovfds on 25/11/15.
 */
public class OrdenaPorData implements OrdenaDicas {
    @Override
    public int ordenaListaDicas(Dica dica1, Dica dica2) {
        return dica1.getDataDeCriacao().compareTo(dica2.getDataDeCriacao());
    }
}
