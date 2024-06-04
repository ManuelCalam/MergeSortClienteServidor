/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mergesort;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import mergeChat.chatServidor;

/**
 *
 * @author DELL
 */
public class mainWindow extends JFrame{
    JTextField arrayLengthInput, timerSecuential, timerForkJoin, timerExecutor;
    public static JTextArea unfixedArray, fixedArray;
    JPanel panelInputsOutputs, panelButtons, panelResults;
    JButton secuentialBtn, forkJoinBtn, executorBtn, limpiarBtn;
    JLabel numerosTxt;
    
    int arrayLength;
    double startTime, endTime, duration;
    public double times[] = {0,0,0};
    public double promedio;
    
    chatServidor servidor; // Referencia al servidor

    public mainWindow(chatServidor servidor) {
        this.servidor = servidor;
        //drawWindow();
    }
    
    public void drawWindow(){
        setTitle("Menu");
        setSize(1200,650);
        setLayout(new BorderLayout());
        setLocationRelativeTo(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panelInputsOutputs = new JPanel();
        panelResults = new JPanel();
        panelButtons = new JPanel();
        
        unfixedArray = new JTextArea();
        fixedArray = new JTextArea();
        arrayLengthInput = new JTextField(9);
        timerSecuential = new JTextField(8);
        timerForkJoin = new JTextField(8);
        timerExecutor = new JTextField(8);
        secuentialBtn = new JButton("Secuencial");
        forkJoinBtn = new JButton(" Fork/Join ");
        executorBtn = new JButton("Executor");
        limpiarBtn = new JButton("Limpiar");
        numerosTxt = new JLabel("Numeros");
        
        //unfixedArray.setPreferredSize(new Dimension(450, 530));
        //unfixedArray.setEnabled(false);
        //fixedArray.setPreferredSize(new Dimension(450, 380));
        //fixedArray.setEnabled(false);
        
        JScrollPane unfixedScrollPane = new JScrollPane(unfixedArray);      
        JScrollPane fixedScrollPane = new JScrollPane(fixedArray);
        
        unfixedScrollPane.setPreferredSize(new Dimension(450, 530));
        fixedScrollPane.setPreferredSize(new Dimension(450, 530));
                
    
        
        
        unfixedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        unfixedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desactiva la barra de desplazamiento horizontal

        fixedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fixedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desactiva la barra de desplazamiento horizontal
        

        panelInputsOutputs.add(unfixedScrollPane);
        panelInputsOutputs.add(fixedScrollPane);
        panelInputsOutputs.add(arrayLengthInput);
        
        
        panelResults.add(timerSecuential);
        panelResults.add(timerForkJoin);
        panelResults.add(timerExecutor);

        panelButtons.add(secuentialBtn);
        panelButtons.add(forkJoinBtn);
        panelButtons.add(executorBtn);
        limpiarBtn.setBounds(1000, 200, 100, 25);
        numerosTxt.setBounds(1015, 280, 100, 25);
        
        
        add(limpiarBtn);
        add(numerosTxt);
        add(panelInputsOutputs, BorderLayout.NORTH);
        add(panelResults, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);


        
        setVisible(true);
        
        limpiarBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                unfixedArray.setText("");
                fixedArray.setText("");
                timerSecuential.setText("");
                timerForkJoin.setText("");
                timerExecutor.setText("");
            }
        });
            
        secuentialBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                unfixedArray.setText("");
                fixedArray.setText("");
                
                
                arrayLength = Integer.parseInt(arrayLengthInput.getText());
                int[] randomArray = createArray(arrayLength);
                
                Secuencial secuential = new Secuencial();
                
                try {
                    servidor.enviarCantidad(arrayLength);
                    servidor.enviarArray(randomArray, false, "Secuencial");
                } catch (RemoteException ex) {
                    Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0; i < 3; i++) {
                    startTime = System.nanoTime();
                    secuential.sort(randomArray, arrayLength);                   
                    
                    endTime = System.nanoTime(); 
                    duration = (endTime - startTime) / 1000000; 
                    
                    times[i] = duration;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        
                    }
                }
                
                
                
                /*for (int i = 0; i < 3; i++) {
                    System.out.println(times[i] + ", ");
                }*/
                
                setArrayOnTxt(randomArray, fixedArray);
                
                try {
                    servidor.enviarTiempo(duration, "Secuencial");
                    servidor.enviarArray(randomArray, true, "Secuencial");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                
                timerSecuential.setText(Double.toString(duration) + " ms");
            }
        });
        
        
        forkJoinBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                unfixedArray.setText("");
                fixedArray.setText("");
                
                arrayLength = Integer.parseInt(arrayLengthInput.getText());
                int[] randomArray = createArray(arrayLength);
                
                ForkJoin ForkJoin = new ForkJoin(randomArray, 0, arrayLength);
                
                try {
                    servidor.enviarCantidad(arrayLength);
                    servidor.enviarArray(randomArray, false, "ForkJoin");
                } catch (RemoteException ex) {
                    Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0; i < 3; i++) {
                    startTime = System.nanoTime();
                    ForkJoin.compute();
                    endTime = System.nanoTime(); 
                    duration = (endTime - startTime) / 1000000; 
                    times[i] = duration;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                       
                    }
                }
                
                /*for (int i = 0; i < 3; i++) {
                    System.out.println(times[i] + ", ");
                }*/
                
                
                setArrayOnTxt(randomArray, fixedArray);
                
               
                try {
                    servidor.enviarTiempo(duration, "ForkJoin");
                    servidor.enviarArray(randomArray, true, "ForkJoin");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                
                timerForkJoin   .setText(Double.toString(duration) + " ms");

            }
        });
        
        
        executorBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                unfixedArray.setText("");
                fixedArray.setText("");
                
                arrayLength = Integer.parseInt(arrayLengthInput.getText());
                int[] randomArray = createArray(arrayLength);
                
                Executor Executor = new Executor();
                
                try {
                    servidor.enviarCantidad(arrayLength);
                    servidor.enviarArray(randomArray, false, "Executor");
                } catch (RemoteException ex) {
                    Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0; i < 3; i++) {
                    startTime = System.nanoTime();
                    Executor.sort(randomArray, 0);
                    endTime = System.nanoTime(); 
                    duration = (endTime - startTime) / 1000000; 
                    times[i] = duration;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        
                    }
                }
                
                /*for (int i = 0; i < 3; i++) {
                    System.out.println(times[i]);
                }*/
                
                

                
                setArrayOnTxt(randomArray, fixedArray);
                
                try {
                    servidor.enviarTiempo(duration, "Executor");
                    servidor.enviarArray(randomArray, true, "Executor");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                
                timerExecutor.setText(Double.toString(duration) + " ms"
                        + "");
            }
        });
    }
    
    public void actualizarTiempo(double tiempo, String metodo) {
        
        SwingUtilities.invokeLater(() -> {
            switch (metodo) {
                case "Secuencial":
                    timerSecuential.setText(Double.toString(tiempo) + " ms");
                    break;
                case "ForkJoin":
                    timerForkJoin.setText(Double.toString(tiempo) + " ms");
                    break;
                case "Executor":
                    timerExecutor.setText(Double.toString(tiempo) + " ms");
                    break;
            }
        });
    }
    
        public void actualizarCantidad(int cantidad) {
        SwingUtilities.invokeLater(() -> {
            arrayLengthInput.setText(Integer.toString(cantidad));
        });
    }

    public void actualizarArray(int[] array, boolean ordenado, String metodo) {
        SwingUtilities.invokeLater(() -> {
            JTextArea targetArea = ordenado ? fixedArray : unfixedArray;
            targetArea.setText("");
            setArrayOnTxt(array, targetArea);
        });
    }
    
    public int[] createArray(int arrayLength) {
        int[] array = new int[arrayLength];
        Random rand = new Random();

        for (int i = 0; i < arrayLength; i++) {
            array[i] = rand.nextInt(100); // Números aleatorios entre 0 y 99
        }
        
        setArrayOnTxt(array, unfixedArray);
        return array;
    }
    
    public static void setArrayOnTxt(int[] array, JTextArea showArray) {
        

        for (int i = 0; i < array.length; i++) {
            showArray.append(String.valueOf(array[i]));
            if (i != array.length - 1) {
                showArray.append(", ");
            }
            if(i%21 == 0 && i != 0 && i != 1){
                showArray.append("\n");
            }
        }

        //showArray.setText(arrayString.toString());
    }

}
