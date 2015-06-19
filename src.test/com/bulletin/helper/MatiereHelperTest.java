package com.bulletin.helper;

import com.bulletin.entity.Matiere;
import com.bulletin.exception.MatiereNotFoundException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by geoffroy on 18/06/15.
 */
public class MatiereHelperTest {

    @Test
    public void testGetMatiereByName() throws MatiereNotFoundException {
        Matiere m1 = new Matiere();
        m1.setNom("m1");
        Matiere m2 = new Matiere();
        m2.setNom("m2");
        List<Matiere> matieres = Arrays.asList(m1, m2);

        assertThat(MatiereHelper.getMatiereByName("m1", matieres), is(m1));
    }

    @Test(expected = MatiereNotFoundException.class)
    public void testGetMatiereByName_matiereNotFound() throws MatiereNotFoundException {
        Matiere m1 = new Matiere();
        m1.setNom("m1");
        Matiere m2 = new Matiere();
        m2.setNom("m2");
        List<Matiere> matieres = Arrays.asList(m1, m2);

        assertThat(MatiereHelper.getMatiereByName("m3", matieres), is(m1));
    }

    @Test
    public void testGetRootMatieres() throws Exception {

    }
}