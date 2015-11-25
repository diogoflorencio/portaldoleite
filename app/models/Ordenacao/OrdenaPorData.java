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
    public List<Dica> ordenaListaDicas(List<Dica> dicaList) {
        Collections.sort(dicaList, new Comparator<Dica>() {
            @Override
            public int compare(Dica o1, Dica o2) {
                if (o1.getDataDeCriacao().before(o2.getDataDeCriacao())){
                    return 1;
                } else if (o1.getDataDeCriacao().after(o2.getDataDeCriacao())){
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return dicaList;
    }
}
