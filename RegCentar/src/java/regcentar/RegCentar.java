/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regcentar;

/**
 *
 * @author Vladimir Siskin
 */



import baza.Documentrequest;
import baza.TerminAv;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;




public class RegCentar {

    /**
     * @param args the command line arguments
     */
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
        
    @Resource(lookup="MyQueue")
    static Queue myQueue;
    
    private JMSContext context = connectionFactory.createContext();
    private JMSConsumer consumer = context.createConsumer(myQueue);
    private EntityManagerFactory emf=Persistence.createEntityManagerFactory("is1ProjekatPU");
    private EntityManager em=emf.createEntityManager();
    private static long mojCentar=17368;
    
   
   private static boolean uprodukciji=false;
    
    
    private void provera() throws JMSException, IOException, ParseException{
    
        
    
        while(true){
        
           
            try{
                
            ObjectMessage msg = (ObjectMessage)consumer.receive();
            Documentrequest req = msg.getBody(Documentrequest.class);
            
            boolean proverenTermin=false;
            
            HttpURLConnection huc;
                try {
                   URL url = new URL("http://collabnet.netset.rs:8081/is/terminCentar/checkTimeslotAvailability?regionalniCentarId=17368&termin=2020-02-24T16:45:00");
                    huc=(HttpURLConnection)url.openConnection();  
                    huc.setRequestMethod("GET");
                    System.out.println("STIGO ISPRED CONNECTA");
                    huc.connect();
                    System.out.println("POSLE CONNECTA");
                    int povratnaVr=200;
                    povratnaVr=huc.getResponseCode();
                    if(povratnaVr==200)
                    {
                        System.out.println("200");
                        BufferedReader input = new BufferedReader(new InputStreamReader(huc.getInputStream()));
                        TerminAv termin=new ObjectMapper().readValue(input,TerminAv.class);
                        System.out.println(termin.getPoruka());
                        huc.disconnect();
                        proverenTermin=termin.isDostupnost();
                    }else{
                        
                        System.out.println(povratnaVr);
                        huc.disconnect();
                        proverenTermin=false;
                    }
                  
                } catch (MalformedURLException ex) {
                    Logger.getLogger(RegCentar.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                
            
            System.out.println(req.getIme());
            //ako je proveren termin i okej je ubacuje ga u bazu
            if(proverenTermin){
            System.out.println("Pre trans");
                em.getTransaction().begin();
                //Upit koji mi izvlaci listu ovih id
            System.out.println("Posle trans");    
                TypedQuery<Long> query = em.createQuery(
                "SELECT dr.id FROM Documentrequest dr ORDER BY dr.id DESC", Long.class); 
                List<Long> poslednji=query.getResultList();
                
                System.out.println("Pre if-a");
                
                if (poslednji.isEmpty()){
                mojCentar*=10000000;
                req.setId(mojCentar);
                }else{
                   
                    long novi=poslednji.get(0)+1;
                    req.setId(novi);
                }
                System.out.println("Posle ifa");
                req.setStatus("kreiran");
                em.persist(req);
                em.flush();
                em.getTransaction().commit();
                
                sendReq(req);
             }   
            
            //Sad treba da posalje ovaj request u PersoCentar+
            //sad pravimo send request
            
            
            }finally {
                if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            }
        }
    }
    
    
    private void proveriStanje(){
        
    }
    
    private void sendReq(Documentrequest req){
        
        try {
            HttpURLConnection huc;
            URL url = new URL("http://collabnet.netset.rs:8081/is/persoCentar/submit");
            huc=(HttpURLConnection)url.openConnection();
            huc.setRequestProperty("Content-Type", "application/json");
            huc.setRequestMethod("POST");
            huc.setRequestProperty("User-Agent","Mozilla/5.0");
            huc.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            huc.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(huc.getOutputStream());
            out.write(toJson(req).toString());
            out.flush();
           // out.close();
 /*        //   out.close();
//            String ulazniStr= "{"
//                    +"\"id\":\""+Long.toString(req.getId())
//                    +"\",\"ime\":\""+req.getIme()
//                    +"\",\"prezime\":\""+req.getPrezime()
//                    +"\",\"imeMajke\":\""+req.getImeMajke()
//                    +"\",\"imeOca\":\""+req.getImeOca()
//                    +"\",\"prezimeMajke\":\""+req.getPrezimeMajke()
//                    +"\",\"prezimeOca\":\""+req.getPrezimeOca()
//                    +"\",\"pol\":\""+req.getPol()
//                     +"\",\"datumRodjenja\":\""+req.getDatumRodjenja()
//                    +"\",\"nacionalnost\":\""+req.getNacionalnost()
//                    +"\",\"profesija\":\""+req.getProfesija()
//                    +"\",\"bracnoStanje\":\""+req.getBracnoStanje()
//                    +"\",\"opstinaPrebivalista\":\""+req.getOpstinaPrebivalista()
//                    +"\",\"ulicaPrebivalista\":\""+req.getUlicaPrebivalista()
//                    +"\",\"brojPrebivalista\":\""+req.getBrojPrebivalista()
//                    +"\",\"JMBG\":\""+req.getJmbg()
//                    +"\"}";
//                    try(OutputStream os = huc.getOutputStream()) {
//                    byte[] output = ulazniStr.getBytes("utf-8");
//                    os.write(output, 0, output.length);           
//}       
            
 //           System.out.println(ulazniStr);
 //           huc.connect();*/
            int povratnaVr;
            povratnaVr=huc.getResponseCode();
            if(povratnaVr==200){
                
                uprodukciji=true;
                System.out.println("200");
                BufferedReader input = new BufferedReader(new InputStreamReader(huc.getInputStream()));
                 out.close();
                 input.close();
                
                em.getTransaction().begin();
                em.createQuery("UPDATE Documentrequest r SET r.status='uProdukciji' Where r.id=:id").setParameter("id", req.getId()).executeUpdate();
                em.flush();
                em.getTransaction().commit();
                
            }else{
                System.out.println(povratnaVr);
            }
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegCentar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegCentar.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private static JSONObject toJson(Documentrequest req){
        JSONObject jb = new JSONObject();
        jb.put("id",req.getId());
        jb.put("JMBG",req.getJmbg());
        jb.put("ime",req.getIme());
        jb.put("prezime",req.getPrezime());
        jb.put("imeMajke",req.getImeMajke());
        jb.put("imeOca",req.getImeOca());
        jb.put("prezimeMajke",req.getPrezimeMajke());
        jb.put("prezimeOca",req.getPrezimeOca());
        jb.put("pol",req.getPol());
        jb.put("datumRodjenja",req.getDatumRodjenja());
        jb.put("nacionalnost",req.getNacionalnost());
        jb.put("profesija",req.getProfesija());
        jb.put("bracnoStanje",req.getBracnoStanje());
        jb.put("opstinaPrebivalista",req.getOpstinaPrebivalista());
        jb.put("ulicaPrebivalista",req.getUlicaPrebivalista());
        jb.put("brojPrebivalista",req.getBrojPrebivalista());
        jb.put("status",req.getStatus());
        return jb;
    }
    
    public static void main(String[] args) throws JMSException, IOException, ParseException {
        // TODO code application logic here
         RegCentar reg=new RegCentar();
         reg.provera();
    }
    
}
