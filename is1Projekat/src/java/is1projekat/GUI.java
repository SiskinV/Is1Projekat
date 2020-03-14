/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1projekat;

import baza.Documentrequest;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.json.Json;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

/**
 *
 * @author Vladimir Siskin
 */

  

public class GUI extends JFrame implements ActionListener,Serializable{

    /**
     * @param args the command line arguments
     */
        
        @Resource(lookup="jms/__defaultConnectionFactory")
        static ConnectionFactory connectionFactory;
        
        @Resource(lookup="MyQueue")
        static Queue myQueue;
        
        private JPanel prviP;
        private JPanel drugiP;
        private JPanel treciP;
        private JPanel cetvrtiP;
        private JButton kreirajZ,proveriS,uruciDok,osveziS;
        private Label unosID,stanje,ime,prezime,jmbg,imeO,prezimeO,imeM,prezimeM,nac,prof,opstina,ulica,broj,pol,datum,brak;
        private TextField TunosID,Tstanje,Time,Tprezime,Tjmbg,TimeO,TprezimeO,TimeM,TprezimeM,Tnac,Tprof,Topstina,Tulica,Tbroj,Tpol,Tdatum;
        private Choice Cbrak;
       // private static long mojCentar=17368;
        private static int i=0;
        
        private EntityManagerFactory emf;
        private EntityManager em;
        private JMSContext context = connectionFactory.createContext();
        private JMSProducer producer = context.createProducer();
        private static String provera="";
        
        
    public GUI(){
        
            super("Formular");
            
            //ovaj entity manager je potreban za ubacivanje update i sve ostalo s bazom 
            
            emf=Persistence.createEntityManagerFactory("is1ProjekatPU");
            em=emf.createEntityManager();
            
            this.setBounds(250, 250,1000,800);
            this.setVisible(true);
            this.setResizable(false);
            this.setBackground(Color.WHITE);
            this.setLayout(new GridLayout(4,1));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            dodajPolja();
       
        }
        
    public void dodajPolja(){
        
       
        int x=25;
        int y=35;
        int razmak=10;
        int r=razmak;
        
        prviP=new JPanel();
        prviP.setLayout(null);
        prviP.setBackground(Color.ORANGE);
         
        ime = L("Ime:",x,x+8);
        Time = T("unesi ime",x+55,x);
        prezime = L("Prezime:",x,x+y+8+r);
        Tprezime = T("unesi prezime",x+55,x+y+r);
        jmbg = L("Jmbg:",x,x+2*y+8+2*r);
        Tjmbg=T("unesi jmbg",x+55,x+2*y+2*r);
        pol = L("Pol:",x+500,x+8);
        Tpol = T("unesite pol",x+500+55,x);
        datum = L("Datum:",x+500,x+y+8+r);
        Tdatum = T("datum",x+500+55,x+y+r);
        
        
        prviP.add(prezime);
        prviP.add(Tprezime);
        prviP.add(ime);
        prviP.add(Time);
        prviP.add(jmbg);
        prviP.add(Tjmbg);
        prviP.add(pol);
        prviP.add(Tpol);
        prviP.add(datum);
        prviP.add(Tdatum);
        
        this.add(prviP);
        
        drugiP=new JPanel();
        drugiP.setLayout(null);
        drugiP.setBackground(Color.green);
        
        imeM = L("MajkaI:",x,x+8);
        TimeM = T("unesi ime",x+50,x);
        prezimeM = L("MajkaP:",x,x+y+8+r);
        TprezimeM = T("unesi prezime",x+50,x+y+r);
        nac=L("nac: ",x,x+2*y+8+2*r);
        Tnac=T("unesi nac",x+50,x+2*y+2*r);
        
        imeO = L("OtacI:",x+500,x+8);
        TimeO = T("unesi ime",x+500+50,x);
        prezimeO = L("OtacP:",x+500,x+y+8+r);
        TprezimeO = T("unesi prezime",x+500+50,x+y+r);
        prof=L("prof: ",x+500,x+2*y+8+2*r);
        Tprof=T("unesi profesiju",x+500+50,x+2*y+2*r);
        
        
        //drugiP.add(imeO);
        //drugiP.add(prezimeO);
        
        drugiP.add(imeM);
        drugiP.add(prezimeM);
        drugiP.add(TimeM);
        drugiP.add(TprezimeM);
        drugiP.add(nac);
        drugiP.add(Tnac);
        
        drugiP.add(imeO);
        drugiP.add(prezimeO);
        drugiP.add(TimeO);
        drugiP.add(TprezimeO);
        drugiP.add(prof);
        drugiP.add(Tprof);
        
        this.add(drugiP);
        
        treciP=new JPanel();
        treciP.setLayout(null);
        treciP.setBackground(Color.RED);
        
        opstina = L("Opstina:",x,x+8);
        Topstina = T("opstina",x+50,x);
        ulica = L("Ulica:",x,x+y+8+r);
        Tulica = T("ulica",x+50,x+y+r);
        broj=L("Broj: ",x,x+2*y+8+2*r);
        Tbroj=T("broj",x+50,x+2*y+2*r);
        
        brak = L("brak:",x+500,x+4);
        Cbrak = new Choice();
        String status[]={"neozenjen/neudata","ozenjen/udata","razveden/a","udovac/udovica"};
        Cbrak.add("neozenje/neudata");
        Cbrak.add("ozenje/udata");
        Cbrak.add("razveden/a");
        Cbrak.add("udovac/udovica");
        
        Cbrak.setBounds(x+50+500, x, 200, 35);
        
        treciP.add(opstina);
        treciP.add(Topstina);
        treciP.add(ulica);
        treciP.add(Tulica);
        treciP.add(broj);
        treciP.add(Tbroj);
        treciP.add(brak);
        treciP.add(Cbrak);
        
        this.add(treciP);
        
        
        
        cetvrtiP=new JPanel(new GridLayout(2,1));
   
        cetvrtiP.setBackground(Color.BLUE);
        JPanel c1=new JPanel();
        JPanel c2=new JPanel();
        c1.setLayout(null);
        c2.setLayout(null);
        c1.setBackground(Color.YELLOW);
        c2.setBackground(Color.PINK);
        
        
        kreirajZ = B("Kreiraj zahtev",75,25);
        uruciDok = B("Uruci dokument",380,25);
        osveziS = B("Osvezi status",700,25);
//        uruciDok.setEnabled(false);
//        osveziS.setEnabled(false);
        
        c1.add(kreirajZ);
        c1.add(uruciDok);
        c1.add(osveziS);
        
        
       proveriS = B("Proveri stanje",380,25);
       unosID = L("ID:",25,33);
       TunosID=T("unesi ID",75,25);
       stanje = L("Stanje",650,33);
       Tstanje= T ("trenutnoStanje",700,25);
       
        c2.add(unosID);
        c2.add(TunosID);
        c2.add(proveriS);
        
        c2.add(stanje);
        c2.add(Tstanje);
        
        cetvrtiP.add(c1);
        cetvrtiP.add(c2);
      
        this.add(cetvrtiP);
        
    }
        
    
    private JButton B(String ime,int x,int y){
        JButton b=new JButton(ime);
        b.setBounds(x,y,200,35);
        b.addActionListener(this);
        b.setVisible(true);
        return b;
    }
    private TextField T(String ime,int x,int y){
        TextField novi=new TextField(ime);
        novi.setBounds(x, y, 200, 35);
        return novi;
    }
    private Label L(String ime,int x,int y){
        Label nova=new Label(ime);
        nova.setBounds(x,y,50,15);
    
        return nova;
    }
    
    private void addToDatabase(){
    
        //pravim objekat i izvlacim sve iz mojih polja da bi poslao u moju bazu
       
        Documentrequest req=new Documentrequest();
                
                req.setBracnoStanje(Cbrak.getSelectedItem());
                req.setIme(Time.getText());
                req.setPrezime(Tprezime.getText());
                req.setPol(Tpol.getText());
                req.setJmbg(Tjmbg.getText());
                req.setDatumRodjenja(Tdatum.getText());
                req.setImeMajke(TimeM.getText());
                req.setPrezimeMajke(TprezimeM.getText());
                req.setImeOca(TimeO.getText());
                req.setPrezimeOca(TprezimeO.getText());
                req.setNacionalnost(Tnac.getText());
                req.setProfesija(Tprof.getText());
                req.setOpstinaPrebivalista(Topstina.getText());
                req.setUlicaPrebivalista(Tulica.getText());
                req.setBrojPrebivalista(Tbroj.getText());
                
                
                //ubacujem u bazu i postavljam mu id
         try {

              /*  em.getTransaction().begin();
                //Upit koji mi izvlaci listu ovih id
                
                TypedQuery<Long> query = em.createQuery(
                "SELECT dr.id FROM Documentrequest dr ORDER BY dr.id DESC", Long.class); 
                List<Long> poslednji=query.getResultList();
                
                if (poslednji.isEmpty()){
                mojCentar*=1000000;
                req.setId(mojCentar);
                }else{
                   
                    long novi=poslednji.get(0)+1;
                    req.setId(novi);
                }
                req.setStatus("Kreiran");
                em.persist(req);
                em.flush();
                em.getTransaction().commit();*/
   
                //Pravim poruku ovog requesta koji sam napiravio i saljem je na queue
                ObjectMessage msg = context.createObjectMessage(req);
                producer.send(myQueue, msg);
                System.out.println("Poslata je poruka ");
                
            }
        finally {
                if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String command=e.getActionCommand();
        if(command.equals("Kreiraj zahtev")){
            addToDatabase();
        }
        if(command.equals("Proveri stanje")){
            provera = TunosID.getText();
            System.out.println(provera);
            System.out.println("Radi uzimanje iz IDa");
            try {
                proveriStanje(provera);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if(command.equals("Uruci dokument")){
            provera = TunosID.getText();
            System.out.println(provera);
            
            try {
                uruciDokument(provera);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private  void proveriStanje(String id) throws MalformedURLException, IOException{
        HttpURLConnection huc;
        URL url=new URL("http://collabnet.netset.rs:8081/is/persoCentar/"+id);
        huc=(HttpURLConnection)url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        
        int povratnaVr=200;
        povratnaVr=huc.getResponseCode();
        if(povratnaVr==200)
        {
            System.out.println("200");
            BufferedReader input=new BufferedReader(new InputStreamReader(huc.getInputStream()));
          // System.out.println(input.toString());
          
           // baza.Documentrequest req = new ObjectMapper().readValue(input,baza.Documentrequest.class);
           
           ObjectMapper objMap=new ObjectMapper();
           objMap.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           baza.Documentrequest req=objMap.readValue(input, baza.Documentrequest.class);
           System.out.println(req.getStatus()+" "+req.getId());
            
           
           if(req.getStatus().equals("proizveden")){
           em.getTransaction().begin();
           em.createQuery("UPDATE Documentrequest r SET r.status=:status Where r.id=:id").setParameter("id",req.getId()).setParameter("status",req.getStatus()).executeUpdate();
           em.flush();
           em.getTransaction().commit();
           
           huc.disconnect();
            }else{
               System.out.println("Nije proziveden");
           }
        }else{
            System.out.println(povratnaVr);
            huc.disconnect();
        }
    }

    private void uruciDokument(String id) throws IOException{
        HttpURLConnection huc;
        URL url=new URL("http://collabnet.netset.rs:8081/is/persoCentar/"+id);
        huc=(HttpURLConnection)url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        
        int povratnaVr=200;
        povratnaVr=huc.getResponseCode();
        if(povratnaVr==200)
        {
            System.out.println("200");
            BufferedReader input=new BufferedReader(new InputStreamReader(huc.getInputStream()));
          // System.out.println(input.toString());
          
           // baza.Documentrequest req = new ObjectMapper().readValue(input,baza.Documentrequest.class);
           
           ObjectMapper objMap=new ObjectMapper();
           objMap.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           baza.Documentrequest req=objMap.readValue(input, baza.Documentrequest.class);
           System.out.println(req.getStatus()+" "+req.getId());
            
           
           
           em.getTransaction().begin();
           em.createQuery("UPDATE Documentrequest r SET r.status='urucen' Where r.id=:id").setParameter("id",req.getId()).executeUpdate();
           em.flush();
           em.getTransaction().commit();
           
           huc.disconnect();
            
        }else{
            System.out.println(povratnaVr);
            huc.disconnect();
        }
    }
}