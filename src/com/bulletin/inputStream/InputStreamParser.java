package com.bulletin.inputStream;

import com.bulletin.entity.Classe;
import com.bulletin.entity.Matiere;
import com.bulletin.exception.EleveNotFoundException;
import com.bulletin.exception.MatiereNotFoundException;

import java.util.List;

/**
 * Created by geoffroy on 13/11/14.
 */
public interface InputStreamParser {


    /**
     * Parse the stream to get a class
     * @return The classe
     */
    public Classe getClasse();

    /**
     * Parse the list of matiere
     * @return The list of matiere
     * @throws MatiereNotFoundException
     */
    public List<Matiere> getMatieres() throws MatiereNotFoundException;

    /**
     *
     * @param matieres  The list of matiere for which we want to get note
     * @param classe The classe
     * @return The classe whose eleves now have a note foreach matiere
     * @throws EleveNotFoundException
     */
    public Classe getAllNotes(List<Matiere> matieres, Classe classe) throws EleveNotFoundException;
}
