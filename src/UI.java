import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Anton Fluch "anfl 4215" , Inlämningsuppgift 1 PROG 2
 */
public class UI extends JFrame{
    private ArrayList<Vardesak> vardesaksArray = new ArrayList<>();

    private JLabel   vardesaker = new JLabel("Värdesaker"),
                    sortering = new JLabel("Sortering");

    private JTextArea vardesaksLista = new JTextArea();

    private ButtonGroup  sorteringsGrupp = new ButtonGroup();
    private JRadioButton namn =  new JRadioButton("Namn", true),
                        varde = new JRadioButton("Värde", false);

    private String[] vardesaksBoxforemal = {"Smycke","Aktie","Apparat"};
    private JComboBox<String> vardesaksBox = new JComboBox<>(vardesaksBoxforemal);

    private JButton  visaBtn = new JButton("Visa"),
                    borskraschBtn = new JButton("Börskrasch");

    private JPanel   visaPanel = new JPanel(),
                    radioButtonPanel = new JPanel(),
                    valPanel = new JPanel();

    private UI() {
        add(visaPanel, BorderLayout.CENTER); add(radioButtonPanel, BorderLayout.EAST);                          // Lägger till panelerna med rätt positionering i fönstret
        add(valPanel, BorderLayout.SOUTH);

        visaPanel.setLayout(new BoxLayout(visaPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(vardesaksLista);                                                   // Lägger till label och scroll-bar textfield till första panelen
        visaPanel.add(vardesaker); visaPanel.add(scroll);
        vardesaksLista.setEditable(false);                                                                      // GÖR SÅ ATT TEXTAREAN INTE GÅR ATT SKRIVA I

        radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));
        sorteringsGrupp.add(namn); sorteringsGrupp.add(varde);                                                  // Lägger till radioknapparna till en buttongroup
        radioButtonPanel.add(Box.createHorizontalGlue());
        radioButtonPanel.add(sortering); radioButtonPanel.add(namn); radioButtonPanel.add(varde);               // Lägger till labeln "Sortering" och radioknapparna till andra panelen

        valPanel.add(vardesaksBox); valPanel.add(visaBtn); valPanel.add(borskraschBtn);    // Lägger till komponenterna för den sista panelen

        vardesaksBox.addActionListener(new nyLyss());           // LÄGGER TILL LYSSNARE FÖR VÄRDESAKSBOXEN
        visaBtn.addActionListener(new visaLyss());              // LÄGGER TILL LYSSNARE FÖR VISA-KNAPPEN
        borskraschBtn.addActionListener(new borsLyss());        // LÄGGER TILL LYSSNARE FÖR BÖRSKRASCH-KNAPPEN
        namn.addActionListener(new visaLyss());                 // LÄGGER TILL LYSSNARE FÖR ATT SORTERA PÅ NAMN
        varde.addActionListener(new visaLyss());                // LÄGGER TILL LYSSNARE FÖR ATT SORTERA PÅ VÄRDE

        setTitle("Sakregister");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class nyLyss implements ActionListener {
        public void actionPerformed(ActionEvent ave){
            String val = (String)vardesaksBox.getSelectedItem();
            if(val.equals("Smycke")){
                smyckeForm();
            }else if(val.equals("Apparat")){
                apparatForm();
            }else{
                aktieForm();
            }
        }
    }

    private class visaLyss implements ActionListener{
        public void actionPerformed(ActionEvent ave){
            vardesaksLista.setText("");

            if(ave.getSource() == namn){                                        // SORTERAR LISTAN PÅ NAMN
                Collections.sort(vardesaksArray, new Comparator<Vardesak>() {
                    @Override
                    public int compare(Vardesak v1, Vardesak v2) {
                        return v1.getNamn().compareTo(v2.getNamn());
                    }
                });
            }else if(ave.getSource() == varde){                                 // SORTERAR LISTAN PÅ VÄRDE
                Collections.sort(vardesaksArray, new Comparator<Vardesak>() {
                    @Override
                    public int compare(Vardesak v1, Vardesak v2) {
                        return Double.compare(v2.getVärde(), v1.getVärde());
                    }
                });
            }
            for(Vardesak v : vardesaksArray){                                   // SKRIVER UT ARRAYEN I TEXTAREAN
                vardesaksLista.append(v.toString());
                vardesaksLista.append("\n");
            }
        }
    }

    private class borsLyss implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            for(Vardesak v : vardesaksArray){
                if(v instanceof Aktie){
                    ((Aktie) v).setKurs(0);
                }
            }
        }
    }

    private void smyckeForm(){
        JPanel form = new JPanel(); JPanel rad1 = new JPanel();
        JPanel rad2 = new JPanel(); JPanel rad3 = new JPanel();

        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JTextField namnFält = new JTextField(10);
        rad1.add(new JLabel("Namn:")); rad1.add(namnFält);

        JTextField stenFält = new JTextField(10);
        rad2.add(new JLabel("Stenar:")); rad2.add(stenFält);

        JCheckBox avGuld = new JCheckBox("Av guld");
        rad3.add(avGuld);

        form.add(rad1); form.add(rad2); form.add(rad3);

        String namn = null;
        int stenar = 0;
        try {
            int answer =  JOptionPane.showConfirmDialog(UI.this, form, "Nytt Smycke", JOptionPane.OK_CANCEL_OPTION);
                if(answer != JOptionPane.OK_OPTION){
                    return;
                }
            namn = namnFält.getText();
            stenar = Integer.parseInt(stenFält.getText());
        }catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(UI.this, "FEL ANGIVEN ENHET", "FEL", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(stenar < 0){
            JOptionPane.showMessageDialog(UI.this, "ANTAL STENAR MÅSTE VARA STÖRRE ÄN 0", "FEL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean arguld = avGuld.isSelected();
        Vardesak smycke = new Smycke(namn, stenar, arguld);
        vardesaksArray.add(smycke);
    }

    private void apparatForm(){
        JPanel form = new JPanel(); JPanel rad1 = new JPanel();
        JPanel rad2 = new JPanel(); JPanel rad3 = new JPanel();

        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JTextField namnFält = new JTextField(10);
        rad1.add(new JLabel("Namn")); rad1.add(namnFält);

        JTextField prisFält = new JTextField(10);
        rad2.add(new JLabel("Pris")); rad2.add(prisFält);

        JTextField slitageFält = new JTextField(10);
        rad3.add(new JLabel("Slitage (1-10)")); rad3.add(slitageFält);

        form.add(rad1); form.add(rad2); form.add(rad3);

        String namn = null;
        int pris = 0;
        int slitage = 0;
        try {
            int answer =  JOptionPane.showConfirmDialog(UI.this, form, "Ny apparat", JOptionPane.OK_CANCEL_OPTION);
                if(answer != JOptionPane.OK_OPTION){
                    return;
                }
            namn = namnFält.getText();
            pris = Integer.parseInt(prisFält.getText());
            slitage = Integer.parseInt(slitageFält.getText());
                if(slitage < 1 || slitage > 10){
                    JOptionPane.showMessageDialog(UI.this, "Slitage måste anges mellan 1 - 10", "Fel", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(UI.this, "FEL ANGIVEN ENHET", "FEL", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Vardesak apparat = new Apparat(namn, pris, slitage);
        vardesaksArray.add(apparat);
    }

    private void aktieForm(){
        JPanel form = new JPanel(); JPanel rad1 = new JPanel();
        JPanel rad2 = new JPanel(); JPanel rad3 = new JPanel();

        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JTextField namnFält = new JTextField(10);
        rad1.add(new JLabel("Namn:")); rad1.add(namnFält);

        JTextField antalFält = new JTextField(10);
        rad2.add(new JLabel("Antal")); rad2.add(antalFält);

        JTextField kursFält = new JTextField(10);
        rad3.add(new JLabel("Kurs")); rad3.add(kursFält);

        form.add(rad1); form.add(rad2); form.add(rad3);

        String namn = null;
        int antal = 0;
        double kurs = 0;
        try {
            int answer =  JOptionPane.showConfirmDialog(UI.this, form, "Ny aktie", JOptionPane.OK_CANCEL_OPTION);
                if(answer != JOptionPane.OK_OPTION){
                    return;
                }
            namn = namnFält.getText();
            antal = Integer.parseInt(antalFält.getText());
            kurs = Double.parseDouble(kursFält.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(UI.this, "FEL ANGIVEN ENHET", "FEL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Vardesak aktie = new Aktie(namn, antal, kurs);
        vardesaksArray.add(aktie);
    }

    public static void main(String[]args){
        UI ui = new UI();
    }
}
