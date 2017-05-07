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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;

import de.sbicher.duv.duv_proxy.model.RunnerSearchResult;

/**
 * Proxy für die Suche nach Läufern, Läufen, ....
 */
@Path("/search")
public class SearchProxy {

    /**
     * Liefert die Suchergebnisse für eine Läufersuche im kompatiblen Format
     *
     * @param searchText
     *            Text, der für die Suche eingegeben wurde
     * @throws IOException
     */
    @GET
    @Path("/runner")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RunnerSearchResult> searchRunner(@QueryParam("query") String searchText) throws IOException {
        System.out.println("Suche nach Laeufer: " + searchText);
        String duvUrl = "http://statistik.d-u-v.org/search_runner.php?sname=" + searchText + "&Submit.x=0&Submit.y=0";

        HttpHost proxy = new HttpHost("proxy.data-experts.de", 8080);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        CloseableHttpClient client = HttpClients.custom().setRoutePlanner(routePlanner).build();
        HttpGet httpGet = new HttpGet(duvUrl);
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            return parseRunnerSearchResults(response.getEntity().getContent());
        }
    }

    /**
     * Liefert die Liste der Läufer, die aus der HTTP-Abfrage von der DUV geparst wurde
     *
     * @param duvHtmlResponse
     *            "Reiner" HTTP-Response von der DUV, wie er über die Webseite zurückkommt (mit viel "Ausschmückung", s.a. demoAnswerSearchRunner.html)
     * @return Liste der
     */
    protected static List<RunnerSearchResult> parseRunnerSearchResults(InputStream duvHtmlResponse) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(duvHtmlResponse)) {
            return CharStreams.readLines(reader, new LineProcessor<List<RunnerSearchResult>>() {
                private final List<RunnerSearchResult> results = new LinkedList<>();
                private RunnerSearchResult currentResult = null;
                private int currentLineNumber = 0;

                @Override
                public boolean processLine(String line) throws IOException {
                    if (line.contains("a href='getresultperson.php?runner=")) {
                        currentResult = new RunnerSearchResult();
                        results.add(currentResult);
                        currentLineNumber = 0;
                    }

                    if (currentResult == null) {
                        return true;
                    }

                    if (currentLineNumber == 0) {
                        // ID + Läufername
                        String endOfLine = line.substring(line.indexOf("?runner=") + "?runner=".length());
                        currentResult.setId(Integer.parseInt(endOfLine.substring(0, endOfLine.indexOf("'"))));

                        String name = getValueFromLine(endOfLine);
                        if (!name.contains(",")) {
                            currentResult.setLastname(getValueFromLine(endOfLine));
                        } else {
                            List<String> lastAndFirstName = Splitter.on(',').trimResults().splitToList(name);
                            currentResult.setLastname(lastAndFirstName.get(0));
                            currentResult.setFirstname(lastAndFirstName.get(1));
                        }
                    } else if (currentLineNumber == 2) {
                        // Teamname
                        currentResult.setTeamname(getValueFromLine(line));
                    } else if (currentLineNumber == 3) {
                        // Stadt
                        currentResult.setCity(getValueFromLine(line));
                    } else if (currentLineNumber == 4) {
                        // Nationalität
                        currentResult.setNationality(getValueFromLine(line));
                    } else if (currentLineNumber == 5) {
                        // Geschlecht
                        String gender = getValueFromLine(line);
                        if (gender.length() > 0) {
                            currentResult.setGender(Character.valueOf(gender.charAt(0)));
                        }
                    } else if (currentLineNumber == 6) {
                        // Jahrgang - letzte Information
                        String year = getValueFromLine(line);
                        if (year != null && year.length() > 0) {
                            currentResult.setYearOfBirth(new Integer(year));
                        }
                        currentResult = null;
                        return true;
                    }

                    currentLineNumber++;
                    return true;
                }

                /**
                 * Liefert den Wert für den Eintrag in dieser Zeile
                 *
                 * @param line
                 *            Ganze Zeile, aus der ein Wert ermittelt werden soll (z.B.
                 *            <td class='boxed' nowrap='nowrap' align='center'>1982</td>)
                 * @return Wert aus dieser Zeile
                 */
                private String getValueFromLine(String line) {
                    int indexOfClosingBracket = line.indexOf('>');
                    String value = line.substring(indexOfClosingBracket + 1, line.indexOf('<', indexOfClosingBracket));
                    // HTML-Zeichen ersetzen
                    value = value.replace("&nbsp;", " ");
                    return value.trim();
                }

                @Override
                public List<RunnerSearchResult> getResult() {
                    return results;
                }
            });
        }
    }
}
