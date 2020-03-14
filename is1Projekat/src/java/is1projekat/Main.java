/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1projekat;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.awt.*;


/**
 *
 * @author Vladimir Siskin
 */

  

public class Main extends Frame{

    /**
     * @param args the command line arguments
     */
        
        @Resource(lookup="jms/__defaultConnectionFactory")
        static ConnectionFactory connectionFactory;
        
        @Resource(lookup="MyQueue")
        static Queue myQueue;
        
        private Panel mainPanel;
        
        public Main(){
        
            
        
        }
        
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
