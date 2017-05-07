/*
 * ----------------------------------------------------------------------------
 *     (c) by data experts gmbh
 *            Postfach 1130
 *            Woldegker Str. 12
 *            17001 Neubrandenburg
 * ----------------------------------------------------------------------------
 *     Dieses Dokument und die hierin enthaltenen Informationen unterliegen
 *     dem Urheberrecht und duerfen ohne die schriftliche Genehmigung des
 *     Herausgebers weder als ganzes noch in Teilen dupliziert, reproduziert
 *     oder manipuliert werden.
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 */
package de.sbicher.duv.duv_proxy.rest;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import de.sbicher.duv.duv_proxy.model.RunnerSearchResult;

public class SearchProxyTestCase {

    @Test
    public void testParseRunnerSearchResults() throws Exception {
        try (InputStream duvResponse = SearchProxyTestCase.class.getResourceAsStream("/testSearchRunnerDuvResponse.html")) {
            List<RunnerSearchResult> results = SearchProxy.parseRunnerSearchResults(duvResponse);
            assertEquals(5, results.size());

            RunnerSearchResult result = results.get(0);
            assertEquals(40840, result.getId());
            assertEquals("Stefan", result.getFirstname());
            assertEquals("Bicher", result.getLastname());
            assertEquals("Berlin", result.getCity());
            assertEquals("GER", result.getNationality());
            assertEquals(Character.valueOf('M'), result.getGender());
            assertEquals(new Integer(1982), result.getYearOfBirth());

            result = results.get(3);
            assertEquals(878213, result.getId());
            assertEquals("Yoann", result.getFirstname());
            assertEquals("Bicherel", result.getLastname());
            assertEquals("Auzebosc", result.getCity());
            assertEquals("FRA", result.getNationality());
            assertEquals(Character.valueOf('M'), result.getGender());
            assertEquals(new Integer(1985), result.getYearOfBirth());
        }
    }
}
