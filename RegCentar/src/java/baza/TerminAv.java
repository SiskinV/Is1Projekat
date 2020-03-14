/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

/**
 *
 * @author Vladimir Siskin
 */
public class TerminAv {
    
    String poruka;
    boolean dostupnost;

    public TerminAv(){}
    
    public TerminAv(String poruka, boolean dostupnost) {
        this.poruka = poruka;
        this.dostupnost = dostupnost;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public boolean isDostupnost() {
        return dostupnost;
    }

    public void setDostupnost(boolean dostupnost) {
        this.dostupnost = dostupnost;
    }
    
    
}
