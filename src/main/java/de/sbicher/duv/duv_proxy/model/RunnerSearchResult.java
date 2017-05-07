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
package de.sbicher.duv.duv_proxy.model;

import com.google.common.base.MoreObjects;

/**
 * Such-Ergebnis für einen einzelnen Läufer
 */
public class RunnerSearchResult {

    /**
     * ID des Läufers in der DUV-Statistik
     */
    private int id;

    /**
     * Vorname des Läufers
     */
    private String firstname;

    /**
     * Nachname des Läufers
     */
    private String lastname;

    /**
     * Geburtsjahr des Läufers
     */
    private Integer yearOfBirth;

    /**
     * Vereinsname
     */
    private String teamname;

    /**
     * Wohnort
     */
    private String city;

    /**
     * Geschlecht des Läufers ('W' or 'M')
     */
    private Character gender;

    /**
     * Nationalität (z.B. "GER")
     */
    private String nationality;

    /**
     * Liefert die ID des Läufers in der DUV-Statistik
     *
     * @return ID des Läufers in der DUV-Statistik
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die ID des Läufers in der DUV-Statistik
     *
     * @param id
     *            ID des Läufers in der DUV-Statistik
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Liefert den Vornamen des Läufers
     *
     * @return Vorname des Läufers
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setzt den Vornamen des Läufers
     *
     * @param firstname
     *            Vorname des Läufers
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Liefert den Nachnamen des Läufers
     *
     * @return Nachname des Läufers
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setzt den Nachnamen des Läufers
     *
     * @param lastname
     *            Nachname des Läufers
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Liefert das Geburtsjahr des Läufers
     *
     * @return Geburtsjahr des Läufers
     */
    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * Setzt das Geburtsjahr des Läufers
     *
     * @param yearOfBirth
     *            Geburtsjahr des Läufers
     */
    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * Liefert den Vereinsnamen
     *
     * @return Vereinsname
     */
    public String getTeamname() {
        return teamname;
    }

    /**
     * Setzt den Vereinsnamen
     *
     * @param teamname
     *            Vereinsname
     */
    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    /**
     * Liefert das Geschlecht des Läufers ('W' or 'M')
     *
     * @return Geschlecht des Läufers ('W' or 'M')
     */
    public Character getGender() {
        return gender;
    }

    /**
     * Setzt das Geschlecht des Läufers ('W' or 'M')
     *
     * @param gender
     *            Geschlecht des Läufers ('W' or 'M')
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * Liefert die Nationalität (z.B. "GER")
     *
     * @return Nationalität (z.B. "GER")
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Setzt die Nationalität (z.B. "GER")
     *
     * @param nationality
     *            Nationalität (z.B. "GER")
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Liefert den Wohnort
     * 
     * @return Wohnort
     */
    public String getCity() {
        return city;
    }

    /**
     * Setzt den Wohnort
     * 
     * @param city
     *            Wohnort
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("first", getFirstname()).add("last", getLastname()).add("year", getYearOfBirth()).toString();
    }
}
